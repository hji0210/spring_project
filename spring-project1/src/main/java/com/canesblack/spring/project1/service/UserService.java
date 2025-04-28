package com.canesblack.spring.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canesblack.spring.project1.entity.User;
import com.canesblack.spring.project1.mapper.UserMapper;

@Service
//추가 기능을 넣을 떄 사용
public class UserService {
//의존성 주입
  @Autowired
  private UserMapper userMapper;
  


  
  public void insertUser(User user) {
    userMapper.insertUser(user);
  }
 

   public String findWriter(String username) {        
   return userMapper.findWriter(username);
   }


    
  public User findUserByUsername(String username) {
    return userMapper.findUserByUsername(username);
  
}

}
