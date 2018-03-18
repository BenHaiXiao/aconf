package com.github.bh.aconf.filter;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 时间过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:20
 */
@Component("datetimeFilter")
public class DatetimeFilter extends AbstractNumberFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        return check(new Date().getTime(), config.getOperatorSymbol(), config.getBoundary(), null);
    }

    @Override
    public String getName() {
        return "datetime-filter";
    }

    @Override
    public String getAlias() {
        return "时间";
    }

    @Override
    public String getSymbol() {
        return "datetime";
    }

}
