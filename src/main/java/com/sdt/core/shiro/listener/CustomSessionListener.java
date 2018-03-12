package com.sdt.core.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import com.sdt.core.shiro.session.ShiroSessionRepository;

public class CustomSessionListener implements SessionListener{
    private ShiroSessionRepository shiroSessionRepository;

    public void onStart(Session session) {
        System.out.println("on start");
    }

    public void onStop(Session session) {
        System.out.println("on stop");
    }

    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
}
