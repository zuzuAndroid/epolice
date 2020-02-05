package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.WhiteList;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface WhiteListMapper {

    @Select("select * from white_list order by id DESC")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "userRight", column = "user_right"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "illegalType", column = "illegal_type"),
    })
    List<WhiteList> findAll();

    @Select("select w.*,l.name as licenseTypeName from white_list w " +
            "JOIN license_plate_type l on w.license_type = l.code " +
            "where w.display_right=1 order by id DESC")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "userRight", column = "user_right"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "illegalType", column = "illegal_type"),
    })
    List<WhiteList> PublicFindAll();

    @Select("select w.*,l.name as licenseTypeName from white_list w " +
            "JOIN license_plate_type l on w.license_type = l.code " +
            "where w.display_right=1 and license_number=#{number}")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "userRight", column = "user_right"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "illegalType", column = "illegal_type"),
    })
    List<WhiteList> PublicFindByLicenseNumber(String number);

    @Select("select w.*,l.name as licenseTypeName from white_list w " +
            "JOIN license_plate_type l on w.license_type = l.code " +
            "where w.display_right=0 and license_number=#{number}")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "userRight", column = "user_right"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "illegalType", column = "illegal_type"),
    })
    List<WhiteList> privateFindByLicenseNumber(String number);

    @Select("select w.*,l.name as licenseTypeName from white_list w " +
            "JOIN license_plate_type l on w.license_type = l.code " +
            "where w.display_right=0 order by id DESC")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "licenseType", column = "license_type"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "userRight", column = "user_right"),
            @Result(property = "addUser", column = "add_user"),
            @Result(property = "illegalType", column = "illegal_type"),
    })
    List<WhiteList> PrivateFindAll();

    @Update("update white_list set " +
            "license_number=#{licenseNumber}," +
            "license_type=#{licenseType}," +
            "start_date=#{startDate}," +
            "end_date=#{endDate}," +
            "illegal_type=#{illegalType}," +
            "add_user=#{addUser}," +
            "remark=#{remark} " +
            "where id=#{id}")
    Integer updateById(WhiteList params);

    @Select("select count(*) from white_list where license_number=#{number}")
    Integer exists(String number);

    @Insert("insert into white_list (license_number,license_type,create_date," +
            "start_date,end_date,use_right,add_user,illegal_type,remark,display_right) " +
            "values (" +
            "#{licenseNumber}," +
            "#{licenseType}," +
            "#{createDate}," +
            "#{startDate}," +
            "#{endDate}," +
            "#{userRight}," +
            "#{addUser}," +
            "#{illegalType}," +
            "#{remark}," +
            "1)")
    Integer add(WhiteList params);

    @Insert("insert into white_list (license_number,license_type,create_date," +
            "start_date,end_date,use_right,add_user,illegal_type,remark,display_right) " +
            "values (" +
            "#{licenseNumber}," +
            "#{licenseType}," +
            "#{createDate}," +
            "#{startDate}," +
            "#{endDate}," +
            "#{userRight}," +
            "#{addUser}," +
            "#{illegalType}," +
            "#{remark}," +
            "0)")
    Integer addPrivate(WhiteList params);

    @Select("select count(*) from white_list " +
            "where license_number=#{number} " +
            "and to_date(#{dateTime},'yyyy-MM-dd') >= start_date " +
            "and to_date(#{dateTime},'yyyy-MM-dd') <= end_date and display_right=1")
    Integer checkForValide(String number,String dateTime);

    @Delete("delete from white_list where id=#{id}")
    Integer remove(int id);

    @Update("update white_list set " +
            "license_number=#{licenseNumber}," +
            "license_type=#{licenseType}," +
            "start_date=#{startDate}," +
            "end_date=#{endDate}," +
            "illegal_type=#{illegalType}," +
            "add_user=#{addUser}," +
            "remark=#{remark} " +
            "where id=#{id} and display_right=0")
    Integer privateUpdate(WhiteList params);
}
