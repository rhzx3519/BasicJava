package com.example.maven.algorithm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 狄杰斯特拉最短路径算法
 */
@Slf4j
public class Dijkstra {
    private static class Solution {

    }

    /**
     *
     * @param edges edges 编号为1-n的节点的邻接表
     * @param K 1<= K <=n,
     * @return 返回K到其余节点的最短路径长度
     */
    public int[] shortestPath(int[][] edges, int n, int K) {
        Map<Integer, List<int[]>> adj = Maps.newHashMap();
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], cost = edge[2];
            List<int[]> ta = adj.computeIfAbsent(a, i -> Lists.newArrayList());
            List<int[]> tb = adj.computeIfAbsent(b, i -> Lists.newArrayList());
            ta.add(new int[]{b, cost});
            tb.add(new int[]{a, cost});
        }
        int[] dis = new int[n+1];
        for (int i = 1; i <= n; i++) {
            if (i != K) {
                dis[i] = Integer.MAX_VALUE;
            }
        }

        Queue<int[]> que = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        que.offer(new int[]{K, 0});


        while (!que.isEmpty()) {
            int[] node = que.poll();
            int i = node[0], d = node[1];
            if (dis[i] < d) {
                continue;
            }
            for (int[] next : adj.getOrDefault(i, Collections.emptyList())) {
                int ni = next[0], nd = next[1];
                if (d + nd < dis[ni]) {
                    dis[ni] = d + nd;
                    que.offer(new int[]{ni, dis[ni]});
                }
            }
        }

        return dis;
    }

    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        int[][] edges = new int[][]{{1,2,3},{1,3,3},{2,3,1},{1,4,2},{5,2,2},{3,5,1},{5,4,10}};
        int[] dis = dijkstra.shortestPath(edges, 5, 5);
        log.info("{}", dis);
    }
}
