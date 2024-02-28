package org.example.demo.common.utils;

import java.util.UUID;

/**
 * @author Joword
 * @date: 2024/2/16 19:08
 * @version: 0.1.0
 * @description: uuid utils
 */
public class UUIDUtils {
    
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    
}
