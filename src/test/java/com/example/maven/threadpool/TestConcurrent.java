package com.example.maven.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhengHao Lou
 * @date 2020/9/17
 */
@Slf4j
public class TestConcurrent {

    private static class Number {
        int num = 0;

        public synchronized void increase() {
            num += 1;
        }
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private CyclicBarrier cyclicBarrier;

    @Test
    public void test1() throws BrokenBarrierException, InterruptedException {
        Number number = new Number();
        final int N = 1000;
        cyclicBarrier = new CyclicBarrier(N + 1);

        for (int i = 0; i < N; i++) {
           executorService.execute(() -> {
               try {
                   number.increase();
//                   cyclicBarrier.await();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           });
        }
//        cyclicBarrier.await();
        executorService.shutdown();
        log.info("{}", number.num);
    }

    @Test
    public void test2() throws BrokenBarrierException, InterruptedException {
        cyclicBarrier = new CyclicBarrier(3);
        new Thread(() -> {
            try {
                cyclicBarrier.await();
                log.info("{}", cyclicBarrier.getNumberWaiting());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        log.info("main: {}", cyclicBarrier.getNumberWaiting());
        cyclicBarrier.await();
        executorService.shutdown();
    }
}
