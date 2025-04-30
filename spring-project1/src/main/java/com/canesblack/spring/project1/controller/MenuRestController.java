package com.canesblack.spring.project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.canesblack.spring.project1.service.MenuRestService;



@RestController
public class MenuRestController {

  @Autowired
  private MenuRestService menuRestService;




}
