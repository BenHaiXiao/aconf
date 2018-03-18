package com.github.bh.aconf.domain;

import com.github.bh.aconf.constants.BusinessStatus;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 17:39
 */
public class ServerResponse {
    private int code;
    private Object data;
    private String message;

    private static final ServerResponse SUCCESS = new ServerResponse(BusinessStatus.SUCCESS);

    public ServerResponse() {
        //no-op
    }

    public ServerResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerResponse(BusinessStatus status) {
        this.code = status.code();
        this.message = status.msg();
    }

    public ServerResponse(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServerResponse addMessage(String message) {
        this.message = this.message+":"+message;
        return this;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setState(BusinessStatus state) {
        this.code = state.code();
        this.message = state.msg();
    }

    public ServerResponse state(BusinessStatus state) {
        this.code = state.code();
        this.message = state.msg();
        return this;
    }

    public ServerResponse data(Object data) {
        this.data = data;
        return this;
    }

    public static ServerResponse success() {
        return SUCCESS;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
