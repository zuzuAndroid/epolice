package mapper;

import org.apache.ibatis.annotations.*;
import pojo.CarBrand;
import pojo.CarColor;

import java.util.List;

@Mapper
public interface CarColorMapper {

    @Select("select id,code,name from car_color order by id")
    List<CarColor> findAll();

    @Select("select id,code,name from car_color where name=#{name}")
    CarColor findByName(String name);

    @Insert("insert into car_color (code,name) values (#{code},#{name})")
    Integer add(CarColor params);

    @Update("update car_color set code=#{code},name=#{name} where id=#{id}")
    Integer update(CarColor carColor);

    @Delete("delete from car_color where id=#{id}")
    Integer remove(int id);
}
