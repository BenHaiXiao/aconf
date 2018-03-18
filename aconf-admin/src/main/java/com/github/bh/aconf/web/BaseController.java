package com.github.bh.aconf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 20:15
 */
@Controller
public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(HttpServletResponse response) throws IOException {
        LOGGER.info("========index invoke========");
        return "app/app-mgr";
    }
}
