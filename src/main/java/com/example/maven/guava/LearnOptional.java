package com.example.maven.guava;

import com.google.common.base.Optional;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * @date 2020/06/10
 */
public class LearnOptional {
    @Data
    static class User {
        private long id;
        private String name;
    }

    public static void main(String[] args) {
        Optional<User> optional = Optional.fromNullable(null);
    }

}
