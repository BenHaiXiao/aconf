package com.github.bh.aconf.restful.utils;

import com.github.bh.aconf.restful.domain.MobConfigRequestMsg;
import com.github.bh.aconf.restful.domain.PcConfigRequestMsg;
import com.github.bh.aconf.filter.FilterRequest;

/**
 * @author xiaobenhai
 */
public final class RequestTransformer {

    private RequestTransformer() {
        // no-op
    }

    public static FilterRequest transform(long uid, long sid, long ssid, MobConfigRequestMsg requestMsg) {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setUid(uid);
        filterRequest.setSid(sid);
        filterRequest.setSsid(ssid);
        if (requestMsg == null) {
            return filterRequest;
        }
        if (requestMsg.getVersion() != null) {
            filterRequest.setVersion(requestMsg.getVersion());
        }
        if (requestMsg.getClient() != null) {
            filterRequest.setOs(requestMsg.getClient());
        }
        if (requestMsg.getOsVersion() != null) {
            filterRequest.setOsVersion(requestMsg.getOsVersion());
        }
        if (requestMsg.getMarket() != null) {
            filterRequest.setMarket(requestMsg.getMarket());
        }
        if (requestMsg.getExtendInfo() != null) {
            filterRequest.setExtensionMap(requestMsg.getExtendInfo());
        }
        if (requestMsg.getModel() != null) {
            filterRequest.setModel(requestMsg.getModel());
        }
        return filterRequest;
    }

    public static FilterRequest transform(long uid, long sid, long ssid, PcConfigRequestMsg requestMsg) {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setUid(uid);
        filterRequest.setSid(sid);
        filterRequest.setSsid(ssid);
        if (requestMsg == null) {
            return filterRequest;
        }
        if (requestMsg.getVersion() != null) {
            filterRequest.setVersion(requestMsg.getVersion());
        }
        if (requestMsg.getClient() != null) {
            filterRequest.setOs(requestMsg.getClient());
        }
        if (requestMsg.getOsVersion() != null) {
            filterRequest.setOsVersion(requestMsg.getOsVersion());
        }
        if (requestMsg.getExtendInfo() != null) {
            filterRequest.setExtensionMap(requestMsg.getExtendInfo());
        }
        return filterRequest;
    }
}
