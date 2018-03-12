package com.sdt.common.model;

import java.io.Serializable;

public class RolePermission implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long rid;

    private Long pid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
