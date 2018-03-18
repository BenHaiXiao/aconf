package com.github.bh.aconf.service;

import com.github.bh.aconf.persist.base.mapper.BssMetaMapper;
import com.github.bh.aconf.persist.base.model.BssMeta;
import com.github.bh.aconf.persist.base.model.BssMetaExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 12:19
 */
@Service
public class BssMetaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BssMetaService.class);

    private static final String DB_EXCEPTION_LOGGER_KEY = "bss-meta-service-get-from-db";

    @Autowired
    private BssMetaMapper bssMetaMapper;
    @Autowired
    private BssMetaCacheService bssMetaCacheService;
    @Autowired
    private EmptyBssMetaCacheService emptyBssMetaCacheService;

    public BssMeta getBssMetaByCode(String code) {
        BssMeta meta = bssMetaCacheService.getBssMetaFromCache(code);
        if (meta == null) {
            LOGGER.debug("bss cache not exists bss code {}, get from db", code);
            String emptyFlag = emptyBssMetaCacheService.getEmptyBssMetaFromCache(code);
            if (StringUtils.isBlank(emptyFlag)) {
                BssMeta fromDb = getBssMetaByCodeFromDb(code);
                if (fromDb == null || fromDb.getId() == null) {
                    emptyBssMetaCacheService.setEmptyBssMetaToCache(code);
                } else {
                    bssMetaCacheService.setBssMetaToCache(code, fromDb);
                }
                return fromDb;
            }
        }
        return meta;
    }

    private BssMeta getBssMetaByCodeFromDb(String bssCode) {
        try {
            BssMetaExample example = new BssMetaExample();
            example.createCriteria().andCodeEqualTo(bssCode);
            List<BssMeta> metas = bssMetaMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(metas)) {
                LOGGER.debug("bss not exists in database, bss code {}", bssCode);
                return null;
            }
            return metas.get(0);
        } catch (Exception e) {
            LOGGER.error("getBssMetaByCodeFromDb error", e);

        }
        return null;
    }

}
