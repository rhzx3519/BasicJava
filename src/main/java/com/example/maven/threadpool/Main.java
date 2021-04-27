package com.example.maven.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhengHao Lou
 * @date 2020/8/13
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        executorService.execute(() -> {
            throw new RuntimeException("123");
        });

        Thread.currentThread().join();
        Thread.sleep(10*1000);
    }
}

