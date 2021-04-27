package com.example.maven.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/7/27
 */
@Slf4j
public class CommonMain {
    public static void main(String[] args) {
        try {
//            throw new Exception();
//            throw new RuntimeException();
            throw new CommonException();
        } catch (RuntimeException e) {
            log.info("I'm runtimeException");
            return;
        } catch (Exception e) {
            log.info("I'm exception.");
        }

        log.info("Out of trycatch.");
    }
}
