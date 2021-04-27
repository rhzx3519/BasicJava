package com.example.maven.bit;

import com.example.maven.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZhengHao Lou
 * @date 2020/8/27
 */
@Slf4j
public class BitTest extends BaseTest {
    @Autowired
    private Car car;

    @Test
    public void test1() {
        log.info("car: {}", car);
    }
}
