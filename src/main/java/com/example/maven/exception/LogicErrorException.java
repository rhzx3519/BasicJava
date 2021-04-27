package com.example.maven.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
public class LogicErrorException extends Exception{
    @Getter
    @Setter
    protected String message;

    public LogicErrorException() {
        setMessage("Logic error!");
    }

    public LogicErrorException(String message) {
        this.message = message;
        setMessage(String.format("Logic error: %s !", message));
    }
}
