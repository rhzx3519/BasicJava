package com.example.maven.beanutils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author ZhengHao Lou
 * @date 2020/8/17
 */
@Slf4j
public class Main {
    @Data
    static class User {
        int id;
        public User(int id) {
            this.id = id;
        }
    }

    @Data
    static class Group {
        String gname;
        final User user;

        public Group(final String gname, final User user) {
            this.gname = gname;
            this.user = user;
        }
    }

    public static void main(String[] args) {
        User u1 = new User(1);
        Group g1 = new Group("g1", u1);

        User u2 = new User(2);
        Group g2 = new Group("g2", u2);

        log.info("g1: {}.", g1);
        log.info("g2: {}.", g2);

        BeanUtils.copyProperties(g1, g2);
        log.info("g1: {}.", g1);
        log.info("g2: {}.", g2);
    }
}
