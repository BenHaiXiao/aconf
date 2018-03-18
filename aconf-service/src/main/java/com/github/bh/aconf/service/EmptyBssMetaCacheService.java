package com.github.bh.aconf.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 * Date: 2017/3/6
 * Time: 15:38
 */
@Service
public class EmptyBssMetaCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmptyBssMetaCacheService.class);
    private static final String CACHE_NAME = "emptyBssMetas";
    public static final String FLAG_EMPTY = "NULL";

    @Autowired
    private CacheManager cacheManager;

    public String getEmptyBssMetaFromCache(String bssCode) {
        Cache emptyBssCache = getEmptyBssMetasCache();
        if (emptyBssCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return null;
        }
        String result = emptyBssCache.get(bssCode, String.class);
        if (result == null) {
            LOGGER.debug("cache value is empty >>> key:{}", bssCode);
            return null;
        }
        return result;
    }

    public void setEmptyBssMetaToCache(String bssCode) {
        if (StringUtils.isBlank(bssCode)) {
            LOGGER.warn("bssCode is empty, bssCode={}", bssCode);
            return;
        }
        Cache bssMetasCache = getEmptyBssMetasCache();
        if (bssMetasCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return;
        }
        bssMetasCache.put(bssCode, FLAG_EMPTY);
    }

    private Cache getEmptyBssMetasCache() {
        Cache bssMetasCache = cacheManager.getCache(CACHE_NAME);
        if (bssMetasCache == null) {
            LOGGER.debug("getBssMetasCache:error >>> cache name:{}", CACHE_NAME);
            return null;
        }
        return bssMetasCache;
    }
}
