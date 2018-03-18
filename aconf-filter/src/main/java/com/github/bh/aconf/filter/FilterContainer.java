package com.github.bh.aconf.filter;

import com.google.common.collect.Maps;
import com.github.bh.aconf.common.constants.FilterType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:30
 */
@Service("filterContainer")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class FilterContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterContainer.class);

    private Map<String, Filter> filters = Maps.newHashMap();

    @Autowired
    private TrueFilter trueFilter;
    @Autowired
    private UidFilter uidFilter;
    @Autowired
    private SidFilter sidFilter;
    @Autowired
    private VersionFilter versionFilter;
    @Autowired
    private OsFilter osFilter;
    @Autowired
    private OsVersionFilter osVersionFilter;
    @Autowired
    private MarketFilter marketFilter;
    @Autowired
    private ModelFilter modelFilter;
    @Autowired
    private ResolutionFilter resolutionFilter;
    @Autowired
    private BpsFilter bpsFilter;
    @Autowired
    private FpsFilter fpsFilter;
    @Autowired
    private DeviceIdFilter deviceIdFilter;
    @Autowired
    private DatetimeFilter datetimeFilter;    
    @Autowired
    private ExtensionMapStringFilter extensionMapStringFilter;
    @Autowired
    private ExtensionMapNumberFilter extensionMapNumberFilter;

    @PostConstruct
    public void init() {
        filters.put(uidFilter.getSymbol(), uidFilter);
        filters.put(sidFilter.getSymbol(), sidFilter);
        filters.put(versionFilter.getSymbol(), versionFilter);
        filters.put(osFilter.getSymbol(), osFilter);
        filters.put(osVersionFilter.getSymbol(), osVersionFilter);
        filters.put(marketFilter.getSymbol(), marketFilter);
        filters.put(modelFilter.getSymbol(), modelFilter);
        filters.put(resolutionFilter.getSymbol(), resolutionFilter);
        filters.put(bpsFilter.getSymbol(), bpsFilter);
        filters.put(fpsFilter.getSymbol(), fpsFilter);
        filters.put(deviceIdFilter.getSymbol(), deviceIdFilter);
        filters.put(datetimeFilter.getSymbol(), datetimeFilter);
        filters.put(extensionMapNumberFilter.getSymbol(), extensionMapNumberFilter);
        filters.put(extensionMapStringFilter.getSymbol(), extensionMapStringFilter);
    }

    public Filter get(String symbol, FilterType filterType) {
        if (StringUtils.isEmpty(symbol)) {
            LOGGER.debug("return filter : {}, cause the symbol parameter is empty!", trueFilter.getName());
            return trueFilter;
        }
        if (filterType != null && FilterType.EXTENSION_STRING.isMe(filterType)) {
            return extensionMapStringFilter;
        }
        if (filterType != null && FilterType.EXTENSION_NUMBER.isMe(filterType)) {
            return extensionMapNumberFilter;
        }
        Filter filter = filters.get(symbol);
        if (filter == null) {
            LOGGER.debug("return filter : {}, cause there are not filters for the symbol : {}!", trueFilter.getName(), symbol);
            return trueFilter;
        }
        LOGGER.debug("return filter : {} for the symbol : {}", filter.getName(), symbol);
        return filter;
    }

    public Collection<Filter> getAllFilters() {
        return filters.values();
    }

}
