package mapper;

import cache.MybatisRedisCache;
import dto.FilterCountDto;
import dto.FirstFilterDto;
import org.apache.ibatis.annotations.*;
import pojo.FirstFilterData;
import pojo.FirstFilterStatistics;

import java.util.List;

/**
 * 违法初筛
 */
@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface FirstFilterMapper {

    @Select("select  w.*,l.name as licenseType from wfcs w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.sysid=#{id}")
    FirstFilterData findById(int id);

    /**
     * 根据用户名返回已提取数据
     */
    @Options(useCache = true)
    @Select("select w.*,l.name as licenseType from wfcs w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.username=#{name} and w.sfsh=-1 "
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            //"order by w.sysid"
            )
    List<FirstFilterData> findByName(@Param("name") String name);

    /**
     * 上一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wfcs w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.sfsh=-1 and w.username = #{username} " +
            "and w.sysid<#{id} " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by w.sysid limit 1")
    FirstFilterData prev(int id,String username);

    /**
     * 下一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wfcs w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where sfsh=-1 and username = #{username} " +
            "and sysid>#{id} " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by sysid limit 1")
    FirstFilterData next(int id,String username);

    /**
     * 初筛查询
     */
    @Select({"<script>",
            "select w.*,l.name as licenseType from wfcs w " +
            //"left JOIN user_account u on u.nickname=w.username " +
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
            "<if test='query.hpzl != null and query.hpzl.length &gt; 0'>" +
            "and w.hpzl=#{query.hpzl,jdbcType=VARCHAR} " +
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
            //"and sysid IN (SELECT max(sysid) FROM wfcs GROUP BY hphm, cjsj, wfdm, sbbh) " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number) " +
            " </script>"})
    List<FirstFilterData> queryForFilter(@Param("query") FirstFilterDto params);

    /**
     *  统计
     */
    @Select({"<script>",
            "select COUNT(CASE WHEN sfsh = -1 and username!='' THEN 0 END) as pickupTotal," +
            "COUNT(CASE WHEN sfsh = 0 and to_char(screendate,'YYYY-MM-DD')=#{today} and username=#{username} THEN 0 END) as todayPass," +
            "COUNT(CASE WHEN sfsh = 1 and to_char(screendate,'YYYY-MM-DD')=#{today} and username=#{username}  THEN 0 END) as todayInvalid from wfcs " +
            //"where username=#{username} " +
            " </script>"})
    FilterCountDto todayCount(@Param("username") String username,
                              @Param("today") String today);

    /**
     * 违法数据查询
     */
    @Select({"<script>",
            "select w.*,r.name as roadName,l.name as licenseType from wfcs w " +
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
    List<FirstFilterData> queryForSearch(@Param("query") FirstFilterDto params);

    /**
     * 通过
     */
    @Update("update wfcs set sfsh=0,screendate=now() where sysid=#{id}")
    Integer updatePass(int id);

    /**
     * 通过后，添加到复核表
     *
     * -------废弃--------
     */
    @Insert("insert into wffh (msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period) " +
            "select msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period " +
            "from wfcs where sysid=#{id}")
    Integer addWffh(int id);


    /**
     * 自动复制
     * 定时任务:从初筛复制到复核表
     * @return
     */
    @Insert("insert into wffh (msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period) " +
            "select msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period " +
            "from wfcs where to_wffh=0 and sfsh=0 and to_date(to_char(screendate,'yyyy-MM-DD'),'yyyy-MM-DD')=CURRENT_DATE-1")
    Integer copyToWffh();

    /**
     *  更新同步状态
     */
    @Update("update wfcs set to_wffh=1 " +
            "where to_wffh=0 and sfsh=0 and to_date(to_char(screendate,'yyyy-MM-DD'),'yyyy-MM-DD')=CURRENT_DATE-1")
    Integer
    updateCopyToWffh();

    /**
     * 手动复制
     * 查找要复制到复核表的*已通过数据*
     */
    @Select("select * from wfcs where sfsh=0 " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_wffh=0"
            )
    List<FirstFilterData> findToWffh(@Param("startDate") String startDate,
                                    @Param("endDate") String endDate);


    @Update("update wfcs set to_wffh=1 where sfsh=0 " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_wffh=0")
    Integer updateToWffh(@Param("startDate") String startDate,
                         @Param("endDate") String endDate);


    @Insert("insert into wffh (msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period) " +
            "select msgid,hphm,hpzl,cjsj,wfdm,cjdz,sbbh,url1,url2,period " +
            "from wfcs where sfsh=0 " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') >= to_timestamp(#{startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(cjsj,'yyyy-MM-dd hh24:mi:ss') <= to_timestamp(#{endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_wffh=1")
    Integer copyToWffhByManual(@Param("startDate") String startDate,
                               @Param("endDate") String endDate);

    /**
     * 作废
     */
    @Update("update wfcs set sfsh=1,screendate=now() where sysid=#{id}")
    Integer updateInvalid(int id);

    /**
     *  提取前判断
     */
    @Select("select count(*) from wfcs where username=#{username} and sfsh=-1")
    Integer countByUsername(@Param("username") String username);

    /**
     * 提取
     */
    @Update({"<script>",
            "UPDATE wfcs set username = #{query.username} WHERE " +
            "sysid = any(array(select sysid from wfcs w where " +
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
            //"and sysid IN (SELECT max(sysid) FROM wfcs GROUP BY hphm, cjsj, wfdm, sbbh) " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') limit #{query.limit}))" +
            " </script>"})
    Integer pickUp(@Param("query") FirstFilterDto params);

    /**
     * 释放
     */
    @Update("update wfcs set username='' WHERE sysid = any(array(select sysid from wfcs where username=#{username} and sfsh=-1))")
    Integer release(@Param("username") String username);

    /**
     * 初筛统计
     */
    @Select({"<script>",
            "select r.name as username,COUNT(CASE WHEN w.sfsh in (0,1) THEN 0 END) as total," +
            "COUNT(CASE WHEN w.sfsh = 0 THEN 0 END) as passTotal," +
            "COUNT(CASE WHEN w.sfsh = -1 THEN 0 END) as pickupTotal," +
            "COUNT(CASE WHEN w.sfsh = 1 THEN 0 END) as invalidTotal from wfcs w " +
            "JOIN user_account r on w.username = r.nickname " +
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
    @Update("update wfcs set username='' where username != '' and sfsh=-1")
    Integer releaseAll();

    /**
     * 根据ID修改车牌
     */
    @Update("update wfcs set hphm=#{hphm} where sysid=#{id}")
    Integer updateLicenseNumberById(@Param("hphm") String hphm,
                                    @Param("id") int id);


    /**
     * 根据ID修改号牌种类
     */
    @Update("update wfcs set hpzl=#{hpzl} where sysid=#{id}")
    Integer updateLicenseTypeById(@Param("hpzl") String hpzl,
                                  @Param("id") int id);


    /**
     * 根据车牌,时间查询出来其他违法数据
     */
    @Options(useCache = false)
    @Select("select hphm,cjdz,cjsj,wfdm,sfsh from wfcs " +
            "where sysid!=#{id} and hphm=#{hphm} and to_date(cjsj,'yyyy-MM-dd')=to_date(#{dateTime},'yyyy-MM-dd')")
    List<FirstFilterData> findByHphm(@Param("id") int id,
                                     @Param("hphm") String hphm,
                                     @Param("dateTime") String dateTime);

    /**
     * 重复数据检查
     */
    @Select("select count(*) from wfcs where " +
            "sysid not in (SELECT min(sysid) FROM wfcs GROUP BY hphm,cjsj,wfdm,sbbh) " +
            "and to_date(cjsj,'yyyy-MM-dd') = to_date(#{dateTime},'yyyy-MM-dd')")
    Integer checkDuplicate(@Param("dateTime") String dateTime);

    /**
     *  计划任务
     *  标记重复数据
     */
    @Update("update wfcs set sfsh=-2 where sysid not in (SELECT min(sysid) FROM wfcs GROUP BY hphm,cjsj,wfdm,sbbh)")
    Integer findDuplicate();


    /**
     * 清理已处理过的数据
     */
    @Delete("delete from wfcs where sfsh=-2")
    Integer clean();
}
