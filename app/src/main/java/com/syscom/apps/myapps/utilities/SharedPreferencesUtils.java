package com.syscom.apps.myapps.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static com.syscom.apps.myapps.utilities.Constants.PREFERENCE_FILE_KEY;
import static com.syscom.apps.myapps.utilities.EncryptData.decrypt;
import static com.syscom.apps.myapps.utilities.EncryptData.encrypt;

/**
 *
 * Read and write SharedPreferences datas
 *
 *
 */

public class SharedPreferencesUtils {

    public static final String DEFAULT = "default";

    public static void saveToSharedPreferences(Context context, String key, String value){
        saveToSharedPreferences(context, PREFERENCE_FILE_KEY, key, value);
    }

    public static void saveToSharedPreferences(Context context, String key, boolean value){
        saveToSharedPreferences(context, PREFERENCE_FILE_KEY, key, value);
    }

    public static void saveToSharedPreferences(Context context, String file, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(encrypt(key), encrypt(value));
        editor.commit();
    }

    public static void saveToSharedPreferences(Context context, String file, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(encrypt(key), value);
        editor.commit();
    }

    public static String getFromSharedPreferences(Context context, String key) {
        return getFromSharedPreferences(context, PREFERENCE_FILE_KEY, key);
    }

    public static String getFromSharedPreferences(Context context, String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        String encryptData = sharedPreferences.getString(encrypt(key),encrypt(DEFAULT));
       return decrypt(encryptData);
    }

    public static boolean getBooleanFromSharedPreferences(Context context, String key) {
        return getBooleanFromSharedPreferences(context, PREFERENCE_FILE_KEY, key);
    }

    public static boolean getBooleanFromSharedPreferences(Context context, String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(encrypt(key),false);
        return value;
    }

    public static void removeFromPrefs(Context context, String key) {
       removeFromPrefs(context, PREFERENCE_FILE_KEY, key);
    }

    public static void removeFromPrefs(Context context, String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(encrypt(key));
        editor.commit();
    }
}
