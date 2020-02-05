package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.RoadEquipment;
import pojo.V2CheckpointEquipment;
import pojo.V2RoadEquipment;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface V2RoadEquipmentMapper {

    @Select("select * from v2_road_equipment order by id")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findAll();

    @Select("select * from v2_road_equipment where valide=1 order by id")
    @Results({
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findByValid();

    @Select("select count(id) from v2_road_equipment where valide=1")
    Integer validTotal();

    @Select("select * from v2_road_equipment " +
            "where asset_name like concat('%', #{name}, '%')")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<RoadEquipment> findByName(String name);

    @Select("select * from v2_road_equipment " +
            "where asset_code like concat('%', #{code}, '%')")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findByCode(String code);

    @Select("select * from v2_road_equipment " +
            "where asset_name like concat('%', #{name}, '%')")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> search(String name);

    @Select("select r.id,r.asset_name,r.asset_code,count(w.*) as total from v2_road_equipment r " +
            "JOIN wfcs w on w.sbbh=r.asset_code " +
            "where w.sfsh=-1 and w.period=2 group by r.id,r.asset_code")
    @Results({
            @Result(property = "id", column = "asset_code"),
            @Result(property = "name", column = "asset_name"),
    })
    List<RoadEquipment> findAllWithCountForWfcs();

    @Select("select r.id,r.asset_name,r.asset_code,count(w.*) as total from v2_road_equipment r " +
            "JOIN jsfp w on w.sbbh=r.asset_code " +
            "where w.sfsh=-1 and w.period=2 group by r.id,r.asset_code")
    @Results({
            @Result(property = "id", column = "asset_code"),
            @Result(property = "name", column = "asset_name"),
    })
    List<RoadEquipment> findAllWithCountForJsfp();


    @Select("select r.id,r.asset_name,r.asset_code,count(w.*) as total from v2_road_equipment r " +
            "JOIN wffh w on w.sbbh=r.asset_code " +
            "where w.sfsh=-1 and w.period=2 group by r.id,r.asset_code")
    @Results({
            @Result(property = "id", column = "asset_code"),
            @Result(property = "name", column = "asset_name"),
    })
    List<RoadEquipment> findAllWithCountForWffh();

    @Select("select * from v2_road_equipment where company=#{company} order by id")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findByCompany(String company);

    @Insert("insert into v2_road_equipment (asset_code,asset_name,asset_type,asset_ip,company) " +
            "values (" +
            "#{assetCode}," +
            "#{assetName}," +
            "#{assetType}," +
            "#{assetIp}," +
            "#{company}" +
            ")")
    Integer add(V2RoadEquipment params);

    @Update("update v2_road_equipment set " +
            "asset_name=#{assetName}," +
            "asset_code=#{assetCode},asset_type=#{assetType}," +
            "asset_ip=#{assetIp},company=#{company},remark=#{remark}" +
            "where id=#{id}")
    Integer update(V2RoadEquipment params);

    @Update("update v2_road_equipment set valide=#{valid},remark=#{remark} where id=#{id}")
    Integer activeEquipment(@Param("id") int id,
                            @Param("valid") int valid,
                            @Param("remark") String remark);

    @Select("select count(*) from v2_road_equipment where asset_no=#{assetNo}")
    Integer checkExist(String assetNo);

    @Update("update v2_road_equipment set valide=#{query.valide},remark=#{query.remark} where asset_no=#{query.assetNo}")
    Integer updateFromExcel(@Param("query") V2RoadEquipment params);

    @Insert("insert into v2_road_equipment (asset_no,asset_code,asset_name,valide) " +
            "values (" +
            "#{query.assetNo}," +
            "#{query.assetCode}," +
            "#{query.assetName}," +
            "#{query.valide})")
    Integer addFromExcel(@Param("query") V2RoadEquipment params);

}
