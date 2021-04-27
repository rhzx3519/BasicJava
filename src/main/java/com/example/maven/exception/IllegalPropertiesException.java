package com.example.maven.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
public class IllegalPropertiesException extends Exception {
    @Getter
    @Setter
    protected String message;

    public IllegalPropertiesException() {
        setMessage("Prop is illegal!");
    }

    public IllegalPropertiesException(String message) {
        this.message = message;
        setMessage(String.format("Prop: %s is illegal!", message));
    }
}