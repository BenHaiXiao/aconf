package com.github.bh.aconf.restful.utils;

import com.github.bh.aconf.restful.domain.PcConfigResponseMsg;
import com.github.bh.aconf.restful.domain.MobConfigResponseMsg;
import com.github.bh.aconf.service.domain.BssConfig;

/**
 * @author xiaobenhai
 * Date: 2017/3/7
 * Time: 15:47
 */
public class ResponseTransformer {
    public static MobConfigResponseMsg transformMobConfigResponseMsg(BssConfig config) {
        MobConfigResponseMsg responseMsg = new MobConfigResponseMsg();
        responseMsg.setResult(config.getResponseCode().getCode());
        responseMsg.setBssCode(config.getBssCode());
        responseMsg.setBssVersion(config.getBssVersion());
        responseMsg.setConfigs(config.getConfigs());
        return responseMsg;
    }

    public static PcConfigResponseMsg transformPcConfigResponseMsg(BssConfig config) {
        PcConfigResponseMsg responseMsg = new PcConfigResponseMsg();
        responseMsg.setResult(config.getResponseCode().getCode());
        responseMsg.setBssCode(config.getBssCode());
        responseMsg.setBssVersion(config.getBssVersion());
        responseMsg.setConfigs(config.getConfigs());
        return responseMsg;
    }
}
