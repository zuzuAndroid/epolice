package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.Home;

import java.util.List;

/**
 * 首页统计
 */
@Mapper
public interface HomeMapper {

    @Select("select " +
            "COUNT(CASE WHEN sfsh in (-1,0,1) THEN sfsh END) as total," +
            "COUNT(CASE WHEN sfsh = 0 THEN sfsh END) as passTotal, " +
            "COUNT(CASE WHEN sfsh = 1 THEN sfsh END) as invalidTotal " +
            "from wfcs")
    Home countTotal();

    @Select("select " +
            "COUNT(CASE WHEN sfsh in (-1,0,1) THEN sfsh END) as total," +
            "COUNT(CASE WHEN sfsh = 0 THEN sfsh END) as passTotal, " +
            "COUNT(CASE WHEN sfsh = 1 THEN sfsh END) as invalidTotal " +
            "from wfcs where to_date(cjsj,'yyyy-MM-dd')=CURRENT_DATE")
    Home todayCountTotal();

    @Select("select " +
            "COUNT(CASE WHEN valide in (0,1) THEN valide END) as total, " +
            "COUNT(CASE WHEN valide = 1 THEN valide END) as activeTotal " +
            "from v2_road_equipment")
    Home countV2Equipment();

    @Select("select " +
            "COUNT(CASE WHEN valide in (0,1) THEN valide END) as total, " +
            "COUNT(CASE WHEN valide = 1 THEN valide END) as activeTotal " +
            "from v2_checkpoint_equipment")
    Home countV2Checkpoint();

    @Select("select " +
            "COUNT(CASE WHEN valide in (0,1) THEN valide END) as total, " +
            "COUNT(CASE WHEN valide = 1 THEN valide END) as activeTotal " +
            "from v2_illegal_parking_equipment")
    Home countV2Parking();

    @Select("select to_date(cjsj,'yyyy-MM-dd') as datetime," +
            "COUNT(CASE WHEN sfsh = 0 THEN sfsh END) as passTotal," +
            "COUNT(CASE WHEN sfsh = 1 THEN sfsh END) as invalidTotal from wfcs " +
            "where to_date(cjsj,'yyyy-MM-dd') BETWEEN CURRENT_DATE - interval '9 day' and CURRENT_DATE and sfsh=0 " +
            "group by datetime order by to_date(cjsj,'yyyy-MM-dd') limit 7")
    List<Home> countByDay();

    @Select("select to_date(cjsj,'yyyy-MM-dd') as datetime," +
            "COUNT(CASE WHEN sfsc=1 THEN 0 END) as total from v2_illegal_parking " +
            "where to_date(cjsj,'yyyy-MM-dd') BETWEEN CURRENT_DATE - interval '9 day' and CURRENT_DATE and sfsc=1 " +
            "group by datetime order by to_date(cjsj,'yyyy-MM-dd') limit 7")
    List<Home> countV2ParkingUpload();

    @Select("select to_date(cjsj,'yyyy-MM-dd') as datetime," +
            "COUNT(CASE WHEN sfsc=1 THEN 0 END) as total from v2_restriction " +
            "where to_date(cjsj,'yyyy-MM-dd') BETWEEN CURRENT_DATE - interval '9 day' and CURRENT_DATE " +
            "group by datetime order by to_date(cjsj,'yyyy-MM-dd') limit 7")
    List<Home> countV2RestrictionUpload();
}