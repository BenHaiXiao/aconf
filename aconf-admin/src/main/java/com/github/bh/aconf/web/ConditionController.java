package com.github.bh.aconf.web;


import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.command.ConditionCommand;
import com.github.bh.aconf.service.ConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 17:26
 */
@Controller
@RequestMapping("/")
public class ConditionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionController.class);
    @Autowired
    private ConditionService conditionService;


    @RequestMapping(value = "condition/search")
    @ResponseBody
    public String searchConfig(String gridPager) {
        LOGGER.info("searchConfig invoked... gridPager:{}", gridPager);
        Pager pager = conditionService.searchCondition(gridPager);
        String result = JsonUtils.toJson(pager);
        LOGGER.info("searchConfig result = {}", result);
        return result;
    }

    @RequestMapping(value = "config/{configId}/condition", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCondition(@PathVariable("configId") long configId, @RequestBody ConditionCommand command) {
        return conditionService.addCondition(configId, command);
    }

    @RequestMapping(value = "config/{configId}/condition/{conditionId}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updateCondition(@PathVariable("configId") long configId,
                                          @PathVariable("conditionId") long conditionId,
                                          @RequestBody ConditionCommand command) {
        return conditionService.updateCondition(conditionId, command);
    }

    //used

    @RequestMapping(value = "condition/{conditionId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCondition(@PathVariable("conditionId") long conditionId) {
        return conditionService.getCondition(conditionId);
    }

    @RequestMapping(value = "config/{configId}/condition/{conditionId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteCondition(@PathVariable("configId") long configId,
                                          @PathVariable("conditionId") long conditionId) {
        return conditionService.delCondition(configId, conditionId);
    }
}
