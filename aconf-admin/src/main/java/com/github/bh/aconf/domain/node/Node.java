package com.github.bh.aconf.domain.node;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/3/2
 * Time: 10:51
 * 详情图节点
 */
public class Node {
    private Long id;
    private String name;
    private String type;
    private List<Node> children;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", children=" + children +
                '}';
    }
}
