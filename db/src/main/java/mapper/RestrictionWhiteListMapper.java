package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.RestrictionWhiteList;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface RestrictionWhiteListMapper {

    @Select("select r.*,l.name as licenseTypeName from restriction_white_list r " +
            "JOIN license_plate_type l on r.hpzl = l.code order by r.id DESC")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
    })
    List<RestrictionWhiteList> findAll();

    @Select("select r.*,l.name as licenseTypeName from restriction_white_list r " +
            "JOIN license_plate_type l on r.hpzl = l.code " +
            "where r.license_number=#{number}")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
    })
    List<RestrictionWhiteList> findByLicenseNumber(String number);

    @Select("select r.*,l.name as licenseTypeName from restriction_white_list r " +
            "JOIN license_plate_type l on r.hpzl = l.code " +
            "where r.remark like concat('%', #{value}, '%') " +
            "order by r.id DESC")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
    })
    List<RestrictionWhiteList> findByRemark(String value);

    @Select("select count(*) from restriction_white_list where license_number=#{number}")
    Integer exists(String number);

    @Insert("insert into restriction_white_list " +
            "(license_number,create_date,remark,remark2,hpzl,add_user,start_date,end_date) " +
            "values (" +
            "#{licenseNumber}," +
            "#{createDate}," +
            "#{remark}," +
            "#{remark2}," +
            "#{hpzl}," +
            "#{addUser}," +
            "#{startDate}," +
            "#{endDate}" +
            ")")
    Integer add(RestrictionWhiteList params);

    @Update("update restriction_white_list set " +
            "license_number=#{licenseNumber}," +
            "hpzl=#{hpzl}," +
            "remark=#{remark}, " +
            "remark2=#{remark2}, " +
            "add_user=#{addUser}, " +
            "start_date=#{startDate}, " +
            "end_date=#{endDate} " +
            "where id=#{id}")
    Integer update(RestrictionWhiteList params);

    @Delete("delete from restriction_white_list where id=#{id}")
    Integer remove(int id);
}
