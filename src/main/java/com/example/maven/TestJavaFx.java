package com.example.maven; /**
 * @auther louzh
 * @create 2020/04/15
 */

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Slf4j
public class TestJavaFx {

    public static void main(String[] args) {
        String str = "EXT902_2GU_20200409.CSV";
        System.out.println(str.startsWith("EXT902"));
        log.info("@lzh {}", 1);

        Config config = ConfigFactory.load().getConfig("server");
        System.out.println(config.getString("host"));
         Stream.of("one", "two", "three", "four")
                        .filter(e -> e.length() > 3)
                         .peek(e -> System.out.println("Filtered value: " + e))
                         .map(String::toUpperCase)
                         .peek(e -> System.out.println("Mapped value: " + e))
                         .collect(Collectors.toList());
    }
}
