package com.github.bh.aconf.restful.controller;

import com.github.bh.aconf.filter.FilterRequest;
import com.github.bh.aconf.restful.domain.PcConfigRequestMsg;
import com.github.bh.aconf.restful.domain.PcConfigResponseMsg;
import com.github.bh.aconf.restful.utils.RequestTransformer;
import com.github.bh.aconf.restful.utils.ResponseTransformer;
import com.github.bh.aconf.service.BssConfigFilterService;
import com.github.bh.aconf.service.domain.BssConfig;
import com.github.bh.aconf.service.domain.ResponseCodes;
import org.apache.commons.lang3.math.NumberUtils;
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
 * Date: 2017/3/8
 * Time: 16:19
 */
@Controller
@RequestMapping("configs")
public class ConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class);

    private final BssConfigFilterService bssConfigFilterService;

    @Autowired
    public ConfigController(BssConfigFilterService bssConfigFilterService) {
        this.bssConfigFilterService = bssConfigFilterService;
    }

    /**
     * 默认是使用get请求，如果需要用到post，可以使用pc/post进行请求
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public PcConfigResponseMsg getConfigsForPc(PcConfigRequestMsg msg,  HttpServletRequest request) {
    	try {
            long sid = NumberUtils.toLong(request.getHeader("sid"), 0L);
            long ssid = NumberUtils.toLong(request.getHeader("ssid"), 0L);
            long uid = NumberUtils.toLong(request.getHeader("uid"), 0L);
            LOGGER.info("getConfigsForPc invoked... sid={}, ssid={}, uid={}, msg={}", sid, ssid, uid, msg);
            FilterRequest filterRequest = RequestTransformer.transform(uid, sid, ssid, msg);
            BssConfig bssConfig = bssConfigFilterService.getBssConfigByCode(msg.getBssCode(), filterRequest);
            PcConfigResponseMsg responseMsg = ResponseTransformer.transformPcConfigResponseMsg(bssConfig);
            LOGGER.info("http pc config response, sid:{} ssid:{}, uid={}, body:{}", sid, ssid, uid, responseMsg);
            return responseMsg;
    	}catch (Exception e) {
    		PcConfigResponseMsg responsefail = new  PcConfigResponseMsg();
    		responsefail.setResult(ResponseCodes.FAIL.getCode());
    		LOGGER.warn("http pc config error", e);            
            return responsefail;
        }
    }
}
