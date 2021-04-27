package com.example.maven.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ErrorMessage<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 100;

    private Integer code;
    private String message;
    private String url;
    private T data;
}