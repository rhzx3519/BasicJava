package com.example.maven.algs4.maxflow;

/**
 * @author ZhengHao Lou
 * @date 2020/9/30
 */
public class FlowEdge {
    private static final double FLOATING_POINT_EPSILON = 1e-10;

    private final int v;            // from
    private final int w;            // to
    private final double capacity;     // capacity
    private double flow;               // flow
    private double cost;                // cost

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
        this.cost = 0.0;
    }

    public FlowEdge(int v, int w, double capacity, double cost) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
        this.cost = cost;
    }

    public double cost() {
        return this.cost;
    }

    public double capacity() {
        return this.capacity;
    }

    public double flow() {
        return this.flow;
    }

    public int from() {
        return this.v;
    }

    public int to() {
        return this.w;
    }

    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new IllegalArgumentException("Invalid endpoint.");
        }
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v) {      // backward edge
            return flow;
        } else if (vertex == w) {   // forward edge
            return capacity - flow;
        } else {
            throw new IllegalArgumentException("Invalid endpoint");
        }
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (delta <= 0.0) {
            throw new IllegalArgumentException("Delta cannot be negtive.");
        }
        if (vertex == v) {  // 从逆向边flow会减少流量
            flow -= delta;
        } else if (vertex == w) {  // 正向边
            flow += delta;
        } else {
            throw new IllegalArgumentException("Invalid endpoint.");
        }
        if (Math.abs(flow) < FLOATING_POINT_EPSILON) {
            flow = 0.0f;
        }
        if (Math.abs(flow - capacity) < FLOATING_POINT_EPSILON) {
            flow = capacity;
        }
        if (flow < 0.0) {
            throw new IllegalArgumentException("Flow is negtive.");
        }
        if (flow > capacity) {
            throw new IllegalArgumentException("Flow exceeds capacity");
        }
    }

    /**
     * format: start->end flow/capacity/cost
     * @return
     */
    public String toString() {
        return this.v + "->" + this.w + " " + this.flow + "/" + this.capacity + "/" + this.cost;
    }

    public static void main(String[] args) {
        System.out.println(new FlowEdge(0, 1, 5));
    }
}
