package link.anyauto.smartkey.demo.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseActivity;
import link.anyauto.smartkey.demo.databinding.ActivityMainBinding;
import link.anyauto.smartkey.demo.prefs.MyAppPreferencesSPBuilder;

public class MainActivity extends BaseActivity {

    MainVM vm;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyAppPreferencesSPBuilder.openTimes(MyAppPreferencesSPBuilder.openTimes() + 1);
        vm = new MainVM();
        vm.setActivity(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(vm);
    }

    public MainVM getVm() {
        return vm;
    }

    @Override
    public ActivityMainBinding getBinding() {
        return binding;
    }
}
