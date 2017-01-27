package link.anyauto.smartkey.sdks.targets;

import android.app.Activity;
import android.content.Intent;

import link.anyauto.smartkey.sdks.IntentKeyMapper;

/**
 * Util for wrapping result when an activity finishes.
 * Created by LYQ on 17-1-18.
 */

public class BackResult {
    int code = Activity.RESULT_OK;
    Intent intent;
    IntentKeyMapper params;

    /**
     * Create a back result, default result code is RESULT_OK.
     * @return
     *  the new BackResult instance.
     */
    public static BackResult newBackResult() {
        return new BackResult();
    }

    /**
     * Create a back result with the given result code.
     * @param resultCode
     *  the result code
     * @return
     *  the new BackResult instance.
     */
    public static BackResult newBackResult(int resultCode) {
        return new BackResult(resultCode);
    }

    /**
     * Create a back result, default result code is RESULT_OK.
     */
    public BackResult(){}

    /**
     * Create a back result with the given result code.
     * @param resultCode
     *  the result code
     */
    public BackResult(int resultCode) {
        code = resultCode;
    }

    /**
     * An intent to send back by this result
     * @param intent
     *  the intent
     * @return
     *  this
     */
    public BackResult intent(Intent intent) {
        this.intent = intent;
        return this;
    }

    /**
     * Set result code other than default.
     * @param resultCode
     *  the result code
     * @return
     *  this
     */
    public BackResult resultCode(int resultCode) {
        code = resultCode;
        return this;
    }

    /**
     * Params that will send back by this result.
     * @param params
     *  the params
     * @return
     *  this
     */
    public BackResult params(IntentKeyMapper params) {
        this.params = params;
        return this;
    }

    /**
     * Finish the activity with your given result code and or intent and or params.
     * @param activity
     *  The activity that will be finish, it's finish() will be called in this method.
     */
    public void finishWithResult(Activity activity) {
        Intent in = intent;
        if(params != null) {
            in = in == null ? params.buildIntent() : in.putExtras(params.buildIntent().getExtras());
        }
        if(in != null) {
            activity.setResult(code, in);
        } else {
            activity.setResult(code);
        }
        activity.finish();;
    }
}
