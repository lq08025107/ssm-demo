package com.sdt.core.freemarker.extend;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FreeMarkerViewExtend extends FreeMarkerView {
    protected void exposehelpers(Map<String, Object> model, HttpServletRequest request){
        try {
            super.exposeHelpers(model, request);
        } catch (Exception e) {
            logger.error( "FreeMarkerViewExtend 加载父类出现异常。请检查。");
        }
        model.put("contextPath", request.getContextPath());
        model.putAll(FreeMarker.initMap);

//        if(Tokenmanager.isLogin()){
//            model.put("token", token);
//        }
    }
}
