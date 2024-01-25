package org.example.demo.commons.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author Joword
 * @date: 2023/8/17 11:11
 * @version: 0.1.0
 * @description: to mask data
 */
@Component
public class DataMaskingUtils {

    /**
     * to generate salt
     *
     * @param password String
     *
     * @return String
     */
    public static String toHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPwd(String hash, String password) {
        return BCrypt.checkpw(password, hash);
    }
}
