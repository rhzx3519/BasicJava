package com.example.maven;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author ZhengHao Lou
 * @date 2020/05/17
 */
@SpringBootApplication(scanBasePackages="com.example")
//@ComponentScan(basePackages = {"com.example.maven"}, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tigerbrokers.alpha.config.*"),})
public class DemoTestApplication {

}
