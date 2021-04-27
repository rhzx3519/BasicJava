package com.example.spring.inject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ZhengHao Lou
 * @date 2021/01/07
 */
@Slf4j
@Order(1)
@Component
public class ImplTwo implements Base {
    @Override
    public void hello() {
        log.info("I'm impl two.");
    }
}
