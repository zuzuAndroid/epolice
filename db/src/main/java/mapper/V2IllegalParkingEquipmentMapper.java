package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.V2IllegalParkingEquipment;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface V2IllegalParkingEquipmentMapper {

    @Select("select * from v2_illegal_parking_equipment order by id")

    List<V2IllegalParkingEquipment> findAll();

    @Select("select * from v2_illegal_parking_equipment " +
            "where name like concat('%', #{name}, '%')")
    List<V2IllegalParkingEquipment> search(String name);

    @Select("select * from v2_illegal_parking_equipment where valide=1 order by id")
    List<V2IllegalParkingEquipment> findByValid();

    @Select("select count(id) from v2_illegal_parking_equipment where valide=1")
    Integer validTotal();

    @Select("select * from v2_illegal_parking_equipment " +
            "where name like concat('%', #{name}, '%')")
    List<V2IllegalParkingEquipment> findByName(String name);

    @Select("select * from v2_illegal_parking_equipment " +
            "where code like concat('%', #{code}, '%')")
    List<V2IllegalParkingEquipment> findByCode(String code);

    @Update("update v2_illegal_parking_equipment set valide=#{valid},remark=#{remark} where id=#{id}")
    Integer activeEquipment(@Param("id") int id,
                            @Param("valid") int valid,
                            @Param("remark") String remark);

    @Select("select count(*) from v2_illegal_parking_equipment where code=#{code}")
    Integer checkExist(String assetCode);

    @Update("update v2_illegal_parking_equipment set valide=#{query.valide},remark=#{query.remark} where code=#{query.code}")
    Integer updateFromExcel(@Param("query") V2IllegalParkingEquipment params);

    @Insert("insert into v2_illegal_parking_equipment (code,name,valide) " +
            "values (" +
            "#{query.code}," +
            "#{query.name}," +
            "#{query.valide})")
    Integer addFromExcel(@Param("query") V2IllegalParkingEquipment params);

    @Insert("insert into v2_illegal_parking_equipment (code,name,remark) " +
            "value (" +
            "#{code}," +
            "#{name}," +
            "#{remark}" +
            ")")
    Integer add(V2IllegalParkingEquipment params);

    @Update("update v2_illegal_parking_equipment set " +
            "code=#{code}," +
            "name=#{name}," +
            "remark=#{remark} " +
            "where id=#{id}")
    Integer update(V2IllegalParkingEquipment params);
}
