package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.DirectionCode;

import java.util.List;

@Mapper
public interface DirectionCodeMapper {

    @Select("select * from direction_code order by id")
    List<DirectionCode> findAll();

    @Update("update direction_code set code=#{code},name=#{name} where id=#{id}")
    Integer update(DirectionCode params);

    @Insert("insert into direction_code (code,name) values (#{code},#{name})")
    Integer add(DirectionCode params);
}
