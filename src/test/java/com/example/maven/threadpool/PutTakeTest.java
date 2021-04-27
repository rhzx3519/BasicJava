package com.example.maven.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhengHao Lou
 * @date 2020/9/17
 */
@Slf4j
public class PutTakeTest {

    protected static final ExecutorService pool = Executors.newCachedThreadPool();

    //栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行
    protected CyclicBarrier barrier;

    protected final ArrayBlockingQueue<Integer> bb;

    protected final int nTrials, nPairs;

    //入列总和
    protected final AtomicInteger putSum = new AtomicInteger(0);

    //出列总和
    protected final AtomicInteger takeSum = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        new PutTakeTest(10, 10, 100000).test(); // 10个承载因子，10个线程，运行100000
        pool.shutdown();

    }

    /**
     * @param capacity 承载因子（缓存）
     * @param npairs 线程数量
     * @param ntrials 单个线程执行数量（吞吐量）
     */
    public PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new ArrayBlockingQueue<Integer>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            barrier.await(); // 等待所有的线程就绪
            barrier.await(); // 等待所有的线程执行完成
            System.out.println("result，put==take :"+(putSum.get()==takeSum.get()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    //生产者
    class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                barrier.await();
                log.info("Producer run...{}, {}", Thread.currentThread(), barrier.getNumberWaiting());
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    //消费者
    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                log.info("Consumer run...{}, {}", Thread.currentThread(), barrier.getNumberWaiting());
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }

}
