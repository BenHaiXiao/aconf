package com.github.bh.aconf.service;

import com.google.common.collect.Lists;
import com.github.bh.aconf.common.constants.BssStates;
import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.domain.command.AppCommand;
import com.github.bh.aconf.domain.vo.AppPageVo;
import com.github.bh.aconf.domain.vo.AppVo;
import com.github.bh.aconf.mapper.AppMapper;
import com.github.bh.aconf.mapper.BssMapper;
import com.github.bh.aconf.persist.base.mapper.AppMetaMapper;
import com.github.bh.aconf.persist.base.mapper.BssMetaMapper;
import com.github.bh.aconf.persist.base.model.AppMeta;
import com.github.bh.aconf.persist.base.model.AppMetaExample;
import com.github.bh.aconf.persist.base.model.BssMeta;
import com.github.bh.aconf.persist.base.model.BssMetaExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 19:56
 */
@Service
public class AppService {
    @Autowired
    private AppMetaMapper appMetaMapper;
    @Autowired
    private BssMetaMapper bssMetaMapper;

    public AppVo getApp(long appid) {
        AppMeta appMeta = appMetaMapper.selectByPrimaryKey(appid);
        return AppMapper.INSTANCE.getAppVo(appMeta);
    }

    public List<AppVo> list(int limit, int offset, String order) {
        AppMetaExample example = new AppMetaExample();
        if (limit > 0) {  //小于等于0表示不分页
            example.setLimit(limit);
            example.setOffset(offset);
        }
        if (StringUtils.isNotEmpty(order)) {
            example.setOrderByClause(order);
        }
        List<AppMeta> appMetas = appMetaMapper.selectByExample(example);
        List<AppVo> result = AppMapper.INSTANCE.getAppVoList(appMetas);
        for (AppVo vo : result) {
            Long id = vo.getId();
            BssMetaExample bssMetaExample = new BssMetaExample();
            bssMetaExample.createCriteria().andAppIdEqualTo(id)
                    .andValidEqualTo(ValidStatus.VALID.getValue());
            List<BssMeta> bssMetas = bssMetaMapper.selectByExample(bssMetaExample);
            vo.setBss(BssMapper.INSTANCE.getBssVos(bssMetas));
        }
        return result;
    }

    public AppVo create(AppCommand command) {
        AppMeta meta = AppMapper.INSTANCE.getAppMetaForCreate(command);
        appMetaMapper.insert(meta);
        // FIXME: 2016/11/9 返回值缺少id
        return AppMapper.INSTANCE.getAppVo(meta);
    }

    public AppVo update(AppCommand command) {
        Long id = command.getId();
        if (id != null) {
            AppMeta ori = AppMapper.INSTANCE.getAppMetaForUpdate(command);
            appMetaMapper.updateByPrimaryKeySelective(ori);
            return AppMapper.INSTANCE.getAppVo(ori);
        }
        return null;
    }

    public AppVo delete(long appid) {
        AppMeta meta = appMetaMapper.selectByPrimaryKey(appid);
        if (meta != null) {
//            appMetaMapper.deleteByPrimaryKey(appid);
            meta.setValid((byte) 0);
            meta.setUpdateTime(new Date());
            appMetaMapper.updateByPrimaryKeySelective(meta);
        }
        return AppMapper.INSTANCE.getAppVo(meta);
    }

    public int countAll() {
        AppMetaExample example = new AppMetaExample();
        return appMetaMapper.countByExample(example);
    }

    public int countAll(AppCommand command) {
        AppMetaExample example = buildExample(command);
        return appMetaMapper.countByExample(example);
    }

    public List<AppVo> query(AppCommand command, int curPage, int pageSize) {
        if (command == null) {
            return Lists.newArrayList();
        }
        AppMetaExample example = buildExample(command);
        example.setOffset((curPage - 1) * pageSize);
        example.setLimit(pageSize);
        example.setOrderByClause("valid desc,id asc");

        List<AppMeta> appMetas = appMetaMapper.selectByExample(example);
        return AppMapper.INSTANCE.getAppVoList(appMetas);

    }

    private AppMetaExample buildExample(AppCommand command) {
        AppMetaExample example = new AppMetaExample();
        AppMetaExample.Criteria criteria = example.createCriteria();
        if (command.getId() != null) {
            criteria.andIdEqualTo(command.getId());
        }
        if (StringUtils.isNotEmpty(command.getName())) {
            criteria.andNameEqualTo(command.getName());
        }
        return example;
    }

    public List<AppPageVo> getAppPageVos() {
        List<AppPageVo> appPageVos = Lists.newArrayList();
        AppMetaExample example = new AppMetaExample();
        example.createCriteria().andValidEqualTo(ValidStatus.VALID.getValue());
        List<AppMeta> apps = appMetaMapper.selectByExample(example);
        for (AppMeta app : apps) {
            BssMetaExample bssMetaExample = new BssMetaExample();
            // FIXME: 2017/1/13 为了方便依次查库，可能影响效率，后期若查询缓慢，考虑改造
            bssMetaExample.createCriteria().andValidEqualTo(BssStates.VALID.getValue()).andAppIdEqualTo(app.getId());
            int bssNum = bssMetaMapper.countByExample(bssMetaExample);
            AppPageVo vo = AppMapper.INSTANCE.getAppPageVo(app, bssNum);
            appPageVos.add(vo);
        }
        return appPageVos;
    }
}
