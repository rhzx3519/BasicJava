package com.example.maven.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author ZhengHao Lou
 * @date 2020/10/18
 */
@Slf4j
public class GaugeTest {
    public static Queue<String> q = new LinkedList<String>();

    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(1, TimeUnit.SECONDS);
//        registry.gauge(MetricRegistry.name(GaugeTest.class, "queue", "size"), () -> () -> q.size());

//        registry.register(MetricRegistry.name(GaugeTest.class, "queue", "size"),
//                new Gauge<Integer>() {
//
//                    public Integer getValue() {
//                        return q.size();
//                    }
//                });
        AtomicInteger num = new AtomicInteger(0);
        int[] nums = new int[1];
        while(true){
            Thread.sleep(1000);
            nums[0] += 1;
            int tmp = nums[0];
            registry.gauge(MetricRegistry.name(GaugeTest.class, "queue", "size"), () -> () -> nums[0]);
            log.info("{}", registry.getGauges().values().stream().map(gauge -> gauge.getValue()).collect(Collectors.toList()));
            q.add("Job-xxx");

        }
    }
}
