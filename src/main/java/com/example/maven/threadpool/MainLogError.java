package com.example.maven.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/8/17
 */
@Slf4j
public class MainLogError {
    public static void main(String[] args) {
        try {
           Integer.valueOf("zbc");
        } catch (Exception e) {
            log.error("123 {}, {}, {}, {}, {}, {}",  1, 2, 3, 4,  e);
        }
    }
}
