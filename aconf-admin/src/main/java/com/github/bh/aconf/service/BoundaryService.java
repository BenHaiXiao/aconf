package com.github.bh.aconf.service;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.github.bh.aconf.common.utils.BeanUtils;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.FilterType;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.webdb.OtherInfo;
import com.github.bh.aconf.domain.webdb.SessionInfo;
import com.github.bh.aconf.domain.webdb.UserInfo;
import com.github.bh.aconf.persist.base.mapper.FilterMetaMapper;
import com.github.bh.aconf.persist.base.model.FilterMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/3
 * Time: 9:53
 */
@Service
public class BoundaryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoundaryService.class);
    @Autowired
    private FilterMetaMapper filterMetaMapper;

    public Pager searchBoundary(String gridPager) {
        Pager pager = JsonUtils.fromJson(gridPager, Pager.class);
        Map<String, Object> params = pager.getParameters();
        Long filterId = ((Double) params.get("filterId")).longValue();

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        try {
            Boundary boundary = fetchData(filterId);
            int count = boundary.count;
            pager.setRecordCount(count);
            int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
            pager.setPageCount(pageCount);
            pager.setExhibitDatas(BeanUtils.batchConvertBean(boundary.data));
            pager.setIsSuccess(true);
        } catch (Exception e) {
            pager.setIsSuccess(false);
            LOGGER.error("searchFilter has an error:{}", e);
        }
        return pager;
    }

    private Boundary fetchData(Long filterId) {
        FilterMeta filterMeta = filterMetaMapper.selectByPrimaryKey(filterId);
        String basis = filterMeta.getBasis();
        FilterType filterType = FilterType.find(basis);

        Boundary boundary = new Boundary();
        boundary.type = filterType;

        String boundaries = filterMeta.getBoundary();
        List<String> boundaryList = splitBoundaries(boundaries);
        boundary.count = boundaryList.size();
        switch (filterType) {
            case SID:
                if (!"$n".equals(filterMeta.getOperator())) {
                    boundary.data = fetchSidData(boundaryList);
                }
                break;
            case UID:
                if (!"$n".equals(filterMeta.getOperator())) {
                    boundary.data = fetchUidData(boundaryList);
                }
                break;
            default:
                break;
        }
        if (boundary.data == null) {
            List<OtherInfo> result = Lists.newArrayList();
            for (String s : boundaryList) {
                OtherInfo info = new OtherInfo();
                info.setData(s);
                result.add(info);
            }
            boundary.data = result;
        }
        return boundary;
    }

    private List<?> fetchUidData(List<String> boundaryList) {
        List<String> needInfo = Lists.newArrayList();
        List<UserInfo> result = Lists.newArrayList();
        return result;
    }

    private List<?> fetchSidData(List<String> boundaryList) {
        List<SessionInfo> result = Lists.newArrayList();
        return result;
    }

    private List<String> splitBoundaries(String boundaries) {
        if (boundaries.startsWith("[") && boundaries.endsWith("]")) {
            return JsonUtils.fromJson(boundaries, new TypeToken<List<String>>() {
            }.getType());
        } else {
            return Lists.newArrayList(boundaries);
        }
    }

    private class Boundary {
        FilterType type;
        int count;
        List<?> data;
    }
}
