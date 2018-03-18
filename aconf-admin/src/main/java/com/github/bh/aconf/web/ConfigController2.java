package com.github.bh.aconf.web;

import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.vo.ConfigVo;
import com.github.bh.aconf.domain.command.ConfigCommand;
import com.github.bh.aconf.service.AppRegisterService;
import com.github.bh.aconf.service.BssService;
import com.github.bh.aconf.service.ConfigService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/12/27
 * Time: 15:22
 */
@Controller
@RequestMapping("/")
public class ConfigController2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController2.class);
    @Autowired
    private ConfigService configService;
    @Autowired
    private BssService bssService;
    @Autowired
    private AppRegisterService registerService;

    @RequestMapping(value = "/bss/{bssCode}/config", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse createConfig(@PathVariable("bssCode") String code,
                                       @RequestBody ConfigCommand command,
                                       HttpServletRequest request) {
        LOGGER.info("createConfig invoked... code={},command={}", code, command);
        ServerResponse response = new ServerResponse();
        String appid = request.getHeader("appid");
        String auth = request.getHeader("auth");
        if (!NumberUtils.isNumber(appid)) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        long appidNum = NumberUtils.toLong(appid, 0);
        boolean passed = registerService.verify(appidNum, auth);
        if (!passed) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        if (command != null) {
            Long id = bssService.getBssIdByCode(code);
            command.setBssId(id);
        }
        return configService.createConfig(command);
    }

    //used, for cba
    @RequestMapping(value = "/bss/{bssCode}/config/{configId}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updateConfig(@PathVariable("bssCode") String code,
                                       @PathVariable("configId") Long configId,
                                       @RequestBody ConfigCommand command,
                                       HttpServletRequest request) {
        LOGGER.info("updateConfig invoked... code={}, configId={}, command={}", code, configId, command);
        ServerResponse response = new ServerResponse();
        String appid = request.getHeader("appid");
        String auth = request.getHeader("auth");
        if (!NumberUtils.isNumber(appid)) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        long appidNum = NumberUtils.toLong(appid, 0);
        boolean passed = registerService.verify(appidNum, auth);
        if (!passed) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        if (command != null) {
            Long bssId = bssService.getBssIdByCode(code);
            command.setBssId(bssId);
            command.setId(configId);
        }
        return configService.updateConfig(command);
    }

    @RequestMapping(value = "/bss/{bssCode}/configs", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listConfigs(@PathVariable("bssCode") String code,
                                      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                      HttpServletRequest request) {
        LOGGER.info("listConfigs invoked...");
        ServerResponse response = new ServerResponse();
        String appid = request.getHeader("appid");
        String auth = request.getHeader("auth");
        if (!NumberUtils.isNumber(appid)) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        long appidNum = NumberUtils.toLong(appid, 0);
        boolean passed = registerService.verify(appidNum, auth);
        if (!passed) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        Long bssId = bssService.getBssIdByCode(code);
        List<ConfigVo> configVos = configService.listConfigs(bssId, limit, offset, "valid desc,version desc");
        return response.state(BusinessStatus.SUCCESS).data(configVos);
    }

    @RequestMapping(value = "/bss/{bssCode}/config/{configId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getConfig(@PathVariable("bssCode") String code,
                                    @PathVariable("configId") Long id,
                                    HttpServletRequest request) {
        ServerResponse response = new ServerResponse();
        String appid = request.getHeader("appid");
        String auth = request.getHeader("auth");
        if (!NumberUtils.isNumber(appid)) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        long appidNum = NumberUtils.toLong(appid, 0);
        boolean passed = registerService.verify(appidNum, auth);
        if (!passed) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        return configService.getConfig(id);
    }

    @RequestMapping(value = "/bss/{bssCode}/config/{configId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteConfig(@PathVariable("bssCode") String code,
                                       @PathVariable("configId") Long id,
                                       HttpServletRequest request) {
        LOGGER.info("deleteConfig invoked...");
        ServerResponse response = new ServerResponse();
        String appid = request.getHeader("appid");
        String auth = request.getHeader("auth");
        if (!NumberUtils.isNumber(appid)) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        long appidNum = NumberUtils.toLong(appid, 0);
        boolean passed = registerService.verify(appidNum, auth);
        if (!passed) {
            return response.state(BusinessStatus.AUTH_FAILED);
        }
        return configService.deleteConfig(id, request);
    }

}
