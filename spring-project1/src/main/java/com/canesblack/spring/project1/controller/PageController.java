package com.canesblack.spring.project1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.canesblack.spring.project1.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

//@Component 한마디로 스프링빈으로 등록하기 위한 라벨링 작업
@Controller
public class PageController {
	@Autowired
//스프링 컨테이너 공간에 있는 기존에 만들어놨던 컨트롤러 같은 컴포넌트 객체 어노테이션을 가지고 있는 클래스 객체들을 
// 스프링 컨테이너에 담음 그럼 자바의 변수와 메서드를 빼서 쓸 때 @Autowired쓴 다음에 쓸 수 있음 
  private UserService userService; // UserService 객체를 주입받음




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



// com.canesblack.spring.project1 이게 바로 base package이고, @ComponentScan은 생략가능합니다
// 모든 패키지는 com.canesblack.spring.project1 하위에 존재해야합니다!


//모델 => 한번 데이터 옮기면 뷰가 새로고침 되면서 회면이 뜨면 끝나는 것, 데이터가 남아있지 않고 사라짐
//데이터가 한번 전달되고 뷰에 데이터가 저장이 된 다음 게시판 추가 페이지에 데이터가 전달되면 화면이 새로고침되는 것
//새로고침된 데이터는 유효하고 그 이후에 데이터는 사라짐


//세션 => 백엔드에서 프론트로 데이터를 넘기는데 서버에 계속 데이터가 남아있음
 //프론트 단에서도 계속해서 그 데이터를 가지고 유지할 수 있다. 한번 로그인하면 로그아웃할 때까지 계속 세션은 유지됨
@GetMapping("/noticeAddPage")
public String noticeAddPage() {
	//springConfig => session에서 username가져와서 접근 가능 
	 
			return "noticeAdd/index";






}
}

