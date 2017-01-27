package link.anyauto.smartkey.demo.util;

import android.view.LayoutInflater;
import android.widget.Toast;

import link.anyauto.smartkey.demo.App;
import link.anyauto.smartkey.demo.databinding.ToastLayoutBinding;

/**
 * Created by discotek on 17-1-22.
 */

public class ToastUtil {
    static Toast toast;
    static ToastLayoutBinding binding;

    public static void toast(int resId) {
        binding.toast.setText(resId);
        toast.show();
    }

    public static void toast(String str) {
        binding.toast.setText(str);
        toast.show();
    }

    static void makeToast() {
        if(toast == null) {
            toast = new Toast(App.getInstance());
            binding = ToastLayoutBinding.inflate(LayoutInflater.from(App.getInstance()));
            toast.setView(binding.getRoot());
            toast.setDuration(Toast.LENGTH_LONG);
        }
    }

    static {
        makeToast();
    }
}
