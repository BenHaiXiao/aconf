package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.ConditionStates;
import com.github.bh.aconf.persist.base.mapper.ConditionMetaMapper;
import com.github.bh.aconf.persist.base.model.ConditionMeta;
import com.github.bh.aconf.persist.base.model.ConditionMetaExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 15:25
 */
@Service
public class ConditionMetaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionMetaService.class);

    @Autowired
    private ConditionMetaMapper conditionMetaMapper;

    /**
     * 获取所有有效的Condition，一般用于更新缓存
     *
     * @return Condition列表
     */
    public List<ConditionMeta> getAllValidConditionMetas() {
        ConditionMetaExample example = new ConditionMetaExample();
        example.createCriteria().andStateEqualTo(ConditionStates.VALID.getValue());
        example.setOrderByClause("seq desc");
        List<ConditionMeta> metas = conditionMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }

    /**
     * 从DB查询有效的Condition
     *
     * @param configId 所属的配置项id
     * @return Condition列表
     */
    public List<ConditionMeta> getValidConditionMetas(long configId) {
        ConditionMetaExample example = new ConditionMetaExample();
        ConditionMetaExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(ConditionStates.VALID.getValue());
        criteria.andConfigIdEqualTo(configId);
        List<ConditionMeta> metas = conditionMetaMapper.selectByExample(example);
        if (metas == null) {
            return Collections.emptyList();
        }
        return metas;
    }
}
