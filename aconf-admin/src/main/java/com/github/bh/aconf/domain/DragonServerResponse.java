package com.github.bh.aconf.domain;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/2/22
 * Time: 17:03
 */
public class DragonServerResponse {
    private int code;
    private boolean success;
    private List<DragonSuggestStaffDto> object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DragonSuggestStaffDto> getObject() {
        return object;
    }

    public void setObject(List<DragonSuggestStaffDto> object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "DragonServerResponse{" +
                "code=" + code +
                ", success=" + success +
                ", object=" + object +
                '}';
    }
}
