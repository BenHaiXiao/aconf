package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2017/1/18
 * Time: 15:17
 */
public class FilterCommandV2 {
    private Long id;
    private String basis;
    private String operator;
    private String boundary;
    private Byte type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FilterCommandV2{" +
                "id=" + id +
                ", basis='" + basis + '\'' +
                ", operator='" + operator + '\'' +
                ", boundary='" + boundary + '\'' +
                ", type=" + type +
                '}';
    }
}
