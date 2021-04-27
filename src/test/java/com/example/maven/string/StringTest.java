package com.example.maven.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/9/1
 */
@Slf4j
public class StringTest {

    @Test
    public void test() {
        for (int i = 0; i < 10000; i += 1) {
            log.info("AAAAAAAAA");
        }
    }
}
