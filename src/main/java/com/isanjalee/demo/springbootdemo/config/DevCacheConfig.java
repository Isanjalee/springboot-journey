package com.isanjalee.demo.springbootdemo.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // In-memory cache (no Redis needed)
        return new ConcurrentMapCacheManager("userById", "usersList", "usersPage");
    }
}
