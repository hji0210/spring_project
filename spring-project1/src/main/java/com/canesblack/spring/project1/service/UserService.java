package com.canesblack.spring.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canesblack.spring.project1.entity.User;
import com.canesblack.spring.project1.mapper.UserMapper;

@Service
// 추가 기능을 넣을 떄 사용
public class UserService implements UserInterface {
  // UserService는 UserInterface를 구현하는 클래스입니다.
  // UserMapper를 사용하여 데이터베이스와 상호작용합니다.

  // UserMapper를 주입받기 위한 필드 선언
  // @Autowired는 스프링이 자동으로 의존성을 주입해주는 어노테이션입니다.
  // 의존성 주입
  @Autowired
  private UserMapper userMapper;

  @Override
  public void insertUser(User user) {
    userMapper.insertUser(user);
  }

  @Override
  public String findWriter(String username) {
    return userMapper.findWriter(username);
  }

  @Override
  public User findUserByUsername(String username) {
    return userMapper.findUserByUsername(username);
  }

  // +

}
