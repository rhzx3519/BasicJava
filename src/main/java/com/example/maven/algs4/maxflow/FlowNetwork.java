package com.example.maven.algs4.maxflow;

import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/9/30
 */
public class FlowNetwork {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;        // number of vertexs
    private int E;              // number of edges
    private List<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Vertex number must be nonnegtive.");
        }
        this.V = V;
        this.E = 0;
        adj = (List<FlowEdge>[])new LinkedList[V];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = Lists.newLinkedList();
        }
    }

    public FlowNetwork(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double capacity = StdRandom.uniform(100);
            double cost = StdRandom.uniform(100);   // 生成每条边的费用
            addEdge(new FlowEdge(v, w, capacity, cost));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(FlowEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    /**
     * 返回邻接表{@code adj[v]} 中所有的边
     * @param v
     * @return
     */
    public Iterable<FlowEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 返回网络中所有的边
     * @return
     */
    public Iterable<FlowEdge> edges() {
        List<FlowEdge> edges = Lists.newLinkedList();
        for (int v = 0; v < V; v++) {
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= V) {
            throw new IllegalArgumentException("Vertex: " + vertex + "is not between 0 and " + V);
        }
    }

    /**
     * Returns a string representation of the flow network.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *    followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) {
                    s.append(e + "  ");
                }
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        FlowNetwork G = new FlowNetwork(10, 20);
        StdOut.println(G);
    }

}
