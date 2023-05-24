package com.mybatis.mapper;

import com.mybatis.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

// 为什么需要加 Repository 注解?
// @Repository是Spring框架提供的一种注解, 在DAO接口中注解了@Repository之后，在Service层才能利用Spring的容器注入,当然这个注解也不是必须的，
// 1、spring配置文件中配置了MapperScannerConfigurer这个bean，它会扫描持久层接口创建实现类并交给spring管理。
// 2、接口上使用了@Mapper注解或者springboot中主类上使用了@MapperScan注解，和MapperScannerConfigurer作用一样。
@Repository
public interface UserMapper {
  int insertUser(User user);

  User getByUserNameAndPassword(User user);

  List<User> getUserList();
}
