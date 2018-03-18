package com.github.bh.aconf.domain.vo;

import java.util.Date;

/**
 * @author xiaobenhai
 * Date: 2017/3/10
 * Time: 17:27
 * 资源vo
 */
public class ResourceVo {
    private Long id;
    private String url;
    private String description;
    private Date uploadTime;
    private Long version;
    private CreatorVo creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


    public CreatorVo getCreator() {
        return creator;
    }

    public void setCreator(CreatorVo creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "ResourceVo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", uploadTime=" + uploadTime +
                ", version=" + version +
                ", creator=" + creator +
                '}';
    }
}
