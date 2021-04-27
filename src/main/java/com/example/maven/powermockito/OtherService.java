package com.example.maven.powermockito;

import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * @date 2020/06/19
 */
@Service
public class OtherService {

    public String hello(String name) {
        return "Hello, " + name;
    }
}
