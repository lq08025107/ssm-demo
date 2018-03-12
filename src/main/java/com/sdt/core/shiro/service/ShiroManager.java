package com.sdt.core.shiro.service;

public interface ShiroManager {
    /**
     * 加载过滤配置信息
     * @return
     */
    public String loadFilterChainDefinitions();

    /**
     * 重新构建权限过滤器
     */
    public void reCreateFilterChains();
}

