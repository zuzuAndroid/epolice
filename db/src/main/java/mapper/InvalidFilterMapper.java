package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.FirstFilterData;
import pojo.InvalidFilter;

import java.util.List;

/**
 * 机筛废片
 */
@Mapper
public interface InvalidFilterMapper {

    @Select("select * from jsfp where id = #{id}")
    InvalidFilter findById(int id);

    /**
     * 根据用户名返回已提取数据
     * 添加 过滤白名单
     */
    @Select("select w.*,l.name as licenseType from jsfp j " +
            "LEFT JOIN license_plate_type l on j.hpzl = l.code " +
            "where j.username = #{name} and j.sfsh=-1 " +
            "and not exists (select wl.license_number from white_list wl where j.hphm=wl.license_number) " +
            "order by j.sysid")
    List<InvalidFilter> findByName(String name);
}
