package com.syscom.apps.myapps.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO : Commentaire pour expliquer ce que fait l'activité
 *
 */

public class MyAppsUtility {

    public static final String EMPTY = "";
    private static Pattern pattern;
    private static Matcher matcher;

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
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

}
