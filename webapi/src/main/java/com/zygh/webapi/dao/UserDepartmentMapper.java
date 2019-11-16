package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.UserDepartment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDepartmentMapper {

    @Select("select * from user_department order by id")
    List<UserDepartment> findAll();

    @Insert("INSERT INTO user_department(name) VALUES (#{name})" )
    Integer insert(String name);

    @Select("select count(id) from user_department where name=#{name}")
    Integer isExist(String name);

    @Update("update user_department set name=#{name} where id=#{id}")
    Integer updateName(String name, int id);

    @Delete("DELETE FROM user_department WHERE id=#{id}")
    Integer remove(int id);

}
