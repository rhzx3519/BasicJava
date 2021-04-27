package com.example.maven.mvnbook;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

/**
 * @auther louzh
 * @create 2020/04/22
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

@Slf4j
public class HelloWorld {
    public String sayHello() {
        return "Hello World";
    }

    public static void main(String[] args) {
        log.info("{}", new HelloWorld().sayHello());
        Queue<Integer> maxPQ = new PriorityQueue<>((a,b) -> b.compareTo(a));
        Comparator<ListNode> comparator = (ListNode x, ListNode y) -> {
            return x.val - y.val;
        };
        Queue<ListNode> que = new PriorityQueue(comparator);
        Queue<Integer> q1 = new PriorityQueue<>((a, b) -> b - a);
    }

}

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        Comparator<ListNode> comparator = (ListNode x, ListNode y) -> {
            return x.val - y.val;
        };
        Queue<ListNode> que = new PriorityQueue(comparator);
        Arrays.stream(lists).forEach(node -> {
            if (node != null) {
                que.add(node);
            }
        });

        ListNode dummy = new ListNode(0);
        ListNode p = dummy;

        while (!que.isEmpty()) {
            ListNode node = que.poll();
            p.next = node;
            p = p.next;
            if (node.next!=null) {
                que.add(node.next);
            }
        }

        return dummy.next;
    }
}


