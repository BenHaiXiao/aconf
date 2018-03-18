package com.github.bh.aconf.filter;

import com.github.bh.aconf.common.utils.VersionConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * YY客户端版本过滤器。
 *
 * 对于手Y，该值为手Y的版本号；对于PC，该值为模板的版本号。
 *
 * 版本号规范为：x.y.z；先比较x，再比较y，最后比较z。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:58
 */
@Component("versionFilter")
public class VersionFilter extends AbstractNumberFilter implements Filter {

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        String version = request.getVersion();
        if (StringUtils.isEmpty(version)) {
            return true;
        }
        return check(VersionConverter.convert(version), config.getOperatorSymbol(), config.getBoundary(), null);
    }

    @Override
    public String getName() {
        return "version-filter";
    }

    @Override
    public String getAlias() {
        return "版本号";
    }

    @Override
    public String getSymbol() {
        return "version";
    }

}
