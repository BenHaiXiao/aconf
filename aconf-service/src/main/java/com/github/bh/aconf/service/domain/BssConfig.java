package com.github.bh.aconf.service.domain;

import com.google.common.collect.Maps;
import com.github.bh.aconf.persist.base.model.ConfigMeta;

import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 10:17
 */
public class BssConfig {

    private ResponseCodes responseCode = ResponseCodes.SUCCESS;

    private long bssId;
    private String bssCode;
    private long bssVersion;
    private Map<String, String> configs = Maps.newHashMap();

    public long getBssId() {
        return bssId;
    }

    public void setBssId(long bssId) {
        this.bssId = bssId;
    }

    public String getBssCode() {
        return bssCode;
    }

    public void setBssCode(String bssCode) {
        this.bssCode = bssCode;
    }

    public long getBssVersion() {
        return bssVersion;
    }

    public void setBssVersion(long bssVersion) {
        this.bssVersion = bssVersion;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public void addConfig(ConfigMeta meta) {
        configs.put(meta.getName(), meta.getValue());
    }

    public ResponseCodes getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCodes responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "BssConfig{" +
               "responseCode=" + responseCode +
               ", bssId=" + bssId +
               ", bssCode='" + bssCode + '\'' +
               ", bssVersion=" + bssVersion +
               ", configs=" + configs +
               '}';
    }

}
