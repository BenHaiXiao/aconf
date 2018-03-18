package com.github.bh.aconf.domain.vo;

import java.util.Date;

/**
 * @author xiaobenhai
 * Date: 2016/11/16
 * Time: 15:33
 */
public class PushVo {
    private long id;
    private String title;
    private Date effectiveTime;
    private Byte sys;
    private int state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Byte getSys() {
        return sys;
    }

    public void setSys(Byte sys) {
        this.sys = sys;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PushVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", sys=" + sys +
                ", state=" + state +
                '}';
    }
}
