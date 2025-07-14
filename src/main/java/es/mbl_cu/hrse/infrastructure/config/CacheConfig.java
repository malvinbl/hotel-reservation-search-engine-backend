package es.mbl_cu.hrse.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String SEARCH_CACHE = "SEARCH_CACHE";

    @Bean
    public CacheManager cacheManager() {
        var searchCache = buildCache(SEARCH_CACHE, 60, TimeUnit.MINUTES, 100);
        List<CaffeineCache> caches = new ArrayList<>();
        caches.add(searchCache);

        var manager = new SimpleCacheManager();
        manager.setCaches(caches);

        return manager;
    }

    private static CaffeineCache buildCache(String name, long ttl, TimeUnit timeUnit, long size) {
        var cache = Caffeine.newBuilder()
            .expireAfterWrite(ttl, timeUnit)
            .maximumSize(size)
            .build();

        return new CaffeineCache(name, cache);
    }

}
