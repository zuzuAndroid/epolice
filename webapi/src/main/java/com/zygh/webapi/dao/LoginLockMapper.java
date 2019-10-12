package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.LoginLock;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LoginLockMapper {

    @Insert("insert into user_login_lock (lock_type,lock_content,datetime,locked)" +
            " values (#{lockType},#{lockContent},#{dateTime},#{locked})")
    Integer insert(LoginLock lock);

    @Select("select * from user_login_lock")
    @Results({
            @Result(property = "lockType", column = "lock_type"),
            @Result(property = "lockContent", column = "lock_content"),
            @Result(property = "dateTime", column = "dateTime"),
    })
    List<LoginLock> findAll();

    @Update("update user_login_lock set locked=#{locked} where id=#{id}")
    Integer update(LoginLock lock);
}
