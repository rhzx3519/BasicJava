package com.example.maven.algorithm.queue.lock_free_queue;

import com.example.maven.algorithm.queue.Action;
import com.example.maven.algorithm.queue.Queue;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LockFreeQueueTest {

    private Action dummyTask = () -> {};

    @Test
    public void test1() {
        Queue que = new LockFreeQueue();
        assertThat(que.empty()).isTrue();
        que.enqueue(dummyTask);
        que.enqueue(dummyTask);
        assertThat(que.size()).isEqualTo(2);

        que.dequeue();
        assertThat(que.size()).isEqualTo(1);
        que.dequeue();
        assertThat(que.size()).isEqualTo(0);
        assertThat(que.empty()).isTrue();
    }

    @Test
    public void test2() throws InterruptedException {
        final int N = 10000;
        CountDownLatch latch = new CountDownLatch(N);

        Queue que = new LockFreeQueue();
        AtomicInteger count = new AtomicInteger(0);
        Action countTask = () -> {
            count.addAndGet(1);
        };

        for (int i = 0; i < N; i++) {
            new Thread(() -> {
                que.enqueue(countTask);
                latch.countDown();
            }).start();
        }

        latch.await();
        assertThat(que.size()).isEqualTo(N);
        assertThat(count.get()).isEqualTo(0);

        CountDownLatch delatch = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            new Thread(() -> {
                Action task = que.dequeue();
                task.action();
                delatch.countDown();
            }).start();
        }

        delatch.await();
        assertThat(que.size()).isEqualTo(0);
        assertThat(count.get()).isEqualTo(N);
    }
}
