package mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.IllegalCode;

import java.util.List;

@Mapper
public interface IllegalCodeMapper {

    @Select("select id,code,name from illegal_code")
    List<IllegalCode> findAll();

    @Insert("insert into illegal_code (code,name) values (#{code},#{name})")
    Integer add(IllegalCode params);
}
