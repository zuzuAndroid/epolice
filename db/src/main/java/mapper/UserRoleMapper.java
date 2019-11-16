package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.UserRole;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    @Select("select * from user_role where id = #{id}")
    UserRole findById(Integer id);

    @Select("select * from user_role")
    List<UserRole> findAll();
}
