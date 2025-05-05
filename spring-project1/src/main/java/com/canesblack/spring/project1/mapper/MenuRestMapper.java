package com.canesblack.spring.project1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import com.canesblack.spring.project1.entity.Menu;

//@Component와 비슷 => @ComponentScan에서 자동으로 스캔하여 Bean으로 등록됨
// @Mapper => @MapperScan을 통해 자동으로 스프링컨테이너에 등록
@Mapper
public interface MenuRestMapper {

  // CRUD의 일부분인데
  // 첫번째 게시글 -> idx:1
  // 두번째 게시글 -> idx:2
  // 세번째 게시글 -> idx:3
  // 세번째 게시글에 가장 먼저 공지사항 페이지에 보여지게 하는 게 DESC

  @Select("SELECT idx, memID, title, content, indate,count FROM backend_spring_project1.menu ORDER BY idx DESC")
  public List<Menu> getLists();
  // 첫번째,두번째,세번째 게시물 쌓이는 형식으로 되서 List형식 => 전체 게시물 가지고 올 때

  @Insert("INSERT INTO backend_spring_project1.menu (memID, title, content, writer) VALUES (#{memID}, #{title}, #{content}, #{writer})")
  // count는 일반적으로 게시글 클릭할 때마다 자동으로 조회수가 증감되고 몇명이 봤는지 확인하는 용도라 넣을 필요가 없다.
  public void boardInsert(Menu menu);

  @Select("SELECT idx, memID, title, content, indate, count FROM backend_spring_project1.menu WHERE idx = #{idx}")
  public Menu boardContent(int idx);

  @Delete("DELETE FROM backend_spring_project1.menu WHERE idx = #{idx}")
  public void boardDelete(int idx);

  // 게시물 작성 날짜 바꾸는거라 작성날짜 그대로 적고 id와 indate(수정날짜)를 바꿀 필요가 없다.
  // 게시글 수정
  @Update("UPDATE menu SET title = #{title}, content = #{content}, writer = #{writer} WHERE idx = #{idx}")
  public void boardUpdate(Menu menu);

  @Update("UPDATE menu SET count = count + 1 WHERE idx = #{idx}")
  public void boardCount(int idx);

}
