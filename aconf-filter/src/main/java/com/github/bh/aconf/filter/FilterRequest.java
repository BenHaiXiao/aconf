package com.github.bh.aconf.filter;

import java.io.Serializable;
import java.util.Map;

/**
 * 过滤器请求参数。
 *
 * 即调用者实际状况，匹配时需要的信息，用于与过滤器的配置规划进行匹配。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:59
 */
public class FilterRequest implements Serializable {

    private static final long serialVersionUID = -2418138515910584887L;

    /**
     * 用户id
     */
    private long uid;

    /**
     * 频道号
     */
    private long sid;

    /**
     * 子频道号
     */
    private long ssid;

    /**
     * 客户端版本
     */
    private String version;

    /**
     * 操作系统：ios, android, ipad, pc
     */
    private String os;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 渠道
     */
    private String market;
    /**
     * 设备型号
     */
    private String model;
    
    /**
     * 设备Id
     */
    private String deviceId;

    /**
     * 拓展字段
     */
    private Map<String,String> extensionMap;
    
    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getSsid() {
        return ssid;
    }

    public void setSsid(long ssid) {
        this.ssid = ssid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Map<String, String> getExtensionMap() {
        return extensionMap;
    }

    public void setExtensionMap(Map<String, String> extensionMap) {
        this.extensionMap = extensionMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterRequest that = (FilterRequest) o;

        if (uid != that.uid) return false;
        if (sid != that.sid) return false;
        if (ssid != that.ssid) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null) return false;
        if (market != null ? !market.equals(that.market) : that.market != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        return extensionMap != null ? extensionMap.equals(that.extensionMap) : that.extensionMap == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (uid ^ (uid >>> 32));
        result = 31 * result + (int) (sid ^ (sid >>> 32));
        result = 31 * result + (int) (ssid ^ (ssid >>> 32));
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (market != null ? market.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (extensionMap != null ? extensionMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilterRequest{" +
                "uid=" + uid +
                ", sid=" + sid +
                ", ssid=" + ssid +
                ", version='" + version + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", market='" + market + '\'' +
                ", model='" + model + '\'' +
                ", extensionMap=" + extensionMap +
                '}';
    }

}
