package com.example.maven.algorithm;

import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * @author ZhengHao Lou
 * @date 2020/11/25
 */
public class StringSort {
    private static class Solution {
        public boolean isAlienSorted(String[] words, String order) {
            int[] od = new int[26];
            for (int i = 0; i < order.length(); i++) {
                od[order.charAt(i) - 'a'] = i;
            }

            String[] sortedWords = Arrays.stream(words).sorted((w1, w2) -> {
                for (int i = 0; i < Math.min(w1.length(), w2.length()); i++) {
                    int t1 = od[w1.charAt(i) - 'a'];
                    int t2 = od[w2.charAt(i) - 'a'];
                    if (t1 < t2) {
                        return -1;
                    } else if (t1 > t2) {
                        return 1;
                    }
                }
                if (w1.length() < w2.length()) {
                    return -1;
                } else if (w1.length() > w2.length()) {
                    return 1;
                }
                return 0;
            }).toArray(String[]::new);

            for (int i = 0; i < words.length; i++) {
                if (!words[i].equals(sortedWords[i])) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        String[] words = new String[]{"hello","leetcode"};
        String order = "hlabcdefgijkmnopqrstuvwxyz";
        Solution su = new Solution();
        su.isAlienSorted(words, order);
    }

}
