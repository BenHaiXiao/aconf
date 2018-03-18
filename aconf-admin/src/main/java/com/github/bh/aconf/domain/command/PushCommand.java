package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/11/17
 * Time: 15:15
 */
public class PushCommand {
    private String title;
    private Long effectiveTime;
    private String sids;
    private Byte sys;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getSids() {
        return sids;
    }

    public void setSids(String sids) {
        this.sids = sids;
    }

    public Byte getSys() {
        return sys;
    }

    public void setSys(Byte sys) {
        this.sys = sys;
    }

    @Override
    public String toString() {
        return "PushCommand{" +
                "title='" + title + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", sids='" + sids + '\'' +
                ", sys=" + sys +
                '}';
    }
}
