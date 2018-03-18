package com.github.bh.aconf.filter;

import org.springframework.stereotype.Component;

import com.github.bh.aconf.common.utils.VersionConverter;

/**
 * @author weng
 */
@Component("osVersionFilter")
public class OsVersionFilter extends AbstractNumberFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
    	return check(VersionConverter.convert(request.getOsVersion()), config.getOperatorSymbol(), config.getBoundary(), null);
    }
    
    @Override
    public String getName() {
        return "osversion-filter";
    }

    @Override
    public String getAlias() {
        return "操作系统版本";
    }

    @Override
    public String getSymbol() {
        return "osversion";
    }

}
