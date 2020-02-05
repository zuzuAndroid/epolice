package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.HxWhiteList;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface HxWhiteListMapper {

    @Select("select r.*,l.name as licenseTypeName from hx_white_list r " +
            "JOIN license_plate_type l on r.hpzl = l.code order by r.id DESC")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "addUser", column = "add_user"),
    })
    List<HxWhiteList> findAll();

    @Select("select r.*,l.name as licenseTypeName from hx_white_list r " +
            "JOIN license_plate_type l on r.hpzl = l.code " +
            "where r.license_number=#{number}")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "addUser", column = "add_user"),
    })
    List<HxWhiteList> findByLicenseNumber(String number);

    @Select("select count(*) from hx_white_list where license_number=#{number}")
    Integer exists(String number);

    @Insert("insert into hx_white_list (license_number,hpzl,create_date,remark,start_date,end_date,add_user) " +
            "values (" +
            "#{licenseNumber}," +
            "#{hpzl}," +
            "#{createDate}," +
            "#{remark}," +
            "#{startDate}," +
            "#{endDate}," +
            "#{addUser})")
    Integer add(HxWhiteList params);

    @Update("update hx_white_list set " +
            "license_number=#{licenseNumber}," +
            "hpzl=#{hpzl}," +
            "start_date=#{startDate}," +
            "end_date=#{endDate}," +
            "add_user=#{addUser}," +
            "remark=#{remark} " +
            "where id=#{id}")
    Integer update(HxWhiteList params);

    @Delete("delete from hx_white_list where id=#{id}")
    Integer remove(int id);
}
