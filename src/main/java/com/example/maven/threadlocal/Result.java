package com.example.maven.threadlocal;

import lombok.Getter;

public class Result {
    @Getter
    private int i = 0;

    public static final Result PASS = new Result();

    public static Result set(int val) {
        PASS.i = val;
        return PASS;
    }
}
