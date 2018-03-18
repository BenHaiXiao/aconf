package com.github.bh.aconf.service;

import com.github.bh.aconf.persist.base.model.*;
import com.google.common.collect.Lists;
import com.github.bh.aconf.common.constants.BssEffectiveType;
import com.github.bh.aconf.common.constants.BssStates;
import com.github.bh.aconf.common.constants.FilterBelongType;
import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.command.BssCodeCommand;
import com.github.bh.aconf.domain.command.BssCommand;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.BssVo;
import com.github.bh.aconf.domain.vo.FilterVo;
import com.github.bh.aconf.mapper.BssMapper;
import com.github.bh.aconf.mapper.FilterMapper;
import com.github.bh.aconf.persist.BssArchivedMetaExtensionMapper;
import com.github.bh.aconf.persist.BssMetaExtensionMapper;
import com.github.bh.aconf.persist.base.mapper.FilterMetaMapper;
import com.github.bh.aconf.utils.AuthUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 14:29
 */
@Service
public class BssService {
    @Autowired
    private BssMetaExtensionMapper bssMetaMapper;
    @Autowired
    private FilterMetaMapper filterMetaMapper;
    @Autowired
    private BssArchivedMetaExtensionMapper bssArchivedMetaMapper;

    private static final Long ONE_HUNDRED_YEARS = 3153600000000L;

    private static final Logger LOGGER = LoggerFactory.getLogger(BssService.class);

    /**
     * 创建业务，注意创建时code不能重复
     *
     * @param bssCommand
     * @return
     */
    public ServerResponse createBss(BssCommand bssCommand, HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        BssMeta record = BssMapper.INSTANCE.getBssMetaForCreate(bssCommand);
        if (BssEffectiveType.TIMES.isMe(record.getEffectiveType())) {
            if (record.getEffectiveTime() == null) {
                record.setEffectiveTime(new Date());
            }
            if (record.getFailureTime() == null) {
                record.setFailureTime(new Date(System.currentTimeMillis() + ONE_HUNDRED_YEARS));
            }
        }
        StaffInfo user = AuthUtils.getAdminUser(request);
        if (user != null) {
            record.setCreator(user.getPassport());
        }
        bssMetaMapper.insert(record);
        BssVo bssVo = BssMapper.INSTANCE.getBssVo(record);
        return response.state(BusinessStatus.SUCCESS).data(bssVo);
    }

    /**
     * 根据业务id获取业务
     *
     * @param bssId 业务id
     * @return
     */
    public ServerResponse getBssWithFilters(long bssId) {
        ServerResponse response = new ServerResponse();
        if (bssId == 0) {
            return response.state(BusinessStatus.INVALID_BSS_ID);
        }
        BssMeta bssMeta = bssMetaMapper.selectByPrimaryKey(bssId);
        if (bssMeta == null || bssMeta.getId() == null) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        BssVo bssVo = BssMapper.INSTANCE.getBssVo(bssMeta);
        List<FilterVo> filters = getFilters(bssMeta);
        bssVo.setFilters(filters);
        return response.state(BusinessStatus.SUCCESS).data(bssVo);
    }

    public ServerResponse getBss(long bssId) {
        ServerResponse response = new ServerResponse();
        if (bssId == 0) {
            return response.state(BusinessStatus.INVALID_BSS_ID);
        }
        BssMeta bssMeta = bssMetaMapper.selectByPrimaryKey(bssId);
        if (bssMeta == null || bssMeta.getId() == null) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        BssVo bssVo = BssMapper.INSTANCE.getBssVo(bssMeta);
        return response.state(BusinessStatus.SUCCESS).data(bssVo);
    }

