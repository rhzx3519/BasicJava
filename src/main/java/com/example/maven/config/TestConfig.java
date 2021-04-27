package com.example.maven.config;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhengHao Lou
 * @date 2020/05/26
 */

public class TestConfig {

    @Setter
    private String host;

    public void foo() {
        try {
           return;
        } finally {
            System.out.println("123");
        }
    }


    public static void main(String[] args) {
        TestConfig config = new TestConfig();
        config.foo();
    }
}
