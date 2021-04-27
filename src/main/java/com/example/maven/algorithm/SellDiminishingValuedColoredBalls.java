package com.example.maven.algorithm;

import java.util.PriorityQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2021/01/07
 */
@Slf4j
public class SellDiminishingValuedColoredBalls {
    private static class Solution {
        private static final int MOD = (int)1E9 + 7;
        public int maxProfit(int[] inventory, int orders) {
            PriorityQueue<Long> que = new PriorityQueue<>((a, b) -> (int) (b - a));
            for (int i : inventory) {
                que.add((long)i);
            }
            long ans = 0;
            while (orders > 0 && !que.isEmpty()) {
                long r = que.poll();
                long l = que.isEmpty() ? 1 : que.peek();
                long num = r - l + 1;
                if (num > orders) {
                   l = r - orders + 1;
                   num = orders;
                }
                orders -= num;
                long x = num*(l + r)/2;
                ans = (ans + x%MOD)%MOD;
                if (l - 1 > 0) {
                    que.add(l - 1);
                }
            }
            return (int)(ans % MOD);
        }
    }

    public static void main(String[] args) {
        int[] inventory =  new int[]{1000000000};
        int orders = 1000000000;
        Solution su = new Solution();
        int ans = su.maxProfit(inventory, orders);
        log.info("ans: {}", ans);
    }
}
