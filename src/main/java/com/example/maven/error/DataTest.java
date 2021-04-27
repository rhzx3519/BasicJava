package com.example.maven.error;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/12/23
 */
@Slf4j
public class DataTest {

    @Data
    private static class Member {
        boolean hardToBorrow;
    }

    public static void main(String[] args) {
        Member member = new Member();
        log.info("{}", member);
    }
}
