package com.github.bh.aconf.persist;

import com.github.bh.aconf.persist.base.mapper.BssMetaMapper;
import com.github.bh.aconf.persist.base.model.BssMeta;
import com.github.bh.aconf.persist.provider.ExtensionSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/11
 * Time: 16:16
 */
public interface BssMetaExtensionMapper extends BssMetaMapper {
    @SelectProvider(type = ExtensionSqlProvider.class, method = "getSearchBssMetaSql")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "code", property = "code"),
            @Result(column = "name", property = "name"),
            @Result(column = "creator", property = "creator"),
            @Result(column = "developer", property = "developer"),
            @Result(column = "tester", property = "tester"),
            @Result(column = "operator", property = "operator"),
            @Result(column = "description", property = "description"),
            @Result(column = "app_id", property = "appId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "version", property = "version"),
            @Result(column = "effective_type", property = "effectiveType"),
            @Result(column = "effective_time", property = "effectiveTime"),
            @Result(column = "failure_time", property = "failureTime"),
            @Result(column = "valid", property = "valid"),
    })
    List<BssMeta> selectBssMetas(@Param("appId") Long appid, @Param("name") String name, @Param("valid") Byte valid,
                                 @Param("limit") int limit, @Param("offset") int offset);

    @SelectProvider(type = ExtensionSqlProvider.class, method = "getCountBssMetaSql")
    @ResultType(Integer.class)
    int countBssMetas(@Param("appId") Long appid, @Param("name") String name, @Param("valid") Byte valid,
                      @Param("limit") int limit, @Param("offset") int offset);
}
