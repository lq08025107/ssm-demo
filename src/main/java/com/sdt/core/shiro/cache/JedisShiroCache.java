package com.sdt.core.shiro.cache;

import com.sdt.common.utils.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class JedisShiroCache<K, V> implements Cache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(JedisShiroCache.class);

    private static final String REDIS_SHIRO_CACHE = "shiro-demo-cache";

    private static final int DB_INDEX = 1;

    private JedisManager jedisManager;

    private String name;

    static final Class<JedisShiroCache> SELF = JedisShiroCache.class;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JedisShiroCache(String name, JedisManager jedisManager){
        this.name = name;
        this.jedisManager = jedisManager;

    }

    public V get(K key) throws CacheException{
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(key));
        byte[] byteVal = new byte[0];
        try {
            byteVal = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e){
            logger.error("get value by cache throws exception");
        }
        return  (V) SerializeUtil.deserialize(byteVal);
    }

    public V put(K key, V val) throws CacheException{
        V pre = get(key);
        try{
            jedisManager.saveValueBykey(DB_INDEX, SerializeUtil.serialize(key), SerializeUtil.serialize(val),-1);
        } catch (Exception e){
            logger.error("put cache throw exception");
        }
        return pre;
    }

    public V remove(K key) throws CacheException{
        V pre = get(key);
        try{
            jedisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(key));
        } catch (Exception e){
            logger.error("remove cache throw exception");
        }
        return pre;
    }

    private String buildCacheKey(Object key){
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }

    public Collection<V> values() {
        return null;
    }

    public int size(){
        if(keys() == null) return 0;
        return keys().size();
    }

    public Set<K> keys(){
        return null;
    }

    public void clear() throws CacheException{}
}
