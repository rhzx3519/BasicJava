package com.example.maven.service.impl;

import com.example.maven.BaseTest;
import com.example.maven.exception.LogicErrorException;
import com.example.maven.service.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.doReturn;

/**
 * @author ZhengHao Lou
 * @date 2020/06/01
 */
@Slf4j
public class LogicServiceTest extends BaseTest {

    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    @SpyBean
    private LogicService service;

    @Test
    public void testLogic() throws LogicErrorException {
        service.runLogic();
    }

    @Test
    public void testFoo() {
        String res = service.foo();
        log.info(res);
    }

    @Test
    public void testGetName() {
        String name = ReflectionTestUtils.invokeMethod(service, "getName");
        log.info("name: {}", name);
    }

    @Before
    public void mock() {
        //when(ReflectionTestUtils.invokeMethod(service, "getName")).thenReturn("456");
    }
}
