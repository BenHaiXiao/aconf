package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 15:53
 */
public class ConfigCommand {
    private Long id;
    private String key;
    private String description;
    private String value;
    private String creator;
    private Byte valid;
    private Long bssId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public Long getBssId() {
        return bssId;
    }

    public void setBssId(Long bssId) {
        this.bssId = bssId;
    }

    @Override
    public String toString() {
        return "ConfigCommand{" +
                "key='" + key + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                ", creator='" + creator + '\'' +
                ", valid=" + valid +
                ", bssId=" + bssId +
                '}';
    }
}
