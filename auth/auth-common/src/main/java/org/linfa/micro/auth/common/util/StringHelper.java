package org.linfa.micro.auth.common.util;

/**
 * 字符串处理
 */
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
}
