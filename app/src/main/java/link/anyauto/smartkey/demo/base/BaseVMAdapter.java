package link.anyauto.smartkey.demo.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.BaseObservable;
import android.os.Bundle;

/**
 * Created by discotek on 17-1-22.
 */

public class BaseVMAdapter extends BaseObservable implements BaseVM {

    protected Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public void create(Bundle savedInstanceState) {

    }

    @Override
    public void restart() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void back() {
        if(activity != null) {
            activity.finish();
        }
    }

    @Override
    public void result(int reqCode, int resCode, Intent data) {

    }

    @Override
    public void configChanged(Configuration newConfig) {

    }
}
