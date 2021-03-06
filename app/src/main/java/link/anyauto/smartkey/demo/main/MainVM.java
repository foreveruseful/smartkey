package link.anyauto.smartkey.demo.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import link.anyauto.smartkey.demo.App;
import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseVMAdapter;
import link.anyauto.smartkey.demo.extras.ForResultReqIBuilder;
import link.anyauto.smartkey.demo.extras.ForResultRes;
import link.anyauto.smartkey.demo.extras.ForResultResIBuilder;
import link.anyauto.smartkey.demo.prefs.MyAppPreferencesSPBuilder;
import link.anyauto.smartkey.demo.targets.SmartTargets;
import link.anyauto.smartkey.demo.util.ToastUtil;

import static android.app.Activity.RESULT_OK;

/**
 * Created by discotek on 17-1-22.
 */

public class MainVM extends BaseVMAdapter {

    public static final int REQ_CODE_FOR_RESULT = 1;

    ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ToastUtil.toast(R.string.bound_success);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ToastUtil.toast(R.string.disconnected);
        }
    };

    public String getOpenTimesLabel() {
        return App.getStr(R.string.app_opened_times, MyAppPreferencesSPBuilder.openTimes());
    }

    public void fillForm() {
        // Demo on how to use SharedPreferences in an elegant way.
        SmartTargets.toFillFormActivityATarget().go(activity);
    }

    public void share() {
        // Demo on how to be compatible with legacy keys
        // (some keys that you just don't want to change or 3rd libs or system components).
        SmartTargets.toShareActivityATarget().go(activity);
    }

    public void goForAResult() {
        // Demo on how to elegantly passing and receiving extras with intent.
        SmartTargets.toForResultActivityATarget()
                .params(ForResultReqIBuilder
                        .newBuilder()
                        .title(App.getStr(R.string.input_something))
                        .label(App.getStr(R.string.this_is_label))
                        .hint(App.getStr(R.string.this_is_hint)))
                .goForResult(activity, REQ_CODE_FOR_RESULT);
    }

    public void startService() {
        // demo on starting a service
        SmartTargets.toHelloServiceSTarget().start(activity);
    }

    public void bindService() {
        // demo on binding a service // a service should be started first before it can be bound
        SmartTargets.toHelloServiceSTarget().bind(activity, sc, 0);
    }

    public void stopService() {
        // demo on stopping a service
        SmartTargets.toHelloServiceSTarget().stop(activity);
    }

    @Override
    public void destroy() {
        try {
            activity.unbindService(sc);
        } catch (Exception e){
            e.printStackTrace();
        }
        SmartTargets.toHelloServiceSTarget().stop(activity);
    }

    @Override
    public void result(int reqCode, int resCode, Intent data) {
        if(reqCode == REQ_CODE_FOR_RESULT && resCode == RESULT_OK) {
            // Yes, easy right? One line of code will get all your data from result source very smartly.
            ForResultRes res = ForResultResIBuilder.getSmart(data);
            ToastUtil.toast(App.getStr(R.string.your_input, "\n1. " + res.input + "\n2. " + res.input2));
        }
    }
}
