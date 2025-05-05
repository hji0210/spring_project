package com.canesblack.spring.project1.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.canesblack.spring.project1.entity.Menu;
import com.canesblack.spring.project1.service.MenuRestService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


//Java에서 메서드 이름은 각 클래스(또는 인터페이스) 안에서만 고유하면 됩니다.

//Controller 클래스 안에서는 updateMenu()
//Service 클래스 안에서는 boardUpdate()

//Mapper 인터페이스 안에서는 boardUpdate()
//이처럼 각 계층에서 서로 다른 이름을 써도 작동에 아무 지장이 없습니다.



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
  

  //3.메뉴(한 개의 게시판 수정) 수정
  @PutMapping("/menu/update/{idx}")
  //게시판 수정은 putmapping으로 요청을 보내야 한다.
  //localhost:8080/menu/update/1로 요청을 보내면 1번 게시판이 수정됨
  public void updateMenu(@RequestBody Menu menu ,@PathVariable("idx") int idx) {

    menu.setIdx(idx);//특정 idx를 가진 게시글을 menu안의 title과 content,writer를 가져와서 수정해준다.
    menuRestService.boardUpdate(menu);//게시글 수정 메소드 호출
  }

 //4.메뉴(한 개의 게시판 삭제)삭제 
  @DeleteMapping("/menu/delete/{idx}")
  //게시판 삭제는 postmapping으로 요청을 보내야 한다.
  //localhost:8080/menu/delete/1로 요청을 보내면 1번 게시판이 삭제됨
  public void deleteMenu(@PathVariable("idx") int idx) {
    menuRestService.boardDelete(idx);//게시글 삭제 메소드 호출
  }


   //1번에 전체조회는 없는데 특정 메뉴 조회는 없음
 // 5. 특정 메뉴(게시글 하나) 조회
@GetMapping("/menu/{idx}")
public ResponseEntity<Menu> getMenuById(@PathVariable("idx") int idx) {
  // idx에 해당하는 하나의 게시글(Menu)을 조회
  Menu menu = menuRestService.boardContent(idx);

  if (menu != null) {
    // 게시글이 존재하면 상태 코드 200(OK)과 함께 menu 데이터를 JSON 형태로 응답
    return ResponseEntity.ok(menu);
  } else {
    // 게시글이 존재하지 않으면 상태 코드 404(Not Found)를 응답
    return ResponseEntity.notFound().build();
  }
}


    //6.특정 메뉴(게시판 조회수 증가)조회수 증가, 게시판을 증가하면 하나씩 증가시키는 기능
  @PutMapping("/menu/count/{idx}")
  public void incrementMenuCount(@PathVariable("idx") int idx) {
    menuRestService.boardCount(idx); //게시글 조회수 증가 메소드 호출
//200번대의 상태코드와 "조회수 증가 완료"라는 문자열을 백엔드에서 프론트엔드영역으로 데이터를 넘김

  }
 

}
