package com.github.bh.aconf.service.job;

import com.github.bh.aconf.service.ConditionMetaCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.bh.aconf.service.FilterCacheService;

@Service
public class EmptyCacheMergeLogJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmptyCacheMergeLogJob.class);
	
    public void execute() {
        try {
        	
        	StringBuffer sb = new StringBuffer().append("ConditionCache empty:");
        	for(Long emptykey : ConditionMetaCacheService.emptyCacheMerge.keySet()){
        		sb.append(emptykey).append(",");
        	}
        	ConditionMetaCacheService.emptyCacheMerge.clear();
        	sb.append("FilterCache empty:");
        	for(String emptykey : FilterCacheService.emptyCacheMerge.keySet()){
        		sb.append(emptykey).append(",");
        	}
        	FilterCacheService.emptyCacheMerge.clear();
        	LOGGER.warn(sb.toString());        	
        } catch (Exception e) {
        	LOGGER.warn("execute EmptyCacheMergeLogJob error", e);        	
        }
    }
	
}
