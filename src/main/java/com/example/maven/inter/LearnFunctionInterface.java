package com.example.maven.inter;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */

@Slf4j
public class LearnFunctionInterface {

    public int run(String token) {
       return 1001;
    }

    public <T, R> R get(int errcode , Function<T,  R> function, T t, R r) {
        if (errcode==1) {
            return function.apply(t);
        }
        return r;
    }

    public static void main(String[] args) {
        LearnFunctionInterface instance = new LearnFunctionInterface();
        String t = "123";
        int r = 3;
        Function<String, Integer> function = x -> instance.run(x);
        int res = instance.get(2, x -> instance.run(x), t, r);
        log.info("{}", res);
    }

}
