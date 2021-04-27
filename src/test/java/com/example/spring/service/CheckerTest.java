package com.example.spring.service;

import com.example.maven.BaseTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.doReturn;

public class CheckerTest extends BaseTest {

    @SpyBean
    private CheckerImpl checker;

    @Test
    public void test1() {
        doReturn("test1").when(checker).get();
//        Mockito.doNothing().when(checker).get();
        checker.foo();
    }
}
