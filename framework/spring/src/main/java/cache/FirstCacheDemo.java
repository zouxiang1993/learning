package cache;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ConfigurableApplicationContext;

import static cache.CacheConfig.CACHE_MANAGER_NAME_DEFAULT;
import static cache.CacheConfig.CACHE_NAME_BOOKS;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/3/24
 */
public class FirstCacheDemo {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(CacheConfig.class);

        CacheManager cacheManager = ctx.getBean(CacheManager.class, CACHE_MANAGER_NAME_DEFAULT);
        Cache cache = cacheManager.getCache(CACHE_NAME_BOOKS);
        cache.put(1L, "哈哈哈");

        String book1 = cache.get(1L, String.class);
        // 缓存命中
        System.out.println(book1);

        String book2 = cache.get(2L, String.class);
        // 缓存未命中
        System.out.println(book2);

        Thread.sleep(7000);

        book1 = cache.get(1L, String.class);
        // 缓存已经过期，未命中。
        System.out.println(book1);
    }
}
