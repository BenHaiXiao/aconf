package com.github.bh.aconf.web;

import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.service.BoundaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaobenhai
 * Date: 2017/3/3
 * Time: 9:47
 */
@Controller
@RequestMapping("boundary")
public class BoundaryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoundaryController.class);
    @Autowired
    private BoundaryService boundaryService;

    @RequestMapping("search")
    @ResponseBody
    public String searchBoundary(String gridPager) {
        LOGGER.info("searchBoundary invoked... gridPager:{}", gridPager);
        Pager pager = boundaryService.searchBoundary(gridPager);
        String result = JsonUtils.toJson(pager);
        LOGGER.info("searchBoundary result = {}", result);
        return result;
    }
}
