package com.syscom.apps.myapps.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static com.syscom.apps.myapps.utilities.Constants.PREFERENCE_FILE_KEY;
import static com.syscom.apps.myapps.utilities.EncryptData.decrypt;
import static com.syscom.apps.myapps.utilities.EncryptData.encrypt;

/**
 * Read and write shared preference datas
 */
public class SharedPreferencesUtils {

    public static final String DEFAULT = "default";

    /**
     * Enregistrer une donnée de type String.
     *
     * @param context contexte {@link Context}
     * @param key clé de la donnée
     * @param value valeur de la donnée
     */
    public static void saveToSharedPreferences(Context context, String key, String value){
        saveToSharedPreferences(context, PREFERENCE_FILE_KEY, key, value);
    }

    /**
     * Enregistrer une donnée de type booléenne.
     *
     * @param context contexte {@link Context}
     * @param key clé de la donnée
     * @param value valeur de la donnée
     */
    public static void saveToSharedPreferences(Context context, String key, boolean value){
        saveToSharedPreferences(context, PREFERENCE_FILE_KEY, key, value);
    }

    /**
     * Enregistrer une donnée de type String.
     *
     * @param context contexte {@link Context}
     * @param file fichier contenant la donnée {@link java.io.File}
     * @param key clé de la donnée
     * @param value valeur de la donnée
     */
    public static void saveToSharedPreferences(Context context, String file, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(encrypt(key), encrypt(value));
        editor.commit();
    }

    /**
     * Enregistrer une donnée de type booléenne.
     *
     * @param context contexte {@link Context}
     * @param file fichier contenant la donnée {@link java.io.File}
     * @param key clé de la donnée à enregistrer
     * @param value valeur de la donnée à enregistrer
     */
    public static void saveToSharedPreferences(Context context, String file, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(encrypt(key), value);
        editor.commit();
    }

    /**
     * Rechercher une données de type {@link String}
     *
     * @param context contexte {@link Context}
     * @param key clé de la donnée recherchée.
     * @return donnée recherchée {@link String}
     */
    public static String getFromSharedPreferences(Context context, String key) {
        return getFromSharedPreferences(context, PREFERENCE_FILE_KEY, key);
    }

    /**
     * Rechercher une données de type {@link String}
     *
     * @param context contexte {@link Context}
     * @param file fichier contenant la donnée {@link java.io.File}
     * @param key clé de la donnée recherchée.
     * @return donnée recherchée {@link String}
     */
    public static String getFromSharedPreferences(Context context, String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        String encryptData = sharedPreferences.getString(encrypt(key),encrypt(DEFAULT));
       return decrypt(encryptData);
    }

    /**
     * Rechercher une données de type booléenne.
     *
     * @param context contexte {@link Context}
     * @param key clé de la donnée recherchée.
     * @return donnée de type boolenne.
     */
    public static boolean getBooleanFromSharedPreferences(Context context, String key) {
        return getBooleanFromSharedPreferences(context, PREFERENCE_FILE_KEY, key);
    }

    /**
     * Rechercher une données de type booleenne.
     *
     * @param context contexte {@link Context}
     * @param file fichier contenant la donnée booleenne {@link java.io.File}
     * @param key clé identifiant de la donnée recherchée.
     * @return donnée de type boolenne.
     */
    public static boolean getBooleanFromSharedPreferences(Context context, String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(encrypt(key),false);
        return value;
    }

    /**
     * Supprimer une donnée.
     *
     * @param context contexte {@link Context}
     * @param key clé identifiant de la donnée à supprimer.
     */
    public static void removeFromPrefs(Context context, String key) {
       removeFromPrefs(context, PREFERENCE_FILE_KEY, key);
    }

    /**
     * Supprimer une donnée.
     *
     * @param context contexte {@link Context}
     * @param file fichier contenant la donnée {@link java.io.File}
     * @param key clé identifiant de la donnée à supprimer.
     */
    public static void removeFromPrefs(Context context, String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(encrypt(key));
        editor.commit();
    }
}
