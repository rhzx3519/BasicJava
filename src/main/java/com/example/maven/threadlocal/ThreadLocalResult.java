package com.example.maven.threadlocal;

import lombok.Getter;

public class ThreadLocalResult {

    @Getter
    private int i = 0;

    public static final ThreadLocal<ThreadLocalResult> PASS = ThreadLocal.withInitial(ThreadLocalResult::new);

    public static ThreadLocalResult set(int val) {
        PASS.get().i = val;
        return PASS.get();
    }


}
