package com.github.bh.aconf.filter;

import com.github.bh.aconf.filter.operator.Operator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 永远返回true的过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 18:19
 */
@Component("trueFilter")
public class TrueFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        // no-op
        return true;
    }

    @Override
    public String getName() {
        return "true-filter";
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getSymbol() {
        return "true";
    }

    @Override
    public List<? extends Operator> getSupportOperators() {
        return null;
    }
}
