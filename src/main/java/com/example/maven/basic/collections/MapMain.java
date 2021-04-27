package com.example.maven.basic.collections;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @author ZhengHao Lou
 * @date 2020/10/23
 */
@Slf4j
public class MapMain {
    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.putIfAbsent("TSLA", "11");
        String val = map.get("TSLA");
        log.info(val);
        DoubleStream doubleStream = DoubleStream.generate(() ->1.0);

        EvictingQueue<Double> queue = EvictingQueue.create(2);
        queue.offer(1.0);
        queue.offer(2.0);
        queue.offer(3.0);
        log.info("{}", queue.size());
        log.info("{}", queue);

        double t = 1 - 1000.0 / (1000 + 1002>>4);

        log.info("{}", 1000.0 / (1000 + (1002>>4)));
    }
}
