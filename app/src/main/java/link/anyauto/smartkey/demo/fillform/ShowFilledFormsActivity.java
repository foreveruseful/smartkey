package link.anyauto.smartkey.demo.fillform;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseActivity;
import link.anyauto.smartkey.demo.databinding.ContactsBinding;

/**
 * Created by discotek on 17-1-19.
 */

public class ShowFilledFormsActivity extends BaseActivity {

    ShowFilledFormsVM vm;
    ContactsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vm = new ShowFilledFormsVM();
        vm.setActivity(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.contacts);
        binding.setVm(vm);
    }

    @Override
    public ShowFilledFormsVM getVm() {
        return vm;
    }

    @Override
    public ContactsBinding getBinding() {
        return binding;
    }
}
