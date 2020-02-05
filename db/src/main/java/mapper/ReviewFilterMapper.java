package mapper;

import cache.MybatisRedisCache;
import dto.FilterCountDto;
import dto.FirstFilterDto;
import org.apache.ibatis.annotations.*;
import pojo.FirstFilterStatistics;
import pojo.ReviewFilter;

import java.util.List;


/**
 * 复核
 */
@Mapper
@CacheNamespace(eviction = MybatisRedisCache.class,implementation = MybatisRedisCache.class)
public interface ReviewFilterMapper {

    @Select("select  w.*,l.name as licenseType from wffh w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.id = #{id}")
    ReviewFilter findById(int id);

    /**
     *  统计
     */
    @Select({"<script>",
            "select COUNT(CASE WHEN sfsh = -1 and username!='' THEN sfsh END) as pickupTotal," +
            "COUNT(CASE WHEN sfsh = 0 and to_char(screendate,'YYYY-MM-DD')=#{today} and username=#{username} THEN sfsh END) as todayPass," +
            "COUNT(CASE WHEN sfsh = 1 and to_char(screendate,'YYYY-MM-DD')=#{today} and username=#{username}  THEN sfsh END) as todayInvalid from wffh " +
            //"where username=#{username} " +
            " </script>"})
    FilterCountDto todayCount(@Param("username") String username,
                              @Param("today") String today);

    /**
     * 根据用户名返回已提取数据
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wffh w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.username = #{name} and w.sfsh=-1 " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by w.id")
    List<ReviewFilter> findByName(String name);

    /**
     * 上一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wffh w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.sfsh=-1 and w.username=#{username} " +
            "and w.id<#{id} " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by id limit 1")
    ReviewFilter prev(int id,String username);

    /**
     * 下一条
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from wffh w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where w.sfsh=-1 and w.username = #{username} " +
            "and w.id>#{id} " +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number and wl.illegal_type in (2,3)) " +
            "order by id limit 1")
    ReviewFilter next(int id,String username);

    /**
     * 初筛复核
     */
    @Select({"<script>",
            "select w.*,l.name as licenseType from wffh w " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where  " +
            "to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "<if test='query.username != null and query.username &gt; 0'>" +
            "and w.username=#{query.username} " +
            "</if>" +
            "<if test='query.username == null'>" +
            "and w.username='' " +
            "</if>" +
            "<if test='query.status != null'>" +
            "and w.sfsh=#{query.status} " +
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
            "and w.wfdm=#{query.illegalCode} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            //"and not exists (select wl.license_number from white_list wl where w.hphm=wl.license_number) " +
            " </script>"})
    List<ReviewFilter> queryForFilter(@Param("query") FirstFilterDto params);

    /**
     * 复核查询
     */
    @Select({"<script>",
            "select w.*,l.name as licenseType,u.name as realName from wffh w " +
            "LEFT JOIN user_account u on u.nickname=w.username " +
            "JOIN license_plate_type l on w.hpzl = l.code " +
            "where to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
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
            "<if test='query.road != null and query.road.length &gt; 0'>" +
            "and w.sbbh=#{query.road,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            //"and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            //"and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +

            //"and not exists (select wl.license_number from white_list wl where w.hphm = wl.license_number)" +
            " </script>"})
    List<ReviewFilter> queryForSearch(@Param("query") FirstFilterDto params);

    /**
     * 通过
     */
    @Update("update wffh set sfsh=0,screendate=now() where id=#{id}")
    Integer updatePass(int id);

    /**
     * 废弃
     */
    @Update("update wffh set sfsh=1,screendate=now() where id=#{id}")
    Integer updateInvalid(int id);

    /**
     *  提取前判断
     */
    @Select("select count(id) from wffh where username=#{username} and sfsh=-1")
    Integer countByUsername(String username);

