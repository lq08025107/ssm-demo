package com.sdt.core.shiro;

import com.sdt.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

public class CustomShiroSessionDAO extends AbstractSessionDAO {
    private ShiroSessionRepository shiroSessionRepository;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        getShiroSessionRepository().saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable serializable) {
        return getShiroSessionRepository().getSession(serializable);
    }

    public void update(Session session) throws UnknownSessionException {
        getShiroSessionRepository().saveSession(session);
    }

    public void delete(Session session) {
        getShiroSessionRepository().deleteSession(session.getId());
    }

    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSession();
    }
}