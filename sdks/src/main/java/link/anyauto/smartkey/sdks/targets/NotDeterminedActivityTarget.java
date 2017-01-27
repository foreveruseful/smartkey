package link.anyauto.smartkey.sdks.targets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * <pre>
 *  For activity targets that are not determined.
 *  For example, the class of the target activity is from a config file or some where else.
 * </pre>
 * Created by LYQ on 17-1-17.
 */

public class NotDeterminedActivityTarget extends ActivityTarget<NotDeterminedActivityTarget> {

    Class<? extends Activity> clz;
    String action;
    ArrayList<String> categories = new ArrayList<>();

    @Override
    protected Class<? extends Activity> getActivityClass() {
        return clz;
    }

    public NotDeterminedActivityTarget activityClass(Class<? extends Activity> clz) {
        this.clz = clz;
        return this;
    }

    public NotDeterminedActivityTarget action(String action) {
        this.action = action;
        return this;
    }

    public NotDeterminedActivityTarget addCategory(String category) {
        categories.add(category);
        return this;
    }

    public NotDeterminedActivityTarget addCategories(ArrayList<String> categories) {
        this.categories.addAll(categories);
        return this;
    }

    public NotDeterminedActivityTarget clearCategories() {
        categories.clear();
        return this;
    }

    @Override
    public void go(Context src) {
        if(clz != null) {
            super.go(src);
        } else if(action != null) {
            src.startActivity(addActionAndCats(src));
        }
    }

    @Override
    public void goForResult(Activity src, int reqCode) {
        if(clz != null) {
            super.goForResult(src, reqCode);
        } else if(action != null) {
            src.startActivityForResult(addActionAndCats(src), reqCode);
        }
    }

    Intent addActionAndCats(Context src) {
        Intent in = prepare(src);
        in.setAction(action);
        for (String cat : categories) {
            in.addCategory(cat);
        }
        return in;
    }
}
