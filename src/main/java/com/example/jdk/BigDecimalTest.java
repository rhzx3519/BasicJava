package com.example.jdk;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/12/11
 */
@Slf4j
public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal number = BigDecimal.ZERO;
        log.info("{}", number.add(BigDecimal.valueOf(40)).subtract(BigDecimal.valueOf(10)));
        log.info("{}", number);
    }
}
