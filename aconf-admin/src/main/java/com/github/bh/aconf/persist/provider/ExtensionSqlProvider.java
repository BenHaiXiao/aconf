package com.github.bh.aconf.persist.provider;

import com.github.bh.aconf.common.constants.BssStates;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/1/11
 * Time: 17:35
 */
public class ExtensionSqlProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionSqlProvider.class);

    public String getSearchBssArchivedMetaSql(Map<String, Object> params) {
        String values = "id, code, name, creator, developer, tester, operator, effective_type, effective_time, failure_time, create_time, update_time, name, app_id, valid, version, description";
        StringBuilder whereSqlBuilder = new StringBuilder();

        Long appId = (Long) params.get("appId");
        whereSqlBuilder.append("where app_id=").append(appId).append(" ");
        String name = (String) params.get("name");
        if (StringUtils.isNotEmpty(name)) {
            whereSqlBuilder.append("and name like \"%").append(name).append("%\" ");
        }
        String whereSql = whereSqlBuilder.toString();

        String sql = "select " + values + " from bss_archived " + whereSql +
//                " order by valid asc " +
                " limit " + params.get("offset") + "," + params.get("limit");
        LOGGER.info("getSearchBssArchivedMetaSql() invoked... sql provider build a sql >>> {}", sql);
        return sql;
    }

    public String getCountBssArchivedMetaSql(Map<String, Object> params) {
        String values = "count(*)";

        StringBuilder whereSqlBuilder = new StringBuilder();

        Long appId = (Long) params.get("appId");
        whereSqlBuilder.append("where app_id=").append(appId).append(" ");
        String name = (String) params.get("name");
        if (StringUtils.isNotEmpty(name)) {
            whereSqlBuilder.append("and name like \"%").append(name).append("%\" ");
        }
        String whereSql = whereSqlBuilder.toString();

        String sql = "select " + values + " from bss_archived " + whereSql;
        LOGGER.info("getCountBssArchivedMetaSql() invoked... sql provider build a sql >>> {}", sql);
        return sql;
    }

    public String getSearchBssMetaSql(Map<String, Object> params) {
        String values = "id, code, name, creator, developer, tester, operator, effective_type, effective_time, failure_time, create_time, update_time, name, app_id, valid, version, description";
        StringBuilder whereSqlBuilder = new StringBuilder();

        Long appId = (Long) params.get("appId");
        whereSqlBuilder.append("where app_id=").append(appId).append(" ");
        Byte valid = (Byte) params.get("valid");
        if (valid != null && !BssStates.ALL.isMe(valid)) {
            whereSqlBuilder.append("and valid=").append(valid).append(" ");
        }
        String name = (String) params.get("name");
        if (StringUtils.isNotEmpty(name)) {
            whereSqlBuilder.append("and name like \"%").append(name).append("%\" ");
        }
        String whereSql = whereSqlBuilder.toString();

        String sql = "select " + values + " from bss " + whereSql +
                "union all select " + values + " from bss_archived " + whereSql +
//                " order by valid asc " +
                " limit " + params.get("offset") + "," + params.get("limit");
        LOGGER.info("getSearchBssMetaSql() invoked... sql provider build a sql >>> {}", sql);
        return sql;
    }

    public String getCountBssMetaSql(Map<String, Object> params) {
        String values = "count(*)";

        StringBuilder whereSqlBuilder = new StringBuilder();

        Long appId = (Long) params.get("appId");
        whereSqlBuilder.append("where app_id=").append(appId).append(" ");
        Byte valid = (Byte) params.get("valid");
        if (valid != null && !BssStates.ALL.isMe(valid)) {
            whereSqlBuilder.append("and valid=").append(valid).append(" ");
        }
        String name = (String) params.get("name");
        if (StringUtils.isNotEmpty(name)) {
            whereSqlBuilder.append("and name like \"%").append(name).append("%\" ");
        }
        String whereSql = whereSqlBuilder.toString();

        String sql = "select " + values + "+(select " + values + " from bss_archived " + whereSql + ") AS SUM from bss " + whereSql;
        LOGGER.info("getCountBssMetaSql() invoked... sql provider build a sql >>> {}", sql);
        return sql;
    }
}
