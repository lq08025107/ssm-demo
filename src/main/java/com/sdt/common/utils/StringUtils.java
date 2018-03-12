package com.sdt.common.utils;

import java.util.Set;
import java.util.TreeSet;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static boolean isBlank(Object...objects){
        Boolean result = false;

        for(Object object : objects){
            if(null == object || "".equals(object.toString().trim())||"null".equals(object.toString().trim())){
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 把数组转换成set
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<Object>();
        for (Object id : array) {
            if(null != id){
                set.add(id);
            }
        }
        return set;
    }
}
