package com.github.bh.aconf.web;

import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.FilterCommandV2;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.service.FilterSelectService;
import com.github.bh.aconf.service.FilterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaobenhai
 * Date: 2016/11/17
 * Time: 16:24
 */
@Controller
public class FilterController {
    private static Logger logger = LoggerFactory.getLogger(FilterController.class);

    @Autowired
    private FilterService filterService;
    @Autowired
    private FilterSelectService filterSelectService;


    @RequestMapping(value = "filter-mgr")
    public String filterMgr(@RequestParam("bssid") Long bssId, Model model, HttpServletRequest request) {
        logger.info("filterMgr method invoked... bssid={}", bssId);
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            model.addAttribute("token", token);
        }
        model.addAttribute("bssid", bssId);
        return "filter/filter-mgr";
    }


    @RequestMapping(value = "filter/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchFilter(String gridPager) {
        logger.info("searchBss invoked... gridPager:{}", gridPager);
        Pager pager = filterService.searchFilter(gridPager);
        String result = JsonUtils.toJson(pager);
        logger.info("searchBss result = {}", result);
        return result;
    }

    //used
    @RequestMapping(value = "/bss/{bssid}/filter", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse createFilter(@PathVariable("bssid") long bssId,
                                       @RequestBody FilterCommandV2 command,
                                       HttpServletRequest request) {
        logger.info("createFilter method invoked.. command={}", command);
        return filterService.createBssFilter(bssId, command, request);
    }

    //used
    @RequestMapping(value = "/filter/{filterId}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updateFilter(@PathVariable("filterId") long filterId, @RequestBody FilterCommandV2 command, HttpServletRequest request) {
        logger.info("updateFilter method invoked... filterId={}, command={}", filterId, command);
        return filterService.updateFilter(filterId, command);
    }



    @RequestMapping(value = "filter/{filterId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getFilter(@PathVariable("filterId") Long filterId) {
        logger.info("getFilter method invoked.. filterId={}", filterId);
        return filterService.getFilter(filterId);
    }

    //used
    @RequestMapping(value = "filter/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteFilter(@PathVariable("id") Long id, HttpServletRequest request) {
        logger.info("deleteFilter method invoked... id={}", id);
        return filterService.deleteFilter(id);
    }


    @RequestMapping(value = "filter/basis", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getFilterSelect() {
        logger.info("getFilterSelect invoked...");
        return filterSelectService.getFilters();
    }


    @RequestMapping(value = "filter/operator", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getOperator(String basis) {
        logger.info("getFilterSelect invoked...");
        return filterSelectService.getOperators(basis);
    }
}
