package com.github.bh.aconf.web;

import com.github.bh.aconf.domain.ServerResponse;
import com.github.bh.aconf.domain.command.AppRegisterCommand;
import com.github.bh.aconf.service.AppRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaobenhai
 * Date: 2016/12/28
 * Time: 9:41
 */
@Controller
@RequestMapping("app")
public class RegisterController {

    @Autowired
    private AppRegisterService registerService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(@RequestBody AppRegisterCommand command) {
        return registerService.register(command);
    }
}
