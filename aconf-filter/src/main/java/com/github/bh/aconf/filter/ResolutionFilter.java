package com.github.bh.aconf.filter;

import com.github.bh.aconf.filter.operator.LongNotEqualsOperator;
import com.github.bh.aconf.filter.operator.LongEqualsOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 17:05
 * 分辨率拦截器
 */
@Service("resolutionFilter")
public class ResolutionFilter extends AbstractNumberFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResolutionFilter.class);
    @Autowired
    private LongEqualsOperator longEqualsOperator;
    @Autowired
    private LongNotEqualsOperator longNotEqualsOperator;

    @Override
    public boolean doFilter(FilterRequest request, FilterConfig config) {
        Map<String, String> extensionMap = request.getExtensionMap();
        if (extensionMap == null) {
            LOGGER.warn("extensionMap is empty");
            return false;
        }
        String widthStr = extensionMap.get("width");
        String heightStr = extensionMap.get("height");
        if (StringUtils.isAnyBlank(widthStr, heightStr)) {
            LOGGER.warn("actual value is empty");
            return false;
        }
        int width = NumberUtils.toInt(widthStr, 0);
        int height = NumberUtils.toInt(heightStr, 0);

        String boundary = config.getBoundary();
        String[] ss = boundary.split("\\*");
        if (ss.length != 2) {
            LOGGER.warn("resolution format error >>> {}", boundary);
            return false;
        }
        int boundaryWidth = NumberUtils.toInt(ss[0].trim(), 0);
        int boundaryHeight = NumberUtils.toInt(ss[1].trim(), 0);
        if (longEqualsOperator.isMe(config.getOperatorSymbol()) || longNotEqualsOperator.isMe(config.getOperatorSymbol())) {
            boolean bWidth = check(width, config.getOperatorSymbol(), String.valueOf(boundaryWidth), null);
            boolean bHeight = check(height, config.getOperatorSymbol(), String.valueOf(boundaryHeight), null);
            return bWidth && bHeight;
        }
        return check(width * height, config.getOperatorSymbol(), String.valueOf(boundaryWidth * boundaryHeight), null);
    }

    @Override
    public String getName() {
        return "resolution-filter";
    }

    @Override
    public String getAlias() {
        return "分辨率";
    }

    @Override
    public String getSymbol() {
        return "resolution";
    }

}