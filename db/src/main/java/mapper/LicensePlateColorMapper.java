package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.LicensePlateColor;

import java.util.List;

@Mapper
public interface LicensePlateColorMapper {

    @Select("select * from license_plate_color order by id")
    List<LicensePlateColor> findAll();

    @Update("update license_plate_color set code=#{code},name=#{name} where id=#{id}")
    Integer update(LicensePlateColor params);

    @Insert("insert into license_plate_color (code,name) values (#{code,jdbcType=TINYINT},#{name})")
    Integer add(LicensePlateColor params);
}
