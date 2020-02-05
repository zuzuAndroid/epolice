package mapper;

import org.apache.ibatis.annotations.*;
import pojo.CarBrand;
import pojo.CarType;

import java.util.List;

@Mapper
public interface CarTypeMapper {

    @Select("select id,code,name from car_type order by id")
    List<CarType> findAll();

    @Update("update car_type set code=#{code},name=#{name} where id=#{id}")
    Integer update(CarType carBrand);

    @Insert("insert into car_type (code,name) values (#{code},#{name})")
    Integer add(CarType params);

    @Delete("delete from car_type where id=#{id}")
    Integer remove(int id);
}
