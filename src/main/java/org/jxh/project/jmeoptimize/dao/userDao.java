package org.jxh.project.jmeoptimize.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jxh.project.jmeoptimize.pojo.user;

@Mapper
public interface userDao {

    @Select("SELECT * FROM user WHERE name=#{name} AND password=#{password}")
    user login(@Param("name")String name, @Param("password")String password);
}
