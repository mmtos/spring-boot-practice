package mmtos.practice.springboot.cache.config;

import com.google.common.cache.CacheBuilder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    public final static String BUS_STOP_CACHE_NAME = "busStopCacheStore";
    @Bean
    public CacheManager cacheManager() {
        // https://www.baeldung.com/spring-cache-tutorial
        // https://self-learning-java-tutorial.blogspot.com/2019/09/spring-cache-set-expiry-time-to-cache.html
        // https://stackoverflow.com/questions/8181768/can-i-set-a-ttl-for-cacheable
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name, CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.SECONDS).build().asMap(), true);
            }
        };

        cacheManager.setCacheNames(Arrays.asList(BUS_STOP_CACHE_NAME));

        return cacheManager;
    }
}
