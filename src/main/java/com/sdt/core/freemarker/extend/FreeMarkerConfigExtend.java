package com.sdt.core.freemarker.extend;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

public class FreeMarkerConfigExtend extends FreeMarkerConfigurer {
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException{
        super.afterPropertiesSet();
        Configuration cfg = this.getConfiguration();
        putInitShared(cfg);
    }

    public static void put(Configuration cfg, String k, Object v) throws TemplateModelException{
        cfg.setSharedVariable(k, v);
        cfg.setNumberFormat("#");
    }

    public static void putInitShared(Configuration cfg) throws TemplateModelException{
        put(cfg, "shiro", new ShiroTags());
    }
}
