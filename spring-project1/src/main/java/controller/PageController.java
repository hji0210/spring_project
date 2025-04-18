package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Component 한마디로 스프링빈으로 등록하기 위한 라벨링 작업
@Controller

public class PageController {
	
	
	@GetMapping("/")
	//localhost:8080
	public String returnHome() {
		return "index";
	}
	//@GetMapping=>페이지를 조회 및 이동할 때 GetMapping을 써서 이동
	
	
	

	

}
