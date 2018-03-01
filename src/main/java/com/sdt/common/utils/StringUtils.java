package com.sdt.common.utils;

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
}
