package com.example.maven.algs4.maxflow;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;


/**
 * @author ZhengHao Lou
 * @date 2020/9/30
 */
@Slf4j
public class FordFulkerson {
    private static final double FLOATING_POINT_EPSILON = 1E-11;

    private final int V;          // number of vertices
    private boolean[] marked;     // marked[v] = true if s->v path in residual graph
    private FlowEdge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
    private double value;         // current value of max flow

    public double value() {
        return this.value;
    }

    public FordFulkerson (FlowNetwork G, int s, int t) {
        this.V = G.V();
        validate(s);
        validate(t);
        if (s == t) {
            throw new IllegalArgumentException("Source equals sink.");
        }
        if (!isFeasible(G, s, t)) {
            throw new IllegalArgumentException("Initial flow is infeasible.");
        }
        value = excess(G, t); // 终点的净流入量，即整个网络的最大流量
        while (hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            // edgeTo存储了从t到s的增广路径
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }
            value += bottle;
            log.debug("value: {}, bottle: {}", value, bottle);
        }

        assert check(G, s, t);
    }

    // is there an augmenting path?
    // if so, upon termination edgeTo[] will contain a parent-link representation of such a path
    // this implementation finds a shortest augmenting path (fewest number of edges),
    // which performs well both in theory and in practice
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        // breadth-first search
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty() && !marked[t]) {
           int v = queue.dequeue();
           for (FlowEdge e : G.adj(v)) {
               int w = e.other(v);
               // if residual capacity from v to w
               if (!marked[w] && e.residualCapacityTo(w) > 0.0) {
                   edgeTo[w] = e;
                   marked[w] = true;
                   queue.enqueue(w);
               }
           }
        }
        // is there an augmenting path?
        log.debug("marked: {}", marked);
        return marked[t];
    }

    /**
     * 利用sfpa算法求出费用最小的增广路径
     * @param G
     * @param s
     * @param t
     * @return
     */
    private boolean hasAugmentingPathSFPA(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        Double[] cost = new Double[G.V()];
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[s] = 0.0;
        boolean[] inque = new boolean[G.V()];
        Arrays.fill(inque, false);

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        inque[s] = true;
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            inque[v] = false;
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) <= 0.0) {
                    continue;
                }
                if (cost[v] + e.cost() < cost[w]) {
                    cost[w] = cost[v] + e.cost();
                    edgeTo[w] = e;
                    if (!inque[w]) {
                        queue.enqueue(w);
                        inque[w] = true;
                    }
                }
            }
        }
        return edgeTo[t] != null;
    }

    // return excess flow at vertex v
    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for (FlowEdge e : G.adj(v)) {
            if (e.from() == v) {
                excess -= e.flow();
            } else {
                excess += e.flow();
            }
        }
        return excess;
    }

    // return excess flow at vertex v
    private boolean isFeasible(FlowNetwork G, int s, int t) {
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (e.flow() < -FLOATING_POINT_EPSILON || e.flow() > e.capacity() + FLOATING_POINT_EPSILON) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        // 最大流量 + 起点净流出量 == 0.0
        if (Math.abs(value + excess(G, s)) > FLOATING_POINT_EPSILON) {
            System.err.println("Excess at source = " + excess(G, s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        // 最大流量 + 终点净流入量 == 0.0
        if (Math.abs(value - excess(G, t)) > FLOATING_POINT_EPSILON)  {
            System.err.println("Excess at sink = " + excess(G, s));
            System.err.println("Max flow         = " + value);
            return false;
        }

        // 对于其余顶点，净流出量应该等于净流入量
        for (int v = 0; v < G.V(); v++) {
            if (v == s || v == t) {
                continue;
            }
            if (Math.abs(excess(G, v)) > FLOATING_POINT_EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }

        return true;
    }

    // throw an IllegalArgumentException if v is outside prescibed range
    private void validate(int v)  {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }


    // check optimality conditions
    private boolean check(FlowNetwork G, int s, int t) {

        // check that flow is feasible
        if (!isFeasible(G, s, t)) {
            System.err.println("Flow is infeasible");
            return false;
        }

        // check that s is on the source side of min cut and that t is not on source side
        if (!inCut(s)) {
            System.err.println("source " + s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)) {
            System.err.println("sink " + t + " is on source side of min cut");
            return false;
        }

        // check that value of min cut = value of max flow
        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to()))
                    mincutValue += e.capacity();
            }
        }

        if (Math.abs(mincutValue - value) > FLOATING_POINT_EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }

        return true;
    }

    /**
     * Returns true if the specified vertex is on the {@code s} side of the mincut.
     *
     * @param  v vertex
     * @return {@code true} if vertex {@code v} is on the {@code s} side of the micut;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean inCut(int v)  {
        validate(v);
        return marked[v];
    }

    /**
     * Unit tests the {@code FordFulkerson} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // create flow network with V vertices and E edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int s = 0, t = V-1;
        FlowNetwork G = new FlowNetwork(V, E);
        StdOut.println(G);

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("Min cut: ");
        for (int v = 0; v < G.V(); v++) {
            if (maxflow.inCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Max flow value = " +  maxflow.value());
    }
}
