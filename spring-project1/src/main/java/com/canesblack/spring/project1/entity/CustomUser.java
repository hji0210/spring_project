package com.canesblack.spring.project1.entity;

import org.springframework.security.core.authority.AuthorityUtils;

public class CustomUser extends org.springframework.security.core.userdetails.User {

  private User user;

  public CustomUser(User user) {
    //entity에 있는 username이 userDetails 클래스에 있는 스프링 시큐리티 자체에 있는 
    // user클래스에 매개변수에 우리가 만든 userclass네임을 합치는 것
    super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
    this.user = user;
  }


}
