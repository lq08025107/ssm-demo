package com.sdt.user.controller;

import com.sdt.common.controller.BaseController;
import com.sdt.common.model.User;
import com.sdt.common.utils.StringUtils;
import com.sdt.core.shiro.token.manager.TokenManager;
import com.sdt.user.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Scope(value="prototype")
@RequestMapping("u")
public class UserLoginController extends BaseController {
    @Resource
    UserService userService;

    @RequestMapping(value="login",method= RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }

    @RequestMapping(value = "submitLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitLogin(User entity, Boolean rememberMe, HttpServletRequest request){
        try{
            TokenManager.login(entity, rememberMe);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");

            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url = null;
            if(null != savedRequest){
                url = savedRequest.getRequestUrl();
            }
            if(StringUtils.isBlank(url)){
                url = request.getContextPath() + "/user/index.shtml";
            }
            resultMap.put("back_url", url);
        } catch (DisabledAccountException e){
            resultMap.put("status", 500);
            resultMap.put("message", "账号已经禁用");
        } catch (Exception e){
            resultMap.put("status", 500);
            resultMap.put("message", "账号或密码错误");
        }
        return resultMap;
    }

    @RequestMapping(value="register",method= RequestMethod.GET)
    public ModelAndView register(){
        return new ModelAndView("user/register");
    }

}
