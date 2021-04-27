package com.example.maven.optional;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author ZhengHao Lou
 * @date 2020/7/31
 */
@Slf4j
public class MainOptional {
    @Builder
    @Data
    static class User {
        private String name;
    }
    public static void main(String[] args) {
        Optional<User> optionalEmpty = Optional.empty();
        Optional<User> optional = Optional.of(new User("Jim"));
        optional.get().setName("Tom");
        log.info("{}", optional.get());
    }
}
