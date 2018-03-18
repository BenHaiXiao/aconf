package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.persist.base.mapper.BssMetaMapper;
import com.github.bh.aconf.persist.base.model.BssMeta;
import com.github.bh.aconf.persist.base.model.BssMetaExample;
import net.sf.ehcache.Ehcache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 业务信息缓存处理。
 * <p>
 * 缓存内存为：bssCode : {@link BssMeta} object
 *
 * @author xiaobenhai
 * Date: 2017/2/19
 * Time: 11:40
 */
@Service
public class BssMetaCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BssMetaCacheService.class);

    private static final String CACHE_NAME = "bssMetas";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private BssMetaMapper bssMetaMapper;

    public BssMeta getBssMetaFromCache(String bssCode) {
        Cache bssMetasCache = getBssMetasCache();
        if (bssMetasCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return null;
        }
        BssMeta meta = bssMetasCache.get(bssCode, BssMeta.class);
        if (meta == null) {
            LOGGER.debug("cache value is empty >>> key:{}", bssCode);
            return null;
        }
        return meta;
    }

    public void setBssMetaToCache(String code, BssMeta bssMeta) {
        Cache bssMetasCache = getBssMetasCache();
        if (bssMetasCache == null) {
            LOGGER.debug("cache is not exists >>> name:{}", CACHE_NAME);
            return;
        }
        bssMetasCache.put(code, bssMeta);
    }

    private Cache getBssMetasCache() {
        Cache bssMetasCache = cacheManager.getCache(CACHE_NAME);
        if (bssMetasCache == null) {
            LOGGER.debug("getBssMetasCache:error >>> cache name:{}", CACHE_NAME);
            return null;
        }
        return bssMetasCache;
    }

    public void updateBssMetasCache() {
        Cache bssMetasCache = getBssMetasCache();
        if (bssMetasCache == null) {
            return;
        }
        Ehcache ehcache = (Ehcache) bssMetasCache.getNativeCache();
        List<String> keys = ehcache.getKeys();
        List<BssMeta> metas = getAllValidBssMetasFromDb();
        List<String> metaKeys = new LinkedList<>();
        for (BssMeta meta : metas) {
            metaKeys.add(meta.getCode());
        }
        for (String key : keys) {
            if (!metaKeys.contains(key)) {
                bssMetasCache.evict(key);
            }
        }
        for (BssMeta meta : metas) {
            bssMetasCache.put(meta.getCode(), meta);
        }
    }

    private List<BssMeta> getAllValidBssMetasFromDb() {
        BssMetaExample example = new BssMetaExample();
        BssMetaExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(ValidStatus.VALID.getValue());
        List<BssMeta> metas = bssMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }
}
