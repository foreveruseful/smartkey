package link.anyauto.smartkey.sdks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * <pre>
 * As it is not an easy job to determine the inheritance hierarchy of an object
 *  when in APT environment, objects that having a complex inheritance structure
 *  will be treated back into the android runtime environment.
 * </pre>
 */
public class IntentValueGetter {

    /**
     * Get a value from an intent by a giving class and key.
     * @param intent
     *  the source intent
     * @param key
     *  the key of the value
     * @param clz
     *  the class of the value
     * @return
     *  the value from the source intent
     */
    public static Object getValue(Intent intent, String key, Class<?> clz) {
        Object value = null;
        // Cause it is not an easy job to handle inheritance in apt, it is a lot easier to put the code in the Android environment.
        if(Bundle.class.isAssignableFrom(clz)) {
            // bundle implements parcelable, so it should place before parcelable.
            value = intent.getBundleExtra(key);
        } else if(Parcelable.class.isAssignableFrom(clz)) {
            value = intent.getParcelableArrayExtra(key);
        } else if(Parcelable[].class.isAssignableFrom(clz)) {
            value = intent.getParcelableArrayExtra(key);
        } else if(boolean[].class.isAssignableFrom(clz)) {
            value = intent.getBooleanArrayExtra(key);
        } else if(byte[].class.isAssignableFrom(clz)) {
            value = intent.getByteArrayExtra(key);
        } else if(short[].class.isAssignableFrom(clz)) {
            value = intent.getShortArrayExtra(key);
        } else if(char[].class.isAssignableFrom(clz)) {
            value = intent.getCharArrayExtra(key);
        } else if(int[].class.isAssignableFrom(clz)) {
            value = intent.getIntArrayExtra(key);
        } else if(long[].class.isAssignableFrom(clz)) {
            value = intent.getLongArrayExtra(key);
        } else if(float[].class.isAssignableFrom(clz)) {
            value = intent.getFloatArrayExtra(key);
        } else if(double[].class.isAssignableFrom(clz)) {
            value = intent.getDoubleArrayExtra(key);
        } else if(String[].class.isAssignableFrom(clz)) {
            value = intent.getStringArrayExtra(key);
        } else if(CharSequence[].class.isAssignableFrom(clz)) {
            value = intent.getCharSequenceArrayExtra(key);
        } else if(Serializable.class.isAssignableFrom(clz)) {
            // some of the above are assignable for serializable, so serializable should be in the last place.
            value = intent.getSerializableExtra(key);
        } else {
            throw new RuntimeException(clz + " is not compatible with intent (key=" + key + ")");
        }

        return value;
    }

}
