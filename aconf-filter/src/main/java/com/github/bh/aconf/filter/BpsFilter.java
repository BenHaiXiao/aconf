package com.github.bh.aconf.filter;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:17
 */
@Component("bpsFilter")
public class BpsFilter extends AbstractNumberFilter implements Filter {
    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        Map<String, String> extensionMap = request.getExtensionMap();
        if (extensionMap == null || extensionMap.get("bps") == null) {
            return false;
        }
        String bpsStr = extensionMap.get("bps");
        long bps = NumberUtils.toLong(bpsStr, 0L);
        return check(bps, config.getOperatorSymbol(), config.getBoundary(), null);
    }

    @Override
    public String getName() {
        return "bps-filter";
    }

    @Override
    public String getAlias() {
        return "码率";
    }

    @Override
    public String getSymbol() {
        return "bps";
    }
}
