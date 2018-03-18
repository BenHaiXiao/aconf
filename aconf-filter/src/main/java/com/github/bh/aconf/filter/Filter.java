package com.github.bh.aconf.filter;

import com.github.bh.aconf.filter.operator.Operator;

import java.util.List;

/**
 * 过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:30
 */
public interface Filter {

    boolean doFilter(FilterRequest request, FilterConfig config);

    String getName();

    /**
     * 别名（友好名称）。用于直接显示在管理后台。
     */
    String getAlias();

    String getSymbol();

    List<? extends Operator> getSupportOperators();
}
