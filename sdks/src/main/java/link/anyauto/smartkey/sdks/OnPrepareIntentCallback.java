package link.anyauto.smartkey.sdks;

import android.content.Intent;

/**
 * Callback that allows you to do any additional work before starting the target(activity or service)
 */

public interface OnPrepareIntentCallback {
    /**
     * <pre>
     * When using the generated SmartTargets,
     * this is the callback that will be called just before
     *  going to another activity or starting a service.
     * </pre>
     * @param intent
     */
    void prepare(Intent intent);
}
