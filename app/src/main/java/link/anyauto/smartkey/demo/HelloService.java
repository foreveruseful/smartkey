package link.anyauto.smartkey.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by discotek on 17-1-16.
 */

public class HelloService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("HelloService", "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("HelloService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
