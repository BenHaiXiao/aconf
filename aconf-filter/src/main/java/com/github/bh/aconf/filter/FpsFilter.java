package com.github.bh.aconf.filter;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:17
 * 帧率拦截器
 */
@Component("fpsFilter")
public class FpsFilter extends AbstractNumberFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        Map<String, String> extensionMap = request.getExtensionMap();
        if (extensionMap == null || extensionMap.get("fps") == null) {
            return false;
        }
        String fpsStr = extensionMap.get("fps");
        long fps = NumberUtils.toLong(fpsStr, 0L);
        return check(fps, config.getOperatorSymbol(), config.getBoundary(), null);
    }

    @Override
    public String getName() {
        return "fps-filter";
    }

    @Override
    public String getAlias() {
        return "帧率";
    }

    @Override
    public String getSymbol() {
        return "fps";
    }
}
