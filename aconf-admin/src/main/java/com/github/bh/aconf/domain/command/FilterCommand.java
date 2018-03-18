package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/11/14
 * Time: 20:46
 */
public class FilterCommand {
    private Long id;
    private String basis;
    private String operator;
    private String boundary;
    private Long belongId;
    private Byte belongType;

    public Byte getBelongType() {
        return belongType;
    }

    public void setBelongType(Byte belongType) {
        this.belongType = belongType;
    }

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

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

    @Override
    public String toString() {
        return "FilterCommand{" +
                "id=" + id +
                ", basis='" + basis + '\'' +
                ", operator='" + operator + '\'' +
                ", boundary='" + boundary + '\'' +
                ", belongId=" + belongId +
                ", belongType=" + belongType +
                '}';
    }
}
