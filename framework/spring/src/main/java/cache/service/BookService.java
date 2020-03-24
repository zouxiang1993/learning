package cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static cache.CacheConfig.CACHE_MANAGER_NAME_DEFAULT;
import static cache.CacheConfig.CACHE_NAME_BOOKS;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/3/24
 */
@Service
public class BookService {

    @Cacheable(cacheManager = CACHE_MANAGER_NAME_DEFAULT, cacheNames = CACHE_NAME_BOOKS, sync = true)
    public String getBookNameById(long id) {
        System.out.println("开始加载book, id=" + id);
        // 休眠几秒钟，可以看到加了sync=true时只有两个请求能进入。即锁的是key，而不是整个方法。
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StringBuilder().append(id).append(id).append(id).toString();
    }
}
