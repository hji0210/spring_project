package com.canesblack.spring.project1.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.canesblack.spring.project1.entity.CustomUser;
import com.canesblack.spring.project1.entity.User;

@Service
public class UserDetailsServicelmpl implements UserDetailsService {

  @Autowired
  // mapper파일에서 mapper라는 어노테이션 컴포넌트 대신 할 수 있는데
  // 이게 지금 스프링컨테이너에 등록이 되어서 쓸 수가 있음
  private UserMapper userMapper;

  @Override
  // /username을 통해서 로그인 시도(아이디:1입력하면 1이 담기는 것)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // UserDetails => 스프링 시큐리티만을 위한 로그인 기능을 사용하기 위한 공간

    // username 1이 들어감
    User user = userMapper.findUserByUsername(username);
    if (user == null) {
      // 데이터가 없을 때
      throw new UsernameNotFoundException(username + "존재하지 않습니다.");
    }
    // 로그인했을 때 디비에 로그인데이터가 즉, 유저정보가 존재할 시에는
    return new CustomUser(user);

  }
}