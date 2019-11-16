package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.RoadEquipment;

import java.util.List;

@Mapper
public interface RoadEquipmentMapper {

    @Select("select * from road_equipment")
    List<RoadEquipment> findAll();

    @Select("select * from road_equipment where name like CONCAT('%',#{name},'%')")
    List<RoadEquipment> findByName(String name);

    @Select("select r.id,r.name,count(w.*) as total from road_equipment r " +
            "LEFT JOIN wfcs w on w.sbbh=r.id " +
            "where w.sfsh=-1 group by r.id")
    List<RoadEquipment> findAllWithCount();
}
