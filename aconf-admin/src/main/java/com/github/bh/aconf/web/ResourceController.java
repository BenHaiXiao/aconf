package com.github.bh.aconf.web;

import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.command.ResourceCommand;
import com.github.bh.aconf.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaobenhai
 * Date: 2017/3/10
 * Time: 17:17
 */
@Controller
public class ResourceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    //used

    @RequestMapping(value = "resource/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchResource(String gridPager) {
        LOGGER.info("searchResource invoked... gridPager:{}", gridPager);
        Pager pager = resourceService.searchResource(gridPager);
        String result = JsonUtils.toJson(pager);
        LOGGER.info("searchResource result = {}", result);
        return result;
    }

    @RequestMapping(value = "resource", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadResource(ResourceCommand command, HttpServletRequest request) {
        LOGGER.info("uploadResponse invoked...");
        return resourceService.upload(command, request);
    }
}
