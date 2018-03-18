package com.github.bh.aconf.web;

import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.BssCommand;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.bh.aconf.common.utils.JsonUtils;
import com.github.bh.aconf.domain.Page;
import com.github.bh.aconf.domain.dtgrid.Pager;
import com.github.bh.aconf.domain.vo.BssVo;
import com.github.bh.aconf.persist.base.model.BssMeta;
import com.github.bh.aconf.service.AppRegisterService;
import com.github.bh.aconf.service.BssService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 11:30
 */
@Controller
@RequestMapping("/")
public class BssController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BssController.class);
    @Autowired
    private BssService bssService;
    @Autowired
    private AppRegisterService registerService;

    //used

    @RequestMapping(value = "bss-mgr", method = RequestMethod.GET)
    public String bssMgrView(@RequestParam("appid") long appid, Model model, HttpServletRequest request) {
//        model.addAttribute("apps", apps);
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            model.addAttribute("token", token);
        }
        model.addAttribute("appid", appid);
        return "bss/bss-mgr";
    }

    //used

    @RequestMapping(value = "bss/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchBss(String gridPager) {
        LOGGER.info("searchBss invoked... gridPager:{}", gridPager);
        Pager pager = bssService.searchBss(gridPager);
        String result = JsonUtils.toJson(pager);
        LOGGER.info("searchBss result = {}", result);
        return result;
    }


    @RequestMapping(value = "bss-list", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse listBss() {
        LOGGER.info("listBss invoked... ");
        List<Map<String, String>> maps = Lists.newArrayList();
        List<BssMeta> list = bssService.listAll();
        for (BssMeta meta : list) {
            Map<String, String> map = Maps.newHashMap();
            map.put("value", meta.getId()+"");
            map.put("name", meta.getName()+"("+meta.getCode()+")");
            maps.add(map);
        }
        ServerResponse response = new ServerResponse();
        return response.state(BusinessStatus.SUCCESS).data(maps);
    }
    
    @RequestMapping(value = "bss", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "appid") long appId,
                               @RequestParam(value = "start", required = false, defaultValue = "0") int offset,
                               @RequestParam(value = "offset", required = false, defaultValue = "100") int limit,
                               @RequestParam(value = "order", required = false, defaultValue = "") String order) {
        LOGGER.info("list method invoked... appId={}, limit={},offset={},order={}", appId, limit, offset, order);
        ServerResponse response = new ServerResponse();
        response.setCode(BusinessStatus.SUCCESS.code());
        Page<BssVo> page = new Page<>();
        page.setList(bssService.list(appId, offset, limit, order));
        page.setTotalSize(bssService.countAll());
        response.setData(page);
        return response;
    }

    //need to use
    @RequestMapping(value = "bss", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse create(@RequestBody BssCommand newBss, HttpServletRequest request) {
        LOGGER.info("create method invoked... BssCommand={}", newBss);
        return bssService.createBss(newBss, request);
    }

    //used

    @RequestMapping(value = "bss/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getBssDetail(@PathVariable("id") long id) {
        LOGGER.info("getBssDetail invoked... bssId={}", id);
        return bssService.getBss(id);
    }

    //used
    @RequestMapping(value = "bss/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updateBss(@PathVariable("id") long id, @RequestBody BssCommand bssCommand) {
        LOGGER.info("updateBss invoked... bssId={},command={}", id, bssCommand);
        bssCommand.setId(id);
        return bssService.updateBss(bssCommand);
    }

    //used
    //确保所有配置项均已下线，方可删除业务
    @RequestMapping(value = "bss/{bssId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteBss(@PathVariable("bssId") long bssId) {
        LOGGER.info("deleteBss invoked... bssId={}", bssId);
        return bssService.deleteBss(bssId);
    }
}
