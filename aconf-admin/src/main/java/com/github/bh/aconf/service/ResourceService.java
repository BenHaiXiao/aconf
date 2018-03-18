package com.github.bh.aconf.service;

import com.github.bh.aconf.common.constants.ValidStatus;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.ResourceCommand;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.ResourceVo;
import com.github.bh.aconf.mapper.ResourceMapper;
import com.github.bh.aconf.persist.base.mapper.ResourceMetaMapper;
import com.github.bh.aconf.persist.base.model.ResourceMeta;
import com.github.bh.aconf.persist.base.model.ResourceMetaExample;
import com.github.bh.aconf.utils.AuthUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/10
 * Time: 17:17
 */
@Service
public class ResourceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceService.class);
    private static final String DEFAULT_ORDER = "upload_time desc";
    @Autowired
    private ResourceMetaMapper resourceMetaMapper;

    public Pager searchResource(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long configId = NumberUtils.toLong((String) params.get("configId"), 0);

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            List<ResourceVo> data = fetchData(configId, pageSize, startRecord);
            int count = countAll(configId);
            pager.setRecordCount(count);
            int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            pager.setPageCount(pageCount);
            pager.setExhibitDatas(BeanUtils.batchConvertBean(data));
            pager.setIsSuccess(true);
        } catch (Exception e) {
            pager.setIsSuccess(false);
            LOGGER.error("searchResource has an error:{}", e);
        }
        return pager;
    }

    private List<ResourceVo> fetchData(Long configId, int limit, int offset) {
        ResourceMetaExample example = new ResourceMetaExample();
        ResourceMetaExample.Criteria criteria = example.createCriteria();
        criteria.andConfigIdEqualTo(configId);
        criteria.andStateEqualTo(ValidStatus.VALID.getValue());
        example.setLimit(limit);
        example.setOffset(offset);
        example.setOrderByClause(DEFAULT_ORDER);

        List<ResourceMeta> metas = resourceMetaMapper.selectByExample(example);
        return ResourceMapper.INSTANCE.getVoList(metas);
    }

    private int countAll(Long configId) {
        if (configId == null || configId == -1) {
            return 0;
        }
        ResourceMetaExample example = new ResourceMetaExample();
        example.createCriteria().andStateEqualTo(ValidStatus.VALID.getValue())
                .andConfigIdEqualTo(configId);
        return resourceMetaMapper.countByExample(example);
    }

    public ServerResponse upload(ResourceCommand command, HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        MultipartFile file = command.getFile();
        if (file == null) {
            return response.state(BusinessStatus.INVALID_PARAMS);
        }
        try {
            String fileName = ((CommonsMultipartFile) file).getFileItem().getName();
            String url = "https:tests.yy.com";
            if (StringUtils.isNotBlank(url)) {
                String passport = AuthUtils.getAdminUser(request).getPassport();
                long configId = command.getConfigId() == null ? 0 : command.getConfigId();
                String description = command.getDescription();
                saveResource(url, passport, configId, description);
                return response.state(BusinessStatus.SUCCESS);
            }
        } catch (Exception e) {
            LOGGER.error("decode file failed, file desc = {}", command.getDescription(), e);
        }
        return response.state(BusinessStatus.NETWORK_ERROR);
    }

    @Transactional
    private void saveResource(String url, String passport, long configId, String description) {
        long version = calcVersion(configId);
        ResourceMeta meta = new ResourceMeta();
        meta.setConfigId(configId);
        meta.setCreator(passport);
        meta.setDescription(description);
        meta.setState(ValidStatus.VALID.getValue());
        meta.setUploadTime(new Date());
        meta.setUrl(url);
        meta.setVersion(version);
        resourceMetaMapper.insert(meta);
    }

    public ResourceMeta getRousourceMetaByUrl(String url) {
        ResourceMetaExample example = new ResourceMetaExample();
        example.createCriteria().andUrlEqualTo(url).andStateEqualTo(ValidStatus.VALID.getValue());
        List<ResourceMeta> reses = resourceMetaMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(reses)) {
            return reses.get(0);
        }
        return null;
    }

    /**
     * 用于先上传了资源，后创建配置项的情况（新建配置项）
     * 在这种情况下，在配置项保存后，应该调用该方法将缺失的configId补上
     *
     * @param res      待补资源
     * @param configId 待补资源所属的配置项ID
     */
    public void supplementConfigId(ResourceMeta res, long configId) {
        if (res != null) {
            long version = calcVersion(configId);
            res.setConfigId(configId);
            res.setVersion(version);
            resourceMetaMapper.updateByPrimaryKeySelective(res);
        }
    }

    private long calcVersion(long configId) {
        long version = 0;
        if (configId != 0) {
            ResourceMetaExample example = new ResourceMetaExample();
            example.createCriteria().andConfigIdEqualTo(configId);
            example.setLimit(1);
            example.setOffset(0);
            example.setOrderByClause("version desc");
            List<ResourceMeta> resourceMetas = resourceMetaMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(resourceMetas)) {
                ResourceMeta meta = resourceMetas.get(0);
                Long v = meta.getVersion();
                if (v != null) {
                    version = v + 1;
                }
            }
        }
        return version;
    }
}
