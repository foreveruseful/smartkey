package link.anyauto.smartkey.demo.fillform;

import android.app.Activity;
import android.databinding.ObservableField;
import android.text.TextUtils;

import java.util.ArrayList;

import link.anyauto.smartkey.demo.App;
import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseVMAdapter;
import link.anyauto.smartkey.demo.objects.MyContact;
import link.anyauto.smartkey.demo.prefs.MyAppPreferencesSPBuilder;
import link.anyauto.smartkey.demo.targets.SmartTargets;
import link.anyauto.smartkey.demo.util.ToastUtil;

/**
 * Created by discotek on 17-1-23.
 */

public class FillFormVM extends BaseVMAdapter {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> mobile = new ObservableField<>();
    public ObservableField<String> addr = new ObservableField<>();
    public ObservableField<String> counts = new ObservableField<>();

    ArrayList<MyContact> contacts;

    public FillFormVM(Activity activity) {
        setActivity(activity);
        updateCounts();
    }

    public void viewContacts() {
        SmartTargets.toShowFilledFormsActivityATarget().go(activity);
    }

    public void confirm() {
        if(saveContact()) {
            viewContacts();
        } else {
            ToastUtil.toast(R.string.input_name_mobile_tips);
        }
    }

    @Override
    public void resume() {
        updateCounts();
    }

    boolean saveContact() {
        if(TextUtils.isEmpty(name.get()) || TextUtils.isEmpty(mobile.get())) {
            return false;
        }
        MyContact contact = new MyContact();
        contact.name = name.get();
        contact.mobile = mobile.get();
        contact.addr = addr.get();
        contacts.add(contact);
        MyAppPreferencesSPBuilder.contacts(contacts);
        updateCounts();
        return true;
    }

    void updateCounts() {
        contacts = MyAppPreferencesSPBuilder.contacts();
        if(contacts == null) {
            contacts = new ArrayList<>();
        }
        counts.set(App.getStr(R.string.contacts_total_label, contacts.size()));
    }
}
