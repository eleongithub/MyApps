package com.syscom.apps.myapps.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitaire pour vérifier le format des adresses mail.
 *
 */
public class MyAppsUtility {

    private MyAppsUtility() {
        throw new IllegalAccessError("Utility class");
    }

    public static final String EMPTY = "";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     *
     * Check Email
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean isValideMail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

}
