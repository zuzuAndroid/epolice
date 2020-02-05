package mapper;

import cache.MybatisRedisCache;
import org.apache.ibatis.annotations.*;
import pojo.V2CheckpointEquipment;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface V2CheckpointEquipmentMapper {

    @Select("select * from v2_checkpoint_equipment order by id")
    @Results({
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2CheckpointEquipment> findAll();

    @Select("select * from v2_checkpoint_equipment where valide=1 order by id")
    @Results({
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2CheckpointEquipment> findByValid();

    @Select("select count(id) from v2_checkpoint_equipment where valide=1")
    Integer validTotal();

    @Select("select * from v2_checkpoint_equipment " +
            "where asset_name like concat('%', #{name}, '%')")
    @Results({
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2CheckpointEquipment> findByName(String name);

    @Select("select * from v2_checkpoint_equipment " +
            "where asset_code like concat('%', #{code}, '%')")
    @Results({
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2CheckpointEquipment> findByCode(String code);

    @Update("update v2_checkpoint_equipment set valide=#{valid},remark=#{remark} where id=#{id}")
    Integer activeEquipment(@Param("id") int id,
                            @Param("valid") int valid,
                            @Param("remark") String remark);

    @Select("select count(*) from v2_checkpoint_equipment where asset_code=#{assetCode}")
    Integer checkExist(String assetCode);

    @Insert("insert into v2_checkpoint_equipment (asset_code,asset_name,direction,asset_ip,company,scope) " +
            "values (" +
            "#{assetCode}," +
            "#{assetName}," +
            "#{direction}," +
            "#{assetIp}," +
            "#{company}," +
            "#{scope}," +
            ")")
    Integer add(V2CheckpointEquipment params);

    @Update("update v2_checkpoint_equipment set valide=#{query.valide},remark=#{query.remark} where asset_code=#{query.assetCode}")
    Integer updateFromExcel(@Param("query") V2CheckpointEquipment params);

    @Insert("insert into v2_checkpoint_equipment (asset_code,asset_name,valide) " +
            "values (" +
            "#{query.assetCode}," +
            "#{query.assetName}," +
            "#{query.valide})")
    Integer addFromExcel(@Param("query") V2CheckpointEquipment params);

    @Update("update v2_checkpoint_equipment set " +
            "asset_code=#{assetCode}," +
            "asset_name=#{assetName}," +
            "direction=#{direction}," +
            "asset_ip=#{assetIp}," +
            "company=#{company}, " +
            "remark=#{remark}, " +
            "scope=#{scope} " +
            "where id=#{id}")
    Integer update(V2CheckpointEquipment params);
}
