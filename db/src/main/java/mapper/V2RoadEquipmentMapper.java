package mapper;

import org.apache.ibatis.annotations.*;
import pojo.V2RoadEquipment;

import java.util.List;

@Mapper
public interface V2RoadEquipmentMapper {

    @Select("select * from v2_road_equipment")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findAll();

    @Select("select * from v2_road_equipment where id=#{id}")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findOne(int id);

    @Select("select * from v2_road_equipment where company=#{company}")
    @Results({
            @Result(property = "assetNo", column = "asset_no"),
            @Result(property = "assetName", column = "asset_name"),
            @Result(property = "assetCode", column = "asset_code"),
            @Result(property = "assetType", column = "asset_type"),
            @Result(property = "assetIp", column = "asset_ip"),
    })
    List<V2RoadEquipment> findByCompany(String company);

    @Update("update v2_road_equipment set " +
            "asset_no=#{assetNo},asset_name=#{assetName}," +
            "asset_code=#{assetCode},asset_type=#{assetType},asset_ip=#{assetIp} " +
            "where id=#{id}")
    Integer update(V2RoadEquipment params);
}
