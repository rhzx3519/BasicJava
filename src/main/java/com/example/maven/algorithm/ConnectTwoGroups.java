package com.example.maven.algorithm;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/10/10
 */
public class ConnectTwoGroups {

    public int connectTwoGroups(List<List<Integer>> cost) {
        int m = cost.size(), n = cost.get(0).size();
        int[][] costMatrix = new int[m][1<<n];
        for (int i = 0; i < m; i++) {
            for (int k = 0; k < (1<<n); k++) {
                for (int j = 0; j < n; j++) {
                    if ((k & (1<<j)) > 0) {
                        costMatrix[i][k] += cost.get(i).get(j);
                    }
                }
            }
        }

        int[][] dp = new int[m][1<<n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0] = costMatrix[0];
        for (int i = 1; i < m; i++) {
            for (int k = 1; k < (1<<n); k++) {
                for (int j = 1; j < (1<<n); j++) {
                    dp[i][k|j] = Math.min(dp[i][k|j], dp[i-1][k] + costMatrix[i][j]);
                }
            }
        }
        return dp[m-1][(1<<n) - 1];
    }

    public static void main(String[] args) {
        List<List<Integer>> cost = Lists.newArrayList();
        int[][] a = new int[][]{{1, 3, 5}, {4, 1, 1}, {1, 5, 3}};
        for (int i = 0; i < a.length; i++) {
            cost.add(Lists.newArrayList());
            for (int j = 0; j < a[i].length; j++) {
                cost.get(i).add(a[i][j]);
            }
        }
        ConnectTwoGroups c = new ConnectTwoGroups();
        c.connectTwoGroups(cost);
    }
}

