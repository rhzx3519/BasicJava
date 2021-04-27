package com.example.maven.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import io.micrometer.core.instrument.util.NamedThreadFactory;

import java.util.concurrent.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/06/19
 */
@Slf4j
public class GuavaCacheTest {

    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .removalListener(notification -> {
                    notification.getCause();
                    notification.getKey();
                    notification.getValue();
                })
            .build(
                new CacheLoader<String, String>() {
                    public String load(String key) {
                        return createExpensiveGraph(key);
                    }
                });


    private String createExpensiveGraph(String key) {
        return "Hello, " + key;
    }

    /**
     * CacheLoader
     * 使用build传入的CacheLoader来加载,
     * 当cache调用 getAll/get/getUnchecked函数时，如果缓存命中，会返回缓存中的value，否则会调用CacheLoader的load函数加载并返回
     * 由于CacheLoader可能抛出异常，LoadingCache.get(K)也声明为抛出ExecutionException异常
     */
    @Test
    public void testCacheLoader() throws ExecutionException {
        String val = cache.get("Jim");
        Assert.assertEquals("Hello, Jim", val);
    }

    /**
     * 使用get(K, Callable<V>)
     * 这个方法返回缓存中相应的值，或者用给定的Callable运算并把结果加入到缓存中
     * @throws ExecutionException
     */
    @Test
    public void testCallable() throws ExecutionException {
        String val = getByCallable("Mike");
        Assert.assertEquals("Hello, Mike", val);
    }


    private String getByCallable(String key) throws ExecutionException {
        return cache.get(key, () -> "Hello, " + key);
    }

    /**
     * 使用cache.put(key, value)方法可以直接向缓存中插入值
     */
    @Test
    public void testPut() throws ExecutionException {
        cache.put("Bob", "Hello, Bob");
        String val = cache.get("Bob");
        Assert.assertEquals("Hello, Mike", val);
    }

    /////////

    /**
     * 如果要规定缓存项的数目不超过固定值，只需使用CacheBuilder.maximumSize(long)。缓存将尝试回收最近没有使用或总体上很少使用的缓存项
     * 警告：在缓存项的数目达到限定值之前，缓存就可能进行回收操作
     */
    @Test
    public void testSizeBasedEviction() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                // 除了maximumSize之外还可以使用自定义的weight值来设置缓存的最大容量
                .maximumWeight(20)
                .weigher((Weigher<String, String>) (key, value) -> key.length() + value.length())
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 基于时间的回收策略
     */
    @Test
    public void testTimeBasedEviction() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                // 缓存项在给定时间内没有被读/写访问，则回收。
                .expireAfterWrite(10, TimeUnit.SECONDS)
                // 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 基于软引用和弱引用的回收策略
     * 软引用：如果一个对象只具有软引用，则内存空间充足时，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。
     * 弱引用：和软引用的不同在于，当GC时，如果一个对象只有弱引用，则无论内存空间是否充足，都会被回收。
     */
    @Test
    public void testReferenceBasedEviction() {
        // 使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键
        CacheBuilder.newBuilder().weakKeys().build();

        //其 使用弱引用存储值。当值没有它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收使仅依赖恒等式（==），用弱引用值的缓存用==而不是equals比较值。
        CacheBuilder.newBuilder().weakValues().build();

        // 使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。考虑到使用软引用的性能影响，
        // 我们通常建议使用更有性能预测性的缓存大小限定（见上文，基于容量回收）。使用软引用值的缓存同样用==而不是equals比较值。
        CacheBuilder.newBuilder().softValues().build();
    }

    /**
     * 移除监听器
     * 通过CacheBuilder.removalListener(RemovalListener)，你可以声明一个监听器，以便缓存项被移除时做一些额外操作。
     * 缓存项被移除时，RemovalListener会获取移除通知[RemovalNotification]，其中包含移除原因[RemovalCause]、键和值。
     */
    @Test
    public void testRemovalListener() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .removalListener(notification -> {
                    notification.getCause();
                    notification.getKey();
                    notification.getValue();
                    log.info("({} : {}) is removed for {}", notification.getKey()
                            , notification.getValue(), notification.getCause().toString());
                })
                .build();
        cache.put("k1", "v1");
        cache.invalidate("k1");
    }

    /**
     * 缓存更新的一般用法,
     * 创建自己的维护线程，以固定的时间间隔调用
     */
    @Test
    public void testRefreshCachescheduled() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor(
                new NamedThreadFactory("Cache update"));

        exec.scheduleWithFixedDelay(() -> {
            cache.cleanUp();
            // 可以使用put显示的加载所有缓存
        }, 0, 1, TimeUnit.HOURS);
    }

    /**
     * asMap()返回缓存对应的ConcurrentMap
     */
    @Test
    public void testAsMap() {
        cache.put("k1", "v1");
        // 包含当前缓存已有的所有键值
        Assert.assertTrue(cache.asMap().containsKey("k1"));

        //asMap().get不会引起缓存项的加载
        String val = cache.asMap().get("k2");
        Assert.assertNull(val);

        // 通过cache.get直接调用可以加载缓存
        val = cache.getUnchecked("k2");
        Assert.assertEquals("Hello, k2", val);

        // Cache.asMap().get(Object)方法和Cache.asMap().put(K, V)会刷新缓存访问时间
        // 但是在集合试图上的操作，比如asMap().keySet()不会刷新缓存访问时间
    }

    /**
     * 测试refresh的同步异步更新机制
     */
    @Test
    public void testRefresh() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
                    new CacheLoader<String, String>() {

                        public String load(String key) {
                            return createExpensiveGraph(key);
                        }

                        public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
//                            return Futures.immediateFuture(oldValue + 1);
                            // asynchronous!
                            ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                                public String call() {
                                    return createExpensiveGraph(key);
                                }
                            });
                            executor.execute(task);
                            return task;
                        }
                    });
    }

    @Test
    public void testUnload() throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, String>() {
                            public String load(String key) {
                                if ("A".equals(key)) {
                                    return "AA";
                                }
                                return null;
                            }
                        });
        String val = cache.get("B");
        log.info("val: {}", val);
    }
}











