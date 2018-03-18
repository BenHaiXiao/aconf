package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.PushMetaStates;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.StaffInfo;
import com.github.bh.aconf.domain.command.PushCommand;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.PushDetailVo;
import com.github.bh.aconf.domain.vo.PushVo;
import com.github.bh.aconf.mapper.PushMapper;
import com.github.bh.aconf.persist.base.mapper.PushMetaMapper;
import com.github.bh.aconf.persist.base.model.PushMeta;
import com.github.bh.aconf.persist.base.model.PushMetaExample;
import com.github.bh.aconf.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
 * Date: 2016/11/16
 * Time: 15:32
 */
@Service
public class PushService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushService.class);
    @Autowired
    private PushMetaMapper pushMetaMapper;

    /**
     * 获取某一业务下所有push配置
     *
     * @param bssId
     * @return
     */
    public List<PushVo> getPushes(long bssId) {
        PushMetaExample example = new PushMetaExample();
        example.createCriteria().andBssIdEqualTo(bssId)
                .andStateNotEqualTo(PushMetaStates.DELETED.getValue())
                .andStateNotEqualTo(PushMetaStates.ARCHIVED.getValue());
        List<PushMeta> pushMetas = pushMetaMapper.selectByExample(example);
        return PushMapper.INSTANCE.getPushVo(pushMetas);
    }

    /**
     * 获取单一push配置
     *
     * @param pushId
     * @return
     */
    public PushDetailVo getPush(long pushId) {
        PushMeta pushMeta = pushMetaMapper.selectByPrimaryKey(pushId);
        return PushMapper.INSTANCE.getPushDetailVo(pushMeta);
    }

    /**
     * 创建和更新push操作
     *
     * @param command push参数
     * @return push详细信息vo
     */
    public ServerResponse createPush(long bssId, PushCommand command, HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        PushMeta meta = PushMapper.INSTANCE.getPushMetaForCreate(command);
        meta.setBssId(bssId);
        meta.setState(PushMetaStates.EFFECTIVE.getValue());
        StaffInfo user = AuthUtils.getAdminUser(request);
        if (user != null) {
            meta.setCreator(user.getPassport());
        }
        pushMetaMapper.insert(meta);
        return response.state(BusinessStatus.SUCCESS);
    }

    public ServerResponse updatePush(long id, PushCommand command) {
        ServerResponse response = new ServerResponse();
        // TODO: 2017/2/7 判断是否是已经删除的push，已经删除的不予修改
        PushMeta meta = PushMapper.INSTANCE.getPushMetaForUpdate(command);
        meta.setId(id);
        pushMetaMapper.updateByPrimaryKeySelective(meta);
        return response.state(BusinessStatus.SUCCESS);
    }

    /**
     * 伪删，将push的state字段置为100，查询时不再显示
     *
     * @param id push id
     * @return 被删除的push的详细信息
     */
    public ServerResponse deletePush(long id) {
        ServerResponse response = new ServerResponse();
        PushMeta meta = new PushMeta();
        meta.setId(id);
        meta.setState(PushMetaStates.DELETED.getValue());
        meta.setUpdateTime(new Date());
        pushMetaMapper.updateByPrimaryKeySelective(meta);
        return response.state(BusinessStatus.SUCCESS);
    }

    /**
     * 查询广播。配合DtGrid表格插件，返回符合格式的Pager对象。
     *
     * @param gridPager pager's json string
     * @return
     */
    public Pager searchPush(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long bssId = NumberUtils.toLong((String) params.get("bssid"), 0);
        String title = (String) params.get("title");
        String channel = (String) params.get("channel");
        Integer state = NumberUtils.toInt((String) params.get("state"), PushMetaStates.EFFECTIVE.getValue());

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            PushMetaExample example = buildExample(bssId, title, channel, state, pageSize, startRecord);
            List<PushMeta> pushMetas = pushMetaMapper.selectByExample(example);
            List<PushDetailVo> result = PushMapper.INSTANCE.getPushDetailVo(pushMetas);
            int count = countAll(bssId, state);
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

    /**
     * 统计某一业务的所有广播总数
     *
     * @param bssId 业务id
     * @return 广播条目数
     */
    private int countAll(Long bssId, int state) {
        PushMetaExample example = new PushMetaExample();
        example.createCriteria().andBssIdEqualTo(bssId).andStateEqualTo(state);
        return pushMetaMapper.countByExample(example);
    }

    private PushMetaExample buildExample(Long bssId, String title, String channel, Integer state, int limit, int offset) {
        PushMetaExample example = new PushMetaExample();
        example.setLimit(limit);
        example.setOffset(offset);
        PushMetaExample.Criteria criteria = example.createCriteria();
        if (bssId != null) {
            criteria.andBssIdEqualTo(bssId);
        }
        if (StringUtils.isNotBlank(title)) {
            criteria.andTitleLike(title);
        }
        if (StringUtils.isNotBlank(channel)) {
            criteria.andSidsLike(channel);
        }
        if (state != null) {
            criteria.andStateEqualTo(state);
        }
        return example;
    }
}
