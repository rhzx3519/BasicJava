package com.example.maven.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2021/01/12
 */
@Slf4j
public class SortItemsbyGroupsRespectingDependencies {
    private static class Solution {
        public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
           List<Integer> ans = new ArrayList<>();

            List<Set<Integer>> itemAdj = new ArrayList<>();
            List<Integer> itemInd = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                itemAdj.add(new HashSet<>());
                itemInd.add(0);
            }

            // build item adj and ind
            for (int i = 0; i < n; i++) {
                if (group[i] == -1) {
                    group[i] = m;
                    m += 1;
                }

                for (int j = 0; j < beforeItems.get(i).size(); j++) {
                    int p = beforeItems.get(i).get(j);
                    itemAdj.get(p).add(i);
                }
            }

            for (int i = 0; i < n; i++) {
                for (int ni : itemAdj.get(i)) {
                    itemInd.set(ni, itemInd.get(ni) + 1);
                }
            }

            // build group adj and ind
            List<Set<Integer>> groupAdj = new ArrayList<>();
            List<Integer> groupInd = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                groupAdj.add(new HashSet<>());
                groupInd.add(0);
            }

            for (int i = 0; i < n; i++)  {
                for (int j = 0; j < beforeItems.get(i).size(); j++) {
                    int p = beforeItems.get(i).get(j);
                    if (group[p] != group[i]) {
                        groupAdj.get(group[p]).add(group[i]);
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int ni : groupAdj.get(i)) {
                    groupInd.set(ni, groupInd.get(ni) + 1);
                }
            }

            List<Integer> itemTopoList = this.topoSort(itemAdj, itemInd);
            if (itemTopoList.isEmpty()) {
                return new int[0];
            }
            List<Integer> groupTopoList = this.topoSort(groupAdj, groupInd);
            if (groupTopoList.isEmpty()) {
                return new int[0];
            }
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int itemId : itemTopoList) {
                map.putIfAbsent(group[itemId], new ArrayList<>());
                map.get(group[itemId]).add(itemId);
            }

            for (int groupId : groupTopoList) {
                if (map.containsKey(groupId)) {
                    ans.addAll(map.get(groupId));
                }
            }

            return ans.size() == n ? ans.stream().mapToInt(i -> i).toArray() : new int[0];
        }

        private List<Integer> topoSort(List<Set<Integer>> adj, List<Integer> ind) {
            List<Integer> ans = new ArrayList<>();
            Queue<Integer> que = new LinkedList<>();
            int n = ind.size();
            for (int i = 0; i < n; i++) {
                if (ind.get(i) == 0) {
                    que.add(i);
                }
            }
            while (!que.isEmpty()) {
                int i = que.poll();
                ans.add(i);
                for (int ni : adj.get(i)) {
                    ind.set(ni, ind.get(ni) - 1);
                    if (ind.get(ni) == 0) {
                        que.add(ni);
                    }
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        int n = 8;
        int m = 2;
        int[] group = new int[]{-1,-1,1,0,0,1,0,-1};
        int[][] tmp = new int[][]{{},{6},{5},{6},{3},{},{4},{}};
        List<List<Integer>> beforeItems = new ArrayList<>();
        for (int i = 0; i < tmp.length; i++) {
            beforeItems.add(new ArrayList<>());
            for (int j = 0; j < tmp[i].length; j++) {
                beforeItems.get(i).add(tmp[i][j]);
            }
        }

        Solution su = new Solution();
        int[] ans = su.sortItems(n, m, group, beforeItems);
        log.info("{}", ans);
    }

}