    /**
     * 通过业务代号获取业务
     *
     * @param code 业务代号
     * @return
     */
    public ServerResponse getBssWithFilters(String code) {
        ServerResponse response = new ServerResponse();
        if (StringUtils.isEmpty(code)) {
            return response.state(BusinessStatus.INVALID_BSS_CODE);
        }
        BssMetaExample example = new BssMetaExample();
        example.createCriteria().andCodeEqualTo(code);
        List<BssMeta> bssMetas = bssMetaMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bssMetas)) {
            BssMeta bssMeta = bssMetas.get(0);
            BssVo bssVo = BssMapper.INSTANCE.getBssVo(bssMeta);
            List<FilterVo> filters = getFilters(bssMeta);
            bssVo.setFilters(filters);
            return response.state(BusinessStatus.SUCCESS).data(bssVo);
        }
        return response.state(BusinessStatus.NO_THIS_BSS);
    }

    /**
     * 更新业务记录
     *
     * @param bssCommand
     * @return
     */
    public ServerResponse updateBss(BssCommand bssCommand) {
        ServerResponse response = new ServerResponse();
        if (bssCommand == null) {
            return response.state(BusinessStatus.COMMAND_IS_NULL);
        }
        if (bssCommand.getId() == null) {
            return response.state(BusinessStatus.INVALID_BSS_ID);
        }
        BssMeta oriMeta = bssMetaMapper.selectByPrimaryKey(bssCommand.getId());
        if (oriMeta == null) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        BssMeta meta = BssMapper.INSTANCE.getBssMetaForUpdate(bssCommand);
        bssMetaMapper.updateByPrimaryKeySelective(meta);
        BssMeta metaUpdated = bssMetaMapper.selectByPrimaryKey(meta.getId());
        BssVo bssVo = BssMapper.INSTANCE.getBssVo(metaUpdated);
        return response.state(BusinessStatus.SUCCESS).data(bssVo);
    }

    /**
     * 使用code更新业务
     *
     * @param command
     * @return
     */
    public ServerResponse updateBss(BssCodeCommand command) {
        ServerResponse response = new ServerResponse();
        if (command == null) {
            return response.state(BusinessStatus.COMMAND_IS_NULL);
        }
        if (StringUtils.isEmpty(command.getCode())) {
            return response.state(BusinessStatus.INVALID_BSS_CODE);
        }
        BssMetaExample example = new BssMetaExample();
        example.createCriteria().andCodeEqualTo(command.getCode());
        List<BssMeta> bssMetas = bssMetaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bssMetas)) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        //设置bssId、valid和appId
        BssMeta oriMeta = bssMetas.get(0);
        BssMeta bssMeta = BssMapper.INSTANCE.getBssMeta(command);
        bssMeta.setId(oriMeta.getId());
        bssMeta.setAppId(oriMeta.getAppId());
        bssMeta.setValid(ValidStatus.VALID.getValue());
        bssMetaMapper.updateByPrimaryKeySelective(bssMeta);
        BssVo bssVo = BssMapper.INSTANCE.getBssVo(bssMeta);
        bssVo.setCreateTime(oriMeta.getCreateTime());
        bssVo.setVersion(oriMeta.getVersion());
        bssVo.setFilters(getFilters(bssMeta));
        return response.state(BusinessStatus.SUCCESS).data(bssVo);
    }


    /**
     * 删除业务
     *
     * @param bssId
     * @return
     */
    @Transactional
    public ServerResponse deleteBss(long bssId) {
        ServerResponse response = new ServerResponse();
        if (bssId == 0) {
            return response.state(BusinessStatus.INVALID_BSS_ID);
        }
        BssMeta meta = bssMetaMapper.selectByPrimaryKey(bssId);
        if (meta == null) {
            return response.state(BusinessStatus.NO_THIS_BSS);
        }
        meta.setValid(BssStates.DELETE.getValue());
        meta.setUpdateTime(new Date());
        archive(meta);
        BssVo bssVo = BssMapper.INSTANCE.getBssVo(meta);
        response.state(BusinessStatus.SUCCESS).data(bssVo);
        return response;
    }

    public List<BssVo> list(long appid, int offset, int limit, String order) {
        BssMetaExample example = new BssMetaExample();
        example.setLimit(limit);
        example.setOffset(offset);
        example.setOrderByClause("valid desc,id asc");
        if (StringUtils.isNotEmpty(order)) {
            example.setOrderByClause(order);
        }
        example.createCriteria().andAppIdEqualTo(appid)
                .andValidEqualTo(ValidStatus.VALID.getValue());
        List<BssMeta> list = bssMetaMapper.selectByExample(example);
        List<BssVo> vos = Lists.newArrayList();
        for (BssMeta bssMeta : list) {
            List<FilterVo> filters = getFilters(bssMeta);
            BssVo bssVo = BssMapper.INSTANCE.getBssVo(bssMeta);
            bssVo.setFilters(filters);
            vos.add(bssVo);
        }
        return vos;
    }

    public List<BssMeta> listAll() {
        BssMetaExample example = new BssMetaExample();
        example.setOrderByClause(" id asc");
        example.createCriteria().andValidEqualTo(ValidStatus.VALID.getValue());
        List<BssMeta> list = bssMetaMapper.selectByExample(example);
        return list;
    }
    
    private List<FilterVo> getFilters(BssMeta meta) {
        FilterMetaExample filterMetaExample = new FilterMetaExample();
        filterMetaExample.createCriteria()
                .andBelongIdEqualTo(meta.getId())
                .andBelongTypeEqualTo(FilterBelongType.BSS.getValue())
                .andValidEqualTo(ValidStatus.VALID.getValue());
        List<FilterMeta> filterMetas = filterMetaMapper.selectByExample(filterMetaExample);
        return FilterMapper.INSTANCE.getFilterVo(filterMetas);
    }

    public int countAll() {
        BssMetaExample example = new BssMetaExample();
        return bssMetaMapper.countByExample(example);
    }

    public int countAll(BssCommand command) {
        BssMetaExample example = buildExample(command);
        return bssMetaMapper.countByExample(example);
    }

    public List<BssVo> query(BssCommand command, int curPage, int pageSize) {
        if (command == null) {
            return Lists.newArrayList();
        }
        BssMetaExample example = buildExample(command);
        example.setOffset((curPage - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("valid desc,id asc");

        List<BssMeta> appMetas = bssMetaMapper.selectByExample(example);
        return BssMapper.INSTANCE.getBssVos(appMetas);
    }

    private BssMetaExample buildExample(BssCommand command) {
        BssMetaExample example = new BssMetaExample();
        BssMetaExample.Criteria criteria = example.createCriteria();
        if (command.getId() != null) {
            criteria.andIdEqualTo(command.getId());
        }
        if (StringUtils.isNotEmpty(command.getName())) {
            criteria.andNameEqualTo(command.getName());
        }
        return example;
    }

    /**
     * 归档业务
     *
     * @param bss 待归档的业务对象
     */
    private void archive(BssMeta bss) {
        if (bss == null) {
            return;
        }
        bssMetaMapper.deleteByPrimaryKey(bss.getId());
        BssArchivedMeta archivedMeta = BssMapper.INSTANCE.getArchivedMeta(bss);
        bssArchivedMetaMapper.insert(archivedMeta);
    }

    /**
     * 用于通过业务代号获取业务ID
     *
     * @param code
     * @return
     */
    public Long getBssIdByCode(String code) {
        BssMetaExample example = new BssMetaExample();
        example.createCriteria().andCodeEqualTo(code);
        List<BssMeta> bssMetas = bssMetaMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bssMetas)) {
            return -1L;
        }
        return bssMetas.get(0).getId();
    }

    /**
     * 搜索业务
     *
     * @param gridPager 表格数据
     * @return 查询结果页对象
     */
    public Pager searchBss(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long appId = NumberUtils.toLong((String) params.get("appid"), 0);
        String code = (String) params.get("code");
        String name = (String) params.get("name");
        Byte state = NumberUtils.toByte((String) params.get("state"), BssStates.VALID.getValue());

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            if (BssStates.FINISHED.isMe(state) || BssStates.DELETE.isMe(state)) {
                if (StringUtils.isNotBlank(code)) {
                    searchArchivedBssByCode(pager, code, pageSize, startRecord);
                } else {
                    List<BssArchivedMeta> metas = bssArchivedMetaMapper.selectBssMetas(appId, name, pageSize, startRecord);
                    List<BssVo> result = BssMapper.INSTANCE.getBssVosByArchivedMetas(metas);
                    int count = bssArchivedMetaMapper.countBssMetas(appId, name, pageSize, startRecord);
                    pager.setRecordCount(count);
                    int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
                    pager.setPageCount(pageCount);
                    pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
                    pager.setIsSuccess(true);
                }
            } else {
                if (StringUtils.isNotBlank(code)) {
                    searchBssByCode(pager, code, state, pageSize, startRecord);
                } else {
                    List<BssMeta> metas = bssMetaMapper.selectBssMetas(appId, name, state, pageSize, startRecord);
                    List<BssVo> result = BssMapper.INSTANCE.getBssVos(metas);
                    int count = bssMetaMapper.countBssMetas(appId, name, state, pageSize, startRecord);
                    pager.setRecordCount(count);
                    int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
                    pager.setPageCount(pageCount);
                    pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
                    pager.setIsSuccess(true);
                }
            }
        } catch (Exception e) {
            pager.setIsSuccess(false);
            LOGGER.error("searchBss has an error:{}", e);
        }
        return pager;
    }

    private void searchBssByCode(Pager pager, String code, byte state, int limit, int offset) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        BssMetaExample example = new BssMetaExample();
        BssMetaExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        int delCount = 0;
        List<BssArchivedMeta> delMetas = Lists.newArrayList();
        if (!BssStates.ALL.isMe(state)) {
            criteria.andValidEqualTo(state);
        }
        int bssCount = bssMetaMapper.countByExample(example);
        if (BssStates.ALL.isMe(state)) {
            BssArchivedMetaExample archivedMetaExample = new BssArchivedMetaExample();
            archivedMetaExample.createCriteria().andCodeEqualTo(code);
            delCount = bssArchivedMetaMapper.countByExample(archivedMetaExample);
            if (offset >= bssCount) {
                archivedMetaExample.setOffset(offset - bssCount);
                archivedMetaExample.setLimit(limit);
                delMetas = bssArchivedMetaMapper.selectByExample(archivedMetaExample);
            } else if (offset + limit > bssCount) {
                archivedMetaExample.setOffset(offset + limit - bssCount);
                archivedMetaExample.setLimit(limit);
                delMetas = bssArchivedMetaMapper.selectByExample(archivedMetaExample);
            }
        }
        example.setLimit(limit);
        example.setOffset(offset);
        List<BssMeta> metas = bssMetaMapper.selectByExample(example);
        List<BssVo> result = BssMapper.INSTANCE.getBssVos(metas);
        result.addAll(BssMapper.INSTANCE.getBssMetaByArchived(delMetas));
        int count = bssCount + delCount;
        pager.setRecordCount(count);
        int pageCount = count / limit + (count % limit > 0 ? 1 : 0);
        pager.setPageCount(pageCount);
        pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
        pager.setIsSuccess(true);
    }

    private void searchArchivedBssByCode(Pager pager, String code, int limit, int offset) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        BssArchivedMetaExample example = new BssArchivedMetaExample();
        example.createCriteria().andCodeEqualTo(code);
        int count = bssArchivedMetaMapper.countByExample(example);
        example.setLimit(limit);
        example.setOffset(offset);
        List<BssArchivedMeta> metas = bssArchivedMetaMapper.selectByExample(example);
        List<BssVo> result = BssMapper.INSTANCE.getBssVosByArchivedMetas(metas);
        pager.setRecordCount(count);
        int pageCount = count / limit + (count % limit > 0 ? 1 : 0);
        pager.setPageCount(pageCount);
        pager.setExhibitDatas(BeanUtils.batchConvertBean(result));
        pager.setIsSuccess(true);
    }
}
