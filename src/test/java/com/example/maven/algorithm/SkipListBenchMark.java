package com.example.maven.algorithm;

import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author ZhengHao Lou
 * @date 2020/9/11
 */
@Slf4j
@Fork(2)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SkipListBenchMark {
    private static final int TOTAL_NUM = 1000000;
    private int target;
    SkipList<Integer> skipList;
    PriorityQueue<Integer> queue;

    @Setup(Level.Invocation)
    public void setup() {
        skipList = SkipList.newSkipList(16, Integer::compare);
        queue = Queues.newPriorityQueue();

        Random rand = new Random();
        for (int i = 0; i < TOTAL_NUM; i++) {
            int num = rand.nextInt(TOTAL_NUM);
            skipList.add(num);
            queue.add(num);
        }
        target = rand.nextInt(TOTAL_NUM);
        //log.info("{}", skipList);
    }

    //@Setup(Level.Invocation)
    //public void before() {
    //    Random rand = new Random();
    //    target = rand.nextInt((int)TOTAL_NUM);
    //}

    @Benchmark
    public void testSkipListGetLessThanOrEqualTo() {
        List<Integer> deleted = skipList.eraseLessThanOrEqualTo(target);
        //log.info("{}", skipList);
        //if (!deleted.isEmpty()) {
        //    log.info("{}", deleted);
        //}
    }

    @Benchmark
    public void testPriorityQueueGetLessThanOrEqualTo() {
        List<Integer> deleted = new ArrayList<>();
        while (!queue.isEmpty() && queue.peek() <= target) {
            deleted.add(queue.poll());
        }
        //log.info("{}", deleted);
    }

    public static void main(String[] args ) throws RunnerException {
        Options opt = new OptionsBuilder()
                // 导入要测试的类
                .include(SkipListBenchMark.class.getSimpleName())
                // 预热5轮
                .warmupIterations(5)
                // 度量10轮
                .measurementIterations(10)
                .resultFormat(ResultFormatType.JSON)
                .result("logs/benchmark_sequence.json")
                .output("logs/benchmark_sequence.log")
                .build();
        new Runner(opt).run();
    }

}
