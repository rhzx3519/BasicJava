package com.example.maven.powermockito;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ZhengHao Lou
 * @date 2020/7/12
 */
@Slf4j
@Service
public class TextFetcher {

    public String parse() {
        return download();
    }

    private String download() {
        return "text";
    }
}
