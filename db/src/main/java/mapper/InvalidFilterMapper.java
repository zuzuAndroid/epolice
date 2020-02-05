package mapper;

import cache.MybatisRedisCache;
import dto.FilterCountDto;
import dto.FirstFilterDto;
import org.apache.ibatis.annotations.*;
import pojo.FirstFilterData;
import pojo.FirstFilterStatistics;
import pojo.InvalidFilter;

import java.util.List;

/**
 * 机筛废片
 */
@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface InvalidFilterMapper {

    @Select("select  w.*,l.name as licenseType from jsfp w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.id = #{id}")
    InvalidFilter findById(int id);

    /**
     *  统计
     */
    @Select({"<script>",
            "select COUNT(CASE WHEN sfsh = -1 and username!='' THEN 0 END) as pickupTotal," +
            "COUNT(CASE WHEN sfsh = 0 and to_char(screendate,'YYYY-MM-DD')=#{today} and username=#{username} THEN 0 END) as todayPass," +
            "COUNT(CASE WHEN sfsh = 1 and to_char(screendate,'YYYY-MM-DD')=#{today} and username=#{username} THEN 0 END) as todayInvalid from jsfp " +
            //"where username=#{username} " +
            " </script>"})
    FilterCountDto todayCount(@Param("username") String username,
                              @Param("today") String today);

    /**
     * 根据用户名返回已提取数据
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from jsfp w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where username = #{name} and sfsh=-1 " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by w.id")
    List<InvalidFilter> findByName(String name);

    /**
     * 上一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from jsfp w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where sfsh=-1 and username = #{username} " +
            "and id<#{id} " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by w.id limit 1")
    InvalidFilter prev(int id,String username);

    /**
     * 下一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from jsfp w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where sfsh=-1 and username = #{username} " +
            "and id>#{id} " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by w.id limit 1")
    InvalidFilter next(int id,String username);

    /**
     * 初筛查询
     */
    @Select({"<script>",
            "select w.*,l.name as licenseType from jsfp w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where  " +
            "to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='query.username != null and query.username.length &gt; 0'>" +
            "and w.username=#{query.username,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.username == null'>" +
            "and w.username='' " +
            "</if>" +
            "<if test='query.status != null'>" +
            "and w.sfsh=#{query.status,jdbcType=TINYINT} " +
            "</if>" +
            "<if test='query.road != null and query.road.length &gt; 0'>" +
            "and w.sbbh=#{query.road,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.licenseNumber != null and query.licenseNumber.length &gt; 0'>" +
            "and w.hphm=#{query.licenseNumber,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.illegalCode != null and query.illegalCode.length &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number) " +
            " </script>"})
    List<InvalidFilter> queryForFilter(@Param("query") FirstFilterDto params);

    /**
     * 违法数据查询
     */
    @Select({"<script>",
            "select w.*,r.name as roadName,l.name as licenseType from jsfp w " +
            "JOIN road_equipment r on w.sbbh = r.id " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where " +
            "to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='query.status != null'>" +
            "and w.sfsh=#{query.status} " +
            "</if>" +
            "<if test='query.hpzl != null and query.hpzl.length &gt; 0'>" +
            "and w.hpzl=#{query.hpzl,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.licenseNumber != null and query.licenseNumber.length &gt; 0'>" +
            "and w.hphm=#{query.licenseNumber,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.illegalCode != null and query.illegalCode.length &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            "and not exists (select wl.license_number from white_list wl where w.hphm = wl.license_number)" +
            " </script>"})
    List<InvalidFilter> queryForSearch(@Param("query") FirstFilterDto params);

    /**
     * 通过
     */
    @Update("update jsfp set sfsh=0,screendate=now() where id=#{id}")
    Integer updatePass(int id);

    /**
     * 通过后，添加到复核表
     */
    @Insert("insert into wffh (msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period) " +
            "select msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period " +
            "from jsfp where id=#{id}")
    Integer addWffh(int id);

    /**
     * 定时任务:从初筛复制到复核表
     * @return
     */
    @Insert("insert into wffh (msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period) " +
            "select msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period " +
            "from jsfp where sfsh=0 and to_date(to_char(screendate,'yyyy-MM-DD'),'yyyy-MM-DD')=CURRENT_DATE-1")
    Integer copyToWffh();

    /**
     * 废弃
     */
    @Update("update jsfp set sfsh=1,screendate=now() where id=#{id}")
    Integer updateInvalid(int id);

    /**
     *  提取前判断
     */
    @Select("select count(*) from jsfp where username=#{username} and sfsh=-1")
    Integer countByUsername(@Param("username") String username);

    /**
     * 提取
     */
    @Update({"<script>",
            "UPDATE jsfp set username = #{query.username} WHERE " +
            "id = any(array(select id from jsfp w where " +
            "w.username='' " +
            "and w.sfsh=-1 " +
            "<if test='query.hpzl != null and query.hpzl.length &gt; 0'>" +
            "and w.hpzl=#{query.hpzl,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.licenseNumber != null and query.licenseNumber.length &gt; 0'>" +
            "and w.hphm=#{query.licenseNumber,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.illegalCode != null and query.illegalCode.length &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.road != null and query.road.length &gt; 0'>" +
            "and w.sbbh=#{query.road,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            "and id IN (SELECT max(id) FROM jsfp GROUP BY hphm, cjsj, wfdm, sbbh) " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') limit #{query.limit}))" +
            " </script>"})
    Integer pickUp(@Param("query") FirstFilterDto params);

    /**
     * 释放
     */
    @Update("update jsfp set username='' WHERE id = any(array(select id from jsfp where username=#{username} and sfsh=-1))")
    Integer release(@Param("username") String username);

    /**
     * 初筛统计
     */
    @Select({"<script>",
            "select r.name as username,COUNT(CASE WHEN w.sfsh in (0,1) THEN sfsh END) as total," +
                    "COUNT(CASE WHEN w.sfsh = 0 THEN sfsh END) as passTotal," +
                    "COUNT(CASE WHEN w.sfsh = -1 THEN sfsh END) as pickupTotal," +
                    "COUNT(CASE WHEN w.sfsh = 1 THEN sfsh END) as invalidTotal from jsfp w " +
                    "LEFT JOIN user_account r on w.username = r.nickname " +
                    "where w.username != '' " +
                    "<if test='query.illegalCode != null and query.illegalCode.length &gt; 0'>" +
                    "and w.wfdm=#{query.illegalCode} " +
                    "</if>" +
                    "<if test='query.period != 0'>" +
                    "and w.period=#{query.period,jdbcType=TINYINT} " +
                    "</if>" +
                    "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
                    "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') GROUP BY r.name" +
                    " </script>"})
    List<FirstFilterStatistics> count(@Param("query") FirstFilterDto params);


    /**
     * 释放所有未筛选数据
     */
    @Update("update jsfp set username='' where username != '' and sfsh=-1")
    Integer releaseAll();

    /**
     * 根据ID修改车牌
     */
    @Update("update jsfp set hphm=#{hphm} where id=#{id}")
    Integer updateLicenseNumberById(@Param("hphm") String hphm,
                                    @Param("id") int id);

    /**
     * 根据ID修改号牌种类
     */
    @Update("update jsfp set hpzl=#{hpzl} where id=#{id}")
    Integer updateLicenseTypeById(@Param("hpzl") String hpzl,
                                  @Param("id") int id);

    /**
     * 根据车牌,时间查询出来其他违法数据
     */
    @Options(useCache = false)
    @Select("select hphm,cjdz,cjsj,wfdm,sfsh from jsfp " +
            "where id!=#{id} and hphm=#{hphm} and to_date(cjsj,'yyyy-MM-dd')=to_date(#{dateTime},'yyyy-MM-dd')")
    List<InvalidFilter> findByHphm(@Param("id") int id,
                                   @Param("hphm") String hphm,
                                   @Param("dateTime") String dateTime);

    /**
     * 清理已处理过的数据
     */
    @Delete("delete from jsfp where sfsh != -1 and username != ''")
    Integer clean();
}
