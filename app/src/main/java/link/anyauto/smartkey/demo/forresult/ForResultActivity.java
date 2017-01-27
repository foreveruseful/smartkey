package link.anyauto.smartkey.demo.forresult;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseActivity;
import link.anyauto.smartkey.demo.databinding.ForResultBinding;

public class ForResultActivity extends BaseActivity {
    ForResultVM vm;
    ForResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vm = new ForResultVM(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.for_result);
        binding.setVm(vm);
    }

    public ForResultVM getVm() {
        return vm;
    }

    @Override
    public ForResultBinding getBinding() {
        return binding;
    }
}
