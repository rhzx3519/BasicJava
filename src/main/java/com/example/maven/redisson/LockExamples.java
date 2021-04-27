package com.example.maven.redisson;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @author ZhengHao Lou
 * @date 2020/05/20
 */
@Slf4j
public class LockExamples {
    private static RedissonClient client = Redisson.create();

    public static void task(int count) {
        RedissonClient client = Redisson.create();
        RLock lock = client.getLock("vtTest");

        lock.lock(10, TimeUnit.SECONDS);
        try {
            if (count <= 0) {
                return;
            }
            log.info("run...{}", count);
            task(--count);
        } finally {
            lock.unlock();
            log.info("unlock");
        }
    }
    public static void testUnlock() throws InterruptedException {
        RedissonClient client = Redisson.create();
        RLock lock = client.getLock("vtTest");
        lock.lock(10, TimeUnit.SECONDS);

        Thread t = new Thread() {
            public void run() {
                Stopwatch stopwatch = Stopwatch.createStarted();
                RLock lock1 = client.getLock("vtTest");
                log.info("create lock: {}", stopwatch);
                stopwatch.reset();
                stopwatch.start();
                lock1.lock();
                log.info("lock: {}", stopwatch);
                //if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                //}
                log.info("lock: {}", stopwatch);
            }
        };
        t.start();
        t.join();
        log.info("is locked: {}", lock.isLocked());
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
        client.shutdown();
    }

    public static void testAutoReleaseLock() throws InterruptedException {
        RLock lock = client.getLock("vtTest");
        lock.lock(5, TimeUnit.SECONDS);
        Thread.sleep(10*1000);
        log.info("is_lock: {}", lock.isLocked());
    }

    public static void main(String[] args) throws InterruptedException {
        LockExamples.testUnlock();
    }

}
