package mapper;

import org.apache.ibatis.annotations.*;
import pojo.WhiteList;

import java.util.List;

@Mapper
public interface WhiteListMapper {

    @Select("select w.*,l.name as licenseTypeName from white_list w " +
            "LEFT JOIN license_plate_type l on w.license_type = l.code " +
            "where w.display_right=1")
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
            "LEFT JOIN license_plate_type l on w.license_type = l.code " +
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
            "LEFT JOIN license_plate_type l on w.license_type = l.code " +
            "where w.display_right=0")
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
            "illegal_type=#{illegalType} " +
            "where id=#{id}")
    Integer updateById(WhiteList params);

    @Select("select count(*) from white_list where license_number=#{number}")
    Integer exists(String number);

    @Insert("insert into white_list (license_number,license_type,create_date,start_date,end_date,user_right,add_user,illegal_type,remark) " +
            "values (" +
            "#{licenseNumber}," +
            "#{licenseType}," +
            "#{createDate}," +
            "#{startDate}," +
            "#{endDate}," +
            "#{userRight}," +
            "#{addUser}," +
            "#{illegalType}," +
            "#{remark})")
    Integer add(WhiteList params);
}
