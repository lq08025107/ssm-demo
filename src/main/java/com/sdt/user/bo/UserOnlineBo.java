package com.sdt.user.bo;

import com.sdt.common.model.User;

import java.io.Serializable;
import java.util.Date;

public class UserOnlineBo extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId;

    private String host;

    private Date startTime;

    private Date lastAccess;

    private long timeout;

    private boolean sessionStatus = Boolean.TRUE;

    public UserOnlineBo(){}

    public UserOnlineBo(User user){
        super(user);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
