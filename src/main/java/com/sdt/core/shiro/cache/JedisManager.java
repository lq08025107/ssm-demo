package com.sdt.core.shiro.cache;

import com.sdt.common.utils.SerializeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class JedisManager {

    private static final Logger logger = LoggerFactory.getLogger(JedisManager.class);
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Jedis getJedis(){
        Jedis jedis = null;
        try{
            jedis = getJedisPool().getResource();
        } catch (JedisConnectionException e){
            String message = StringUtils.trim(e.getMessage());
            logger.error(message);
            System.out.println("请检查Redis服务");
            System.exit(0);
        }
        return jedis;
    }

    public byte[] getValueByKey(int dbIndex, byte[] key) {
        Jedis jedis = null;
        byte[] result = null;
        try {
            jedis = getJedis();
            jedis.select(dbIndex);
            result = jedis.get(key);
        } catch (Exception e) {
            jedis.close();
        }
        return result;
    }

    public void deleteByKey(int dbIndex, byte[] key){
        Jedis jedis = null;

        try{
            jedis = getJedis();
            jedis.select(dbIndex);
            Long result = jedis.del(key);
            logger.info("删除Session结果：%s", result);
        } catch (Exception e) {
            jedis.close();
        }
    }

    public void saveValueBykey(int dbIndex, byte[] key, byte[] value, int expireTime){
        Jedis jedis = null;

        try{
            jedis = getJedis();
            jedis.select(dbIndex);
            jedis.set(key, value);
            if(expireTime > 0){
                jedis.expire(key, expireTime);
            }
        } catch (Exception e){
            jedis.close();
        }
    }

    public Collection<Session> getAllSession(int dbIndex, String redisShiroSession ){
        Jedis jedis = null;
        Set<Session> sessions = new HashSet<Session>();

        try{
            jedis = getJedis();
            jedis.select(dbIndex);
            Set<byte[]> byteKeys = jedis.keys((JedisShiroSessionRepository.REDIS_SHIRO_ALL).getBytes());
            if(byteKeys != null && byteKeys.size() > 0){
                for (byte[] bytes : byteKeys){
                    Session obj = SerializeUtil.deserialize(jedis.get(bytes), Session.class);
                    if(obj instanceof Session){
                        sessions.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            jedis.close();
        }
        return sessions;
    }





}
