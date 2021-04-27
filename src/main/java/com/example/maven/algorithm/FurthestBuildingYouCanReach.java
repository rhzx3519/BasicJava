package com.example.maven.algorithm;

import java.util.PriorityQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2021/01/07
 */
@Slf4j
public class FurthestBuildingYouCanReach {

    private static class Solution {

        /**
         * 思路：优先使用梯子，梯子不足，取最短的梯子用砖块代替，砖块不足返回
         * @param heights
         * @param bricks
         * @param ladders
         * @return
         */
        public int furthestBuilding(int[] heights, int bricks, int ladders) {
            PriorityQueue<Integer> que = new PriorityQueue<>();
            for (int i = 0; i < heights.length - 1; i++) {
                int h = heights[i+1] - heights[i];
                if (h <= 0) {
                    continue;
                }
                que.add(h);
                if (que.size() > ladders) {
                    bricks -= que.poll();
                }
                if (bricks < 0) {
                    return i;
                }
            }
            return heights.length - 1;
        }
    }

    public static void main(String[] args) {
        int[] heights = new int[]{1,5,1,2,3,4,10000};
        int bricks = 4;
        int ladders = 1;

        Solution su = new Solution();
        int ans = su.furthestBuilding(heights, bricks, ladders);
        log.info("{}", ans);
    }
}
