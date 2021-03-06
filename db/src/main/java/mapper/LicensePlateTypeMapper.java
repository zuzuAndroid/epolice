package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.LicensePlateType;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface LicensePlateTypeMapper {

    @Select("select * from license_plate_type order by code")
    List<LicensePlateType> findAll();

    @Update("update license_plate_type set code=#{code},name=#{name} where id=#{id}")
    Integer update(LicensePlateType params);

    @Insert("insert into license_plate_type (code,name) values (#{code},#{name})")
    Integer add(LicensePlateType params);
}
