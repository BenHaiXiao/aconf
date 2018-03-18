package com.github.bh.aconf.service.job;

import com.github.bh.aconf.service.ConditionMetaCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 15:13
 */
@Service
public class ConditionCacheUpdateJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionCacheUpdateJob.class);
    @Autowired
    ConditionMetaCacheService conditionMetaCacheService;

    public void execute() {
        try {
            conditionMetaCacheService.updateConditionCache();
            LOGGER.info("execute condition cache success");
        } catch (Exception e) {
            LOGGER.error("execute condition cache error", e);

        }
    }
}
