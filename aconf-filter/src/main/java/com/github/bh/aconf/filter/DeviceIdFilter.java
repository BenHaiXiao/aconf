package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

/**
 * 设备ID过滤器
 * @author ZhongDaoQiao
 * @Date 2017年7月14日 下午5:21:03
 */
@Component("deviceIdFilter")
public class DeviceIdFilter extends AbstractStringFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
    	return check(request.getDeviceId(), config.getOperatorSymbol(), config.getBoundary());
    }
    
    @Override
    public String getName() {
        return "deviceid-filter";
    }

    @Override
    public String getAlias() {
        return "设备ID";
    }

    @Override
    public String getSymbol() {
        return "deviceid";
    }

}
