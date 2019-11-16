package mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pojo.FirstFilterData;
import pojo.V2FirstFilter;

import java.util.List;

@Mapper
public interface V2FirstFilterMapper {

    @Select("select * from v2_wfcs")
    List<V2FirstFilter> findAll();

    /**
     * 上一条
     * 添加 过滤白名单
     */
    @Select("select w.* from v2_wfcs w " +
            "where sfsh=-1 " +
            "and not exists (select wl.license_number from white_list wl where w.plateno=wl.license_number) " +
            "order by RANDOM() limit 1")
    V2FirstFilter prev();

    /**
     * 下一条
     * 添加 过滤白名单
     */
    @Select("select w.* from v2_wfcs w " +
            "where sfsh=-1 " +
            "and not exists (select wl.license_number from white_list wl where w.plateno=wl.license_number) " +
            "order by RANDOM() limit 1")
    V2FirstFilter next();
}
