package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.GPTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface GPTestMapper {

    @Select("select * from wfcs where id = #{id}")
    GPTest findById(Integer id);

    @Select("select * from wfcs where username = #{username}")
    List<GPTest> findByName(String username);


    @Update("update wfcs set username = #{username} WHERE sysid = any(array(select sysid from wfcs where username='' AND sfsh =-1 " +
            "AND to_date(cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_date('2019-12-01 00:00:00','yyyy-MM-dd hh24:mi:ss') " +
            "AND to_date(cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_date('2019-12-05 00:00:00','yyyy-MM-dd hh24:mi:ss') limit #{pageSize}))")
    Integer update(String username,int pageSize);


    @Update("update wfcs set username='',sfsh = '-1' WHERE sysid = any(array(select sysid from wfcs where username=#{username}))")
    Integer release(String username);
}
