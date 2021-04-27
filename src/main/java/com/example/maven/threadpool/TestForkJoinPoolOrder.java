package com.example.maven.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2021/01/25
 */
@Slf4j
public class TestForkJoinPoolOrder {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
        AtomicLong num = new AtomicLong(0);

        for (int i = 0; i < 100; i++) {
            forkJoinPool.invoke(new RecursiveAction() {
                @Override
                protected void compute() {
                    log.info("{}", num.getAndIncrement());
                }
            });
        }

    }

}
