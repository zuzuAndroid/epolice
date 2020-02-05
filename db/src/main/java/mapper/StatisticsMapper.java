package mapper;

import cache.MybatisRedisCache;
import dto.FirstFilterDto;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.RoadStatistics;
import pojo.RoadStatisticsReport;
import pojo.RoadTotalStatistics;

import java.util.List;

@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface StatisticsMapper {

    @Select("select count(*) as total,r.name as v1roadName,r2.asset_name as v2roadName from wfcs w " +
            "JOIN road_equipment r on w.sbbh = r.id " +
            "JOIN v2_road_equipment r2 on w.sbbh = r2.asset_code " +
            "where " +
            "to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "GROUP BY r.name,r2.asset_name")
    List<RoadStatistics> roadCount(FirstFilterDto params);

    /**
     * 一期路口统计
     */
    @Select("select count(*) as total,r.name as roadName,r.vendor_name as company from wfcs w " +
            "JOIN road_equipment r on w.sbbh = r.id " +
            "where w.period=1 " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "GROUP BY r.name,r.vendor_name")
    List<RoadStatistics> v1RoadCount(FirstFilterDto params);


    /**
     * 二期路口统计
     */
    @Select("select count(*) as total,r2.asset_name as roadName,r2.company from wfcs w " +
            "JOIN v2_road_equipment r2 on w.sbbh = r2.asset_code " +
            "where w.period=2 and r2.valide=1 " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "GROUP BY r2.asset_name,r2.company")
    List<RoadStatistics> v2RoadCount(FirstFilterDto params);


    @Select({"<script>",
            "select t.cjdz," +
            "COUNT(CASE WHEN t.sfsh in (-1,0,1) THEN 0 END) as total," +
            "count(CASE WHEN t.sfsh = 0 THEN 0 END) as peopleTotal," +
            "count(t1.sbbh) as alogTotal," +
            "count(t2.sbbh) as reviewTotal from " +
            "(select sbbh,cjdz,sfsh,cjsj,period from wfcs) t " +
            "left join (select sbbh,cjdz from jsfp where sfsh=0 " +
            "<if test='query.period != 0'>" +
            "and period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            ") t1 on t.sbbh=t1.sbbh " +
            "join (select sbbh,cjdz from wffh where sfsh=0 " +
            "<if test='query.period != 0'>" +
            "and period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            ") t2 on t.sbbh=t2.sbbh " +
            "where to_timestamp(t.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(t.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='query.period != 0'>" +
            "and t.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            "group by t.cjdz" +
            " </script>"})
    List<RoadTotalStatistics> roadTotalCount(@Param("query") FirstFilterDto params);


    @Select({"<script>",
            "select t.cjdz," +
            "COUNT(CASE WHEN t.sfsh in (-1,0,1) THEN 0 END) as total," +
            "count(CASE WHEN t.sfsh = 0 THEN 0 END) as peopleTotal," +
            "count(t1.sbbh) as alogTotal," +
            "count(t2.sbbh) as reviewTotal " +
            "from " +
            "(select sbbh,cjdz,sfsh,cjsj,period from wfcs) t " +
            "left join (select sbbh,cjdz from jsfp where sfsh=0 " +
            "<if test='query.period != 0'>" +
            "and period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            ") t1 on t.sbbh=t1.sbbh " +
            "join (select sbbh,cjdz from wffh where sfsh=0 " +
            "<if test='query.period != 0'>" +
            "and period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            ") t2 on t.sbbh=t2.sbbh " +
            "where to_timestamp(t.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(t.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='query.period != 0'>" +
            "and t.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            " group by t.cjdz"+
            " </script>"})
    List<RoadStatisticsReport> roadTotalReport(@Param("query") FirstFilterDto params);
}
