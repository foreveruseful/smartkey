package link.anyauto.smartkey.sdks.targets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

/**
 * A wrapper or just say a target of activity, all generated activity targets will inherit this class.
 * @param <T>
 *     the type of generated class.
 */
public abstract class ActivityTarget<T> extends Target<T>{

    private View transitionView;
    private String transitionName;

    /**
     * Add transition view.
     * @param transitionView
     *  the transition view.
     * @return
     *  this
     */
    public T transitionView(View transitionView) {
        this.transitionView = transitionView;
        return (T) this;
    }

    /**
     * Add transition name.
     * @param transitionName
     *  the transition name
     * @return
     *  this
     */
    public T transitionName(String transitionName) {
        this.transitionName = transitionName;
        return (T) this;
    }

    /**
     * The class of the activity.
     * @return
     *  the class.
     */
    protected abstract Class<? extends Activity> getActivityClass();

    /**
     * Start the wrapped activity
     * @param src
     *  the context
     */
    public void go(Context src) {
        realGo(src, prepare(src).setClass(src, getActivityClass()));
    }

    /**
     * Start the wrapped activity for a result.
     * @param src
     *  the activity
     * @param reqCode
     *  the request code
     */
    public void goForResult(Activity src, int reqCode) {
        realGoForResult(src, prepare(src).setClass(src, getActivityClass()), reqCode);
    }

    /**
     * Check and add FLAG_ACTIVITY_NEW_TASK if the source context is not an activity.
     * @param context
     *  the context
     * @return
     *  the intent.
     */
    @Override
    protected Intent prepare(Context context) {
        if(context instanceof Activity) {
            return super.prepare(context);
        } else {
            return super.prepare(context).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    /**
     * Go with transition animation.
     * @param src
     * @param intent
     */
    protected void realGo(Context src, Intent intent) {
        if(src instanceof Activity && transitionView != null && transitionName != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) src, transitionView, transitionName);
            ActivityCompat.startActivity((Activity) src, intent, options.toBundle());
        } else {
            src.startActivity(intent);
        }
    }

    /**
     * Go for result with transition animation.
     * @param src
     * @param intent
     */
    protected void realGoForResult(Activity src, Intent intent, int reqCode) {
        if(transitionView != null && transitionName != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) src, transitionView, transitionName);
            ActivityCompat.startActivityForResult((Activity) src, intent, reqCode, options.toBundle());
        } else {
            src.startActivityForResult(intent, reqCode);
        }
    }
}
