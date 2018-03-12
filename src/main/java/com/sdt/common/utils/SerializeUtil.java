package com.sdt.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializeUtil {
    private final static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    static final Class<?> CLAZZ = SerializeUtil.class;

    public static byte[] serialize(Object value){
        if(null == value){
            throw new NullPointerException("can not serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try{
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            bos.close();
            bos.close();
            rv = bos.toByteArray();
        } catch(Exception e){
            logger.error("serialize error: " + e);
        } finally {
            try{
                bos.close();
                oos.close();
            } catch (IOException e){
                logger.error("error closing bos and oos");
            }

        }
        return rv;
    }
    public static Object deserialize(byte[] in){return deserialize(in, Object.class);}
    public static <T> T deserialize(byte[] in, Class<T>...requiredType){
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try{
            if(in != null){
                bis = new ByteArrayInputStream(in);
                ois = new ObjectInputStream(bis);
                rv = ois.readObject();
            }
        } catch(Exception e) {
            logger.error("deserialize error");
        } finally {
            try{
                bis.close();
                ois.close();
            } catch (IOException e){
                logger.error("error closing bos and oos");
            }
        }
        return (T) rv;
    }
}
