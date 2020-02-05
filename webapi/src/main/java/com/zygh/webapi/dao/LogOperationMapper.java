package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.LogOperation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogOperationMapper {

    @Select("select * from log_operation order by id DESC")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userIp", column = "user_ip"),
            @Result(property = "operType", column = "oper_type"),
            @Result(property = "operEvent", column = "oper_event"),
            @Result(property = "operUrl", column = "oper_url"),
            @Result(property = "operDateTime", column = "oper_datetime"),
    })
    List<LogOperation> findAll();

    @Insert("insert into log_operation (id,user_name," +
            "user_ip,oper_type," +
            "oper_event,oper_url,oper_datetime)" +
            " values " +
            "(#{id},#{userName},#{userIp}," +
            "#{operType},#{operEvent},#{operUrl},#{operDateTime})")
    Integer insert(LogOperation logOperation);
}
