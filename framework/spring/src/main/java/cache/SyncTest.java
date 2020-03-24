package cache;

import cache.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static cache.CacheConfig.CACHE_MANAGER_NAME_DEFAULT;
import static cache.CacheConfig.CACHE_NAME_BOOKS;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/3/24
 */
public class SyncTest {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(CacheConfig.class);

        CacheManager cacheManager = ctx.getBean(CacheManager.class, CACHE_MANAGER_NAME_DEFAULT);
        Cache cache = cacheManager.getCache(CACHE_NAME_BOOKS);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        executor.prestartAllCoreThreads();

        final BookService bookService = ctx.getBean(BookService.class);
        final int total = 1000;
        final CountDownLatch countDownLatch = new CountDownLatch(total);

        // 在BookService#getBookNameById()方法上 加上/移除 sync=true 选项，观察有多少个请求会进入。

        for (int i = 0; i < total; i++) {
            final int ii = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    String bookName = bookService.getBookNameById(ii % 2);
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executor.shutdown();
    }
}
