package com.leetcode;

import java.util.*;

class FrequencyTracker {
    private Map<Integer, Integer> cnt;
    private Map<Integer, Integer> freq;

    public FrequencyTracker() {
        cnt = new HashMap<>();
        freq = new HashMap<>();
    }

    public void add(int number) {
        int old = this.cnt.getOrDefault(number, 0);
        freq.put(old, freq.getOrDefault(old, 0) - 1);
        cnt.put(number, old + 1);
        freq.put(old + 1, freq.getOrDefault(old + 1, 0) + 1);
    }

    public void deleteOne(int number) {
        if (cnt.getOrDefault(number, 0) == 0) {
            return;
        }
        int old = this.cnt.getOrDefault(number, 0);
        freq.put(old, freq.getOrDefault(old, 0) - 1);
        cnt.put(number, old - 1);
        freq.put(old - 1, freq.getOrDefault(old - 1, 0) + 1);
    }

    public boolean hasFrequency(int frequency) {
        return freq.getOrDefault(frequency, 0) > 0;
    }
}
