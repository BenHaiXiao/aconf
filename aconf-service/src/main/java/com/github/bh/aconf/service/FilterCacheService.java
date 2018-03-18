package com.github.bh.aconf.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.common.constants.FilterType;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.common.utils.VersionConverter;
import com.github.bh.aconf.filter.BoundaryUtils;
import com.github.bh.aconf.filter.FilterConfig;
import com.github.bh.aconf.persist.base.model.FilterMeta;
import net.sf.ehcache.Ehcache;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 过滤匹配规划缓存处理。
 * <p>
 * 缓存内存为：bssId/conditionId : List of {@link FilterConfig}
 *
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 11:33
 */
@Service
public class FilterCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterCacheService.class);

    private static final String CACHE_NAME = "filters";
    private static final String BSS_CACHE_KEY_PREFIX = "bss-";
    private static final String ITEM_CACHE_KEY_PREFIX = "item-";
    private static final String CONDITION_CACHE_KEY_PREFIX = "condition-";

    @Autowired
    private FilterMetaService filterMetaService;

    @Autowired
    private CacheManager cacheManager;

    public static ConcurrentHashMap<String,Long> emptyCacheMerge = new ConcurrentHashMap<String,Long>();
    
    @PostConstruct
    public void initCache() {
        updateFilterCache();
    }

    public List<FilterConfig> getFilterConfigsFromCache(long belongId, FilterBelongType belongType) {
        if (FilterBelongType.BSS == belongType) {
            return getFilterConfigsFromCache(getBssCacheKey(belongId));
        }
        if (FilterBelongType.CONDITION == belongType) {
            return getFilterConfigsFromCache(getConfigConditionCacheKey(belongId));
        }
        return Collections.emptyList();
    }

    public List<FilterConfig> getFilterConfigsFromDb(long belongId, FilterBelongType belongType) {
        return transform(filterMetaService.getValidFilterMetas(belongId, belongType));
    }

    private List<FilterConfig> getFilterConfigsFromCache(String key) {
        Cache filtersCache = getFiltersCache();
        if (filtersCache == null) {
            return Collections.emptyList();
        }
        Cache.ValueWrapper valueWrapper = filtersCache.get(key);
        if (valueWrapper == null) {
            //LOGGER.warn("cache value is empty >>> key:{}", key);
        	if(!emptyCacheMerge.containsKey(key)) emptyCacheMerge.put(key, 0L);
            return Collections.emptyList();
        }
        return (List<FilterConfig>) valueWrapper.get();
    }

    private String getBssCacheKey(long bssId) {
        return BSS_CACHE_KEY_PREFIX + bssId;
    }

    private String getConfigItemCacheKey(long configItemId) {
        return ITEM_CACHE_KEY_PREFIX + configItemId;
    }

    private String getConfigConditionCacheKey(long conditionId) {
        return CONDITION_CACHE_KEY_PREFIX + conditionId;
    }

    public void updateFilterCache() {
        Cache filtersCache = getFiltersCache();
        if (filtersCache == null) {
            return;
        }
        Ehcache ehcache = (Ehcache) filtersCache.getNativeCache();
        List<String> keys = ehcache.getKeys();
        Map<String, List<FilterConfig>> filterMap = assemble(getAllValidFilterMetasFromDb());
        for (String key : keys) {
            if (!filterMap.containsKey(key)) {
                filtersCache.evict(key);
            }
        }
        for (Map.Entry entry : filterMap.entrySet()) {
            filtersCache.put(entry.getKey(), entry.getValue());
        }
    }

    private Cache getFiltersCache() {
        Cache filtersCache = cacheManager.getCache(CACHE_NAME);
        if (filtersCache == null) {
            LOGGER.warn("getFiltersCache:error >>> cache name:{}", CACHE_NAME);
            return null;
        }
        return filtersCache;
    }

    private Map<String, List<FilterConfig>> assemble(List<FilterMeta> metas) {
        Map<String, List<FilterConfig>> filterMap = Maps.newHashMap();        
        for (FilterMeta meta : metas) {
            if ("version".equals(meta.getBasis()) || "osversion".equals(meta.getBasis())) {
            	List<String> boundaryList = BoundaryUtils.splitJson(meta.getBoundary());
            	List<String> numVersionList = Lists.newLinkedList();
            	for(String version : boundaryList){
            		numVersionList.add(String.valueOf(VersionConverter.convert(version)));
            	}            	
                meta.setBoundary(JsonUtils.toJson(numVersionList));            	
                //meta.setBoundary(String.valueOf(VersionConverter.convert(meta.getBoundary())));
            }else if ("datetime".equals(meta.getBasis())) {
            	List<String> boundaryList = BoundaryUtils.splitJson(meta.getBoundary());
            	List<String> numDatetimeList = Lists.newLinkedList();
            	for(String datetime : boundaryList){
            		Long d = DatetimeConverter(datetime);
            		if(d!=null && d>0){
            			numDatetimeList.add(String.valueOf(d));
            		}
            	}            	
                meta.setBoundary(JsonUtils.toJson(numDatetimeList));
            }
            long belongId = meta.getBelongId();
            if (FilterBelongType.BSS.isMe(meta.getBelongType())) {
                fill(getBssCacheKey(belongId), filterMap, meta);
            }
//            else if (FilterBelongType.ITEM.isMe(meta.getBelongType())) {
//                fill(getConfigItemCacheKey(belongId), filterMap, meta);
//            }
            else if (FilterBelongType.CONDITION.isMe(meta.getBelongType())) {
                fill(getConfigConditionCacheKey(belongId), filterMap, meta);
            }
        }
        return filterMap;
    }

    private List<FilterMeta> getAllValidFilterMetasFromDb() {
        try {
            List<FilterMeta> metas = filterMetaService.getAllValidFilterMetas();
            if (metas == null) {
                LOGGER.warn("getAllValidConditionMetas from db:empty");
                return Collections.emptyList();
            }
            return metas;
        } catch (Exception e) {
            LOGGER.error("getAllValidFilterMetasFromDb error", e);
        }
        return Collections.emptyList();
    }

    private void fill(String cacheKey, Map<String, List<FilterConfig>> filterMap, FilterMeta meta) {
        List<FilterConfig> configs = filterMap.get(cacheKey);
        if (configs == null) {
            configs = Lists.newArrayList();
            filterMap.put(cacheKey, configs);
        }
        configs.add(transform(meta));
    }

    private FilterConfig transform(FilterMeta meta) {
        if (meta == null) {
            return null;
        }
        return new FilterConfig(meta.getBasis(), meta.getOperator(), meta.getBoundary(), FilterType.valueOf(meta.getBasisType()));
    }

    private List<FilterConfig> transform(List<FilterMeta> metas) {
        if (CollectionUtils.isEmpty(metas)) {
            return Collections.emptyList();
        }
        return Lists.transform(metas, new Function<FilterMeta, FilterConfig>() {
            @Override
            public FilterConfig apply(FilterMeta meta) {
                return transform(meta);
            }
        });
    }

    private Long DatetimeConverter(String datetime) {
    	try{
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		return sf.parse(datetime).getTime();
    	}catch(Exception e){
    		LOGGER.warn("DatetimeConverter error:"+datetime, e);
    		return null;
    	}
    }
}
