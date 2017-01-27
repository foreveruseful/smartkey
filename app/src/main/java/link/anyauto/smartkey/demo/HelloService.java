package link.anyauto.smartkey.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import link.anyauto.smartkey.demo.util.ToastUtil;

/**
 * Created by discotek on 17-1-16.
 */

public class HelloService extends Service {

    Binder binder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ToastUtil.toast(R.string.service_bound);
        if(binder == null) {
            binder = new HelloBinder();
        }
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ToastUtil.toast(R.string.service_started);
        return super.onStartCommand(intent, flags, startId);
    }

    public class HelloBinder extends Binder {
        HelloService getService() {
            return HelloService.this;
        }
    }
}
