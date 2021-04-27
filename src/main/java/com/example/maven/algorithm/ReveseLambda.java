package com.example.maven.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author ZhengHao Lou
 * @date 2020/9/3
 */
@Slf4j
public class ReveseLambda {
    static class Solution {
        public int evalRPN(String[] tokens) {
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < tokens.length; i++) {
                String ch = tokens[i];
                if ("+".equals(ch) || "-".equals(ch) || "*".equals(ch) || "/".equals(ch)) {
                    final int b = stack.pop();
                    final int a = stack.pop();
                    int c = 0;
                    if ("+".equals(ch)) {
                        c = a + b;
                    } else if ("-".equals(ch)) {
                        c = a - b;
                    } else if ("*".equals(ch)) {
                        c = a * b;
                    } else if ("/".equals(ch)) {
                        c = a / b;
                    }
                    stack.push(c);
                } else {
                    stack.push(Integer.valueOf(ch));
                }
            }
            return stack.peek();
        }
    }


    public static void main(String[] args) {
        Solution su = new Solution();
        int val = su.evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"});
        log.info("val: {}", val);
    }
}
