package com.example.maven.threadpool;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/10/29
 */
@Slf4j
public class ConcurrentMapTest {

    static final ExecutorService pool = Executors.newCachedThreadPool();

    @Data
    @AllArgsConstructor
    private static class TestModel {
        private int value;
    }

    @Test
    public void test1() throws InterruptedException {
        int N = 2;
        CountDownLatch countDownLatch = new CountDownLatch(N);
        Map<String, TestModel> map = Maps.newHashMap();
        map.put("test", new TestModel(0));

        for (int i = 0; i < N; i++) {
            pool.execute(() -> {
                int n=0;
                while (n<100){
                    System.out.println("线程:"+ Thread.currentThread() + ":" + map.get("test").getValue());
                    map.get("test").setValue(map.get("test").getValue()+1);
                    n++;
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        log.info("{}", map.get("test").getValue());
    }
}
