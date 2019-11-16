package mapper;

import org.apache.ibatis.annotations.*;
import pojo.RestrictionWhiteList;

import java.util.List;

@Mapper
public interface RestrictionWhiteListMapper {

    @Select("select * from restriction_white_list")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
    })
    List<RestrictionWhiteList> findAll();

    @Select("select * from restriction_white_list where license_number=#{number}")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
    })
    List<RestrictionWhiteList> findByLicenseNumber(String number);

    @Select("select count(*) from restriction_white_list where license_number=#{number}")
    Integer exists(String number);

    @Insert("insert into restriction_white_list (license_number,create_date,remark) " +
            "values (" +
            "#{licenseNumber}," +
            "#{createDate}," +
            "#{remark})")
    Integer add(RestrictionWhiteList params);
}
