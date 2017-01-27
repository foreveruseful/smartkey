package link.anyauto.smartkey.sdks.targets;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import link.anyauto.smartkey.sdks.IntentKeyMapper;
import link.anyauto.smartkey.sdks.OnPrepareIntentCallback;

/**
 * An activity or a service description.
 * @param <T>
 *     the target generic type.
 */
public abstract class Target<T> {

    /**
     * flags to add to the intent.
     */
    protected int flags;

    /**
     * the values wrapped in the generated IntentBuilder.
     */
    protected IntentKeyMapper params;

    /**
     * MIME TYPE of the intent
     */
    protected String mimeType;

    /**
     * ClipData
     */
    protected ClipData clipData;

    /**
     * The callback which allows you to add a finishing touches on the intent.
     */
    protected OnPrepareIntentCallback callback;

    /**
     * <pre>
     * The replacement intent if you don't want to use the generated.
     * But be careful because all value in the IntentBuilder will not be added into your intent automatically.
     * </pre>
     */
    protected Intent replacedIntent;

    /**
     * Set the params that will be added to the intent.
     * @param params
     *  the params wrapped in an IntentBuilder.
     * @return
     *  this
     */
    public  T params(IntentKeyMapper params) {
        this.params = params;
        return (T) this;
    }

    /**
     * Set the callback on preparing the intent.
     * @param callback
     *  the callback
     * @return
     *  this
     */
    public T setCallback(OnPrepareIntentCallback callback) {
        this.callback = callback;
        return (T) this;
    }

    /**
     * Prepare the intent.
     * @param context
     *  the context
     * @return
     *  the intent.
     */
    protected Intent prepare(Context context) {
        Intent intent = replacedIntent != null ? replacedIntent : getIntent();
        intent = intent == null ? new Intent() : intent;
        if(callback != null) {
            callback.prepare(intent);
        }
        if(flags != 0) {
            intent.addFlags(flags);
        }
        if(!TextUtils.isEmpty(mimeType)) {
            intent.setType(mimeType);
        }
        if(clipData != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            intent.setClipData(clipData);
        }
        return intent;
    }

    /**
     * Build an intent from the IntentBuilder
     * @return
     */
    protected Intent getIntent() {
        return params == null ? null : params.buildIntent();
    }

    /**
     * Replace the generated intent.
     * @param intent
     *  the intent
     * @return
     *  this
     */
    public T replaceIntent(Intent intent) {
        replacedIntent = intent;
        return (T) this;
    }

    /**
     * Add flags to the intent.
     * @param flags
     *  flags
     * @return
     *  this
     */
    public T addFlags(int flags) {
        this.flags |= flags;
        return (T) this;
    }

    /**
     * Set mime type of the intent.
     * @param mimeType
     *  the mime type
     * @return
     *  this
     */
    public T mimeType(String mimeType) {
        this.mimeType = mimeType;
        return (T) this;
    }

    /**
     * Set the clip data, only supported at least api level 16.
     * @param data
     *  the clip data
     * @return
     *  this
     */
    public T clipData(ClipData data) {
        this.clipData = data;
        return (T) this;
    }
}
