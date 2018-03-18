package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.persist.base.mapper.ConfigMetaMapper;
import com.github.bh.aconf.persist.base.model.ConfigMeta;
import com.github.bh.aconf.persist.base.model.ConfigMetaExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 10:13
 */
@Service
public class ConfigMetaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMetaService.class);

    @Autowired
    private ConfigMetaMapper configMetaMapper;
    @Autowired
    private ConfigMetaCacheService configMetaCacheService;
    @Autowired
    private EmptyConfigMetaCacheService emptyConfigMetaCacheService;

    public List<ConfigMeta> getConfigMetasForBss(long bssId) {
        List<ConfigMeta> metas = configMetaCacheService.getConfigMetasFromCache(bssId);
        if (metas == null) {
            LOGGER.debug("config cache not exists bss id {}, get from db", bssId);
            String emptyFlag = emptyConfigMetaCacheService.getEmptyConfigMetaFromCache(bssId);
            if (StringUtils.isBlank(emptyFlag)) {
                List<ConfigMeta> fromDb = getConfigMetasForBssFromDb(bssId);
                if (fromDb == null) {
                    emptyConfigMetaCacheService.setEmptyConfigMetaToCache(bssId);
                } else {
                    configMetaCacheService.setConfigMetasToCache(bssId, fromDb);
                }
                return fromDb;
            }
        }
        return metas;
    }

    private List<ConfigMeta> getConfigMetasForBssFromDb(long bssId) {
        ConfigMetaExample example = new ConfigMetaExample();
        ConfigMetaExample.Criteria criteria = example.createCriteria();
        criteria.andBssIdEqualTo(bssId).andValidEqualTo(ValidStatus.VALID.getValue());
        List<ConfigMeta> metas = configMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }

}
