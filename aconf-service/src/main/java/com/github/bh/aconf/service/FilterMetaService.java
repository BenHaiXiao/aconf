package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.persist.base.mapper.FilterMetaMapper;
import com.github.bh.aconf.persist.base.model.FilterMeta;
import com.github.bh.aconf.persist.base.model.FilterMetaExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/2/20
 * Time: 9:13
 */
@Service
public class FilterMetaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterMetaService.class);

    @Autowired
    private FilterMetaMapper filterMetaMapper;

    public List<FilterMeta> getAllValidFilterMetas() {
        FilterMetaExample example = new FilterMetaExample();
        example.createCriteria().andValidEqualTo(ValidStatus.VALID.getValue());
        List<FilterMeta> metas = filterMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }

    public List<FilterMeta> getValidFilterMetas(long belongId, FilterBelongType belongType) {
        FilterMetaExample example = new FilterMetaExample();
        FilterMetaExample.Criteria criteria = example.createCriteria();
        criteria.andValidEqualTo(ValidStatus.VALID.getValue());
        criteria.andBelongTypeEqualTo(belongType.getValue());
        criteria.andBelongIdEqualTo(belongId);
        List<FilterMeta> metas = filterMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }

}
