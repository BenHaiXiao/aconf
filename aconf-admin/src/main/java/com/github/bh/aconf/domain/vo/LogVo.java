package com.github.bh.aconf.domain.vo;

import java.util.Date;

/**
 * @author xiaobenhai
 * Date: 2017/3/1
 * Time: 15:37
 */
public class LogVo {
    private String face;
    private String params;
    private CreatorVo creator;
    private Date createTime;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public CreatorVo getCreator() {
        return creator;
    }

    public void setCreator(CreatorVo creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogVo{" +
                "face='" + face + '\'' +
                ", params='" + params + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                '}';
    }
}
