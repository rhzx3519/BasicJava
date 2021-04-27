package com.example.spring.inject;

import com.example.maven.BaseTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

/**
 * @author ZhengHao Lou
 * @date 2021/01/07
 */
public class InjectTet extends BaseTest {

    @SpyBean
    private Service service;

    @Test
    public void test1() {
        service.say();
    }

}
