package com.example.maven.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.Queue;

@Slf4j
public class NumberofOrdersintheBacklog {
    static class Order {
        int price;
        int amount;

        Order(int price, int amount) {
            this.price = price;
            this.amount = amount;
        }
    }

    private static class Solution {
        public int getNumberOfBacklogOrders(int[][] orders) {
            Queue<Order> buyOrders = new PriorityQueue<>((o1, o2) -> o2.price - o1.price);
            Queue<Order> sellOrders = new PriorityQueue<>((o1, o2) -> o1.price - o2.price);
            for (int[] o : orders) {
                if (o[2] == 0) {
                    buyOrders.offer(new Order(o[0], o[1]));
                } else {
                    sellOrders.offer(new Order(o[0], o[1]));
                }
                match(buyOrders, sellOrders);
            }

            int count = 0;
            final int MOD = (int)Math.pow(10, 9) + 7;
            while (!sellOrders.isEmpty()) {
                count = (count + sellOrders.poll().amount) % MOD;
            }
            while (!buyOrders.isEmpty()) {
                count = (count + buyOrders.poll().amount) % MOD;
            }
            return count;
        }

        private void match(Queue<Order> buyOrders, Queue<Order> sellOrders) {
            while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
                Order buyOrder = buyOrders.peek();
                Order sellOrder = sellOrders.peek();
                if (buyOrder.price < sellOrder.price) {
                    break;
                }
                int minAmount = Math.min(sellOrder.amount, buyOrder.amount);
                sellOrder.amount -= minAmount;
                buyOrder.amount -= minAmount;
                if (sellOrder.amount == 0) {
                    sellOrders.poll();
                } else {
                    buyOrders.poll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution su = new Solution();
//        int count = su.getNumberOfBacklogOrders(new int[][]{{10,5,0},{15,2,1},{25,1,1},{30,4,0}});
        int count = su.getNumberOfBacklogOrders(new int[][]{{7,1000000000,1},{15,3,0},{5,999999995,0},{5,1,1}});
        log.info("{}", count);
    }
}
