package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

/**
 * 频道号过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 15:17
 */
@Component("sidFilter")
public class SidFilter extends AbstractNumberFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        return check(request.getSid(), config.getOperatorSymbol(), config.getBoundary(), null);
    }

    @Override
    public String getName() {
        return "sid-filter";
    }

    @Override
    public String getAlias() {
        return "顶级频道号";
    }

    @Override
    public String getSymbol() {
        return "sid";
    }

}
