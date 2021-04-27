package com.example.maven.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author ZhengHao Lou
 * @date 2020/9/1
 */
@Slf4j
public class ForkJoinPoolMain {

    static class Task extends RecursiveTask<Integer> {

        private int start;

        private int end;
        private int mid;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if (end - start < 6) {
                // 当任务很小时，直接进行计算
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                System.out.println(Thread.currentThread().getName() + " count sum: " + sum);
            } else {
                // 否则，将任务进行拆分
                mid = (end - start) / 2 + start;
                Task left = new Task(start, mid);
                Task right = new Task(mid + 1, end);

                // 执行上一步拆分的子任务
                left.fork();
                right.fork();

                // 拿到子任务的执行结果
                sum += left.join();
                sum += right.join();
            }

            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Task countTask = new Task(1, 100);
        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);

        System.out.println("result: " + result.get());

        forkJoinPool.shutdown();

    }

}
