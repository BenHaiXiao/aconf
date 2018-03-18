package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.filter.Filter;
import com.github.bh.aconf.filter.FilterConfig;
import com.github.bh.aconf.filter.FilterContainer;
import com.github.bh.aconf.filter.FilterRequest;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置中心过滤器中心处理器。
 * 每个config之前都是AND的关系，顺次执行filters，采用“短路运算”。
 *
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 13:13
 */
@Service
public class FilterRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterRequestService.class);

    @Autowired
    private FilterCacheService filterCacheService;

    @Autowired
    private FilterContainer filterContainer;

    public boolean check(long belongId, FilterBelongType belongType, FilterRequest request) {
        List<FilterConfig> configs = filterCacheService.getFilterConfigsFromCache(belongId, belongType);
        if (CollectionUtils.isEmpty(configs)) {
            LOGGER.debug("type : {}, id : {}, filter config is empty!", belongType.getValue(), belongId);
            return true;
        }
        for (FilterConfig config : configs) {
            Filter filter = filterContainer.get(config.getFilterSymbol(), config.getFilterType());
            LOGGER.debug("check by filter : {}", filter.getName());
            try {
                boolean pass = filter.doFilter(request, config);
                if (!pass) {
                    LOGGER.info("break by filter : {}, request : {}, config : {}", filter.getName(), request, config);
                    return false; // 短路
                }
            } catch (Exception e) {
                LOGGER.warn("filter check error : {}", filter.getName(), e);
            }
        }
        return true; // 通过所有的过滤规划
    }
}
