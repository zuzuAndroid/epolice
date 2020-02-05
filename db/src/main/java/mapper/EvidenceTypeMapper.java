package mapper;

import org.apache.ibatis.annotations.*;
import pojo.CarBrand;
import pojo.EvidenceType;

import java.util.List;

@Mapper
public interface EvidenceTypeMapper {

    @Select("select id,code,name from evidence_type order by id")
    List<EvidenceType> findAll();

    @Select("select id,code,name from evidence_type where name=#{name}")
    CarBrand findByName(String name);

    @Update("update evidence_type set code=#{code},name=#{name} where id=#{id}")
    Integer update(EvidenceType params);

    @Insert("insert into evidence_type (code,name) values (#{code},#{name})")
    Integer add(EvidenceType params);

    @Delete("delete from evidence_type where id=#{id}")
    Integer remove(int id);
}
