package org.jxh.project.jmeoptimize.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import org.jxh.project.jmeoptimize.pojo.user;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.*;
import java.util.List;

@Mapper
public interface userDao {

    @Select("SELECT * FROM user WHERE name=#{name} AND password=#{password}")
    user login(@Param("name")String name, @Param("password")String password);

    @Delete("DELETE FROM user WHERE id=#{id}")
    void deleteUser(@Param("id")int id);

   @Insert("INSERT INTO user (name,password,role) VALUES (#{name},123,#{role})")
    void addUser(@Param("name")String name,@Param("role")String password);

   @Select("SELECT COUNT(*) FROM user WHERE name=#{name}")
    int existUser(@Param("name")String name);

   @Select("SELECT id,name ,role FROM user")
    List<JSONObject> init();

   @Update("UPDATE user SET password=#{password} WHERE id=#{id}")
    void updatePassword(@Param("id")int id,@Param("password")String password);
}
