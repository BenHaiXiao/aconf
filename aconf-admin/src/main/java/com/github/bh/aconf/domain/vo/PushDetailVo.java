package com.github.bh.aconf.domain.vo;

import java.util.Date;

/**
 * @author xiaobenhai
 * Date: 2016/11/17
 * Time: 14:48
 * Push详细信息Vo
 */
public class PushDetailVo {
    private Long id;
    private Long bssId;
    private String title;
    private String description;
    private CreatorVo creator;
    private Date effectiveTime;
    private String sids;
    private Date createTime;
    private Date updateTime;
    private Integer state;
    private Byte sys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBssId() {
        return bssId;
    }

    public void setBssId(Long bssId) {
        this.bssId = bssId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreatorVo getCreator() {
        return creator;
    }

    public void setCreator(CreatorVo creator) {
        this.creator = creator;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getSids() {
        return sids;
    }

    public void setSids(String sids) {
        this.sids = sids;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Byte getSys() {
        return sys;
    }

    public void setSys(Byte sys) {
        this.sys = sys;
    }

    @Override
    public String toString() {
        return "PushDetailVo{" +
                "id=" + id +
                ", bssId=" + bssId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", sids='" + sids + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", sys=" + sys +
                '}';
    }
}
