package com.github.bh.aconf.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.persist.base.mapper.ConfigMetaMapper;
import com.github.bh.aconf.persist.base.model.ConfigMeta;
import com.github.bh.aconf.persist.base.model.ConfigMetaExample;
import net.sf.ehcache.Ehcache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 配置项缓存处理器。
 * <p>
 * 缓存内存为：bssId : list of {@link ConfigMeta}
 *
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 12:41
 */
@Service
public class ConfigMetaCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMetaCacheService.class);

    private static final String CACHE_NAME = "configMetas";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ConfigMetaMapper configMetaMapper;

    public List<ConfigMeta> getConfigMetasFromCache(long bssId) {
        Cache configMetasCache = getConfigMetasCache();
        if (configMetasCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return null;
        }
        Cache.ValueWrapper valueWrapper = configMetasCache.get(bssId);
        if (valueWrapper == null) {
            LOGGER.debug("cache value is empty >>> key:{}", bssId);
            return null;
        }
        return (List<ConfigMeta>) valueWrapper.get();
    }

    public void setConfigMetasToCache(long bssId, List<ConfigMeta> fromDb) {
        Cache configMetasCache = getConfigMetasCache();
        if (configMetasCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return;
        }
        configMetasCache.put(bssId, fromDb);
    }

    private Cache getConfigMetasCache() {
        Cache configMetasCache = cacheManager.getCache(CACHE_NAME);
        if (configMetasCache == null) {
            LOGGER.debug("getConfigMetasCache:error >>> cache name:{}", CACHE_NAME);
            return null;
        }
        return configMetasCache;
    }

    public void updateConfigMetasCache() {
        Cache configMetasCache = getConfigMetasCache();
        if (configMetasCache == null) {
            return;
        }
        Ehcache ehcache = (Ehcache) configMetasCache.getNativeCache();
        List<Long> keys = ehcache.getKeys();
        Map<Long, List<ConfigMeta>> bssConfigMetasMap = assemble(getAllValidConfigMetasFromDb());
        for (Long key : keys) {
            if (!bssConfigMetasMap.containsKey(key)) {
                configMetasCache.evict(key);
            }
        }
        for (Map.Entry entry : bssConfigMetasMap.entrySet()) {
            configMetasCache.put(entry.getKey(), entry.getValue());
        }
    }

    private Map<Long, List<ConfigMeta>> assemble(List<ConfigMeta> metas) {
        Map<Long, List<ConfigMeta>> bssConfigMetasMap = Maps.newHashMap();
        for (ConfigMeta meta : metas) {
            List<ConfigMeta> configMetas = bssConfigMetasMap.get(meta.getBssId());
            if (configMetas == null) {
                configMetas = Lists.newArrayList();
                bssConfigMetasMap.put(meta.getBssId(), configMetas);
            }
            configMetas.add(meta);
        }
        return bssConfigMetasMap;
    }

    private List<ConfigMeta> getAllValidConfigMetasFromDb() {
        ConfigMetaExample example = new ConfigMetaExample();
        ConfigMetaExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(ValidStatus.VALID.getValue());
        List<ConfigMeta> metas = configMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }
}
