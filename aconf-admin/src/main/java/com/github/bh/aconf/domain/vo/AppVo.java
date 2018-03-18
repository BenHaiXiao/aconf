package com.github.bh.aconf.domain.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 19:51
 */
public class AppVo {
    private Long id;
    private String name;
    private String description;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private Byte valid;
    private List<BssVo> bss;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
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

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public List<BssVo> getBss() {
        return bss;
    }

    public void setBss(List<BssVo> bss) {
        this.bss = bss;
    }

    @Override
    public String toString() {
        return "AppVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", valid=" + valid +
                ", bss=" + bss +
                '}';
    }
}
