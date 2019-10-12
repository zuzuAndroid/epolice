package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.LoginSetup;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginSetupMapper {

    @Select("select * from login_setup limit 1")
    @Results({
            @Result(property = "pwdLevel", column = "pwd_level"),
            @Result(property = "userLock", column = "user_lock"),
            @Result(property = "terminalLock", column = "terminal_lock"),
            @Result(property = "userTotal", column = "user_total"),
    })
    LoginSetup find();

    @Update("update login_setup set pwd_level=#{pwdLevel}," +
            "user_lock=#{userLock}," +
            "terminal_lock=#{terminalLock}," +
            "user_total=#{userTotal}")
    Integer update(LoginSetup setup);
}
