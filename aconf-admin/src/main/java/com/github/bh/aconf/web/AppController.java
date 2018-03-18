package com.github.bh.aconf.web;

import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.constants.BusinessStatus;
import com.github.bh.aconf.domain.Page;
import com.github.bh.aconf.domain.command.AppCommand;
import com.github.bh.aconf.domain.vo.AppPageVo;
import com.github.bh.aconf.domain.vo.AppVo;
import com.github.bh.aconf.service.AppService;
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
 * Date: 2016/11/9
 * Time: 19:56
 */
@Controller
@RequestMapping("/")
public class AppController {
    private static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }


    @RequestMapping(value = {"app-mgr"}, method = RequestMethod.GET)
    public String appMgrView(Model model, HttpServletRequest request) {
        List<AppPageVo> apps = appService.getAppPageVos();
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            model.addAttribute("token", token);
        }
        model.addAttribute("apps", apps);
        return "app/app-mgr";
    }


    @RequestMapping(value = "app", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getApp(@RequestParam("id") Long appid) {
        LOG.info("getApp invoked... , appid={}", appid);
        ServerResponse response = new ServerResponse();
        AppVo app = appService.getApp(appid);
        if (app != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setData(app);
        } else {
            response.setCode(BusinessStatus.NO_THIS_ELEMENT.code());
            response.setMessage(BusinessStatus.NO_THIS_ELEMENT.msg());
        }
        return response;
    }

    @RequestMapping(value = "app", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse create(AppCommand command) {
        LOG.info("create invoked... AppCommand={}", command);
        ServerResponse response = new ServerResponse();
        AppVo appVo;
        if (command.getId() != null) {
            appVo = appService.update(command);
        } else {
            appVo = appService.create(command);
        }
        if (appVo != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setData(appVo);
        } else {
            response.setCode(BusinessStatus.CREATE_NEW_ELEMENT_ERROR.code());
            response.setMessage(BusinessStatus.CREATE_NEW_ELEMENT_ERROR.msg());
        }
        return response;
    }

    @RequestMapping(value = "apps", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "start", required = false, defaultValue = "0") int offset,
                               @RequestParam(value = "offset", required = false, defaultValue = "0") int limit,
                               @RequestParam(value = "order", required = false, defaultValue = "valid desc,id asc") String order) {
        LOG.info("list method invoked... limit={},offset={},order={}", limit, offset, order);
        List<AppVo> list = appService.list(limit, offset, order);
        ServerResponse response = new ServerResponse();
        if (list != null) {
            response.setState(BusinessStatus.SUCCESS);
            Page<AppVo> page = new Page<>();
            page.setList(list);
            page.setTotalSize(appService.countAll());
            response.setData(page);
        } else {
            response.setState(BusinessStatus.NO_ANY_ELEMENT);
        }
        return response;
    }

    @RequestMapping(value = "app/{appid}", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse update(@PathVariable("appid") long appid, @RequestBody AppCommand command) {
        LOG.info("update method invoked... appid={}, command={}", appid, command);
        ServerResponse response = new ServerResponse();
        if (command.getId() == null) {
            command.setId(appid);
        }
        AppVo vo = appService.update(command);
        if (vo != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setData(vo);
        } else {
            response.setCode(BusinessStatus.CREATE_NEW_ELEMENT_ERROR.code());
            response.setMessage(BusinessStatus.CREATE_NEW_ELEMENT_ERROR.msg());
        }
        return response;
    }

    @RequestMapping(value = "app/{appid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse delete(@PathVariable("appid") long appid) {
        LOG.info("delete method invoked... appid={}", appid);
        ServerResponse response = new ServerResponse();
        AppVo vo = appService.delete(appid);
        if (vo != null) {
            response.setCode(BusinessStatus.SUCCESS.code());
            response.setData(vo);
        } else {
            response.setCode(BusinessStatus.DELETE_ELEMENT_ERROR.code());
            response.setMessage(BusinessStatus.DELETE_ELEMENT_ERROR.msg());
        }
        return response;
    }
}
