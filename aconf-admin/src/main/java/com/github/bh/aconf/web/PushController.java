package com.github.bh.aconf.web;


import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.PushCommand;
import com.github.bh.aconf.domain.vo.PushDetailVo;
import com.github.bh.aconf.domain.vo.PushVo;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.service.PushService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/16
 * Time: 15:32
 */
@Controller
public class PushController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushController.class);
    @Autowired
    private PushService pushService;


    @RequestMapping(value = "push-mgr", method = RequestMethod.GET)
    public String appMgrView(@RequestParam("bssid") long bssid, Model model, HttpServletRequest request) {
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            model.addAttribute("token", token);
        }
        model.addAttribute("bssid", bssid);
        return "push/push-mgr";
    }

    //used

    @RequestMapping(value = "push/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchConfig(String gridPager) {
        LOGGER.info("searchBss invoked... gridPager:{}", gridPager);
        Pager pager = pushService.searchPush(gridPager);
        String result = JsonUtils.toJson(pager);
        LOGGER.info("searchBss result = {}", result);
        return result;
    }

    @RequestMapping(value = "bss/{bssId}/pushes", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listPushes(@PathVariable("bssId") Long bssId) {
        LOGGER.info("listPushes method invoked... bssId={}", bssId);
        ServerResponse response = new ServerResponse();
        List<PushVo> pushes = pushService.getPushes(bssId);
        if (pushes != null) {
            response.setState(BusinessStatus.SUCCESS);
            response.setData(pushes);
        } else {
            response.setState(BusinessStatus.NO_ANY_ELEMENT);
        }
        return response;
    }

    //used
    @RequestMapping(value = "push/{pushId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getPush(@PathVariable("pushId") Long pushId) {
        LOGGER.info("getPush method invoked.. pushId={}", pushId);
        ServerResponse response = new ServerResponse();
        PushDetailVo push = pushService.getPush(pushId);
        if (push != null) {
            response.setState(BusinessStatus.SUCCESS);
            response.setData(push);
        } else {
            response.setState(BusinessStatus.NO_THIS_ELEMENT);
        }
        return response;
    }

    //used
    @RequestMapping(value = "/bss/{bssid}/push", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse createPush(@PathVariable("bssid") long bssId, @RequestBody PushCommand command,
                                     HttpServletRequest request) {
        LOGGER.info("createPush method invoked... command={}", command);
        return pushService.createPush(bssId, command, request);
    }

    //used
    @RequestMapping(value = "push/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deletePush(@PathVariable("id") long id) {
        LOGGER.info("deletePush method invoked... id={}", id);
        return pushService.deletePush(id);
    }

    //used
    @RequestMapping(value = "push/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updatePush(@PathVariable("id") long id, @RequestBody PushCommand command) {
        LOGGER.info("deletePush method invoked... id={}, command={}", id, command);
        return pushService.updatePush(id, command);
    }
}