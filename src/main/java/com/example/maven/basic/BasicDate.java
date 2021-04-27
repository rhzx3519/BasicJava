package com.example.maven.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import java.time.Duration;
import java.util.Date;

/**
 * @author ZhengHao Lou
 * @date 2020/7/14
 */
@Slf4j
public class BasicDate {
    public static void main(String[] args) {
        System.out.println(new Date());
        log.info("{}", Duration.parse("PT" + "0.5s").toMillis());

        Throwable throwable = new RuntimeException("what");
        String message = MessageFormatter.format("", throwable).getMessage();
        log.error(message);
//        log.error("", throwable);
    }
}
