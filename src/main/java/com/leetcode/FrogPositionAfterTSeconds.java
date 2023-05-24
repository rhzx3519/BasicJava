package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/frog-position-after-t-seconds/description/
 */
public class FrogPositionAfterTSeconds {

    private int dfs(List<Integer>[] g, int target, int x, int fa, int leftT) {
        if (leftT == 0) {
            if (x == target) {
                return 1;
            }
            return 0;
        }
        if (x == target) {
            if (g[x].size() == 1) {
                return 1;
            }
            return 0;
        }
        for (int nx : g[x]) {
            if (nx != fa) {
                int prod = dfs(g, target, nx, x, leftT - 1);
                if (prod != 0) {
                    return prod * (g[x].size() - 1);
                }
            }
        }
        return 0;
    }

    public double frogPosition(int n, int[][] edges, int t, int target) {
        List<Integer>[] g = new ArrayList[n+1];
        Arrays.setAll(g, e -> new ArrayList<>());
        g[1].add(0);
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        int prod = dfs(g, target, 1, 0, t);
        return prod == 0 ? 0 : 1.0/prod;
    }

}
