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
public class EhCacheFirstDemo {
    public static void main(String[] args) {
        // 初始化一个CacheManager实例
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        // 创建一个Cache实例。使用堆内内存，最多存储10条记录
        CacheConfigurationBuilder<Long, String> bookcConf =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10));
        cacheManager.createCache("books", bookcConf);

        // 获取一个Cache实例
        Cache<Long, String> books = cacheManager.getCache("books", Long.class, String.class);

        // 向Cache实例中填充
        books.put(1L, "啊啊啊");

        String book1 = books.get(1L);
        System.out.println(book1); // 缓存命中

        String book2 = books.get(2L);
        System.out.println(book2); // 缓存未命中

        books.remove(1L);

        book1 = books.get(1L);
        System.out.println(book1); // 缓存已经被删除

    }
}
