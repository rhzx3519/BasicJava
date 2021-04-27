package com.example.maven.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/8/21
 */
@Configuration
@Slf4j
public class LoadConfig {

    public static class AlgoConfig {
        @Value("${apollo.bootstrap.namespaces}")
        private String[] namespaces;

        @PostConstruct
        private void setup() {
            log.info("namespaces: {}", namespaces[2]);
        }
    }

//    @Bean
//    public AlgoConfig algoConfig() {
//        return new AlgoConfig();
//    }
}
