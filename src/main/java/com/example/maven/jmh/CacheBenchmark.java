package com.example.maven.jmh;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author ZhengHao Lou
 * @date 2020/06/13
 */
public class CacheBenchmark {


    /**
     * 测试ConcurrentHashMap的的put函数
     */
    @Benchmark
    public void testConcurrentMapPut() {
        ConcurrentMap<String, String> map = Maps.newConcurrentMap();
        map.put("k1", "v1");
    }

    /**
     * 测试LoadingCache的put函数
     */
    @Benchmark
    public void testLoadingCachePut() {
         LoadingCache<String, String> stringLoadingCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });

         stringLoadingCache.put("k1", "v1");
    }



    public static void main( String[] args ) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(CacheBenchmark.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                // 度量10轮
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .forks(3)
                .build();
        new Runner(opt).run();
    }
}
