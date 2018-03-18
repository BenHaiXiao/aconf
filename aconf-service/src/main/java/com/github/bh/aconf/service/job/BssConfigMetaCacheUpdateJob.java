package com.github.bh.aconf.service.job;

import com.github.bh.aconf.service.ConfigMetaCacheService;
import com.github.bh.aconf.service.BssMetaCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaobenhai
 * Date: 2017/2/19
 * Time: 11:17
 */
@Service
public class BssConfigMetaCacheUpdateJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(BssConfigMetaCacheUpdateJob.class);

    @Autowired
    private BssMetaCacheService bssMetaCacheService;
    @Autowired
    private ConfigMetaCacheService configMetaCacheService;

    public void execute() {
        try {
            // 同时更新bss和config，保证version一致！
            bssMetaCacheService.updateBssMetasCache();
            configMetaCacheService.updateConfigMetasCache();
            LOGGER.info("execute bss meta cache success");
        } catch (Exception e) {
            LOGGER.error("execute bss meta cache error", e);
        }
    }

}
