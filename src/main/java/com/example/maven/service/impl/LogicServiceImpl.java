package com.example.maven.service.impl;

import com.example.maven.exception.LogicErrorException;
import com.example.maven.service.LogicService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
@Slf4j
@Service
public class LogicServiceImpl implements LogicService {

    @Data
    private static class Room {
        int no;
    }

    @PostConstruct
    private void setup() {
        log.info("Room: {}", new Room());
        final String msg = MessageFormat.format("{0}", new Room());
        log.info("Room: {}", msg);
    }


    public String runLogic() throws LogicErrorException {
        log.info("visit user test");
        throw new LogicErrorException("run logic com.example.maven.service error");
    }

    @Override
    public String foo() {
        log.info("hello foo");
        return "";
    }

    private String getName() {
        return "123";
    }
}
