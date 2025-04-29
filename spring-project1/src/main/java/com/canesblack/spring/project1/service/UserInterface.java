package com.canesblack.spring.project1.service;

import com.canesblack.spring.project1.entity.User;

public interface UserInterface {

  public void insertUser(User user);

  public String findWriter(String username);

  public User findUserByUsername(String username);

}
