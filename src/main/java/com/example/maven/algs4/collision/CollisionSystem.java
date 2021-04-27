package com.example.maven.algs4.collision;

/**
 * @author ZhengHao Lou
 * @date 2020/9/29
 */

import com.google.common.collect.Queues;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.awt.*;
import java.util.PriorityQueue;

/**
 * 模拟粒子间弹性碰撞
 */
public class CollisionSystem {

    /**
     *  An event during a particle collision simulation. Each event contains
     *  the time at which it will occur (assuming no supervening actions)
     *  and the particles a and b involved.
     *
     *    -  a and b both null:      redraw event
     *    -  a null, b not null:     collision with vertical wall
     *    -  a not null, b null:     collision with horizontal wall
     *    -  a and b both not null:  binary collision between a and b
     *
     */
    private static class Event implements Comparable<Event> {
        // time that event is scheduled to occur
        private final double time;
        // particles involved in the event
        private final Particle a, b;
        // collision count when event is created
        private final int countA, countB;

        public Event (double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            countA = a != null ? a.getCount() : -1;
            countB = b != null ? b.getCount() : -1;
        }


        @Override
        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        public boolean isValid() {
            if (a != null && a.getCount() != countA) {
                return false;
            }
            if (b != null && b.getCount() != countB) {
                return false;
            }
            return true;
        }
    }

    private static final double HZ = 0.5;    // number of redraw events per clock tick

    private PriorityQueue<Event> pq;          // the priority queue
    private double t  = 0.0;          // simulation clock time
    private Particle[] particles;     // the array of particles

    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();
    }

    private void predict(Particle a, double limit) {
        if (a == null) {
            return;
        }

        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit) {
                this.pq.offer(new Event(t + dt, a, particles[i]));
            }
        }

        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizonWall();
        if (t + dtX <= limit) {
            this.pq.offer(new Event(t + dtX, a, null));
        }
        if (t + dtY <= limit) {
            this.pq.offer(new Event(t + dtY, null, a));
        }
    }

    /**
     * redraw all particles
     * @param limit
     */
    public void redraw(double limit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (t <= limit) {
            this.pq.offer(new Event(t + 1/HZ, null, null));
        }
    }

    /**
     * Simulates the system of particles for the specified amount of time.
     *
     * @param  limit the amount of time
     */
    public void simulate(double limit) {
        this.pq = Queues.newPriorityQueue();
        for (int i = 0; i < particles.length; i++) {
            predict(particles[i], limit);
        }
        // redraw event
        pq.offer(new Event(0, null, null));


        while (!pq.isEmpty()) {
            Event event = pq.poll();
            if (!event.isValid()) {
                continue;
            }
            Particle a = event.a;
            Particle b = event.b;

            // physical collision, so update positions, and then simulation clock
            for (int i = 0; i < particles.length; i++) {
                particles[i].move(event.time - t);
            }
            t = event.time;

            // process event
            if (a != null && b != null) { // particle-particle collision
                a.bounceOff(b);
            } else if (a != null && b == null) { // particle-wall collision
                a.bounceOffVerticalWall();
            } else if (a == null && b != null) { // particle-wall collision
                b.bounceOffHorizontalWall();
            } else {                            // redraw event
                this.redraw(limit);
            }

            // update the priority queue with new collisions involving a or b
            predict(a, limit);
            predict(b, limit);
        }
    }


    public static void main(String[] args) {

        StdDraw.setCanvasSize(600, 600);

        // enable double buffering
        StdDraw.enableDoubleBuffering();

        // the array of particles
        Particle[] particles;

        // create n random particles
        if (args.length == 1) {
            int n = Integer.parseInt(args[0]);
            particles = new Particle[n];
            for (int i = 0; i < n; i++)
                particles[i] = new Particle();
        }

        // or read from standard input
        else {
            int n = StdIn.readInt();
            particles = new Particle[n];
            for (int i = 0; i < n; i++) {
                double rx     = StdIn.readDouble();
                double ry     = StdIn.readDouble();
                double vx     = StdIn.readDouble();
                double vy     = StdIn.readDouble();
                double radius = StdIn.readDouble();
                double mass   = StdIn.readDouble();
                int r         = StdIn.readInt();
                int g         = StdIn.readInt();
                int b         = StdIn.readInt();
                Color color   = new Color(r, g, b);
                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            }
        }

        // create collision system and simulate
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000);
    }
}
