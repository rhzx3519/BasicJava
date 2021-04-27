package com.example.maven.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ZhengHao Lou
 * @date 2020/06/02
 */
@Slf4j
public class LearnCache {

    private LoadingCache<String, String> stringLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    //return get(s);
                    return null;
                }
            });

    private String get(String key) {
        log.info("call get {}", key);
        return key;
    }

    public String getFromCache(String key) {
        return stringLoadingCache.getUnchecked(key);
    }

    @Data
    public static class RefreshCache {

        private LoadingCache<String, String> stringLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .refreshAfterWrite(10, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        if ("k1".equals(s)) {
                            return "k1";
                        }
                        return "";
                        //return get(s);
                    }
                });

        private String get(String key) {
            log.info("call get {}", key);
            return key;
        }
    }

    public static void main(String[] args) throws ExecutionException {
        RefreshCache refreshCache = new RefreshCache();
        String v = refreshCache.get("k1");
        log.info(v);
        refreshCache.getStringLoadingCache().refresh("k2");
        log.info(refreshCache.getStringLoadingCache().getUnchecked("k2"));

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return s;
                    }
                });

        cache.put("k1", "v1");
        cache.put("k2", "v2");
        log.info("{}", cache.asMap());
        cache.invalidate("k1");
        cache.getUnchecked("k1");
        log.info("{}", cache.asMap());
        log.info("{}", cache.getAll(Arrays.asList("k1", "k2")));

        Cache<String, Boolean> quoteMessageLogTimer = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS).build();
        quoteMessageLogTimer.put("key", false);
        log.info("{}", quoteMessageLogTimer.asMap());
        LockSupport.parkNanos(Duration.ofSeconds(1).toNanos());
        log.info("{}", quoteMessageLogTimer.size());
        log.info("{}", quoteMessageLogTimer.asMap());
    }


}
