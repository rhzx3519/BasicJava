package com.example.maven.exception;

import com.example.maven.BaseTest;
import com.example.maven.service.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
@Slf4j
public class ExceptionHandlerTest extends BaseTest {

    @Autowired
    private LogicService logicService;

    @Test
    public void testCatcheException() throws LogicErrorException {
        logicService.runLogic();
    }

    @Test
    public void test2() {
        try {
            for (int i = 0; i < 10; i++) {
                log.info("{}", foo(i));
            }
        } catch (Throwable throwable) {
            log.error("error", throwable);
            return;
        }

    }

    private String foo(int idx) {
        if (idx == 5) {
            throw new RuntimeException("runtime foo");
        }
        return "" + idx;
    }

}
