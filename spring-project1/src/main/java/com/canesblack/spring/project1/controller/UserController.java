package com.canesblack.spring.project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.canesblack.spring.project1.entity.Role;
import com.canesblack.spring.project1.entity.User;
import com.canesblack.spring.project1.service.UserService;



@Controller
public class UserController {


  @Autowired
    private UserService userService;

   //생성자 주입
   //@Autowired
   //public UserController(UserService userService) {
    //  this.userService = userService;


  @Autowired
  private PasswordEncoder passwordEncoder;

   

   @PostMapping("/register")
   //regist->index에 있는 register.html에서 post방식으로 요청을 보냄
   public String register(@ModelAttribute User user) {
    //input에 입력한 값들(아이디,비밀번호,작성자)이 User 객체에 담김
    //담기게 하는 기능이 @ModelAttribute
    
    String userPassword = user.getPassword();
    System.out.println("userPassword: " + userPassword);
    user.setRole(Role.MEMBER);

    String passwordEncoded = passwordEncoder.encode(userPassword);
    user.setPassword(passwordEncoded);


    userService.insertUser(user);

      return "redirect:/loginPage"; 
      //데이터를 받으면 끝이 아니라 등록이 되면 회원가입페이지로 로그인되게 해줘야함
      //redirect:/loginPage는 pageController의 loginPage로 이동
      



   }



  

} 
