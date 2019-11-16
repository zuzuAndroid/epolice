package mapper;

import dto.FirstFilterDto;
import org.apache.ibatis.annotations.*;
import org.springframework.util.StringUtils;
import pojo.FirstFilterData;
import pojo.FirstFilterStatistics;

import java.util.Date;
import java.util.List;

/**
 * 违法初筛
 */
@Mapper
public interface FirstFilterMapper {

    @Select("select * from wfcs where sysid = #{id}")
    FirstFilterData findById(int id);

    /**
     * 根据用户名返回已提取数据
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wfcs w " +
            "LEFT JOIN license_plate_type l on w.hpzl = l.code " +
            "where username = #{name} and sfsh=-1 " +
            "and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by sysid")
    List<FirstFilterData> findByName(String name);

    /**
     * 上一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wfcs w " +
            "LEFT JOIN license_plate_type l on w.hpzl = l.code " +
            "where sfsh=-1 and username = #{username} " +
            "and sysid<#{id} " +
            "and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by sysid limit 1")
    FirstFilterData prev(int id,String username);

    /**
     * 下一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wfcs w " +
            "LEFT JOIN license_plate_type l on w.hpzl = l.code " +
            "where sfsh=-1 and username = #{username} " +
            "and sysid>#{id} " +
            "and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by sysid limit 1")
    FirstFilterData next(int id,String username);

    /**
     * 初筛查询
     */
    @Select({"<script>",
            "select w.*,l.name as licenseType from wfcs w " +
            "LEFT JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.username = #{query.username,jdbcType=VARCHAR} " +
            "and w.sfsh=-1 " +
            "<if test='query.licenseNumber != null and query.licenseNumber &gt; 0'>" +
            "and w.hphm=#{query.licenseNumber} " +
            "</if>" +
            "<if test='query.illegalCode != null and query.illegalCode &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            "and to_date(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_date(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_date(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_date(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            " </script>"})
    List<FirstFilterData> queryForFilter(@Param("query") FirstFilterDto params);

    /**
     * 违法数据查询
     */
    @Select({"<script>",
            "select w.*,r.name as roadName,l.name as licenseType from wfcs w " +
            "LEFT JOIN road_equipment r on w.sbbh = r.id " +
            "LEFT JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.sfsh=#{query.status} " +
            "<if test='query.hpzl != null and query.hpzl &gt; 0'>" +
            "and w.hpzl=#{query.hpzl,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.licenseNumber != null and query.licenseNumber &gt; 0'>" +
            "and w.hphm=#{query.licenseNumber,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.illegalCode != null and query.illegalCode &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            "and to_date(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_date(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_date(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_date(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and not exists (select wl.license_number from white_list wl where w.hphm = wl.license_number and wl.illegal_type in (2,3))" +
            " </script>"})
    List<FirstFilterData> queryForSearch(@Param("query") FirstFilterDto params);

    /**
     * 通过
     */
    @Update("update wfcs set sfsh=0 where sysid=#{id}")
    Integer updatePass(int id);

    /**
     * 通过后，添加到复核表
     */
    @Insert("insert into wffh (msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,period) " +
            "select msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,period " +
            "from wfcs where sysid=#{id}")
    Integer addWffh(int id);

    /**
     * 废弃
     */
    @Update("update wfcs set sfsh=1 where sysid=#{id}")
    Integer updateInvalid(int id);

    /**
     * 提取
     */
    @Update({"<script>",
            "UPDATE wfcs set username = #{query.username} WHERE " +
            "sysid = any(array(select sysid from wfcs w where " +
            "w.username='' " +
            "and w.sfsh=-1 " +
            "<if test='query.hpzl != null and query.hpzl &gt; 0'>" +
            "and w.hpzl=#{query.hpzl,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.licenseNumber != null and query.licenseNumber &gt; 0'>" +
            "and w.hphm=#{query.licenseNumber,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.illegalCode != null and query.illegalCode &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            "and to_date(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_date(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_date(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_date(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') limit #{limit}))" +
            " </script>"})
    Integer pickUp(@Param("query") FirstFilterDto params);

    /**
     * 释放
     */
    @Update("update wfcs set username='',sfsh=-1 WHERE sysid = any(array(select sysid from wfcs where username=#{username}))")
    Integer release(String username);

    /**
     * 初筛统计
     */
    @Select({"<script>",
            "select username,COUNT(CASE WHEN sfsh in (0,1) THEN sfsh END) as total," +
            "COUNT(CASE WHEN sfsh = 0 THEN sfsh END) as passTotal," +
            "COUNT(CASE WHEN sfsh = 1 THEN sfsh END) as invalidTotal from wfcs " +
            "where username != '' " +
            "<if test='illegalCode != 0'>" +
            "and wfdm=#{illegalCode} " +
            "</if>" +
            "and to_date(cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_date(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_date(cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_date(#{endDate},'yyyy-MM-dd hh24:mi:ss') GROUP BY username" +
            " </script>"})
    List<FirstFilterStatistics> count(@Param("illegalCode") String illegalCode,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);


    /**
     * 释放所有未筛选数据
     */
    @Update("update wfcs set username='',sfsh=-1")
    Integer releaseAll();
}
