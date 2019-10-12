package com.zygh.webapi.dao;

import com.zygh.webapi.pojo.UserAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserAccountMapper {

    /**
     * 登陆
     * @param name
     * @return
     */
    @Select("select u.name,u.pwd from user_account u" +
            " LEFT JOIN user_role r on u.role = r.id" +
            " where u.name=#{name}")
    UserAccount findByName(String name);

    @Select("select u.*,r.name as roleName,d.name as deptName from user_account u" +
            " LEFT JOIN user_role r on u.role = r.id " +
            " LEFT JOIN user_department d on u.department = d.id" +
            " where u.name=#{name}")
    @Results({
            @Result(property = "validDate", column = "valid_date"),
            @Result(property = "idCard", column = "id_card"),
            @Result(property = "policeId", column = "police_id"),
            @Result(property = "pwdDate", column = "pwd_date"),
            @Result(property = "limitIp", column = "limit_ip"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "createDateTime", column = "create_datetime"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "deptName", column = "deptName"),
    })
    UserAccount findOne(String name);

    /**
     * 判断用户是否存在
     * @param user
     * @return
     */
    @Select("select count(id) from user_account where name=#{name}")
    Integer isUserNameExist(UserAccount user);

    @Select("select count(id) from user_account where id_card=#{idCard}")
    Integer isUserIdCardExist(UserAccount user);

    /**
     * 保存
     * @param user
     * @return
     */
    @Insert("insert into user_account (id,name, pwd, role, " +
            "department,valid_date, create_datetime, " +
            "id_card, police_id, pwd_date, start_time, " +
            "end_time, limit_ip)" +
            " values (#{id},#{name},#{pwd},#{role},#{department},#{validDate},#{createDateTime}," +
            "#{idCard},#{policeId},#{pwdDate},#{startTime},#{endTime},#{limitIp})")
    Integer save(UserAccount user);

    @Update("update user_account set " +
            "department=#{department}," +
            "role=#{role}," +
            "police_id=#{policeId}," +
            "valid_date=#{validDate}," +
            "pwd_date=#{pwdDate}," +
            "start_time=#{startTime}," +
            "end_time=#{endTime}," +
            "limit_ip=#{limitIp}," +
            "status=#{status}" +
            " where id=#{id}")
    Integer update(UserAccount user);

    /**
     * 用户列表
     * @return
     */
    @Select("select u.*,r.name as roleName,d.name as deptName from user_account u" +
            " LEFT JOIN user_role r on u.role = r.id " +
            " LEFT JOIN user_department d on u.department = d.id")
    @Results({
            @Result(property = "validDate", column = "valid_date"),
            @Result(property = "idCard", column = "id_card"),
            @Result(property = "policeId", column = "police_id"),
            @Result(property = "pwdDate", column = "pwd_date"),
            @Result(property = "limitIp", column = "limit_ip"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "createDateTime", column = "create_datetime"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "deptName", column = "deptName"),
    })
    List<UserAccount> findAll();
}