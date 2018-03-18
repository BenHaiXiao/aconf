package com.github.bh.aconf.utils;

import com.yy.atom.message.yy.YYGroupSender;
import com.yy.atom.message.yy.YYMessageArgs;

import java.text.SimpleDateFormat;

/**
 * @author xiaobenhai
 * Date: 2017/2/9
 * Time: 16:52
 * 发群消息提醒
 */
public class MsgUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static YYGroupSender sender = new YYGroupSender();

    public static void sendYYGroupMessage(String message) {
        sender.send("客户端统一配置中心(测试环境)", message, 98048266, YYMessageArgs.normal().base64());
    }
}
