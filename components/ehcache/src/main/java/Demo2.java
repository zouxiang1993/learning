import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/3/24
 */
public class Demo2 {
    public static void main(String[] args) {
        // 初始化一个CacheManager实例
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        // 创建一个Cache实例。使用堆内内存，最多存储10条记录
        CacheConfigurationBuilder<Long, String> bookcConf =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(2));
        cacheManager.createCache("books", bookcConf);

        // 获取一个Cache实例
        Cache<Long, String> books = cacheManager.getCache("books", Long.class, String.class);

        books.put(1L, "111");
        books.put(2L, "222");
        books.get(1L);
        books.get(2L);

        books.put(3L, "333");

        System.out.println("1?" + books.containsKey(1L));
        System.out.println("2?" + books.containsKey(2L));
        System.out.println("3?" + books.containsKey(3L));
    }
}
