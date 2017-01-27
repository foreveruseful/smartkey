package link.anyauto.smartkey.demo.forresult;

import android.app.Activity;
import android.databinding.Bindable;

import link.anyauto.smartkey.demo.BR;
import link.anyauto.smartkey.demo.base.BaseVMAdapter;
import link.anyauto.smartkey.demo.extras.ForResultReq;
import link.anyauto.smartkey.demo.extras.ForResultReqIBuilder;
import link.anyauto.smartkey.demo.extras.ForResultResIBuilder;
import link.anyauto.smartkey.sdks.targets.BackResult;


public class ForResultVM extends BaseVMAdapter {

    public ForResultVM(Activity activity) {
        setActivity(activity);
        // Getting values in this very easy way
        ForResultReq req = ForResultReqIBuilder.getSmart(activity.getIntent());

        title = req.title;
        label = req.label;
        hint = req.hint;
    }

    @Bindable
    private String input;
    @Bindable
    private String input2;

    public String title;

    public String label;

    public String hint;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        if(this.input == null || !this.input.equals(input)) {
            this.input = input;
            notifyPropertyChanged(BR.input);
        }
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        if(this.input2 == null || !this.input2.equals(input2)) {
            this.input2 = input2;
            notifyPropertyChanged(BR.input2);
        }
    }

    public void confirm() {
        // Yes, it is so easy to return values back to the caller.
        // No need to call set result, no need to put values into intent yourself.
        // The same as passing values to another activity when start.
        BackResult
                .newBackResult()
                .params(ForResultResIBuilder
                        .newBuilder()
                        .input(input)
                        .input2(input2))
                .finishWithResult(activity);
    }
}
