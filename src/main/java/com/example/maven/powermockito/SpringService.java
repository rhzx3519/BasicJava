package com.example.maven.powermockito;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * @date 2020/06/18
 */
@Slf4j
@Service
public class SpringService {

    @Autowired
    private OtherService otherService;

    public String callPrivateMethod() {
        return this.privateMethod();
    }

    public String callFinalMethod() {
        return this.finalMethod();
    }

    public String callHello(String name) {
        return otherService.hello(name);
    }

    public static String staticMethod() {
        return "123";
    }

    private String privateMethod() {
        return "123";
    }

    private final String finalMethod() {
        return "123";
    }
}
