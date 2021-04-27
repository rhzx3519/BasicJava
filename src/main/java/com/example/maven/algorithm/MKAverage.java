package com.example.maven.algorithm;

import java.util.*;

public class MKAverage {

    private TreeMap<Integer, Integer> treeMap;
    private List<Integer> list;
    private int m;
    private int k;

    public MKAverage(int m, int k) {
        this.m = m;
        this.k = k;
        list = new ArrayList<>();
        treeMap = new TreeMap<>();
    }

    public void addElement(int num) {
        if (list.size() >= m) {
            int last = list.get(list.size() - m);
            treeMap.put(last, treeMap.get(last)-1);
            if (treeMap.get(last) == 0 ) {
                treeMap.remove(last);
            }
        }
        list.add(num);
        treeMap.put(num, treeMap.getOrDefault(num, 0) + 1);

    }

    public int calculateMKAverage() {
        if (list.size() < m) {
            return -1;
        }
        int sum = 0;
        int count = 0;

        for (int key : this.treeMap.keySet()) {
            int n = this.treeMap.get(key);
            for (int i = 0; i < n; i++) {
               count++;
                if (count > this.k && count <= this.m - this.k) {
                    sum += key;
                }
            }
        }

        return sum/(this.m-2*this.k);
    }

    public static void main(String[] args) {
        int avg = 0;
        MKAverage mk = new MKAverage(3, 1);
        mk.addElement(3);
        mk.addElement(1);
        avg = mk.calculateMKAverage();
        mk.addElement(10);
        avg = mk.calculateMKAverage();
        mk.addElement(5);
        mk.addElement(5);
        mk.addElement(5);
//        mk.addElement(5);
        avg = mk.calculateMKAverage();
        System.out.println(avg);
    }
}
