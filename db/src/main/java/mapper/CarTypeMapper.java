package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.CarType;

import java.util.List;

@Mapper
public interface CarTypeMapper {

    @Select("select id,code,name from car_type")
    List<CarType> findAll();
}
