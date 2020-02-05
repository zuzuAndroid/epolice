package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.AllRoadEquipmentView;

import java.util.List;

@Mapper
public interface AllRoadEquipmentMapper {

    @Select("select * from all_road_equipment_view")
    List<AllRoadEquipmentView> findAll();

    @Select("select * from all_road_equipment_view where period=#{period}")
    List<AllRoadEquipmentView> findByPeriod(int period);

    @Select("select * from all_road_equipment_view where name like concat('%', #{name}, '%')")
    List<AllRoadEquipmentView> search(String name);

    /*@Select({"<script>",
            "select r.name,r.code,count(w.*) as total from all_road_equipment_view r " +
            "JOIN wfcs w on w.sbbh=r.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss')" +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and w.wfdm=#{wfdm} " +
            "</if>" +
            "and name like concat('%', #{name}, '%') and w.sfsh=-1 group by r.code,r.name order by total DESC"+
            " </script>"})*/
    @Select({"<script>",
            "select count(*) as total,cjdz as name,sbbh as code from wfcs " +
            "where to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and wfdm=#{wfdm} " +
            "</if>" +
            "and cjdz like concat('%', #{name}, '%') and sfsh=-1 group by sbbh,cjdz order by total DESC" +
            " </script>"})
    List<AllRoadEquipmentView> searchForWfcs(@Param("name") String name,
                                             @Param("startDate") String startDate,
                                             @Param("endDate") String endDate,
                                             @Param("wfdm") String wfdm);

    /*@Select({"<script>",
            "select r.name,r.code,count(w.*) as total from all_road_equipment_view r " +
            "JOIN jsfp w on w.sbbh=r.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss')" +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and w.wfdm=#{wfdm} " +
            "</if>" +
            "and name like concat('%', #{name}, '%') and w.sfsh=-1 group by r.code,r.name order by total DESC"+
            " </script>"})*/
    @Select({"<script>",
            "select count(*) as total,cjdz as name,sbbh as code from jsfp " +
            "where to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and wfdm=#{wfdm} " +
            "</if>" +
            "and cjdz like concat('%', #{name}, '%') and sfsh=-1 group by sbbh,cjdz order by total DESC" +
            " </script>"})
    List<AllRoadEquipmentView> searchForJsfp(@Param("name") String name,
                                             @Param("startDate") String startDate,
                                             @Param("endDate") String endDate,
                                             @Param("wfdm") String wfdm);

    /*@Select({"<script>",
            "select r.name,r.code,count(w.*) as total from all_road_equipment_view r " +
            "JOIN wffh w on w.sbbh=r.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss')" +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and w.wfdm=#{wfdm} " +
            "</if>" +
            "and name like concat('%', #{name}, '%') and w.sfsh=-1 group by r.code,r.name order by total DESC" +
            " </script>"})*/
    @Select({"<script>",
            "select count(*) as total,cjdz as name,sbbh as code from wffh " +
            "where to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and wfdm=#{wfdm} " +
            "</if>" +
            "and cjdz like concat('%', #{name}, '%') and sfsh=-1 group by sbbh,cjdz order by total DESC" +
            " </script>"})
    List<AllRoadEquipmentView> searchForWffh(@Param("name") String name,
                                             @Param("startDate") String startDate,
                                             @Param("endDate") String endDate,
                                             @Param("wfdm") String wfdm);

    /*@Select({"<script>",
            "select r.name,r.code,count(w.*) as total from all_road_equipment_view r " +
            "JOIN wfcs w on w.sbbh=r.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and w.wfdm=#{wfdm} " +
            "</if>" +
            "and w.sfsh=-1 group by r.code,r.name order by total DESC" +
            " </script>"})*/
    @Select({"<script>",
            "select COUNT(CASE WHEN sfsh=-1 THEN 0 END) as total,cjdz as name,sbbh as code from wfcs " +
            "where to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and wfdm=#{wfdm} " +
            "</if>" +
            "group by sbbh,cjdz order by total DESC" +
            " </script>"})
    List<AllRoadEquipmentView> findAllWithCountForWfcs(@Param("startDate") String startDate,
                                                       @Param("endDate") String endDate,
                                                       @Param("wfdm") String wfdm);

    /*@Select({"<script>",
            "select r.name,r.code,count(w.*) as total from all_road_equipment_view r " +
            "JOIN jsfp w on w.sbbh=r.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and w.wfdm=#{wfdm} " +
            "</if>" +
            "and w.sfsh=-1 group by r.code,r.name order by total DESC" +
            " </script>"})*/
    @Select({"<script>",
            "select COUNT(CASE WHEN sfsh=-1 THEN 0 END) as total,cjdz as name,sbbh as code from jsfp " +
            "where to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and wfdm=#{wfdm} " +
            "</if>" +
            "group by sbbh,cjdz order by total DESC" +
            " </script>"})
    List<AllRoadEquipmentView> findAllWithCountForJsfp(@Param("startDate") String startDate,
                                                       @Param("endDate") String endDate,
                                                       @Param("wfdm") String wfdm);

    /*@Select({"<script>",
            "select r.name,r.code,count(w.*) as total from all_road_equipment_view r " +
            "JOIN wffh w on w.sbbh=r.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss')" +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and w.wfdm=#{wfdm} " +
            "</if>" +
            "and w.username='' " +
            "and w.sfsh=-1 group by r.code,r.name order by total DESC" +
            " </script>"})*/
    @Select({"<script>",
            "select COUNT(CASE WHEN sfsh=-1 THEN 0 END) as total,cjdz as name,sbbh as code from wffh " +
            "where to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='wfdm != null and wfdm.length &gt; 0'>" +
            "and wfdm=#{wfdm} " +
            "</if>" +
            "group by sbbh,cjdz order by total DESC" +
            " </script>"})
    List<AllRoadEquipmentView> findAllWithCountForWffh(@Param("startDate") String startDate,
                                                       @Param("endDate") String endDate,
                                                       @Param("wfdm") String wfdm);
}
