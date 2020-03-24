package cache;

import cache.service.ServicePackageMarker;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/3/24
 */
@Configuration
@EnableCaching
@ComponentScan(basePackageClasses = ServicePackageMarker.class)
public class CacheConfig {
    public static final String CACHE_MANAGER_NAME_DEFAULT = "defaultCacheManager";

    public static final String CACHE_NAME_BOOKS = "books";

    @Bean(name = CACHE_MANAGER_NAME_DEFAULT)
    public CacheManager defaultCacheManager() {
        // 上层应用统一使用spring cache, 配置时使用jcache, 底层实现使用ehcache

        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager();

        MutableConfiguration<Long, String> booksConfiguration = new MutableConfiguration<Long, String>()
                .setTypes(Long.class, String.class)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 5)));
        // 创建一个名称为"books"的缓存
        cacheManager.createCache(CACHE_NAME_BOOKS, booksConfiguration);

        JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        jCacheCacheManager.setCacheManager(cacheManager);
        return jCacheCacheManager;
    }
}
