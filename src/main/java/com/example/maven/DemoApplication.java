package com.example.maven;

import java.util.Arrays;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @auther louzh
 * @create 2020/04/22
 */

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        Config config = ConfigFactory.load().getConfig("apollo");
        System.setProperty("apollo.meta", config.getString("meta"));
        System.setProperty("apollo.cluster", config.getString("cluster"));
        System.setProperty("env", config.getString("env"));

        SpringApplication.run(DemoApplication.class, args);
    }

    //@Bean
    //public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    //    return args -> {
    //
    //        System.out.println("Let's inspect the beans provided by Spring Boot:");
    //
    //        String[] beanNames = ctx.getBeanDefinitionNames();
    //        Arrays.sort(beanNames);
    //        for (String beanName : beanNames) {
    //            System.out.println(beanName);
    //        }
    //
    //    };
    //}
}
