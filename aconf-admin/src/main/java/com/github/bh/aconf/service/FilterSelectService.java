package com.github.bh.aconf.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.bh.aconf.common.constants.FilterType;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.filter.Filter;
import com.github.bh.aconf.filter.FilterContainer;
import com.github.bh.aconf.filter.operator.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/23
 * Time: 16:57
 */
@Service
public class FilterSelectService {
    @Autowired
    private FilterContainer filterContainer;

    public ServerResponse getFilters() {
        List<Map<String, String>> maps = Lists.newArrayList();
        Collection<Filter> filters = filterContainer.getAllFilters();
        for (Filter filter : filters) {
            Map<String, String> map = Maps.newHashMap();
            map.put("value", filter.getSymbol());
            map.put("name", filter.getAlias());
            maps.add(map);
        }
        ServerResponse response = new ServerResponse();
        return response.state(BusinessStatus.SUCCESS).data(maps);
    }

    public ServerResponse getOperators(String basis) {
        List<Map<String, String>> maps = Lists.newArrayList();
        Filter filter = filterContainer.get(basis, FilterType.NORMAL);
        List<? extends Operator> supportOperators = filter.getSupportOperators();
        for (Operator operator : supportOperators) {
            Map<String, String> map = Maps.newHashMap();
            map.put("value", operator.getSymbol());
            map.put("name", operator.getSymbol() + "(" + operator.getAlias() + ")");
            maps.add(map);
        }

        ServerResponse response = new ServerResponse();
        return response.state(BusinessStatus.SUCCESS).data(maps);
    }
}
