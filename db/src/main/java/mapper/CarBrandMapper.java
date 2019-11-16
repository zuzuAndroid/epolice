package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.CarBrand;

import java.util.List;

@Mapper
public interface CarBrandMapper {

    @Select("select id,code,name from car_brand")
    List<CarBrand> findAll();

    @Select("select id,code,name from car_brand where name=#{name}")
    CarBrand findByName(String name);

    @Update("update car_brand set code=#{code},name=#{name} where id=#{id}")
    Integer update(CarBrand carBrand);

    @Insert("insert into car_brand (code,name) values (#{code},#{name})")
    Integer add(CarBrand params);
}
