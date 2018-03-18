package com.github.bh.aconf.persist;

import com.github.bh.aconf.persist.base.mapper.BssArchivedMetaMapper;
import com.github.bh.aconf.persist.base.model.BssArchivedMeta;
import com.github.bh.aconf.persist.provider.ExtensionSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/11
 * Time: 16:17
 */
public interface BssArchivedMetaExtensionMapper extends BssArchivedMetaMapper {
    @SelectProvider(type = ExtensionSqlProvider.class, method = "getSearchBssArchivedMetaSql")
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
            @Result(column = "effective_time", property = "effectiveTime"),
            @Result(column = "failure_time", property = "failureTime"),
            @Result(column = "valid", property = "valid"),
    })
    List<BssArchivedMeta> selectBssMetas(@Param("appId") Long appid, @Param("name") String name,
                                         @Param("limit") int limit, @Param("offset") int offset);

    @SelectProvider(type = ExtensionSqlProvider.class, method = "getCountBssArchivedMetaSql")
    @ResultType(Integer.class)
    int countBssMetas(@Param("appId") Long appid, @Param("name") String name,
                      @Param("limit") int limit, @Param("offset") int offset);
}