    /**
     * 提取
     */
    @Update({"<script>",
            "UPDATE wffh set username = #{query.username} WHERE " +
            "id = any(array(select id from wffh w where " +
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
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            "and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') limit #{query.limit}))" +
            " </script>"})
    Integer pickUp(@Param("query") FirstFilterDto params);

    /**
     * 释放
     */
    @Update("update wffh set username='' WHERE id = any(array(select id from wffh where username=#{username} and sfsh=-1))")
    Integer release(String username);

    /**
     * 复核统计
     */
    @Select({"<script>",
            "select r.name as username,COUNT(CASE WHEN w.sfsh in (0,1) and w.screendate &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') and w.screendate &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') THEN 0 END) as total," +
            "COUNT(CASE WHEN w.sfsh = 0 and w.screendate &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') and w.screendate &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') THEN sfsh END) as passTotal," +
            "COUNT(CASE WHEN w.sfsh = -1 and w.username != '' THEN sfsh END) as pickupTotal," +
            "COUNT(CASE WHEN w.sfsh = 1 and w.screendate &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') and w.screendate &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') THEN sfsh END) as invalidTotal from wffh w " +
            "JOIN user_account r on w.username = r.nickname " +
            "where w.username != '' " +
            "<if test='query.illegalCode != null and query.illegalCode.length &gt; 0'>" +
            "and w.wfdm=#{query.illegalCode} " +
            "</if>" +
            "<if test='query.period != 0'>" +
            "and w.period=#{query.period,jdbcType=TINYINT} " +
            "</if>" +
            //"and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
            //"and to_timestamp(w.cjsj,'yyyy-MM-dd hh24:mi:ss') &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') GROUP BY r.name" +
           // "and w.screendate &gt;= to_timestamp(#{query.startDate},'yyyy-MM-dd hh24:mi:ss') " +
           // "and w.screendate &lt;= to_timestamp(#{query.endDate},'yyyy-MM-dd hh24:mi:ss') " +
            "GROUP BY r.name " +
            " </script>"})
    List<FirstFilterStatistics> count(@Param("query") FirstFilterDto params);


    /**
     * 释放所有未筛选数据
     */
    @Update("update wffh set username='' where username != '' and sfsh=-1")
    Integer releaseAll();

    @Update("update wffh set hphm=#{hphm} where id=#{id}")
    Integer updateLicenseNumberById(@Param("hphm") String hphm,
                                    @Param("id") int id);


    /**
     * 根据ID修改号牌种类
     */
    @Update("update wffh set hpzl=#{hpzl} where id=#{id}")
    Integer updateLicenseTypeById(@Param("hpzl") String hpzl,
                                  @Param("id") int id);


    /**
     * 根据车牌,时间查询出来其他违法数据
     */
    @Options(useCache = false)
    @Select("select hphm,cjdz,cjsj,wfdm,sfsh from wffh " +
            "where id!=#{id} and hphm=#{hphm} and to_date(cjsj,'yyyy-MM-dd')=to_date(#{dateTime},'yyyy-MM-dd')")
    List<ReviewFilter> findByHphm(@Param("id") int id,
                                  @Param("hphm") String hphm,
                                  @Param("dateTime") String dateTime);


    /**
     *  标记重复数据
     */
    @Update("update wffh set sfsh=-2 where id not in (SELECT min(id) FROM wffh GROUP BY hphm,cjsj,wfdm,sbbh)")
    Integer updateDuplicate();

    /**
     *  获取重复数据
     */
    @Select("select count(*) as total from wffh where sfsh=-2")
    Integer findDuplicate();

    /**
     * 重复数据检查
     */
    @Select("select count(*) from wffh where " +
            "id not in (SELECT min(id) FROM wffh GROUP BY hphm,cjsj,wfdm,sbbh) " +
            "and to_date(cjsj,'yyyy-MM-dd') = to_date(#{dateTime},'yyyy-MM-dd')")
    Integer checkDuplicate(@Param("dateTime") String dateTime);

    @Delete("delete from wffh where sfsh=-2")
    Integer cleanDuplicate();
}
