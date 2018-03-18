package com.github.bh.aconf.persist.base.model;

import java.util.Date;

public class ConfigHistoryMeta {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.serial_id
     *
     * @mbggenerated
     */
    private Long serialId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.value
     *
     * @mbggenerated
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.bss_id
     *
     * @mbggenerated
     */
    private Long bssId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.version
     *
     * @mbggenerated
     */
    private Long version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.valid
     *
     * @mbggenerated
     */
    private Byte valid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.send_default
     *
     * @mbggenerated
     */
    private Byte sendDefault;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column config_history.value_type
     *
     * @mbggenerated
     */
    private Byte valueType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.serial_id
     *
     * @return the value of config_history.serial_id
     *
     * @mbggenerated
     */
    public Long getSerialId() {
        return serialId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.serial_id
     *
     * @param serialId the value for config_history.serial_id
     *
     * @mbggenerated
     */
    public void setSerialId(Long serialId) {
        this.serialId = serialId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.id
     *
     * @return the value of config_history.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.id
     *
     * @param id the value for config_history.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.name
     *
     * @return the value of config_history.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.name
     *
     * @param name the value for config_history.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.value
     *
     * @return the value of config_history.value
     *
     * @mbggenerated
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.value
     *
     * @param value the value for config_history.value
     *
     * @mbggenerated
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.description
     *
     * @return the value of config_history.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.description
     *
     * @param description the value for config_history.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.bss_id
     *
     * @return the value of config_history.bss_id
     *
     * @mbggenerated
     */
    public Long getBssId() {
        return bssId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.bss_id
     *
     * @param bssId the value for config_history.bss_id
     *
     * @mbggenerated
     */
    public void setBssId(Long bssId) {
        this.bssId = bssId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.creator
     *
     * @return the value of config_history.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.creator
     *
     * @param creator the value for config_history.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.create_time
     *
     * @return the value of config_history.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.create_time
     *
     * @param createTime the value for config_history.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.version
     *
     * @return the value of config_history.version
     *
     * @mbggenerated
     */
    public Long getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.version
     *
     * @param version the value for config_history.version
     *
     * @mbggenerated
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.valid
     *
     * @return the value of config_history.valid
     *
     * @mbggenerated
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.valid
     *
     * @param valid the value for config_history.valid
     *
     * @mbggenerated
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.send_default
     *
     * @return the value of config_history.send_default
     *
     * @mbggenerated
     */
    public Byte getSendDefault() {
        return sendDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.send_default
     *
     * @param sendDefault the value for config_history.send_default
     *
     * @mbggenerated
     */
    public void setSendDefault(Byte sendDefault) {
        this.sendDefault = sendDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column config_history.value_type
     *
     * @return the value of config_history.value_type
     *
     * @mbggenerated
     */
    public Byte getValueType() {
        return valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column config_history.value_type
     *
     * @param valueType the value for config_history.value_type
     *
     * @mbggenerated
     */
    public void setValueType(Byte valueType) {
        this.valueType = valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_history
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ConfigHistoryMeta other = (ConfigHistoryMeta) that;
        return (this.getSerialId() == null ? other.getSerialId() == null : this.getSerialId().equals(other.getSerialId()))
                && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getBssId() == null ? other.getBssId() == null : this.getBssId().equals(other.getBssId()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getValid() == null ? other.getValid() == null : this.getValid().equals(other.getValid()))
                && (this.getSendDefault() == null ? other.getSendDefault() == null : this.getSendDefault().equals(other.getSendDefault()))
                && (this.getValueType() == null ? other.getValueType() == null : this.getValueType().equals(other.getValueType()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_history
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSerialId() == null) ? 0 : getSerialId().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getBssId() == null) ? 0 : getBssId().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getValid() == null) ? 0 : getValid().hashCode());
        result = prime * result + ((getSendDefault() == null) ? 0 : getSendDefault().hashCode());
        result = prime * result + ((getValueType() == null) ? 0 : getValueType().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table config_history
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialId=").append(serialId);
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", value=").append(value);
        sb.append(", description=").append(description);
        sb.append(", bssId=").append(bssId);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", version=").append(version);
        sb.append(", valid=").append(valid);
        sb.append(", sendDefault=").append(sendDefault);
        sb.append(", valueType=").append(valueType);
        sb.append("]");
        return sb.toString();
    }
}