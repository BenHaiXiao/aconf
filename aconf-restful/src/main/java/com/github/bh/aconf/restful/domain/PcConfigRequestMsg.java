package com.github.bh.aconf.restful.domain;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/8
 * Time: 16:56
 */
public class PcConfigRequestMsg {
    /**
     * 业务代号，可在管理后台配置，全局唯一。例如："pc-video"
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
     * 客户端类型：pc, ios, android, ipad, web, etc.
     */
    private String client;
    /**
     * 操作系统版本，例如：window 7
     */
    private String osVersion;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PcConfigRequestMsg{" +
                "bssCode='" + bssCode + '\'' +
                ", bssVersion=" + bssVersion +
                ", bssMode='" + bssMode + '\'' +
                ", version='" + version + '\'' +
                ", client='" + client + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", extendInfo=" + extendInfo +
                '}';
    }
}
