package com.example.maven.threadpool;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * @author ZhengHao Lou
 * @date 2020/10/30
 */
public class ThreadTrain implements Runnable{
    private int trainCount = 10;

    private Map<String, Long> ticketMap = Maps.newConcurrentMap();

    private static final String NAME = "ticket";

    public ThreadTrain(long count) {
        ticketMap.put(NAME, count);
    }

    @Override
    public void run() {
        while (trainCount > 0) {
            try {
                Thread.sleep(500);
                sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void sale() {
        if (ticketMap.get(NAME) > 0) {
            //--trainCount;
            ticketMap.put(NAME, ticketMap.get(NAME) - 1);
            System.out.println(Thread.currentThread().getName() + ",出售第" + (10 - ticketMap.get(NAME)) + "张票");
        }
    }

    public static void main(String[] args) {
        ThreadTrain threadTrain = new ThreadTrain(10);
        Thread t1 = new Thread(threadTrain, "1台");
        Thread t2 = new Thread(threadTrain, "2台");
        t1.start();
        t2.start();
    }

}
