package com.example.maven.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
public class NullOrEmptyException extends Exception{
    @Getter
    @Setter
    protected String message;

    public NullOrEmptyException() {
        setMessage("Parameter is null or empty!");
    }

    public NullOrEmptyException(String message) {
        this.message = message;
    }
}
