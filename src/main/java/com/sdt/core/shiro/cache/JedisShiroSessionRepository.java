package com.sdt.core.shiro.cache;

import com.sdt.common.utils.SerializeUtil;
import com.sdt.core.shiro.session.CustomSessionManager;
import com.sdt.core.shiro.session.SessionStatus;
import com.sdt.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Serializable;
import java.util.Collection;

public class JedisShiroSessionRepository implements ShiroSessionRepository{
    private static final Logger logger = LoggerFactory.getLogger(JedisShiroSessionRepository.class);

    public static final String REDIS_SHIRO_SESSION = "shiro-demo-session:";

    public static final String REDIS_SHIRO_ALL = "*shiro-demo-session:";

    private static final int SESSION_VAL_TIME_SPAN = 18000;

    private static final int DB_INDEX = 1;

    private JedisManager jedisManager;

    public void saveSession(Session session) {
        if(null == session || session.getId() == null){
            throw new NullPointerException("session is empty");
        }
        try{
            byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));

            if(null == session.getAttribute(CustomSessionManager.SESSION_STATUS)){
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }

            byte[] value = SerializeUtil.serialize(session);

            getJedisManager().saveValueBykey(DB_INDEX, key, value, (int)session.getTimeout() / 1000);
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

    public void deleteSession(Serializable sessionId) {
        if(null == sessionId){
            throw new NullPointerException("session is empty");
        }
        try{
            getJedisManager().deleteByKey(DB_INDEX, SerializeUtil.serialize(buildRedisSessionKey(sessionId)));
        } catch(Exception e){
            logger.error("删除session失败");
        }
    }

    public Session getSession(Serializable sessionId) {
        if(null == sessionId){
            throw new NullPointerException("session is empty");
        }
        Session session = null;
        try{
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, SerializeUtil.serialize(buildRedisSessionKey(sessionId)));
            session = SerializeUtil.deserialize(value, Session.class);
        } catch(Exception e){
            logger.error("获取session失败");
        }
        return session;
    }

    public Collection<Session> getAllSession() {
        Collection<Session> sessions = null;
        try{
            sessions = getJedisManager().getAllSession(DB_INDEX, REDIS_SHIRO_SESSION);
        } catch(Exception e){
            logger.error("获取全部session异常");
        }
        return sessions;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

    private String buildRedisSessionKey(Serializable sessionId){
        return REDIS_SHIRO_SESSION + sessionId;
    }
}
