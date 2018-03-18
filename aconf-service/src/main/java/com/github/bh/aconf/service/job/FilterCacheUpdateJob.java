package com.github.bh.aconf.service.job;

import com.github.bh.aconf.service.FilterCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 * Date: 2017/2/19
 * Time: 10:17
 */
@Service
public class FilterCacheUpdateJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterCacheUpdateJob.class);

    @Autowired
    private FilterCacheService filterCacheService;

    public void execute() {
        try {
            filterCacheService.updateFilterCache();
            LOGGER.info("execute filter cache success");
        } catch (Exception e) {
            LOGGER.error("execute filter cache error", e);

        }
    }

}
