package link.anyauto.smartkey.demo;

import android.app.Application;

import link.anyauto.smartkey.annotation.SmartManifest;
import link.anyauto.smartkey.demo.prefs.MyAppPreferencesSPBuilder;

@SmartManifest(manifestPath = "app/src/main/AndroidManifest.xml")
public class App extends Application {

    protected static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyAppPreferencesSPBuilder.setApp(this);
        checkVersion();
    }

    void checkVersion() {

    }

    public static String getStr(int resId) {
        return instance.getString(resId);
    }

    public static String getStr(int resId, Object ...args) {
        return instance.getString(resId, args);
    }
}
