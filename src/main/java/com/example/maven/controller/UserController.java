package com.example.maven.controller;

import com.example.maven.config.LoadConfig;
import com.example.maven.data.User;
import com.example.maven.exception.IllegalPropertiesException;
import com.example.maven.exception.LogicErrorException;
import com.example.maven.exception.NullOrEmptyException;
import com.example.maven.exception.SessionNotFoundException;
import com.example.maven.service.LogicService;
import com.example.maven.springboot.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZhengHao Lou
 * @date 2020/05/21
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LogicService service;

    @PostMapping("user")
    public ResponseEntity<?> save(HttpServletRequest request, HttpSession session) throws Exception {
        String sessionId = (String) session.getAttribute("sessionId");
        if (StringUtils.isBlank(sessionId)) {
            throw new SessionNotFoundException();
        }

        String userPlainText = request.getParameter("user");
        if (StringUtils.isBlank(userPlainText) || StringUtils.equalsIgnoreCase("{}", userPlainText)) {
            throw new NullOrEmptyException();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userPlainText, User.class);

        if (StringUtils.isBlank(user.getName())) {
            throw new IllegalPropertiesException("username");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            throw new IllegalPropertiesException("password");
        }
        return ResponseEntity.ok("Successful");
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() throws LogicErrorException {
        String res = service.runLogic();
        log.info(res);
        return "hello";
    }

    @GetMapping("/userbean")
    @ResponseBody
    public String userbean() {
        User user = BeanUtil.getBean("User");
        user.setName("Jim");
        log.info("hello {}", user.getName());
        return user.getName();
    }

}