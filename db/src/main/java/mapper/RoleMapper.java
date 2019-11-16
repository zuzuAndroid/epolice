package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("select * from user_role where id = #{id}")
    Role findById(Integer id);

    @Select("select * from user_role")
    List<Role> findAll();
}
