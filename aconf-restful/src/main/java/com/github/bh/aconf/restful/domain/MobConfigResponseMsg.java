package com.github.bh.aconf.restful.domain;

import com.google.common.collect.Maps;
import com.github.bh.aconf.service.domain.ResponseCodes;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/8
 * Time: 17:23
 */
public class MobConfigResponseMsg {
    /**
     * 状态码
     * 0--成功
     * 1--没通过过滤器规则
     * 2--还没生效
     * 99--失败
     */
    private Integer result = ResponseCodes.SUCCESS.getCode();

    /**
     * 业务代号，用来标识业务，全局唯一
     */
    private String bssCode;
    /**
     * 业务最新版本号
     */
    private Long bssVersion;

    /**
     * 下发模式
     * all--全量下发
     * newest--增量下发
     */
    private String bssMode;

    /**
     * 增量时为生效的配置项，全量时为所有的配置项
     */
    private Map<String, String> configs = Maps.newHashMap();
    /**
     * 被删除的配置项(仅用于增量)
     */
    private Map<String, String> deleteds = Maps.newHashMap();

    /**
     * 扩展字段
     */
    private Map<String, String> extendInfo = Maps.newHashMap();

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

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

    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }

    public Map<String, String> getDeleteds() {
        return deleteds;
    }

    public void setDeleteds(Map<String, String> deleteds) {
        this.deleteds = deleteds;
    }

    public Map<String, String> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String, String> extendInfo) {
        this.extendInfo = extendInfo;
    }

    @Override
    public String toString() {
        return "MobConfigResponseMsg{" +
                "result=" + result +
                ", bssCode='" + bssCode + '\'' +
                ", bssVersion=" + bssVersion +
                ", bssMode='" + bssMode + '\'' +
                ", configs=" + configs +
                ", deleteds=" + deleteds +
                ", extendInfo=" + extendInfo +
                '}';
    }
}
