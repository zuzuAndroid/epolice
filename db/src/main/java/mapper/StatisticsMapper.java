package mapper;

import dto.FirstFilterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.RoadStatistics;

import java.util.List;

@Mapper
public interface StatisticsMapper {

    /**
     * 路口统计
     */
    @Select("select count(*) as total,r.name as roadName from wfcs w " +
            "LEFT JOIN road_equipment r on w.sbbh = r.id " +
            "where " +
            "to_date(cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_date(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_date(cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_date(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "GROUP BY r.name")
    List<RoadStatistics> roadCount(FirstFilterDto params);
}
