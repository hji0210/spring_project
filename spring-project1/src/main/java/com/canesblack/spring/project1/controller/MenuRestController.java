package com.canesblack.spring.project1.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.canesblack.spring.project1.entity.Menu;
import com.canesblack.spring.project1.service.MenuRestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
//데이터를 반환하는 것이라 게시판 안에 있는 데이터들을 전부다 rest api, json형식으로 반환하는 것

// /ResponseEntity는 상태코드와 동시에 JSON타입으로 데이터를 반환 
//=>//(백엔드에서 프론트단으로 데이터를 넘길 수 있는데 데이터 타입으로 넘길 수 있고 동시에 상태 코드도 프론트에서 백엔드로 넘길 수 있음음
public class MenuRestController {

  @Autowired
  private MenuRestService menuRestService;


  //1.메뉴(모든게시판)조회:모든 게시판을 가져온다.
  @GetMapping("/menu/all")
  public   ResponseEntity<List<Menu>> getAllMenus() {
    List<Menu> menus = menuRestService.getLists();
 
    //menus에 값이 존재하고 값이 있을 때 true
    if (menus != null && !menus.isEmpty()) {
     return ResponseEntity.ok(menus);
  } else {
    //게시글이 없을 때
    //공간에 담아서 데이터를 넘겨야 하는데 공간에 아무것도 없는 것
     return ResponseEntity.noContent().build();//상태코드 204번 No Content 반환

   }
  }



  //2.메뉴(한 개의 게시판 생성) 생성 
  //생성은 postmapping으로 요청을 보내야 한다.
  //게시물 작성해서 작성버튼 누르면 서버측으로 데이터 넘어감
  @PostMapping("/menu/add")  
  public ResponseEntity<String> addMenu(@RequestBody Menu menu) {
  //@RequestBody는 프론트에서 백엔드로 데이터를 넘길 떄 공간에 담아서 데이터를 넘김
    //공간에 json형태의 키와 value값 형태의 데이터가 담겨가지고 데이터가 쭉 넘어감
    //프론트에서 백엔드로 menu라는 공간에 담아서 넘기면 키의 값이 전부다 json형태는 키의 값이 전부다 menu클래스에 나온 변수명과 똑같아야한다.


    //프론트에서 넘어온 데이터는 json형태로 넘어오고 그걸 자바의 객체로 변환해주는 역할을 함
    //작성된 시점의 날짜를 자동 설정
      if(menu.getIndate() == null || menu.getIndate().isEmpty()) {
        //게시글 작성 날짜가 없을 때
          menu.setIndate(LocalDate.now().toString());//400번 Bad Request 반환
      }
      //조회수는 처음에는 0으로 설정
      menu.setCount(0);
      //메뉴를 데이터베이스에 삽입 
      menuRestService.boardInsert(menu);
      return ResponseEntity.ok("게시글 잘 작성됨"); //게시글이 잘 작성되었을 때 200번 OK 반환


      //List타입의 메뉴는 스프링부트 프레임워크가 json타입으로 반환, boardInsert(menu)에 있는 menu는 일반 텍스트로 반환 
      //List 메뉴는 일반적으로 스프링부트 프레임워크가 json타입으로 변환시켜줌
      //<String>는 게시글 잘 작성됨"처럼 String타입으라 문자열로 반환
      
  }
  


 

}
