package mapper;


import org.apache.ibatis.annotations.*;
import pojo.Department;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("select * from department order by id")
    List<Department> findAll();

    @Insert("INSERT INTO department(name) VALUES (#{name})" )
    Integer insert(String name);

    @Select("select count(id) from user_department where name=#{name}")
    Integer isExist(String name);

    @Update("update department set name=#{name} where id=#{id}")
    Integer updateName(@Param("name") String name,
                       @Param("id") int id);

    @Delete("DELETE FROM department WHERE id=#{id}")
    Integer remove(int id);
}
