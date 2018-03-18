package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.common.constants.FilterType;
import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.command.FilterCommand;
import com.github.bh.aconf.domain.command.FilterCommandV2;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.FilterVo;
import com.github.bh.aconf.mapper.FilterMapper;
import com.github.bh.aconf.persist.base.mapper.BssMetaMapper;
import com.github.bh.aconf.persist.base.mapper.ConditionMetaMapper;
import com.github.bh.aconf.persist.base.mapper.ConfigMetaMapper;
import com.github.bh.aconf.persist.base.mapper.FilterMetaMapper;
import com.github.bh.aconf.persist.base.model.*;
import com.github.bh.aconf.utils.AuthUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2016/11/15
 * Time: 17:05
 */
@Service
public class FilterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterService.class);
    @Autowired
    private FilterMetaMapper filterMetaMapper;
    @Autowired
    private BssMetaMapper bssMetaMapper;
    @Autowired
    private ConfigMetaMapper configMetaMapper;
    @Autowired
    private ConditionMetaMapper conditionMetaMapper;

    public void marge(FilterMeta meta) {
        if (meta.getId() == null) {
            filterMetaMapper.insert(meta);
        } else {
            filterMetaMapper.updateByPrimaryKeySelective(meta);
        }
    }

    public void marge(List<FilterMeta> metas) {
        if (CollectionUtils.isEmpty(metas)) {
            return;
        }
        for (FilterMeta meta : metas) {
            marge(meta);
        }
    }

    /**
     * 获取指定的filter
     *
     * @param filterId
     * @return
     */
    public ServerResponse getFilter(Long filterId) {
        ServerResponse response = new ServerResponse();
        FilterMeta meta = filterMetaMapper.selectByPrimaryKey(filterId);
        FilterVo filterVo = FilterMapper.INSTANCE.getFilterVo(meta);
        if (filterVo == null) {
            return response.state(BusinessStatus.NO_THIS_FILTER);
        }
        return response.state(BusinessStatus.SUCCESS).data(filterVo);
    }

    /**
     * 创建或更新单一Filter
     * Filter的创建或更新会影响到bss配置的取值，所以应该增加版本号
     *
     * @param command
     * @return
     */
    @Transactional
    public FilterVo createOrUpdateFilter(FilterCommand command) {
        FilterMeta meta;
        if (command.getId() != null) {
            meta = FilterMapper.INSTANCE.getFilterMetaForUpdate(command);
            filterMetaMapper.updateByPrimaryKeySelective(meta);
        } else {
            meta = FilterMapper.INSTANCE.getFilterMetaForCreate(command);
            filterMetaMapper.insertSelective(meta);
        }
// FIXME: 2017/1/10 由于暂时不做增量下发，还未完全实现增量，版本号暂不增加
//        updateBssAndConfigVersion(meta);

        FilterMeta result = filterMetaMapper.selectByPrimaryKey(meta.getId());
        return FilterMapper.INSTANCE.getFilterVo(result);
    }

    /**
     * 创建业务拦截器
     *
     * @param bssId
     * @param filterCommandV2
     * @return
     * @since 0.0.2
     */
    public ServerResponse createBssFilter(long bssId, FilterCommandV2 filterCommandV2, HttpServletRequest request) {
        // FIXME: 2017/2/7 注意版本号问题
        FilterMeta record = FilterMapper.INSTANCE.getFilterMetaForCreate(filterCommandV2);
        record.setBelongId(bssId);
        record.setBelongType(FilterBelongType.BSS.getValue());
        StaffInfo user = AuthUtils.getAdminUser(request);
        if (user != null) {
            record.setCreator(user.getPassport());
        }
        filterMetaMapper.insert(record);
        return ServerResponse.success();
    }

    public ServerResponse createFilter(FilterCommand command) {
        ServerResponse response = new ServerResponse();

        if (command.getId() != null) {
            return response.state(BusinessStatus.NO_NEED_PARAMS);
        }
        FilterMeta meta = FilterMapper.INSTANCE.getFilterMetaForCreate(command);
        filterMetaMapper.insertSelective(meta);
        updateBssAndConfigVersion(meta);

        FilterMeta result = filterMetaMapper.selectByPrimaryKey(meta.getId());
        FilterVo filterVo = FilterMapper.INSTANCE.getFilterVo(result);
        return response.state(BusinessStatus.SUCCESS).data(filterVo);
    }

    public ServerResponse updateFilter(FilterCommand command) {
        ServerResponse response = new ServerResponse();
        if (command.getId() == null) {
            return response.state(BusinessStatus.INVALID_FILTER_ID);
        }
        FilterMeta meta = FilterMapper.INSTANCE.getFilterMetaForUpdate(command);
        filterMetaMapper.updateByPrimaryKeySelective(meta);
        updateBssAndConfigVersion(meta);

        FilterMeta result = filterMetaMapper.selectByPrimaryKey(meta.getId());
        FilterVo filterVo = FilterMapper.INSTANCE.getFilterVo(result);
        return response.state(BusinessStatus.SUCCESS).data(filterVo);
    }

    public ServerResponse updateFilter(long filterId, FilterCommandV2 command) {
        command.setId(filterId);
        FilterMeta meta = FilterMapper.INSTANCE.getFilterMeta(command);
        meta.setUpdateTime(new Date());
        filterMetaMapper.updateByPrimaryKeySelective(meta);
        return ServerResponse.success();
    }

    private void updateBssAndConfigVersion(FilterMeta meta) {
        long bssId;
        ConfigMeta config = null;
        if (FilterBelongType.BSS.isMe(meta.getBelongType())) {
            bssId = meta.getBelongId();
        } else if (FilterBelongType.ITEM.isMe(meta.getBelongType())) {
            config = configMetaMapper.selectByPrimaryKey(meta.getBelongId());
            bssId = config.getBssId();
        } else {
            ConditionMeta condition = conditionMetaMapper.selectByPrimaryKey(meta.getBelongId());
            ConfigMeta configMeta = configMetaMapper.selectByPrimaryKey(condition.getConfigId());
            bssId = configMeta.getBssId();
        }
        long currentVersion = getBssVersion(bssId);
        if (config != null) {
            config.setVersion(currentVersion + 1);
            configMetaMapper.updateByPrimaryKeySelective(config);
        }
        increaseBssVersion(bssId, currentVersion);
    }

    /**
     * Filter的删除会影响到bss配置的取值，所以应该增加版本号
     *
     * @param id
     * @return
     */
    @Transactional
    public ServerResponse deleteFilter(Long id) {
        ServerResponse response = new ServerResponse();
        FilterMeta meta = filterMetaMapper.selectByPrimaryKey(id);
        meta.setValid(ValidStatus.INVALID.getValue());
        meta.setUpdateTime(new Date());
        updateBssAndConfigVersion(meta);

        filterMetaMapper.updateByPrimaryKeySelective(meta);
        FilterVo filterVo = FilterMapper.INSTANCE.getFilterVo(meta);
        return response.state(BusinessStatus.SUCCESS).data(filterVo);
    }

    /**
     * 增加业务版本号(+1)
     *
     * @param bssId          业务id
     * @param currentVersion 当前版本
     */
    private void increaseBssVersion(Long bssId, long currentVersion) {
        BssMeta meta = new BssMeta();
        meta.setId(bssId);
        meta.setVersion(currentVersion + 1);
        bssMetaMapper.updateByPrimaryKeySelective(meta);
    }

    /**
     * 获取当前业务的最新版本
     */
    private long getBssVersion(long bssId) {
        BssMeta meta = bssMetaMapper.selectByPrimaryKey(bssId);
        if (meta == null) {
            return 0;
        }
        Long version = meta.getVersion();
        if (version == null) {
            return 0;
        }
        return version;
    }

    /**
     * 分页列出filter列表
     *
     * @param belongId
     * @param belongType
     * @param limit
     * @param offset
     * @return
     */
    public ServerResponse listFilters(long belongId, FilterBelongType belongType, int limit, int offset) {
        ServerResponse response = new ServerResponse();

        FilterMetaExample example = new FilterMetaExample();
        example.createCriteria().andBelongIdEqualTo(belongId).andBelongTypeEqualTo(belongType.getValue());
        List<FilterMeta> filterMetas = filterMetaMapper.selectByExample(example);
        return response.state(BusinessStatus.SUCCESS).data(filterMetas);
    }

    /**
     * 保存Filter
     *
     * @param belongId
     * @param belongType
     * @param filter
     */
    void saveFilter(Long belongId, FilterBelongType belongType, FilterMeta filter, HttpServletRequest request) {
        filter.setCreateTime(new Date());
        filter.setUpdateTime(new Date());
        filter.setBelongType(belongType.getValue());
        filter.setBelongId(belongId);
        StaffInfo user = AuthUtils.getAdminUser(request);
        if (user != null) {
            filter.setCreator(user.getPassport());
        }
        filter.setValid(ValidStatus.VALID.getValue());
        if (filter.getBasisType() == null) {
            filter.setBasisType(FilterType.NORMAL.getValue());
        }
        filterMetaMapper.insert(filter);
    }

    private void updateFilter(FilterMeta filterMeta) {
        filterMeta.setUpdateTime(new Date());
        filterMetaMapper.updateByPrimaryKeySelective(filterMeta);
    }

    void mergeFilter(Long belongId, FilterBelongType belongType, FilterMeta filterMeta, HttpServletRequest request) {
        if (filterMeta.getId() != null) {
            updateFilter(filterMeta);
        } else {
            saveFilter(belongId, belongType, filterMeta, request);
        }
    }

    public void deleteFilters(List<Long> deletedFilterIds) {
        if (CollectionUtils.isEmpty(deletedFilterIds)) {
            return;
        }
        FilterMetaExample example = new FilterMetaExample();
        example.createCriteria().andIdIn(deletedFilterIds);
        FilterMeta record = new FilterMeta();
        record.setUpdateTime(new Date());
        record.setValid(ValidStatus.INVALID.getValue());
        filterMetaMapper.updateByExampleSelective(record, example);
    }

    public Pager searchFilter(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long bssId = NumberUtils.toLong((String) params.get("bssid"), 0);

        Double condId = (Double) params.get("conditionId");
        Long conditionId = 0L;
        if (condId != null) {
            conditionId = condId.longValue();
        }
        Long id = NumberUtils.toLong((String) params.get("id"), 0);

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            FilterMetaExample example = buildExample(bssId, conditionId, id, pageSize, startRecord);
            List<FilterMeta> filterMetas = filterMetaMapper.selectByExample(example);
            List<FilterVo> result = FilterMapper.INSTANCE.getFilterVo(filterMetas);
            int count = countAll(bssId);
            pager.setRecordCount(count);
            int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            pager.setPageCount(pageCount);
            pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
            pager.setIsSuccess(true);
        } catch (Exception e) {
            pager.setIsSuccess(false);
            LOGGER.error("searchFilter has an error:{}", e);
        }
        return pager;
    }

    /**
     * 根据业务ID统计公共拦截器个数
     *
     * @param bssId
     * @return
     */
    private int countAll(Long bssId) {
        FilterMetaExample example = new FilterMetaExample();
        example.createCriteria().andBelongIdEqualTo(bssId).andBelongTypeEqualTo(FilterBelongType.BSS.getValue())
                .andValidEqualTo(ValidStatus.VALID.getValue());
        return filterMetaMapper.countByExample(example);
    }

    private FilterMetaExample buildExample(Long bssId, Long conditionId, Long id, int limit, int offset) {
        FilterMetaExample example = new FilterMetaExample();
        example.setLimit(limit);
        example.setOffset(offset);
        if (bssId != 0 && conditionId != 0) {
            return example;
        }
        FilterMetaExample.Criteria criteria = example.createCriteria();
        if (bssId != 0) {
            criteria.andBelongTypeEqualTo(FilterBelongType.BSS.getValue()).andBelongIdEqualTo(bssId);
        }
        if (conditionId != 0) {
            criteria.andBelongTypeEqualTo(FilterBelongType.CONDITION.getValue()).andBelongIdEqualTo(conditionId);
        }
        if (id != null && id != 0) {
            criteria.andIdEqualTo(id);
        }
        criteria.andValidEqualTo(ValidStatus.VALID.getValue());
        return example;
    }
}
