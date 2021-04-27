package com.example.maven.generic;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author ZhengHao Lou
 * @date 2020/05/28
 */

abstract class A {
    protected abstract <T> T foo();

    protected abstract <T> T boo(T t);

    protected abstract <T> void bar(T t);
}





abstract class Person {

    public abstract <E extends Object> void setName(E name);

    public abstract <E> E getName();
}

public class LeartGeneric {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
    }
}
