package com.example.maven.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadLocalTest {
    @Test
    public void test() throws InterruptedException {
        final int THREAD_NUM = 16;
        CountDownLatch latch = new CountDownLatch(THREAD_NUM);

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                for (int k = 0; k < 10; k++) {
                    if (Result.set((int)Thread.currentThread().getId()).getI() != (int)Thread.currentThread().getId()) {
                        log.error("error, threadId: {}, i: {}", Thread.currentThread().getId(), Result.PASS.getI());
                        break;
                    }
                }
                latch.countDown();
            }).start();
        }

        latch.await();
    }


    @Test
    public void testThreadLocal() throws InterruptedException {
        final int THREAD_NUM = 16;
        CountDownLatch latch = new CountDownLatch(THREAD_NUM);

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                for (int k = 0; k < 10; k++) {
                    if (ThreadLocalResult.set((int)Thread.currentThread().getId()).getI() != (int)Thread.currentThread().getId()) {
                        log.error("error, threadId: {}, i: {}", Thread.currentThread().getId(), ThreadLocalResult.PASS.get().getI());
                        break;
                    }
                }
                latch.countDown();
            }).start();
        }

        latch.await();
    }

}
