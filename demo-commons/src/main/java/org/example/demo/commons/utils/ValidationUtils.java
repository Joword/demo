package org.example.demo.commons.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author Joword
 * @date: 2023/8/15 18:09
 * @version: 0.1.0
 * @description: validation arguments
 */
@Component
public class ValidationUtils {

    public static String isEmail(String email) {
        final String REGEX_EMAIL = ".*@.*";
        return Pattern.matches(REGEX_EMAIL, email) ? "YES" : "NO";
    }

}
