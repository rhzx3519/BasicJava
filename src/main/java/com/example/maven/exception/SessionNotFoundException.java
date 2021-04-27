package com.example.maven.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
public class SessionNotFoundException extends Exception {
    @Getter
    @Setter
    protected String message;

    public SessionNotFoundException() {
        setMessage("Session is not found!");
    }

    public SessionNotFoundException(String message) {
        this.message = message;
    }
}
