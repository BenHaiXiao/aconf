package com.github.bh.aconf.web;

import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.Page;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.ConfigCommand;
import com.github.bh.aconf.domain.command.ConfigCommandV2;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.ConfigVo;
import com.github.bh.aconf.service.ConfigService;
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
 * Date: 2016/11/3
 * Time: 15:47
 */
@Controller
@RequestMapping("/")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    //used

    @RequestMapping(value = "config-mgr", method = RequestMethod.GET)
    public String configMgrView(@RequestParam("bssid") long bssid, Model model, HttpServletRequest request) {
        String token = request.getParameter("token");
        String appid = request.getParameter("appid");
        if (StringUtils.isNotBlank(token)) {
            model.addAttribute("token", token);
        }
        if (StringUtils.isNotBlank(appid)) {
            model.addAttribute("appid", appid);
        }
        model.addAttribute("bssid", bssid);
        return "config/config-mgr";
    }

    //used

    @RequestMapping(value = "config/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchConfig(String gridPager) {
        LOGGER.info("searchBss invoked... gridPager:{}", gridPager);
        Pager pager = configService.searchConfig(gridPager);
        String result = JsonUtils.toJson(pager);
        LOGGER.info("searchBss result = {}", result);
        return result;
    }

    //used

    @RequestMapping("config/{configId}/view")
    public String getDetailConfigPage(@PathVariable("configId") long configId, Model model, HttpServletRequest request) {
        LOGGER.info("getDetailConfigPage invoked... configId={}", configId);
        model.addAttribute("configId", configId);
        String token = request.getParameter("token");
        String appid = request.getParameter("appid");
        String bssid = request.getParameter("bssid");
        if (StringUtils.isNotBlank(token)) {
            model.addAttribute("token", token);
        }
        if (StringUtils.isNotBlank(appid)) {
            model.addAttribute("appid", appid);
        }
        if (StringUtils.isNotBlank(bssid)) {
            model.addAttribute("bssid", bssid);
        }
        return "config/config-detail";
    }

    //used

    @RequestMapping("config/{configId}/view-data")
    @ResponseBody
    public ServerResponse getDetailConfigData(@PathVariable("configId") long configId) {
        LOGGER.info("getDetailConfigData invoked... configId={}", configId);
        return configService.getConfigNode(configId);
    }

    //used

    @RequestMapping("config/{configId}")
    @ResponseBody
    public ServerResponse getConfigById(@PathVariable("configId") Long id) {
        return configService.getConfig(id);
    }

    @RequestMapping(value = "configs", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@RequestParam("bssId") long bssId,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int offset,
                               @RequestParam(value = "offset", required = false, defaultValue = "100") int limit,
                               @RequestParam(value = "order", required = false, defaultValue = "valid desc,version desc") String order) {
        ServerResponse response = new ServerResponse();
        List<ConfigVo> list = configService.listConfigs(bssId, limit, offset, order);
        if (list != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setMessage(BusinessStatus.SUCCESS.msg());
            Page<ConfigVo> page = new Page<>();
            page.setList(list);
            page.setTotalSize(configService.countAll(bssId));
            response.setData(page);
        } else {
            response.setCode(BusinessStatus.NO_THIS_ELEMENT.code());
            response.setMessage(BusinessStatus.NO_THIS_ELEMENT.msg());
        }
        return response;
    }

    @RequestMapping(value = "configs/v2", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listV2(@RequestParam("bssId") long bssId,
                                 @RequestParam(value = "start", required = false, defaultValue = "0") int offset,
                                 @RequestParam(value = "offset", required = false, defaultValue = "100") int limit,
                                 @RequestParam(value = "order", required = false, defaultValue = "valid desc,version desc") String order) {
        return configService.listConfigsV2(bssId, limit, offset, order);
    }

    //used
    @RequestMapping(value = "/bss/{bssId}/config/v2", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse createConfigV2(@PathVariable("bssId") long bssId,
                                         @RequestBody ConfigCommandV2 command,
                                         HttpServletRequest request) {
        LOGGER.info("create config invoked.. bssId={}, command={}", bssId, command);
        return configService.createConfigV2(bssId, command, request);
    }

    //used
    @RequestMapping(value = "/bss/{bssId}/config/{configId}/v2", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updateConfigV2(@PathVariable("bssId") long bssId,
                                         @PathVariable("configId") long configId,
                                         @RequestBody ConfigCommandV2 command,
                                         HttpServletRequest request) {
        LOGGER.info("create config invoked.. bssId={}, configId={}, command={}", bssId, configId, command);
        return configService.updateConfigV2(bssId, configId, command, request);
    }

    //used
    @RequestMapping(value = "/bss/{bssId}/config/{configId}/v2", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteConfigV2(@PathVariable("bssId") long bssId,
                                         @PathVariable("configId") long configId, HttpServletRequest request) {
        LOGGER.info("delete config invoked.. bssId={}, configId={}");
        return configService.deleteConfig(configId, request);
    }

    @RequestMapping(value = "config/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse delete(@PathVariable("id") long id, HttpServletRequest request) {
        return configService.deleteConfig(id, request);
    }

    @RequestMapping(value = "bss/{bssId}/key/{keyName}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse update(@PathVariable("bssId") long bssId, @PathVariable("keyName") String key,
                                 @RequestBody ConfigCommand command) {
        ServerResponse response = new ServerResponse();
        ConfigVo vo = configService.createOrUpdateConfig(command);
        if (vo != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setMessage(BusinessStatus.SUCCESS.msg());
            response.setData(vo);
        } else {
            response.setCode(BusinessStatus.UPDATE_ELEMENT_ERROR.code());
            response.setMessage(BusinessStatus.UPDATE_ELEMENT_ERROR.msg());
        }
        return response;
    }

    @RequestMapping(value = "config", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse create(ConfigCommand command) {
        ServerResponse response = new ServerResponse();
        ConfigVo vo = configService.createOrUpdateConfig(command);
        if (vo != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setMessage(BusinessStatus.SUCCESS.msg());
            response.setData(vo);
        } else {
            response.setCode(BusinessStatus.CREATE_NEW_ELEMENT_ERROR.code());
            response.setMessage(BusinessStatus.CREATE_NEW_ELEMENT_ERROR.msg());
        }
        return response;
    }

    @RequestMapping(value = "keys", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse batchCreateOrUpdate(@RequestBody List<ConfigCommand> command) {
        ServerResponse response = new ServerResponse();
        configService.batchMerge(command);
        response.setCode(BusinessStatus.SUCCESS.code());
        response.setMessage(BusinessStatus.SUCCESS.msg());
        return response;
    }
}
