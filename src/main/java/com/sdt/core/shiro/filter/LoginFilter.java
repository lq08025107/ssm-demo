package com.sdt.core.shiro.filter;

import com.sdt.common.model.User;
import com.sdt.core.shiro.token.manager.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends AccessControlFilter {
    final static Class<LoginFilter> CLASS = LoginFilter.class;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        User token = TokenManager.getToken();
        if(null != token || isLoginRequest(servletRequest, servletResponse)){
            return true;
        }
        if(ShiroFilterUtils.isAjax(servletRequest)){
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("login_status", "300");
            resultMap.put("message", "当前用户没有登陆");
            ShiroFilterUtils.out(servletResponse, resultMap);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }
}
