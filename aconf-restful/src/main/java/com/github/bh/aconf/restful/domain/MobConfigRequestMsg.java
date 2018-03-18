package com.github.bh.aconf.restful.domain;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/8
 * Time: 17:23
 */
public class MobConfigRequestMsg {
    /**
     * 业务代号，可在管理后台配置，全局唯一。例如："mob-video"
     */
    private String bssCode;
    /**
     * 当前客户端业务版本号，用于增量下发，默认值设置为0
     */
    private Long bssVersion;
    /**
     * 数据获取模式(可选):
     * all--全量下发。默认
     * newest--增量下发
     */
    private String bssMode;
    /**
     * 客户端版本号，格式：x.x.x
     */
    private String version;
    /**
     * 客户端类型：pc, ios, android, iPad, web, etc.
     */
    private String client;
    /**
     * 操作系统版本，例如：android 6.0.1, iOS 10
     */
    private String osVersion;
    /**
     * 渠道号
     */
    private String market;
    /**
     * 网络类型
     */
    private String net;
    /**
     * 网络提供商
     */
    private String isp;
    /**
     * 手机型号
     */
    private String model;
    /**
     * 扩展字段
     */
    public Map<String, String> extendInfo;

    public String getBssCode() {
        return bssCode;
    }

    public void setBssCode(String bssCode) {
        this.bssCode = bssCode;
    }

    public Long getBssVersion() {
        return bssVersion;
    }

    public void setBssVersion(Long bssVersion) {
        this.bssVersion = bssVersion;
    }

    public String getBssMode() {
        return bssMode;
    }

    public void setBssMode(String bssMode) {
        this.bssMode = bssMode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Map<String, String> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String, String> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "MobConfigRequestMsg{" +
                "bssCode='" + bssCode + '\'' +
                ", bssVersion=" + bssVersion +
                ", bssMode='" + bssMode + '\'' +
                ", version='" + version + '\'' +
                ", client='" + client + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", market='" + market + '\'' +
                ", net='" + net + '\'' +
                ", isp='" + isp + '\'' +
                ", model='" + model + '\'' +
                ", extendInfo=" + extendInfo +
                '}';
    }
}
