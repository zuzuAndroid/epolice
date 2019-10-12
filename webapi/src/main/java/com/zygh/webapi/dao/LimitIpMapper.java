package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.LimitIp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LimitIpMapper {

    @Select("select * from limit_ip")
    List<LimitIp> findAll();

    @Insert("insert into limit_ip (ip) values (#{ip})")
    Integer insert(String ip);

    @Update("update limit_ip set ip=#{ip} where id=#{id}")
    Integer update(LimitIp ip);
}
