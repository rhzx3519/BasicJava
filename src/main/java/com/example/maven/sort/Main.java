package com.example.maven.sort;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/9/11
 */
@Slf4j
public class Main {
    @Data
    private static class ListNode {
        private int val;
        private ListNode prev = null;
        private ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
    public static void main(String[] args) {
        List<Integer> nums = Lists.newArrayList(2, 1, 3);
        Collections.sort(nums, Comparator.reverseOrder());
        log.info("{}", nums);

        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.prev = head;

        while (head != null) {
            log.info("{}", head.val);
            head = head.next;
        }
    }
}
