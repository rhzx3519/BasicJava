package com.example.maven.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author ZhengHao Lou
 * @date 2020/8/20
 */
@Slf4j
public class BasicHash {
    static class User {
        int id;
        String name;

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

    }

    public static void main(String[] args) {
        User user = new User();
        log.info("{}", user.hashCode());
    }
}


