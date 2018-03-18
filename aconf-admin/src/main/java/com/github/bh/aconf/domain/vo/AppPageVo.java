package com.github.bh.aconf.domain.vo;

/**
 * @author xiaobenhai
 * Date: 2017/1/13
 * Time: 16:42
 * App列表展示页Vo
 */
public class AppPageVo {
    private long id;
    private String name;
    private String description;
    private int bssNum;
    private String updateTime;
    private String creator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getBssNum() {
        return bssNum;
    }

    public void setBssNum(int bssNum) {
        this.bssNum = bssNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "AppPageVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bssNum=" + bssNum +
                ", updateTime='" + updateTime + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
