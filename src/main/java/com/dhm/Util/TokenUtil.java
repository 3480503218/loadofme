package com.dhm.Util;

import org.apache.commons.codec.digest.DigestUtils;

public class TokenUtil {
    private static TokenUtil instance = new TokenUtil();

    //存储token的map
    private ExpireMapUtil<String, String> tokenMap;

    private TokenUtil() {
        tokenMap =  new ExpireMapUtil<String, String>();
    }

    public static TokenUtil getInstance() {
        return instance;
    }


    /**
     * MD5加密方法
     * @param text: 明文
     * @param key：密钥
     * @return ：密文
     */
    public static String md5(String text, String key) {
        //加密
        String encodeStr = DigestUtils.md5Hex(text + key);
        return encodeStr;
    }

    /**
     * MD5验证方法
     * @param text ：明文
     * @param key ：密钥
     * @param md5 ：密文
     */
    public static boolean verify(String text, String key, String md5){
        //验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            return true;
        }
        return false;
    }

    //getter
    public ExpireMapUtil<String, String> getTokenMap() {
        return tokenMap;
    }

}
