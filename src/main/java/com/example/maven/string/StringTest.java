package com.example.maven.string;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author ZhengHao Lou
 * @date 2020/05/28
 */
@Slf4j
public class StringTest {

    public static void main(String[] args) {
        String str = "UD1U.SGX";
        List<String> strSplit = Splitter.on(".").splitToList(str);
        log.info("{}", strSplit);
        String strDigit = "20.00";

        int strToint= Optional.ofNullable(strDigit)
                .map(Doubles::tryParse)
                .orElse(0.0).shortValue();
        log.info("{}", strToint);
        log.info("{}", StringUtils.isNumeric("123.0"));

        boolean sameDirection = (Double.doubleToLongBits(0.0) ^ Double.doubleToLongBits(1.0)) >= 0;
        log.info("{}", sameDirection);

        List<String> l1 = Lists.newArrayList("l");
        List<String> l2 = Lists.newArrayList("l");
        log.info("{}, {}", l1.hashCode(), l2.hashCode());
    }
}
