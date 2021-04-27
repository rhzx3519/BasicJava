package com.example.maven.basic;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author ZhengHao Lou
 * @date 2020/11/09
 */
public class PointQueue {
    private static class Solution {
        public int[][] kClosest(int[][] points, int K) {
            Queue<int[]> que = new PriorityQueue<>(Comparator.comparingInt(p -> -(p[0] * p[0] + p[1] * p[1])));
            for (int[] point : points) {
                que.add(point);
                if (que.size() > K) {
                    que.poll();
                }
            }
            int i = 0;
            int[][] ans = new int[K][2];
            while (!que.isEmpty()) {
                int[] p = que.poll();
                ans[i][0] = p[0];
                ans[i][1] = p[1];
                i += 1;
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        //int[][] points = {{1,3},{-2,2}};
        //int K = 1;
        int[][] points = {{3,3},{5,-1},{-2,4}};
        int K = 2;
        Solution su = new Solution();
        su.kClosest(points, K);
    }

}
