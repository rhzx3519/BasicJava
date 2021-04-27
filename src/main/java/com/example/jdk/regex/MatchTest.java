package com.example.jdk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2021/01/29
 */
@Slf4j
public class MatchTest {

    @Test
    public void test1() {
        String content = "gta(1)";

        String pattern = "[a-z]+\\([0-9]+\\)";

        boolean isMatch = Pattern.matches(pattern, content);
        log.info("{}", isMatch);

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        if (m.find()) {
           log.info(m.group(0));
        }

    }

}
