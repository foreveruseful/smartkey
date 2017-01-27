package link.anyauto.smartkey.demo.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by discotek on 17-1-22.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getVm() != null) {
            getVm().create(savedInstanceState);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(getVm() != null) {
            getVm().restart();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getVm() != null) {
            getVm().start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(getVm() != null) {
            getVm().stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getVm() != null) {
            getVm().resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getVm() != null) {
            getVm().pause();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if(getVm() != null) {
            getVm().destroy();
        }
        if(getBinding() != null) {
            getBinding().unbind();
        }
    }

    @Override
    public void onBackPressed() {
        if(getVm() != null) {
            getVm().back();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(getVm() != null) {
            getVm().result(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getVm() != null) {
            getVm().configChanged(newConfig);
        }
    }

    protected BaseVM getVm() {
        return null;
    }

    protected ViewDataBinding getBinding() {
        return null;
    }
}
