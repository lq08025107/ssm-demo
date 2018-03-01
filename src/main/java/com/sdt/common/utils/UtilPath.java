package com.sdt.common.utils;

import org.apache.commons.lang.StringUtils;

public class UtilPath {
    public static String getClassPath(){
        String systemName = System.getProperty("os.name");

        if(!StringUtils.isBlank(systemName) && systemName.indexOf("Windows") !=-1){
            return UtilPath.class.getResource("/").getFile().toString().substring(1);
        }else{
            return UtilPath.class.getResource("/").getFile().toString();
        }
    }

    public static String getRootPath(){
        return getWEB_INF().replace("WEB-INF/", "");
    }

    public static String getHTMLPath(){
        return getFreePath() + "html/html/";
    }

    public static String getWEB_INF(){
        return getClassPath().replace("classes/", "");
    }

    public static String getFreePath(){
        return getWEB_INF() + "ftl/";
    }

    /**
     * 获取输出FTL目录
     * @return
     */
    public static String getFTLPath(){
        return getFreePath() + "html/ftl/";
    }

    public static void main(String[] args){
        String s = UtilPath.getFreePath();
        System.out.println(s);
    }
}
