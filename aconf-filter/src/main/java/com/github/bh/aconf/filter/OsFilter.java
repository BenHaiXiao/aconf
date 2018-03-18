package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

/**
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:55
 */
@Component("osFilter")
public class OsFilter extends AbstractStringFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        return check(request.getOs(), config.getOperatorSymbol(), config.getBoundary());
    }

    @Override
    public String getName() {
        return "os-filter";
    }

    @Override
    public String getAlias() {
        return "操作系统";
    }

    @Override
    public String getSymbol() {
        return "os";
    }

}
