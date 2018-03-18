package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 19:51
 */
public class AppCommand {
    //for update
    private Long id;
    private String name;
    private String description;
    private String creator;
    private Byte valid;

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

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "AppCommand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", valid=" + valid +
                '}';
    }
}
