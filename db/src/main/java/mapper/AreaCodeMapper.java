package mapper;


import org.apache.ibatis.annotations.*;
import pojo.AreaCode;

import java.util.List;

@Mapper
public interface AreaCodeMapper {

    @Select("select hx_number,area from equipment_number order by id")
    @Results({
            @Result(property = "code", column = "hx_number"),
            @Result(property = "name", column = "area"),
    })
    List<AreaCode> findAll();

    @Delete("delete from equipment_number where id=#{id}")
    Integer remove(int id);
}
