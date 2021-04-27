package com.example.maven.algorithm;

import java.util.TreeMap;

public class LongestContinuousSubarrayWithAbsolute {
    static class Solution {
        public int longestSubarray(int[] nums, int limit) {
            int ans = 0;
            TreeMap<Integer, Integer> map = new TreeMap<>();
            int l = 0, r = 0;
            int n = nums.length;
            while (r < n) {
                map.put(nums[r], map.getOrDefault(nums[r], 0) + 1);
                while (map.size() >= 2 && (map.lastKey() - map.firstKey()) > limit) {
                    map.put(nums[l], map.get(nums[l]) - 1);
                    if (map.get(nums[l]) == 0) {
                        map.remove(nums[l]);
                    }
                    l += 1;
                }
                ans = Math.max(ans, r - l  + 1);
                r += 1;
            }
            return ans;
        }
    }

}
