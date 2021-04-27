package com.example.maven.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/05/27
 */
@Slf4j
public class BasicCompare {

    public static void main(String[] args) {
        byte t = 100;
        log.info("{}", Byte.compare((byte)100, t));
        Double d = 100.0;
        log.info("{}", d.byteValue());
    }

}
