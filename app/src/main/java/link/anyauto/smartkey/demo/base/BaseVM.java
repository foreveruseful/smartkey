package link.anyauto.smartkey.demo.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by discotek on 17-1-22.
 */

public interface BaseVM {
    /**
     * Please put your "new XxxVM()" before calling super.onCreate in your activity's onCreate method or you'll have to call the vm's onCreate method yourself.
     * @param savedInstanceState
     */
    void create(Bundle savedInstanceState);
    void restart();
    void start();
    void stop();
    void resume();
    void pause();
    void destroy();
    void back();
    void result(int reqCode, int resCode, Intent data);
    void configChanged(Configuration newConfig);
}
