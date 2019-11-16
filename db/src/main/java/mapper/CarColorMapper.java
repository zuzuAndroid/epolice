package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.CarColor;

import java.util.List;

@Mapper
public interface CarColorMapper {

    @Select("select id,code,name from car_color")
    List<CarColor> findAll();

    @Select("select id,code,name from car_color where name=#{name}")
    CarColor findByName(String name);
}
