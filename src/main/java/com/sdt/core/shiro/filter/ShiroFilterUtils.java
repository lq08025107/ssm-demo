package com.sdt.core.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

public class ShiroFilterUtils {
    private final static Logger logger = LoggerFactory.getLogger(ShiroFilterUtils.class);
    final static Class<?extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;

    static final String LOGIN_URL = "/u/login.shtml";

    final static String KICKED_OUT = "/open/kickedOut.shtml";

    final static String UNAUTHORIZED = "/open/unauthorized.shtml";

    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest)request).getHeader("X-Requested-With"));
    }

    public static void out(ServletResponse response, Map<String, String> resultMap){
        PrintWriter out = null;
        try{
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JSONObject.toJSON(resultMap).toString());
        } catch(Exception e){
            logger.error("输出json报错");
        } finally {
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
