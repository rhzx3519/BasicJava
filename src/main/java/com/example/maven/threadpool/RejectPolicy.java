package com.example.maven.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhengHao Lou
 * @date 2020/8/14
 */
@Slf4j
public class RejectPolicy {
    static class MyThread implements Runnable {
        String name;
        public MyThread(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程:"+Thread.currentThread().getName() +" 执行:"+name +"  run");
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>(2),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 6; i++) {
            System.out.println("添加第"+i+"个任务");
            try {
                executor.execute(new MyThread("线程"+i));
            } catch (RejectedExecutionException e) {
//                log.error("ThreadPool is full. {}", "lzh",e);
                log.error("123", e);
            }

            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()){
                MyThread thread = (MyThread) iterator.next();
                System.out.println("列表："+thread.name);
            }
        }
    }
}
