package com.github.bh.aconf.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/23
 * Time: 15:27
 */
@Component("extensionMapStringFilter")
public class ExtensionMapStringFilter extends AbstractStringFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionMapStringFilter.class);

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        Map<String, String> extensionMap = request.getExtensionMap();
        if (extensionMap == null) {
            LOGGER.warn("extensionMap is empty");
            return false;
        }
        String realValue = extensionMap.get(config.getFilterSymbol());
        return check(realValue, config.getOperatorSymbol(), config.getBoundary());
    }

    @Override
    public String getName() {
        return "extension-map-string-filter";
    }

    @Override
    public String getAlias() {
        return "自定义字符拦截器";
    }

    @Override
    public String getSymbol() {
        return "extension-string";
    }
}
