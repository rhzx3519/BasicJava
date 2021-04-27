package com.example.maven.log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import com.example.maven.BaseTest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.Setup;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ZhengHao Lou
 * @date 2020/06/20
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> que = new PriorityQueue(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });

        for (ListNode node : lists) {
            que.offer(node);
        }

        ListNode dummy = new ListNode(0);
        ListNode p = dummy;
        while (!que.isEmpty()) {
            ListNode node = que.poll();
            p.next = node;
            p = p.next;
            if (node.next != null) {
                que.offer(node.next);
            }
        }

        return dummy.next;
    }
}


@Slf4j
public class LogbackTest extends BaseTest {

    @Before
    public void setup() {
        MDC.put("orderId", "order1");
    }

    @Test
    public void testLog() {
        log.trace("这是 info 级别");
        log.debug("这是 debug 级别");
        log.info("这是 info 级别");
        log.warn("这是 warn 级别");
        log.error("这是 error 级别");
        List<Boolean> l = new ArrayList<>();
        l.add(true);
    }
}
