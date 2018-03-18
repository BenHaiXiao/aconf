package com.github.bh.aconf.domain.command;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaobenhai
 * Date: 2017/3/10
 * Time: 18:22
 */
public class ResourceCommand {
    private String description;
    private MultipartFile file;
    private Long configId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    @Override
    public String toString() {
        return "ResourceCommand{" +
                "description='" + description + '\'' +
                ", file=" + (file == null ? "null" : file.getName()) +
                ", configId=" + configId +
                '}';
    }
}
