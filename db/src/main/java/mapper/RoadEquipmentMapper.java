package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.RoadEquipment;

import java.util.List;

@Mapper
public interface RoadEquipmentMapper {

    @Select("select * from road_equipment order by id")
    List<RoadEquipment> findAll();

    @Select("select r.id,r.name,count(w.*) as total from road_equipment r " +
            "JOIN wfcs w on w.sbbh=r.id " +
            "where w.sfsh=-1 and w.period=1 and r.name like concat('%', #{name}, '%') group by r.id")
    List<RoadEquipment> findByName(String name);

    @Select("select r.id,r.name,count(w.*) as total from road_equipment r " +
            "JOIN wfcs w on w.sbbh=r.id " +
            "where w.sfsh=-1 and w.period=1 group by r.id")
    List<RoadEquipment> findAllWithCountForWfcs();

    @Select("select r.id,r.name,count(w.*) as total from road_equipment r " +
            "JOIN jsfp w on w.sbbh=r.id " +
            "where w.sfsh=-1 and w.period=1 group by r.id")
    List<RoadEquipment> findAllWithCountForJsfp();

    @Select("select r.id,r.name,count(w.*) as total from road_equipment r " +
            "JOIN wffh w on w.sbbh=r.id " +
            "where w.sfsh=-1 and w.period=1 group by r.id")
    List<RoadEquipment> findAllWithCountForWffh();
}
