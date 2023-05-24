package com.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.cn/problems/largest-values-from-labels/
 */
class Pair {
    int value, label;
    public Pair(int x, int c) {
        this.value = x;
        this.label = c;
    }
}
public class LargestValuesFromLabels {
    public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            if (a.value > b.value) {
                return -1;
            } else if (a.value < b.value) {
                return 1;
            }
            return 0;
        });
        for (int i = 0; i < values.length; i++) {
            pq.offer(new Pair(values[i], labels[i]));
        }
        int tot = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int k = 0; !pq.isEmpty() && k < numWanted;) {
            Pair p = pq.poll();
            if (cnt.getOrDefault(p.label, 0) >= useLimit) {
                continue;
            }
            tot += p.value;
            cnt.put(p.label, cnt.getOrDefault(p.label, 0) + 1);
            k++;
        }
        return tot;
    }

    public static void main(String[] args) {
        LargestValuesFromLabels solution = new LargestValuesFromLabels();
//        solution.largestValsFromLabels(new int[]{5,4,3,2,1}, new int[]{1,1,2,2,3}, 3, 1);
//        solution.largestValsFromLabels(new int[]{5,4,3,2,1}, new int[]{1,3,3,3,2}, 3, 2);
        solution.largestValsFromLabels(new int[]{9,8,8,7,6}, new int[]{0,0,0,1,1}, 3, 1);
    }
}


