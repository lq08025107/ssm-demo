package com.sdt.core.shiro.session;

import java.io.Serializable;

public class SessionStatus implements Serializable {
    private final static long serialVersionUID = 1L;

    private Boolean isOnlineStatus(){
        return onlineStatus;
    }

    public Boolean getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    private Boolean onlineStatus = Boolean.TRUE;


}
