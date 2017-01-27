package link.anyauto.smartkey.demo.share;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseActivity;
import link.anyauto.smartkey.demo.databinding.ShareBinding;

/**
 * Created by discotek on 17-1-19.
 */

public class ShareActivity extends BaseActivity {

    ShareVM vm;
    ShareBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vm = new ShareVM(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.share);
        binding.setVm(vm);
    }

    @Override
    public ShareVM getVm() {
        return vm;
    }

    @Override
    public ShareBinding getBinding() {
        return binding;
    }
}
