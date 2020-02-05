package mapper;


import org.apache.ibatis.annotations.*;
import pojo.CarBrand;
import pojo.IllegalCode;

import java.util.List;

@Mapper
public interface IllegalCodeMapper {

    @Select("select id,code,name from illegal_code order by id")
    List<IllegalCode> findAll();

    @Insert("insert into illegal_code (code,name) values (#{code},#{name})")
    Integer add(IllegalCode params);

    @Update("update illegal_code set code=#{code},name=#{name} where id=#{id}")
    Integer update(IllegalCode params);

    @Delete("delete from illegal_code where id=#{id}")
    Integer remove(int id);
}
