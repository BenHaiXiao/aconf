package com.github.bh.aconf.domain.vo;

import java.util.Date;

/**
 * @author xiaobenhai
 * Date: 2016/11/14
 * Time: 16:07
 */
public class FilterVo {
    private long id;
    private String basis;
    private String operator;
    private String boundary;
    private CreatorVo creator;
    private Date createTime;
    private Date updateTime;
    private long belongId;
    private byte belongType;
    private byte valid;  //0--已删除,1--有效
    private byte type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getBelongId() {
        return belongId;
    }

    public void setBelongId(long belongId) {
        this.belongId = belongId;
    }

    public byte getBelongType() {
        return belongType;
    }

    public void setBelongType(byte belongType) {
        this.belongType = belongType;
    }

    public byte getValid() {
        return valid;
    }

    public void setValid(byte valid) {
        this.valid = valid;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FilterVo{" +
                "id=" + id +
                ", basis='" + basis + '\'' +
                ", operator='" + operator + '\'' +
                ", boundary='" + boundary + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", belongId=" + belongId +
                ", belongType=" + belongType +
                ", valid=" + valid +
                ", type=" + type +
                '}';
    }
}
