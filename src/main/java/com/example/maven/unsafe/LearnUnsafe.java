package com.example.maven.unsafe;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * @author ZhengHao Lou
 * @date 2020/7/20
 */

@Slf4j
public class LearnUnsafe {

    public boolean foo() {
       try {
           throw new Exception();
       } catch (Exception e) {
           log.info("{}", e);
       }
       return true;
    }

    public static void main(String[] args) {
        LearnUnsafe unsafe = new LearnUnsafe();
        boolean res = unsafe.foo();
        if (res) {
            System.out.println("res: " + res);
        }
        log.info("{}", res);
    }
}
