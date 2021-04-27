package com.example.maven.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2021/01/21
 */
@Slf4j
public class MinimumSpanningTree {
    // https://leetcode-cn.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/

    /**
     * 解题思路：
     *
     * 这道题本质一个求最小生成树的问题，底层还是用Kruskal算法求最生成树的权重；
     * 所谓关键边，就是去掉了这条边，图的最小生成树权重值就会变化；
     * 所谓伪关键边，就是提前加上这条边，图的最小生成树权重值不会发生变化；
     * 根据上面这个思路，就很好做了，
     * 第一步，只需要先求出所有边存在情况下的最小生成树权重；
     * 第二步，求分别去掉每一条边时，最小生成树的权重是否会发生变化，如果发生变化了，那它肯定是关键边；
     * 第三步，再剩下的边中，求分别提前加上每一条边时，最小生成树的权重是否会发生变化，如果没有发生变化了，那它肯定是伪关键边。
     *
     * 作者：lippon
     * 链接：https://leetcode-cn.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/solution/java-guan-jian-bian-he-wei-guan-jian-bia-1as1/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    private static class Solution {
        private static class Edge {
            int idx;
            int a;
            int b;
            int w;

            Edge(int idx, int a, int b, int w) {
                this.idx = idx;
                this.a = a;
                this.b = b;
                this.w = w;
            }
        }

        public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] es) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(new ArrayList<>());
            ans.add(new ArrayList<>());

            // 初始化边集合
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < es.length; i++) {
                edges.add(new Edge(i, es[i][0], es[i][1], es[i][2]));
            }

            // 得到最小生成树的权重
            int minVal = kruskal(getLookUp(n), edges, 0);
            List<Edge> tmpEdges = new ArrayList<>(edges);
            // 删除一条边看看最小生成树的权值是否发生了变化，如果是，则是关键边
            for (Edge edge : edges)  {
                tmpEdges.remove(edge);
                int val = kruskal(getLookUp(n), tmpEdges, 0);
                if (val != minVal) {
                    ans.get(0).add(edge.idx);
                }
                tmpEdges.add(edge);
            }

            // 第三步：判断伪关键边
            tmpEdges = new ArrayList<>(edges);
            for (Edge edge : edges) {
                if (ans.get(0).contains(edge.idx)) {
                    continue;
                }
                List<Integer> lookUp = getLookUp(n);
                // 连上这条边
                lookUp.set(edge.b, edge.a);
                // 如果先连上了这条边，最小生成树的权重还是一样的，说明这是伪关键边
                int val = kruskal(lookUp, tmpEdges, edge.w);
                if (val == minVal) {
                    ans.get(1).add(edge.idx);
                }
            }

            return ans;
        }

        // Kruskal求最小生成树
        private int kruskal(List<Integer> lookUp, List<Edge> edges, int w) {
            edges.sort(Comparator.comparingInt(a -> a.w));
            for (Edge edge : edges) {
                int pa = find(lookUp, edge.a), pb = find(lookUp, edge.b);
                if (pa != pb) {
                    w += edge.w;
                    lookUp.set(pb, pa);
                }
            }
            return w;
        }

        // 并查集, 查询父节点
        private int find(List<Integer> lookUp, int x) {
            int px = lookUp.get(x);
            if (px != x) {
                lookUp.set(x, find(lookUp, px));
            }
            return lookUp.get(x);
        }

        private List<Integer> getLookUp(int n) {
            // 初始化并查集查询集合
            List<Integer> lookUp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                lookUp.add(i);
            }
            return lookUp;
        }
    }

    public static void main(String[] args) {
        //int n = 4;
        //int[][] edges = new int[][]{{0,1,1},{1,2,1},{2,3,1},{0,3,1}};
        int n = 5;
        int[][] edges = new int[][]{{0,1,1},{1,2,1},{2,3,2},{0,3,2},{0,4,3},{3,4,3},{1,4,6}};
        Solution su = new Solution();
        List<List<Integer>> ans = su.findCriticalAndPseudoCriticalEdges(n, edges);
        log.info("{}", ans);
    }
}
