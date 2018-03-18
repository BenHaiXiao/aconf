package com.github.bh.aconf.persist.base.model;

public class AppRegisterMeta {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_register.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_register.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_register.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_register.rnum
     *
     * @mbggenerated
     */
    private Integer rnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_register.valid
     *
     * @mbggenerated
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_register.id
     *
     * @return the value of app_register.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_register.id
     *
     * @param id the value for app_register.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_register.name
     *
     * @return the value of app_register.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_register.name
     *
     * @param name the value for app_register.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_register.description
     *
     * @return the value of app_register.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_register.description
     *
     * @param description the value for app_register.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_register.rnum
     *
     * @return the value of app_register.rnum
     *
     * @mbggenerated
     */
    public Integer getRnum() {
        return rnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_register.rnum
     *
     * @param rnum the value for app_register.rnum
     *
     * @mbggenerated
     */
    public void setRnum(Integer rnum) {
        this.rnum = rnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_register.valid
     *
     * @return the value of app_register.valid
     *
     * @mbggenerated
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_register.valid
     *
     * @param valid the value for app_register.valid
     *
     * @mbggenerated
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_register
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
        AppRegisterMeta other = (AppRegisterMeta) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getRnum() == null ? other.getRnum() == null : this.getRnum().equals(other.getRnum()))
            && (this.getValid() == null ? other.getValid() == null : this.getValid().equals(other.getValid()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_register
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRnum() == null) ? 0 : getRnum().hashCode());
        result = prime * result + ((getValid() == null) ? 0 : getValid().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_register
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", rnum=").append(rnum);
        sb.append(", valid=").append(valid);
        sb.append("]");
        return sb.toString();
    }
}