package com.leetcode;

public class RegularExpressionMatching {

}


class Solution {
    private int[][] mem = new int[21][21];

    private boolean dfs(String s, String p, int i, int j) {
        if (mem[i][j] != 0) {
            return mem[i][j] > 0? true: false;
        }
        if (j == p.length()) {
            return i == s.length();
        }

        boolean b = false;
        boolean first = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        if (j < p.length() && p.charAt(j+1) == '*') {
            b = dfs(s, p, i, j+2) || (first && dfs(s, p, i+1, j));
        } else {
            b = first && dfs(s,  p, i+1, j+1);
        }
        return b;
    }

    public boolean isMatch(String s, String p) {
        return dfs(s, p, 0, 0);
    }
}
