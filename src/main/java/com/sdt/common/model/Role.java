package com.sdt.common.model;

import java.io.Serializable;

public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long type;

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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
