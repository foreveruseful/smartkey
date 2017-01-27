package link.anyauto.smartkey.sdks;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Helper class for doing with conversion between POJO and JSON.
 */
public class GsonHelper {

    private static Gson gson = new Gson();

    /**
     * <pre>
     *  Replace the default gson.
     *  It is recommend to replace the gson in your application class,
     *  as if there are some special cases like your object has a Date field,
     *  and it should be treated differently.
     * </pre>
     * @param gson
     */
    public static void replaceGson(Gson gson) {
        GsonHelper.gson = gson;
    }

    /**
     * Helper method to convert a json string to POJO
     * @param src
     *  the source json format string
     * @param clz
     *  the class of the converted POJO
     * @param <T>
     *  the generic type
     * @return
     *  null if src is null, otherwise the POJO representation of the source json string
     */
    public static <T> T fromJson(String src, Class<T> clz) {
        if(TextUtils.isEmpty(src))
            return null;
        return gson.fromJson(src, clz);
    }

    /**
     * Helper method to convert a json string to POJO
     * @param src
     *  the source json format string
     * @param type
     *  the type of the converted POJO
     * @param <T>
     *  the generic type
     * @return
     *  null if src is null, otherwise the POJO representation of the source json string
     */
    public static <T> T fromJson(String src, Type type) {
        if(TextUtils.isEmpty(src))
            return null;
        return gson.fromJson(src, type);
    }

    /**
     * Helper method to convert a POJO to json string
     * @param anyThing
     *  any object
     * @return
     *  null if the anyThing param is null, otherwise the json string representation of the anyThing param.
     */
    public static String toJson(Object anyThing) {
        if(anyThing == null)
            return null;
        return gson.toJson(anyThing);
    }

    /**
     * Helper method to convert a json string to POJO
     * @param src
     *  the source json format string
     * @param clz
     *  the class of the converted POJO
     * @param gson
     *  your own gson instance
     * @param <T>
     *  the generic type
     * @return
     *  null if src or gson is null, otherwise the POJO representation of the source json string
     */
    public static <T> T fromJson(String src, Class<T> clz, Gson gson) {
        if(TextUtils.isEmpty(src) || gson == null)
            return null;
        return gson.fromJson(src, clz);
    }

    /**
     * Helper method to convert a json string to POJO
     * @param src
     *  the source json format string
     * @param type
     *  the type of the converted POJO
     * @param gson
     *  your own gson instance
     * @param <T>
     *  the generic type
     * @return
     *  null if src or gson is null, otherwise the POJO representation of the source json string
     */
    public static <T> T fromJson(String src, Type type, Gson gson) {
        if(TextUtils.isEmpty(src) || gson == null)
            return null;
        return gson.fromJson(src, type);
    }

    /**
     * Helper method to convert a POJO to json string
     * @param anyThing
     *  any object
     * @return
     *  null if the anyThing or gson is null, otherwise the json string representation of the anyThing param.
     */
    public static String toJson(Object anyThing, Gson gson) {
        if(anyThing == null || gson == null)
            return null;
        return gson.toJson(anyThing);
    }
}
