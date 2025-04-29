package com.canesblack.spring.project1.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

//@Component 한마디로 스프링빈으로 등록하기 위한 라벨링 작업
@Controller
public class PageController {

	// 스프링 시큐리티가 켜져있으면 /login 리다이렉트
	// 1 / 1 로그인하면 index.html로 리다이렉트 되도록 디폴트로 되어있음음
	// The root URL ("/") is handled by Spring Security and redirected to /login by
	// default if security is enabled.

	@GetMapping("/")
	public String home() {
		return "index"; // "index"라는 뷰를 반환 (예: index.html)
	}
	// @GetMapping=>페이지를 조회 및 이동할 때 GetMapping을 써서 이동

	// Handles requests to the registration page at localhost:8080/register

	@GetMapping("/registerPage")
	// request는 통로
	public String registerPage(HttpServletRequest request, Model model) {
		// System.out.println(">>> registerPage() 호출됨");

		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		model.addAttribute("_csrf", csrfToken);

		return "register/index";
	}

	// =>localhost:8080/loginPage

	@GetMapping("/loginPage")
	// request는 통로
	public String loginPage(HttpServletRequest request, Model model) {
		// System.out.println(">>> registerPage() 호출됨");

		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		model.addAttribute("_csrf", csrfToken);

		return "login/index";
	}

}

// com.canesblack.spring.project1 이게 바로 base package이고, @ComponentScan은 생략가능합니다
// 모든 패키지는 com.canesblack.spring.project1 하위에 존재해야합니다!
