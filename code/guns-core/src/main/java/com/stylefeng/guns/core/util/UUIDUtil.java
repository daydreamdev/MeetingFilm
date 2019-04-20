package com.stylefeng.guns.core.util;

import java.util.UUID;

public class UUIDUtil {

    public static String genUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
