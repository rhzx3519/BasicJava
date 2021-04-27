package com.example.maven.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/single-threaded-cpu/
 */
@Slf4j
public class SingleThreadedCPU {
    private static class Solution {

        class Task implements Comparable<Task> {
            int idx;
            int enqueueTime;
            int processingTime;

            Task(int idx, int enqueueTime, int processingTime) {
                this.idx = idx;
                this.enqueueTime = enqueueTime;
                this.processingTime = processingTime;
            }

            @Override
            public int compareTo(Task other) {
                if (processingTime != other.processingTime) {
                    return processingTime - other.processingTime;
                }
                return idx - other.idx;
            }
        }

        public int[] getOrder(int[][] tasks) {
            List<Integer> orders = new ArrayList<>();

            Queue<Task> que =  new PriorityQueue<>();

            List<Task> ts = new ArrayList<>();
            for (int i = 0; i < tasks.length; i++) {
                ts.add(new Task(i, tasks[i][0], tasks[i][1]));
            }
            ts.sort(Comparator.comparingInt(t -> t.enqueueTime));
            que.offer(new Task(-1, 0, 0));
            int i = 0, n = tasks.length;
            int curTime = 0;
            while (!que.isEmpty()) {
                Task cur = que.poll();
                curTime += cur.processingTime;
//                log.info("{}, {}", cur.idx, curTime);
                orders.add(cur.idx);
                while (i < n && curTime >= ts.get(i).enqueueTime) {
                    que.offer(ts.get(i));
                    i++;
                }
                if (i < n && que.isEmpty()) {
                    do {
                        curTime = ts.get(i).enqueueTime;
                        que.offer(ts.get(i));
                        i++;
                    } while (i < n && (i > 0 && ts.get(i).enqueueTime == ts.get(i-1).enqueueTime));

                }
            }

            int[] ans = new int[tasks.length];
            for (int k = 1; k < orders.size(); k++) {
                ans[k -1] = orders.get(k);
            }


            return ans;
        }

    }

    public static void main(String[] args) {
        Solution su = new Solution();
        log.info("{}", su.getOrder(new int[][]{{1,2},{2,4},{3,2},{4,1}}));
        log.info("{}", su.getOrder(new int[][]{{7,10},{7,12},{7,5},{7,4},{7,2}}));
//        [6,1,2,9,4,10,0,11,5,13,3,8,12,7]
        log.info("{}", su.getOrder(new int[][]{{19,13},{16,9},{21,10},{32,25},{37,4},{49,24},{2,15},{38,41},{37,34},
                {33,6},{45,4},{18,18},{46,39},{12,24}}));
    }
}
