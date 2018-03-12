package com.sdt.core.shiro.cache.impl;

import com.sdt.core.shiro.cache.JedisManager;
import com.sdt.core.shiro.cache.JedisShiroCache;
import com.sdt.core.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;

public class JedisShiroCacheManager implements ShiroCacheManager {

    private JedisManager jedisManager;

    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    public void destroy() {


    }
    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

}
