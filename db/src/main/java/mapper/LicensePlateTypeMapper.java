package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.LicensePlateType;

import java.util.List;

@Mapper
public interface LicensePlateTypeMapper {

    @Select("select * from license_plate_type")
    List<LicensePlateType> findAll();

    @Update("update license_plate_type set code=#{code},name=#{name} where id=#{id}")
    Integer update(LicensePlateType params);

    @Insert("insert into license_plate_type (code,name) values (#{code},#{name})")
    Integer add(LicensePlateType params);
}
