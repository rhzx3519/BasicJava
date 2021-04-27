package com.example.maven.algorithm;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/12/23
 */
@Slf4j
public class MaxProbability {
    private static class Solution {
        class Edge {
            int idx;
            double prob;

            Edge(int idx, double prob) {
                this.idx = idx;
                this.prob = prob;
            }
        }

        private List<List<Edge>> es = new ArrayList<>();
        int[] vis;
        double[] dis;

        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            for (int i = 0; i < n; i++) {
                es.add(new ArrayList<>());
            }

            for (int i = 0; i < edges.length; i++) {
                int a = edges[i][0];
                int b = edges[i][1];
                es.get(a).add(new Edge(b, succProb[i]));
                es.get(b).add(new Edge(a, succProb[i]));
            }

            vis = new int[n];
            dis = new double[n];
            Arrays.fill(vis, 0);
            Arrays.fill(dis, 0.0);

            dijkstra(start, n);
            return dis[end];
        }

        private void dijkstra(int K, int n) {
            for (Edge edge : es.get(K)) {
                dis[edge.idx] = edge.prob;
            }
            vis[K] = 1;
            for (int i = 0; i < n; i++) {
                int maxId = -1;
                double maxVal = 0;
                for (int j = 0; j < n; j++) {
                    if (vis[j] == 0 && dis[j] > maxVal) {
                        maxId = j;
                        maxVal = dis[j];
                    }
                }

                if (maxId == -1) {
                    continue;
                }
                vis[maxId] = 1;
                for (Edge edge : es.get(maxId)) {
                    if (edge.prob*maxVal > dis[edge.idx]) {
                        dis[edge.idx] = edge.prob*maxVal;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        int[][] edges = new int[][]{{0,1},{1,2},{0,2}};
        double[] succProb = new double[]{0.5,0.5,0.2};
        int start = 0;
        int end = 2;

        Solution su = new Solution();
        double res = su.maxProbability(n, edges, succProb, start, end);
        log.info("{}", res);
    }
}
