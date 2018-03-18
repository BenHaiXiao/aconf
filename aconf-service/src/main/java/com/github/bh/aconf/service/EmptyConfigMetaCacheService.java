package com.github.bh.aconf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 * Date: 2017/3/6
 * Time: 15:39
 */
@Service
public class EmptyConfigMetaCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyConfigMetaCacheService.class);
    private static final String CACHE_NAME = "emptyConfigMetas";
    public static final String FLAG_EMPTY = "NULL";

    @Autowired
    private CacheManager cacheManager;

    public String getEmptyConfigMetaFromCache(Long bssId) {
        Cache emptyBssCache = getEmptyConfigMetasCache();
        if (emptyBssCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return null;
        }
        String result = emptyBssCache.get(bssId, String.class);
        if (result == null) {
            LOGGER.debug("cache value is empty >>> bssId:{}", bssId);
            return null;
        }
        return result;
    }

    public void setEmptyConfigMetaToCache(Long bssId) {
        if (bssId == null) {
            LOGGER.warn("bssId is null, bssId={}", bssId);
            return;
        }
        Cache bssMetasCache = getEmptyConfigMetasCache();
        if (bssMetasCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return;
        }
        bssMetasCache.put(bssId, FLAG_EMPTY);
    }

    private Cache getEmptyConfigMetasCache() {
        Cache bssMetasCache = cacheManager.getCache(CACHE_NAME);
        if (bssMetasCache == null) {
            LOGGER.debug("getBssMetasCache:error >>> cache name:{}", CACHE_NAME);
            return null;
        }
        return bssMetasCache;
    }
}
