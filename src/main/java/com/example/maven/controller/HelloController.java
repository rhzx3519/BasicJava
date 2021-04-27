package com.example.maven.controller;

import com.example.maven.data.User;
import com.example.maven.springboot.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther louzh
 * @create 2020/04/22
 */
@RestController
@Slf4j
public class HelloController {
    @RequestMapping("/")
    public String index() {
        User user = BeanUtil.getBean("User");
        log.info("user: {}", user);
        return "Greetings from Spring Boot!";
    }
}
