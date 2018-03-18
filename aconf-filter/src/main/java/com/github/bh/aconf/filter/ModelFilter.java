package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 16:55
 * 设备型号拦截器
 */
@Component("modelFilter")
public class ModelFilter extends AbstractStringFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        return check(request.getModel(), config.getOperatorSymbol(), config.getBoundary());
    }

    @Override
    public String getName() {
        return "model-filter";
    }

    @Override
    public String getAlias() {
        return "手机型号";
    }

    @Override
    public String getSymbol() {
        return "model";
    }
}
