package link.anyauto.smartkey.sdks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Helper class for storing and retrieving data in default SharedPreferences.
 */
public class StorageHelper {

    /**
     * Get a string.
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @return
     *  the saved value, null if no such a value.
     */
    public static String get(Context ctx, String key) {
        return getSp(ctx).getString(key, null);
    }

    /**
     * Get a boolean
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @return
     *  the saved value, false if no such a value.
     */
    public static boolean getBoolean(Context ctx, String key) {
        return getSp(ctx).getBoolean(key, false);
    }

    /**
     * Get a float
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @return
     *  the saved value, 0 if no such a value.
     */
    public static float getFloat(Context ctx, String key) {
        return getSp(ctx).getFloat(key, 0);
    }

    /**
     * Get an int value
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @return
     *  the saved value, 0 if no such a value.
     */
    public static int getInt(Context ctx, String key) {
        return getSp(ctx).getInt(key, 0);
    }

    /**
     * Get a long
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @return
     *  the saved value, 0 if no such a value.
     */
    public static long getLong(Context ctx, String key) {
        return getSp(ctx).getLong(key, 0);
    }

    /**
     * Get a String set
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @return
     *  the saved value, null if no such a value.
     */
    public static Set<String> getStringSet(Context ctx, String key) {
        return getSp(ctx).getStringSet(key, null);
    }

    /**
     * Save or remove a string
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @param value
     *  the value, remove the value from SharedPreferences if the value is null.
     */
    public static void save(Context ctx, String key, String value) {
        if(value == null) {
            getEditor(ctx).remove(key).commit();
        } else {
            getEditor(ctx).putString(key, value).commit();
        }
    }

    /**
     * Save a boolean value
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @param value
     *  the boolean value
     */
    public static void save(Context ctx, String key, boolean value) {
        getEditor(ctx).putBoolean(key, value).commit();
    }

    /**
     * Save a float value
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @param value
     *  the float value
     */
    public static void save(Context ctx, String key, float value) {
        getEditor(ctx).putFloat(key, value).commit();
    }

    /**
     * Save an int value
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @param value
     *  the int value
     */
    public static void save(Context ctx, String key, int value) {
        getEditor(ctx).putInt(key, value).commit();
    }

    /**
     * Save a long value
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @param value
     *  the long value
     */
    public static void save(Context ctx, String key, long value) {
        getEditor(ctx).putLong(key, value).commit();
    }

    /**
     * Save a String set value
     * @param ctx
     *  the context
     * @param key
     *  the key
     * @param value
     *  the String set value, remove from SharedPreferences if the value is null
     */
    public static void save(Context ctx, String key, Set<String> value) {
        if(value == null) {
            getEditor(ctx).remove(key).commit();
        } else {
            getEditor(ctx).putStringSet(key, value);
        }
    }

    static SharedPreferences.Editor getEditor(Context ctx) {
        return getSp(ctx).edit();
    }

    static SharedPreferences getSp(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
}
