package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import pojo.HxWhiteList;

import java.util.List;

@Mapper
public interface HxWhiteListMapper {

    @Select("select * from hx_white_list")
    @Results({
            @Result(property = "licenseNumber", column = "license_number"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "startDate", column = "start_date"),
            @Result(property = "endDate", column = "end_date"),
            @Result(property = "addUser", column = "add_user"),
    })
    List<HxWhiteList> findAll();

    @Select("select * from hx_white_list where license_number=#{number}")
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
}
