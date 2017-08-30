package com.syscom.apps.myapps.utilities;

import android.util.Base64;

/**
 * Utilitaire pour encrypter/décrypter les données.
 *
 */
public class EncryptData {

    // Simple encryption !! To be improved
    public static String encrypt(String input) {

        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
