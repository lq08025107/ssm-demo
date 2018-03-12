package com.sdt.core.shiro.session;

import com.sdt.common.model.User;
import com.sdt.common.utils.StringUtils;
import com.sdt.core.shiro.CustomShiroSessionDAO;
import org.apache.shiro.session.Session;

import java.util.*;

import com.sdt.user.bo.UserOnlineBo;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(CustomSessionManager.class);

    public static final String SESSION_STATUS = "online-status";

    ShiroSessionRepository shiroSessionRepository;

    CustomShiroSessionDAO customShiroSessionDAO;

    //获取所有session
    public List<UserOnlineBo> getAllUser(){
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        List<UserOnlineBo> list = new ArrayList<UserOnlineBo>();

        for(Session session : sessions){
            UserOnlineBo bo = getSessionBo(session);
            if(null != bo){
                list.add(bo);
            }
        }
        return list;
    }
    //获取单个session
    public UserOnlineBo getSession(String sessionId){
        Session session = shiroSessionRepository.getSession(sessionId);
        UserOnlineBo bo = getSessionBo(session);
        return bo;
    }

    public UserOnlineBo getSessionBo(Session session){
        Object object = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

        if(null == object){
            return null;
        }

        if(object instanceof SimplePrincipalCollection){
            SimplePrincipalCollection spc = (SimplePrincipalCollection) object;

            object = spc.getPrimaryPrincipal();
            if(null != object && object instanceof User){
                UserOnlineBo userBo = new UserOnlineBo((User) object);

                userBo.setLastAccess(session.getLastAccessTime());

                userBo.setHost(session.getHost());

                userBo.setSessionId(session.getId().toString());

                userBo.setLastLoginTime(session.getLastAccessTime());

                userBo.setTimeout(session.getTimeout());

                userBo.setStartTime(session.getStartTimestamp());

                SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);

                boolean status = Boolean.TRUE;

                if(null != sessionStatus){
                    status = sessionStatus.getOnlineStatus();
                }
                userBo.setSessionStatus(status);

                return userBo;
            }
        }

        return null;
    }

    /**
     * 改变session状态
     * @param status（true：踢出 false：激活）
     * @param sessionIds
     * @return
     */
    public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            String[] sessionIdArray = null;
            if(sessionIds.indexOf(",") == -1){
                sessionIdArray = new String[]{sessionIds};
            } else {
                sessionIdArray = sessionIds.split(",");
            }
            for(String sessionId : sessionIdArray){
                Session session = shiroSessionRepository.getSession(sessionId);
                SessionStatus sessionStatus = new SessionStatus();
                sessionStatus.setOnlineStatus(status);
                session.setAttribute(SESSION_STATUS, sessionStatus);
                customShiroSessionDAO.update(session);
            }
            map.put("status", 200);
            map.put("sessionStatus", status?1:0);
            map.put("sessionStatusText", status?"踢出":"激活");
            map.put("sessionStatusTextTd",status?"有效":"已踢出");
        } catch(Exception e){
            logger.error("改变Session状态错误");
            map.put("status", 500);
            map.put("message","改变状态失败，有可能session不存在，请刷新重试！");
        }
        return map;
    }

    /**
     *
     * @param id
     * @param status
     */
    public void forbidUserById(Long id, Long status){
        for(UserOnlineBo bo : getAllUser()){
            Long userId = bo.getId();

            if(userId.equals(id)){
                Session session = shiroSessionRepository.getSession(bo.getSessionId());

                SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);

                sessionStatus.setOnlineStatus(status.intValue() == 1);

                customShiroSessionDAO.update(session);
            }
        }
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
        this.customShiroSessionDAO = customShiroSessionDAO;
    }

    /**
     * 根据ID查询 SimplePrincipalCollection
     * @param userIds	用户ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Long...userIds){
        //把userIds 转成Set，好判断
        Set<Long> idset = (Set<Long>) StringUtils.array2Set(userIds);
        //获取所有session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取SimplePrincipalCollection
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof User){
                    User user = (User)obj;
                    //比较用户ID，符合即加入集合
                    if(null != user && idset.contains(user.getId())){
                        list.add(spc);
                    }
                }
            }
        }
        return list;
    }

}
