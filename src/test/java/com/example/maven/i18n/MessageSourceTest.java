package com.example.maven.i18n;

import com.example.maven.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author ZhengHao Lou
 * @date 2020/9/18
 */
@Slf4j
public class MessageSourceTest extends BaseTest {
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    @Test
    public void test1() {
        //String key = messageSource.getMessage("hhhh", null, Locale.CHINA);
        LocaleContextHolder.setLocale(Locale.US);
        String msg = messageSource.getMessage("order.modify.reject.immutable.triggeredOrder.param",
                buildArgumentResolvable("stopPrice"), LocaleContextHolder.getLocale());
        log.info(msg);
        String s = messageSource.getMessage("algo.test", null, Locale.US);
        log.info(s);
    }

    private Object[] buildArgumentResolvable(String... args) {
        return Arrays.stream(args).map(arg -> new DefaultMessageSourceResolvable(new String[]{arg}, arg))
                .collect(Collectors.toList()).toArray();
    }

    public static String getLang() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.getLanguage() + "_" + locale.getCountry();
    }

}
