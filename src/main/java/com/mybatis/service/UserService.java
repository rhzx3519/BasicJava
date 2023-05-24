package com.mybatis.service;

import com.mybatis.entity.User;
import com.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
  UserMapper userMapper;

  public int insertUser(User user) {
    return userMapper.insertUser(user);
  }

  public User getByUserNameAndPassword(User user) {
    return userMapper.getByUserNameAndPassword(user);
  }

  public List<User> getUserList() {
    return userMapper.getUserList();
  }
}
