package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

/**
 * 用户id过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:15
 */
@Component("uidFilter")
public class UidFilter extends AbstractNumberFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        return check(request.getUid(), config.getOperatorSymbol(), config.getBoundary(), config.getValues());
    }

    @Override
    public String getName() {
        return "uid-filter";
    }

    @Override
    public String getAlias() {
        return "用户ID";
    }

    @Override
    public String getSymbol() {
        return "uid";
    }

}
