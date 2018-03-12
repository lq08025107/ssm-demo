package com.sdt.core.shiro.token;

import com.sdt.common.model.User;
import com.sdt.core.shiro.token.manager.TokenManager;
import com.sdt.permission.service.PermissionService;
import com.sdt.permission.service.RoleService;
import com.sdt.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

public class SampleRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;

    public SampleRealm(){super();}

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long userId = TokenManager.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Set<String> roles = roleService.findRoleByUserId(userId);
        info.setRoles(roles);

        Set<String> permissions = permissionService.findPermissionByUserId(userId);
        info.setStringPermissions(permissions);
        return info;
    }
    //认证登陆
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ShiroToken token = (ShiroToken)authenticationToken;
        User user = userService.login(token.getUsername(), token.getPswd());

        if(null == user)
        {
            throw new AccountException("账号密码不正确");
        } else if(User._0.equals(user.getStatus())){
            throw new DisabledAccountException("账号已禁止登陆");
        } else {
            user.setLastLoginTime(new Date());
            userService.updateByPrimaryKeySelective(user);
        }
        return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
    }

    /**
     * 清空当前用户权限信息
     */
    public  void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
