package com.github.bh.aconf.service;

import com.github.bh.aconf.service.domain.BssConfig;
import com.google.common.collect.Range;
import com.github.bh.aconf.common.constants.BssEffectiveType;
import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.common.constants.SendDefaultType;
import com.github.bh.aconf.common.constants.ValueType;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.filter.FilterRequest;
import com.github.bh.aconf.persist.base.model.BssMeta;
import com.github.bh.aconf.persist.base.model.ConditionMeta;
import com.github.bh.aconf.persist.base.model.ConfigMeta;
import com.github.bh.aconf.service.domain.ResponseCodes;
import com.github.bh.aconf.service.utils.ConfigUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 业务配置项过滤处理器。
 *
 * @author xiaobenhai
 * Date: 2017/2/19
 * Time: 11:19
 */
@Service
public class BssConfigFilterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BssConfigFilterService.class);

    @Autowired
    private BssMetaService bssMetaService;
    @Autowired
    private ConfigMetaService configMetaService;
    @Autowired
    private ConditionMetaCacheService conditionMetaCacheService;
    @Autowired
    private FilterRequestService filterRequestService;
    @Autowired
    @Qualifier("expressionParser")
    private ExpressionParser expressionParser;

    public BssConfig getBssConfigByCode(String bssCode, FilterRequest filterRequest) {
        BssMeta bssMeta = bssMetaService.getBssMetaByCode(bssCode);
        if (bssMeta == null) {
            LOGGER.warn("bss meta is null, bss code : {}", bssCode);
            BssConfig bssConfig = new BssConfig();
            bssConfig.setBssCode(bssCode);
            bssConfig.setResponseCode(ResponseCodes.FAIL);
            return bssConfig;
        }
        return getBssConfigByBssMeta(bssMeta, filterRequest);
    }

    private BssConfig getBssConfigByBssMeta(BssMeta bssMeta, FilterRequest filterRequest) {
        long bssId = bssMeta.getId();

        // 检查生效时间
        if (!checkEffectiveTime(bssMeta)) {
            LOGGER.info("bss meta is ineffective, bss : {}", bssMeta);
            return getIneffectiveBssConfig(bssMeta);
        }
        // 进行bss过滤规则运算
        if (!filterRequestService.check(bssId, FilterBelongType.BSS, filterRequest)) {
            LOGGER.info("bss meta is not passed by filter, bss id : {}, request : {}", bssId, filterRequest);
            return getNoPassBssConfig(bssMeta);
        }

        BssConfig bssConfig = new BssConfig();
        bssConfig.setBssId(bssId);
        bssConfig.setBssCode(bssMeta.getCode());
        // 设置版本信息
        bssConfig.setBssVersion(bssMeta.getVersion() == null ? 0L : bssMeta.getVersion());
        // 进行config过滤规则运算
        List<ConfigMeta> metas = configMetaService.getConfigMetasForBss(bssId);
        for (ConfigMeta meta : metas) {
            boolean needDefault = true;
            List<ConditionMeta> conditions = conditionMetaCacheService.getConditionMetasFromCache(meta.getId());
            if (CollectionUtils.isEmpty(conditions)) {
                ConfigMeta result = parseExpression(meta, filterRequest);
                String value = result.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    bssConfig.addConfig(result);
                }
                continue;
            }
            for (ConditionMeta condition : conditions) {
                LOGGER.debug("config >>> {}, condition >>> {}", meta.getName(), condition.getName());
                if (filterRequestService.check(condition.getId(), FilterBelongType.CONDITION, filterRequest)) {
                    String value = condition.getValue();
                    Byte valueType = condition.getValueType();
                    ConfigMeta result = ConfigUtils.createConfigMetaWithValue(meta, value, valueType);
                    result = parseExpression(result, filterRequest);
                    String resultValue = result.getValue();
                    if (StringUtils.isNotEmpty(resultValue)) {
                        bssConfig.addConfig(result);
                    }
                    needDefault = false;
                    break;
                }
            }
            if (needDefault) {
                Byte sendDefault = meta.getSendDefault();
                if (SendDefaultType.SEND.isMe(sendDefault)) {
                    ConfigMeta result = parseExpression(meta, filterRequest);
                    String value = result.getValue();
                    if (StringUtils.isNotEmpty(value)) {
                        bssConfig.addConfig(result);
                    }
                }
            }
        }

        return bssConfig;
    }

    private BssConfig getNoPassBssConfig(BssMeta bssMeta) {
        BssConfig bssConfig = new BssConfig();
        bssConfig.setBssId(bssMeta.getId());
        bssConfig.setBssCode(bssMeta.getCode());
        bssConfig.setResponseCode(ResponseCodes.NOPASS);
        return bssConfig;
    }

    private BssConfig getIneffectiveBssConfig(BssMeta bssMeta) {
        BssConfig config = new BssConfig();
        config.setBssId(bssMeta.getId());
        config.setBssCode(bssMeta.getCode());
        config.setResponseCode(ResponseCodes.INEFFECTIVE);
        return config;
    }

    private boolean checkEffectiveTime(BssMeta bssMeta) {
        if (BssEffectiveType.ALWAYS.isMe(bssMeta.getEffectiveType())) {
            LOGGER.debug("bss meta always effective, return true, bss : {}", bssMeta);
            return true;
        }
        if (bssMeta.getEffectiveTime() == null) {
            LOGGER.debug("bss meta effective time is null, return true, bss : {}", bssMeta);
            return true;
        }
        Range<Date> dateRange;
        if (bssMeta.getFailureTime() == null) {
            dateRange = Range.atLeast(bssMeta.getEffectiveTime());
        } else {
            dateRange = Range.closed(bssMeta.getEffectiveTime(), bssMeta.getFailureTime());
        }
        return dateRange.contains(new Date());
    }

    // TODO 实现增量下发
    public BssConfig getNewestBssConfig(String bssCode, long bssVersion, FilterRequest filterRequest) {
        return getBssConfigByCode(bssCode, filterRequest);
    }

    private ConfigMeta parseExpression(ConfigMeta meta, FilterRequest filterRequest) {
        if (!ValueType.EXPRESSION.isMe(meta.getValueType())) {
            return meta;
        }
        String value = meta.getValue();
        if (StringUtils.isBlank(value)) {
            return new ConfigMeta();
        }
        try {
            Expression expression = expressionParser.parseExpression("#this['" + value + "']");
            String newValue = expression.getValue(BeanUtils.tileOpenBean(filterRequest), String.class);
            return ConfigUtils.createConfigMetaWithValue(meta, newValue, meta.getValueType());
        } catch (Exception e) {
            LOGGER.warn("tile the FilterRequest error, FilterRequest={}", filterRequest, e);
        }
        return new ConfigMeta();
    }
}
