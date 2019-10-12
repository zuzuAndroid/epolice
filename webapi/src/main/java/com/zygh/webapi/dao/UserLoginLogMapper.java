package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.UserLoginLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserLoginLogMapper {

    @Insert("insert into log_login(" +
            "login_name," +
            "user_account_id," +
            "user_role_name," +
            "login_ip," +
            "login_datetime," +
            "login_msg," +
            "check_bit) " +
            "VALUES " +
            "(#{loginName}," +
            "#{userAccountId}," +
            "#{userRoleName}," +
            "#{loginIp}," +
            "#{loginDateTime}," +
            "#{loginMsg}," +
            "#{checkBit})")
    Integer insert(UserLoginLog log);

    @Select("select * from log_login order by id DESC")
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userAccountId", column = "user_account_id"),
            @Result(property = "userRoleName", column = "user_role_name"),
            @Result(property = "loginIp", column = "login_ip"),
            @Result(property = "loginDateTime", column = "login_datetime"),
            @Result(property = "loginMsg", column = "login_msg"),
            @Result(property = "checkBit", column = "check_bit"),
    })
    List<UserLoginLog> findAll();
}
