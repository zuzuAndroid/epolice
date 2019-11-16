package mapper;

import org.apache.ibatis.annotations.*;
import pojo.UserAccount;
import pojo.UserInfo;

import java.util.List;

@Mapper
public interface UserAccountMapper {

    @Select("select *,r.name as roleName,d.name as deptName from user_account u " +
            "LEFT JOIN user_role r on u.role_id = r.id " +
            "LEFT JOIN user_department d on u.dept_id = d.id ")
    @Results({
            @Result(property = "deptId", column = "dept_id"),
            @Result(property = "roleId", column = "role_id"),
    })
    List<UserInfo> findAll();

    @Select("select * from user_account " +
            "where nickname=#{nickname}")
    UserAccount findByNickname(String nickname);

    @Select("select *,r.name as roleName,d.name as deptName from user_account u " +
            "LEFT JOIN user_role r on u.role_id = r.id " +
            "LEFT JOIN user_department d on u.dept_id = d.id " +
            "where nickname=#{nickname}")
    @Results({
            @Result(property = "deptId", column = "dept_id"),
            @Result(property = "roleId", column = "role_id"),
    })
    UserInfo getUserByNickname(String nickname);

    @Insert("insert into user_account (name,nickname,role_id,dept_id,phone,password) values (" +
            "#{name}," +
            "#{nickname}," +
            "#{roleId}," +
            "#{deptId}," +
            "#{phone}," +
            "#{password})")
    Integer addUser(UserAccount user);

    @Update("update user_account set role_id=#{roleId},dept_id=#{deptId},phone=#{phone} where id=#{id}")
    Integer updateUser(UserAccount user);
}
