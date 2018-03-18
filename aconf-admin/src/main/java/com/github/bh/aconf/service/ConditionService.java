package com.github.bh.aconf.service;

import com.google.common.collect.Lists;
import com.github.bh.aconf.common.constants.ConditionStates;
import com.github.bh.aconf.common.constants.ValueType;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.command.ConditionCommand;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.ConditionVo;
import com.github.bh.aconf.mapper.ConditionMapper;
import com.github.bh.aconf.persist.base.mapper.ConditionMetaMapper;
import com.github.bh.aconf.persist.base.model.ConditionMeta;
import com.github.bh.aconf.persist.base.model.ConditionMetaExample;
import com.github.bh.aconf.persist.base.model.ResourceMeta;
import com.github.bh.aconf.utils.AuthUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 17:27
 */
@Service
public class ConditionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionService.class);
    @Autowired
    private ConditionMetaMapper conditionMetaMapper;
    @Autowired
    private ResourceService resourceService;

    public ServerResponse addCondition(long configId, ConditionCommand command) {
        ServerResponse response = new ServerResponse();
        if (configId <= 0 || command == null) {
            return response.state(BusinessStatus.INVALID_PARAMS);
        }
        ConditionMeta meta = ConditionMapper.INSTANCE.getMetaForCreate(configId, command);
        conditionMetaMapper.insert(meta);
        ConditionVo vo = ConditionMapper.INSTANCE.getVo(meta);
        return response.state(BusinessStatus.SUCCESS).data(vo);
    }

    List<ConditionMeta> getConditionMetasByConfigId(Long configId) {
        if (configId == null) {
            return Lists.newArrayList();
        }

        ConditionMetaExample example = new ConditionMetaExample();
        example.createCriteria().andConfigIdEqualTo(configId)
                .andStateEqualTo(ConditionStates.VALID.getValue());
        example.setOrderByClause("seq desc");
        return conditionMetaMapper.selectByExample(example);
    }

    /**
     * 通过condition id 精确查询
     *
     * @param conditionId
     * @return
     */
    public ServerResponse getCondition(long conditionId) {
        ServerResponse response = new ServerResponse();
        ConditionMeta condition = conditionMetaMapper.selectByPrimaryKey(conditionId);
        return response.state(BusinessStatus.SUCCESS).data(ConditionMapper.INSTANCE.getVo(condition));
    }

    public ServerResponse delCondition(long configId, long conditionId) {
        ServerResponse response = new ServerResponse();
        ConditionMeta data = conditionMetaMapper.selectByPrimaryKey(conditionId);
        conditionMetaMapper.deleteByPrimaryKey(conditionId);
        return response.state(BusinessStatus.SUCCESS).data(data);
    }

    public ServerResponse updateCondition(long conditionId, ConditionCommand command) {
        ServerResponse response = new ServerResponse();
        ConditionMeta condition = ConditionMapper.INSTANCE.getMetaForUpdate(conditionId, command);
        conditionMetaMapper.updateByPrimaryKeySelective(condition);
        ConditionMeta data = conditionMetaMapper.selectByPrimaryKey(conditionId);
        return response.state(BusinessStatus.SUCCESS).data(data);
    }

    /**
     * 保存条件值元信息
     *
     * @param configId
     * @param conditionMeta
     */
    void saveCondition(Long configId, ConditionMeta conditionMeta, HttpServletRequest request) {
        ResourceMeta res = null;
        String value = conditionMeta.getValue();
        if (value != null && value.startsWith("http")) {
            res = resourceService.getRousourceMetaByUrl(value);
        }

        conditionMeta.setCreateTime(new Date());
        conditionMeta.setUpdateTime(new Date());
        conditionMeta.setState(ConditionStates.VALID.getValue());
        conditionMeta.setConfigId(configId);
        StaffInfo adminUser = AuthUtils.getAdminUser(request);
        if (adminUser != null) {
            conditionMeta.setCreator(adminUser.getPassport());
        }
        if (res != null) {
            conditionMeta.setValueType(ValueType.RESOURCE.getValue());
        }
        conditionMetaMapper.insert(conditionMeta);
        resourceService.supplementConfigId(res, conditionMeta.getConfigId());
    }

    private void updateCondition(ConditionMeta conditionMeta) {
        conditionMeta.setUpdateTime(new Date());
        conditionMetaMapper.updateByPrimaryKeySelective(conditionMeta);
    }

    void mergeCondition(Long configId, ConditionMeta conditionMeta, HttpServletRequest request) {
        if (conditionMeta.getId() != null) {
            updateCondition(conditionMeta);
        } else {
            saveCondition(configId, conditionMeta, request);
        }
    }

    public void deleteConditions(List<Long> deletedConditionIds) {
        if (CollectionUtils.isEmpty(deletedConditionIds)) {
            return;
        }
        ConditionMetaExample example = new ConditionMetaExample();
        example.createCriteria().andIdIn(deletedConditionIds);
        ConditionMeta record = new ConditionMeta();
        record.setUpdateTime(new Date());
        record.setState(ConditionStates.DELETE.getValue());
        conditionMetaMapper.updateByExampleSelective(record, example);
    }

    public Pager searchCondition(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long configId = ((Double) params.get("configId")).longValue();

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            ConditionMetaExample example = buildExample(configId, pageSize, startRecord);
            List<ConditionMeta> metas = conditionMetaMapper.selectByExample(example);
            List<ConditionVo> result = ConditionMapper.INSTANCE.getVos(metas);
            int count = countAll(configId);
            pager.setRecordCount(count);
            int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            pager.setPageCount(pageCount);
            pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
            pager.setIsSuccess(true);
        } catch (Exception e) {
            pager.setIsSuccess(false);
            LOGGER.error("searchBss has an error:{}", e);
        }
        return pager;
    }

    private int countAll(Long configId) {
        ConditionMetaExample example = new ConditionMetaExample();
        example.createCriteria().andConfigIdEqualTo(configId);
        return conditionMetaMapper.countByExample(example);
    }

    private ConditionMetaExample buildExample(Long configId, int limit, int offset) {
        ConditionMetaExample example = new ConditionMetaExample();
        example.setLimit(limit);
        example.setOffset(offset);
        example.setOrderByClause("seq desc");
        ConditionMetaExample.Criteria criteria = example.createCriteria();
        if (configId != 0) {
            criteria.andConfigIdEqualTo(configId);
        }
        criteria.andStateEqualTo(ConditionStates.VALID.getValue());
        return example;
    }
}
