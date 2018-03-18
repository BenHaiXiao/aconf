package com.github.bh.aconf.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.bh.aconf.persist.base.model.ConditionMeta;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 15:13
 */
@Service
public class ConditionMetaCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionMetaCacheService.class);

    private static final String CACHE_NAME = "conditionMetas";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ConditionMetaService conditionMetaService;
    public static ConcurrentHashMap<Long,Long> emptyCacheMerge = new ConcurrentHashMap<Long,Long>();
    
    @SuppressWarnings("unchecked")
    public List<ConditionMeta> getConditionMetasFromCache(long configId) {
        Cache conditionsCache = getConditionsCache();
        if (conditionsCache == null) {
            LOGGER.warn("cache is not exists >>> name:{}", CACHE_NAME);
            return null;
        }
        Cache.ValueWrapper valueWrapper = conditionsCache.get(configId);
        if (valueWrapper == null) {
            //LOGGER.warn("cache value is empty >>> key:{}", configId);
        	if(!emptyCacheMerge.containsKey(configId)) emptyCacheMerge.put(configId, configId);
            return null;
        }
        return (List<ConditionMeta>) valueWrapper.get();
    }

    public void updateConditionCache() {
        Cache conditionsCache = getConditionsCache();
        if (conditionsCache == null) {
            return;
        }
        Ehcache ehcache = (Ehcache) conditionsCache.getNativeCache();
        List<Long> keys = ehcache.getKeys();
        Map<Long, List<ConditionMeta>> conditionMetasMap = assemble(getAllValidConditionMetasFromDb());
        for (Long key : keys) {
            if (!conditionMetasMap.containsKey(key)) {
                conditionsCache.evict(key);
            }
        }
        for (Map.Entry entry : conditionMetasMap.entrySet()) {
            conditionsCache.put(entry.getKey(), entry.getValue());
        }
    }

    private Cache getConditionsCache() {
        Cache filtersCache = cacheManager.getCache(CACHE_NAME);
        if (filtersCache == null) {
            LOGGER.warn("getConditionsCache:error >>> cache name:{}", CACHE_NAME);
            return null;
        }
        return filtersCache;
    }

    private List<ConditionMeta> getAllValidConditionMetasFromDb() {
        try {

            List<ConditionMeta> metas = conditionMetaService.getAllValidConditionMetas();
            if (metas == null) {
                LOGGER.warn("getAllValidConditionMetas from db:empty");
                return Collections.emptyList();
            }
            return metas;
        } catch (Exception e) {
            LOGGER.error("getAllValidConditionMetasFromDb error", e);

        }
        return Collections.emptyList();
    }

    private Map<Long, List<ConditionMeta>> assemble(List<ConditionMeta> metas) {
        Map<Long, List<ConditionMeta>> conditionMetasMap = Maps.newHashMap();
        for (ConditionMeta meta : metas) {
            List<ConditionMeta> conditionMetas = conditionMetasMap.get(meta.getConfigId());
            if (conditionMetas == null) {
                conditionMetas = Lists.newArrayList();
                conditionMetasMap.put(meta.getConfigId(), conditionMetas);
            }
            conditionMetas.add(meta);
        }
        return conditionMetasMap;
    }
}
