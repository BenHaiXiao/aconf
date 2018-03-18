package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

/**
 * 渠道过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:50
 */
@Component("marketFilter")
public class MarketFilter extends AbstractStringFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        return check(request.getMarket(), config.getOperatorSymbol(), config.getBoundary());
    }

    @Override
    public String getName() {
        return "market-filter";
    }

    @Override
    public String getAlias() {
        return "渠道";
    }

    @Override
    public String getSymbol() {
        return "market";
    }

}
