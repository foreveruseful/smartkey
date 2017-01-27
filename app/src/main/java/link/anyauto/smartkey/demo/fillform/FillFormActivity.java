package link.anyauto.smartkey.demo.fillform;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseActivity;
import link.anyauto.smartkey.demo.databinding.FillFormBinding;

/**
 * Created by discotek on 17-1-19.
 */

public class FillFormActivity extends BaseActivity {

    FillFormVM vm;
    FillFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vm = new FillFormVM(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fill_form);
        binding.setVm(vm);
    }

    @Override
    public FillFormVM getVm() {
        return vm;
    }

    @Override
    public FillFormBinding getBinding() {
        return binding;
    }
}
