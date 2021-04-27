package com.example.maven.expression;

import com.google.common.collect.Maps;
import org.mvel2.MVEL;

import java.util.Map;

/**
 * @author ZhengHao Lou
 * @date 2020/9/13
 */
public class Main {
    public static void main(String[] args) {
        String expression = "a == b";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("a", "123");
        paramMap.put("b", 123);
        Object object = MVEL.eval(expression, paramMap);
        System.out.println(object);
    }
}
