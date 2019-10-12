package com.zygh.webapi.utils;

import cn.hutool.core.codec.Base64;

public class CheckBitUtil {

    public static boolean check(String fields,String checkbit){
        String check = encode(fields);

        if(check.equals(checkbit)){
            return true;
        }
        return false;
    }

    public static String encode(String code){
        return Base64.encode(code);
    }

    public static String decode(String code){
        return Base64.decodeStr(code);
    }
}
