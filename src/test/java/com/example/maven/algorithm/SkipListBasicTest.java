package com.example.maven.algorithm;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Queues;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author ZhengHao Lou
 * @date 2021/01/19
 */
@Slf4j
public class SkipListBasicTest {

    @Test
    public void test1() {
        Stopwatch stopWatch = Stopwatch.createStarted();
        Queue<Integer> queue = Queues.newPriorityQueue();
        SkipList<Integer> skipList = SkipList.newSkipList(16, Integer::compare);
        final int TOTAL_NUM = 2000000;
        Random rand = new Random();
        for (long i = 0; i < TOTAL_NUM; i++) {
            int num = rand.nextInt(TOTAL_NUM);
            queue.add(num);
            skipList.add(num);
        }
        int target = rand.nextInt(TOTAL_NUM);
        log.info("Prepare: {}", stopWatch);
        stopWatch.reset();
        stopWatch.start();
        //skipList.add(2);
        //skipList.add(4);
        //skipList.add(4);
        //skipList.add(6);

        //log.info(skipList.toString());
        //skipList.erase(4);
        List<Integer> lessEqualList = skipList.eraseLessThanOrEqualTo(target);
        //log.info("{}", lessEqualList);
        //log.info(skipList.toString());
        log.info("skiplist: {}", stopWatch.stop());

        stopWatch.reset();
        stopWatch.start();

        List<Integer> queList = Lists.newArrayList();
        while (!queue.isEmpty() && queue.peek() <= target) {
            queList.add(queue.poll());
        }

        log.info("PriorityQueue: {}", stopWatch.stop());

        // check
        assertThat(skipList.size()).isEqualTo(queue.size());
        lessEqualList.sort(Integer::compare);
        queList.sort(Integer::compare);
        assertThat(lessEqualList.size()).isEqualTo(queList.size());
        for (int i = 0; i < lessEqualList.size(); i++) {
            assertThat(lessEqualList.get(i)).isEqualTo(queList.get(i));
        }
    }

}
