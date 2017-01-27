package link.anyauto.smartkey.demo.fillform;

import java.util.ArrayList;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.adapter.OneTypeItemSource;
import link.anyauto.smartkey.demo.adapter.SomethingClicked;
import link.anyauto.smartkey.demo.base.BaseVMAdapter;
import link.anyauto.smartkey.demo.objects.MyContact;
import link.anyauto.smartkey.demo.prefs.MyAppPreferencesSPBuilder;
import link.anyauto.smartkey.demo.util.ToastUtil;

/**
 * Created by discotek on 17-1-23.
 */

public class ShowFilledFormsVM extends BaseVMAdapter {
    public OneTypeItemSource<MyContact> source;
    public ShowFilledFormsVM() {
        source = new OneTypeItemSource<>();
        source.itemViewId = R.layout.contact_item;
        source.listeners = new SomethingClicked[]{new SomethingClicked() {
            @Override
            public void clicked(int position) {
                delete(position);
            }
        }};
        ArrayList<MyContact> contacts = MyAppPreferencesSPBuilder.contacts();
        if(contacts != null) {
            source.items.addAll(contacts);
        }
    }

    void delete(int position) {
        if(source.items != null && position >= 0 && position < source.items.size()) {
            source.items.remove(position);
            MyAppPreferencesSPBuilder.contacts(source.items);
        } else {
            ToastUtil.toast(R.string.out_of_range);
        }
    }
}
