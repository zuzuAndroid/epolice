package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.CarBrand;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface CarBrandMapper {

    @Select("select id,code,name from car_brand order by id")
    List<CarBrand> findAll();

    @Select("select id,code,name from car_brand where name=#{name}")
    CarBrand findByName(String name);

    @Update("update car_brand set code=#{code},name=#{name} where id=#{id}")
    Integer update(CarBrand carBrand);

    @Insert("insert into car_brand (code,name) values (#{code},#{name})")
    Integer add(CarBrand params);

    @Delete("delete from car_brand where id=#{id}")
    Integer remove(int id);
}
