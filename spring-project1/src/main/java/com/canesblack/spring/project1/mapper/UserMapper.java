package com.canesblack.spring.project1.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.canesblack.spring.project1.entity.User;

@Mapper
//select만 return 타입입

// 자동으로 @Component기능 비슷하게 스프링컨테이너에 등록이 됨(인터페이스)
// 자바언어와 mysql언어를 통역해주는 역할을 하는 게 UserMapper
public interface UserMapper {

    // CRUD CREATE에 해당하는 기능 중 하나
    @Insert("INSERT INTO `backend_spring_project1`.`user` (username, password, writer, role) "
            + "VALUES (#{username}, #{password}, #{writer}, #{role})")
    void insertUser(User user);
    // void -> 우리가 데이터베이스에서 백엔드 영역(스프링프레임워크)으로 데이터를 가져오는 게 없기 때문에
    // void로 가져오는 게 없다고 작성한다.

    // CRUD READ에 해당하는 기능 중 하나
    @Select("SELECT username,password,writer,role FROM backend_spring_project1.user WHERE username = #{username}")
    User findUserByUsername(String username);

    @Select("SELECT writer FROM backend_spring_project1.user WHERE username = #{username}")
    String findWriter(String username);

    // CRUD UPDATE에 해당하는 기능 중 하나
    // @Update()

    // CRUD UPDATE에 해당하는 기능 중 하나
    // @Delete()

}
