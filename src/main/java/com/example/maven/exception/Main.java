package com.example.maven.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/7/27
 */
@Slf4j
public class Main {

    @Data
    @Builder
    static class Msg {
        private int code;
        private String error;
    }

    static class EventHandler {

        public void handle() {
            try {
                return;
               //Double.valueOf("D.D");
            } catch (Exception e) {
                log.error("I'm {}", Msg.builder().code(0).error("test").build().toString(), e);
            } finally {
                System.out.println("finally");
            }
        }
    }

    public static void main(String[] args) {
        EventHandler eventHandler = new EventHandler();
        eventHandler.handle();
    }
}
