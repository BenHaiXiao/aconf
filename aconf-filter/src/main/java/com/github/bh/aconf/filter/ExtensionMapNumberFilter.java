package com.github.bh.aconf.filter;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/23
 * Time: 15:27
 */
@Component("extensionMapNumberFilter")
public class ExtensionMapNumberFilter extends AbstractNumberFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionMapNumberFilter.class);

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        Map<String, String> extensionMap = request.getExtensionMap();
        if (extensionMap == null) {
            LOGGER.warn("extensionMap is empty");
            return false;
        }
        String value = extensionMap.get(config.getFilterSymbol());
        if (value == null) {
            LOGGER.warn("extension value is null");
            return false;
        }
        long realValue = NumberUtils.toLong(value, 0L);
        return check(realValue, config.getOperatorSymbol(), config.getBoundary(), null);
    }

    @Override
    public String getName() {
        return "extension-map-number-filter";
    }

    @Override
    public String getAlias() {
        return "自定义数值拦截器";
    }

    @Override
    public String getSymbol() {
        return "extension-number";
    }
}
