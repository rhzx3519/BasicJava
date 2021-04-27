package com.example.maven.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.Queue;

@Slf4j
public class MaxAverageRatio {
    private static class Solution {
        public double maxAverageRatio(int[][] classes, int extraStudents) {
            double total = 0;
            Queue<double[]> que = new PriorityQueue<>((a, b) -> Double.compare(b[2] - a[2], 0.0f));
            for (int[] clazz : classes) {
                double x = clazz[0], y = clazz[1];
                que.offer(new double[]{x, y, diff(x, y)});
                total += x/y;
            }

            for (; extraStudents > 0; extraStudents--) {
                double[] clazz = que.poll();
                double x = clazz[0] + 1, y = clazz[1] + 1;
                total += clazz[2];
                que.offer(new double[]{x, y, diff(x, y)});
            }

            return total/classes.length;
        }

        private double diff(double x, double y) {
            return (x + 1) / (y + 1) - x / y;
        }
    }

    public static void main(String[] args) {
        Solution su = new Solution();
        su.maxAverageRatio(new int[][]{{1,2},{3,5},{2,2}}, 2);
        su.maxAverageRatio(new int[][]{{2,4},{3,9},{4,5},{2,10}}, 4);
    }
}

