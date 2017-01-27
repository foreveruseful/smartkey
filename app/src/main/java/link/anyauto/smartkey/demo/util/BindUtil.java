package link.anyauto.smartkey.demo.util;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.adapter.OneTypeItemSource;
import link.anyauto.smartkey.demo.adapter.OneTypeItemSourceListAdapter;

/**
 * Created by discotek on 17-1-24.
 */

public class BindUtil {
    @BindingAdapter("oneTypeSource")
    public static void setOneTypeSource(ListView view, OneTypeItemSource source) {
        OneTypeItemSourceListAdapter adapter = new OneTypeItemSourceListAdapter(source, LayoutInflater.from(view.getContext()));
        view.setAdapter(adapter);
    }

    @BindingAdapter("emptyView")
    public static void setEmptyView(ListView view, int resId) {
        view.setEmptyView(((View) view.getParent()).findViewById(resId));
    }

    @BindingAdapter("imgUri")
    public static void setImgUri(ImageView view, Uri uri) {
        try {
            view.setImageURI(uri);
        } catch (OutOfMemoryError error) {
            ToastUtil.toast(R.string.pic_too_large);
        }
    }
}
