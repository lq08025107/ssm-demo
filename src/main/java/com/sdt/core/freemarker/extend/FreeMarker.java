package com.sdt.core.freemarker.extend;

import com.sdt.common.utils.SpringContextUtil;
import com.sdt.common.utils.UtilPath;
import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class FreeMarker {
    //html输出目录
    protected static String HTML_PATH = UtilPath.getHTMLPath();
    //ftl输入目录
    protected static String FTL_PATH = UtilPath.getFTLPath();
    private static Configuration cfg = null;
    private static Log logger = LogFactory.getLog(FreeMarker.class);

    static Map<String, Object> initMap;

    static{
        initMap = new LinkedHashMap<String, Object>();

        /**Freemarker Config**/
        cfg = new Configuration();
        cfg.setLocale(Locale.getDefault());
        cfg.setEncoding(Locale.getDefault(), "UTF-8");

        FreeMarkerConfigExtend ext = SpringContextUtil.getBean("freemarkerConfig", FreeMarkerConfigExtend.class);

        Configuration vcfg = ext.getConfiguration();
        Set<String> keys = vcfg.getSharedVariableNames();
        for(String key : keys){
            TemplateModel value = vcfg.getSharedVariable(key);
            cfg.setSharedVariable(key, value);
        }
        try {
            FreeMarkerConfigExtend.putInitShared(cfg);
        } catch (TemplateModelException e) {
            logger.error("添加FreeMarker自定义方法失败；", e);
        }
    }
}
