package mapper;

import org.apache.ibatis.annotations.*;
import pojo.CarBrand;
import pojo.Role;
import pojo.UserRole;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("select * from user_role where id = #{id}")
    @Results({
            @Result(property = "permissionId", column = "permission_id"),
            @Result(property = "permissionName", column = "permission_name"),
    })
    Role findById(Integer id);

    @Select("select * from user_role order by id")
    @Results({
            @Result(property = "permissionId", column = "permission_id"),
            @Result(property = "permissionName", column = "permission_name"),
    })
    List<UserRole> findAll();

    @Update("update user_role set " +
            "name=#{name}," +
            "permissions=#{permissions}," +
            "permission_id=#{permissionId}, " +
            "permission_name=#{permissionName} " +
            "where id=#{id}")
    Integer update(Role params);

    @Insert("insert into user_role (name,permissions,permission_id,permission_name) values" +
            " (#{name},#{permissions},#{permissionId},#{permissionName})")
    Integer add(Role params);

    @Delete("delete from user_role where id=#{id}")
    Integer remove(int id);
}
