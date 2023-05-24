package com.mybatis.controller;

import com.mybatis.entity.Response;
import com.mybatis.entity.User;
import com.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("user/insert")
  public Response insertUser(@RequestBody User user) {
    try {
      int result = userService.insertUser(user);
      return Response.success(result);
    } catch(Exception e) {
      return Response.failure(500, "服务器异常");
    }
  }

  @PostMapping("user/getByUserNameAndPassword")
  public Response getByUserNameAndPassword(@RequestBody User user) {
    try {
      User result = userService.getByUserNameAndPassword(user);
      return Response.success(result);
    } catch(Exception e) {
      return Response.failure(500, "服务器异常");
    }
  }

  @GetMapping("user/getUserList")
  public Response getUserList() {
    try {
      List<User> result = userService.getUserList();
      return Response.success(result);
    } catch(Exception e) {
      return Response.failure(500, "服务器异常");
    }
  }
}
