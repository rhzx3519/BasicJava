package com.example.maven.algorithm;

import com.example.maven.data.User;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhengHao Lou
 * @date 2020/9/7
 */
@Slf4j
public class KFreq {
    private static class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            PriorityQueue<int[]> que = new PriorityQueue<>(Comparator.comparingInt(o -> -o[1]));

            Map<Integer, AtomicInteger> freq = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                freq.putIfAbsent(nums[i], new AtomicInteger(0));
                freq.get(nums[i]).incrementAndGet();
            }
            List<Integer> lt = new ArrayList<>();
            freq.entrySet().forEach(entry -> {
                que.offer(new int[]{entry.getKey(), entry.getValue().get()});
                if (que.size() > freq.size() - k) {
                    int[] pair = que.poll();
                    lt.add(pair[0]);
                }
            });
            int[] ans = new int[k];
            for (int i = 0; i < k; i++) {
                ans[i] = lt.get(i);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3};
        int k = 2;
        Solution su = new Solution();
        int[] ans = su.topKFrequent(nums, k);
        log.info("{}", ans);
        log.info("a: {1}, b: {0}", 0, 1);

        final User user = User.builder().id(1).name("Mathew").password("123").build();
        user.setName("Zack");
        log.info("user: {}", user);
    }
}
