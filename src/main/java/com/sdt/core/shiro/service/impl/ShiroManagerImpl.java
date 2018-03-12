package com.sdt.core.shiro.service.impl;

import com.sdt.core.config.INI4j;
import com.sdt.core.shiro.service.ShiroManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

public class ShiroManagerImpl implements ShiroManager {
    private static final Logger logger = LoggerFactory.getLogger(ShiroManagerImpl.class);

    // 注意/r/n前不能有空格
    private static final String CRLF = "\r\n";

    @Resource
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFixedAuthRule());
        return sb.toString();
    }

    private String getFixedAuthRule(){
        String fileName = "shiro_base_auth.ini";
        ClassPathResource cp = new ClassPathResource(fileName);
        INI4j ini = null;
        try{
            ini = new INI4j(cp.getFile());
        } catch (Exception e){
            logger.error("加载文件出错");
        }
        String section = "base_auth";
        Set<String> keys = ini.get(section).keySet();
        StringBuffer sb = new StringBuffer();
        for(String key : keys){
            String value = ini.get(section, key);
            sb.append(key).append(" = ").append(value).append(CRLF);
        }
        return sb.toString();
    }

    public synchronized void reCreateFilterChains() {
        AbstractShiroFilter shiroFilter = null;
        try{
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e){
            logger.error("getShiroFilter from factory bean error");
            throw new RuntimeException("getShiroFilter from factory bean error");
        }
        PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
        // 重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean
                .getFilterChainDefinitionMap();
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }
    }

    public void setShiroFilterFactoryBean(
            ShiroFilterFactoryBean shiroFilterFactoryBean) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
    }
}
