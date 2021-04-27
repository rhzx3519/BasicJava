package com.example.maven.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/11/30
 */
@Slf4j
public class ReorganizeString {
    private static class Solution {
        public String reorganizeString(String S) {
            int maxVal = 0;
            Map<Character, Integer> freq = new HashMap<>();
            for (int i = 0; i < S.length(); i++) {
                freq.put(S.charAt(i), freq.getOrDefault(S.charAt(i), 0) + 1);
                maxVal = Math.max(maxVal, freq.get(S.charAt(i)));
            }

            if (maxVal > S.length() - maxVal + 1) {
                return "";
            }
            PriorityQueue<Character> que = new PriorityQueue<>((c1, c2) -> {
                if (freq.get(c1) < freq.get(c2)) {
                    return 1;
                } else if (freq.get(c1) > freq.get(c2)) {
                    return -1;
                }
                return 0;
            });

            freq.keySet().forEach(c -> {
                que.offer(c);
            });

            //log.info(que.toString());
            StringBuilder sb = new StringBuilder();
            while (que.size() >= 2) {
                char c1 = que.poll();
                char c2 = que.poll();
                freq.put(c1, freq.get(c1) - 1);
                freq.put(c2, freq.get(c2) - 1);
                if (freq.get(c1) > 0) {
                    que.offer(c1);
                }
                if (freq.get(c2) > 0) {
                    que.offer(c2);
                }
                sb.append(c1);
                sb.append(c2);
            }

            if (!que.isEmpty()) {
                sb.append(que.peek());
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String S = "aaab";
        Solution su = new Solution();
        String str = su.reorganizeString(S);
        log.info(str);
    }
}
