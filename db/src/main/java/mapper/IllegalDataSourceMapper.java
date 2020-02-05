package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pojo.IllegalDataSource;

import java.util.List;

@Mapper
public interface IllegalDataSourceMapper {

    @Select("select id,code,name from illegal_datasource order by id")
    List<IllegalDataSource> findAll();

    @Update("update illegal_datasource set code=#{code},name=#{name} where id=#{id}")
    int update(IllegalDataSource illegalDataSource);

    @Insert("insert into illegal_datasource (code,name) values (#{code},#{name})")
    int add(IllegalDataSource illegalDataSource);

}
