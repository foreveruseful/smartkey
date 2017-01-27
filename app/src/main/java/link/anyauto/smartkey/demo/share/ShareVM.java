package link.anyauto.smartkey.demo.share;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableArrayMap;
import android.net.Uri;

import java.util.ArrayList;

import link.anyauto.smartkey.demo.R;
import link.anyauto.smartkey.demo.base.BaseVMAdapter;
import link.anyauto.smartkey.demo.targets.SmartTargets;
import link.anyauto.smartkey.demo.util.ToastUtil;

/**
 * Created by discotek on 17-1-27.
 */

public class ShareVM extends BaseVMAdapter {

    static final int REQ_PICK_PIC = 1;
    int pickPosition;

    public ObservableArrayMap<Integer, Uri> pics = new ObservableArrayMap<>();

    public ShareVM(Activity activity) {
        setActivity(activity);
    }

    public void pick(int position) {
        SmartTargets.toNotDeterminedActivityTarget()
                .action(Intent.ACTION_GET_CONTENT)
                .mimeType("image/*")
                .goForResult(activity, REQ_PICK_PIC);
        pickPosition = position;
    }

    public void shareOne() {
        Uri uri = null;
        for(Uri u : pics.values()) {
            uri = u;
            break;
        }
        if(uri == null) {
            ToastUtil.toast(R.string.select_pics_please);
            return;
        }
        SmartTargets.toNotDeterminedActivityTarget()
                .action(Intent.ACTION_SEND)
                .params(SingleFileShareIBuilder
                        .newBuilder()
                        .uri(uri))
                .mimeType("image/*")
                .go(activity);
    }

    public void shareAll() {
        ArrayList<Uri> uris = new ArrayList<>(2);
        uris.addAll(pics.values());
        if(uris.size() > 0) {
            SmartTargets.toNotDeterminedActivityTarget()
                    .action(Intent.ACTION_SEND_MULTIPLE)
                    .params(MultiFileShareIBuilder
                            .newBuilder()
                            .uri(uris))
                    .mimeType("image/*")
                    .go(activity);
        } else {
            ToastUtil.toast(R.string.select_pics_please);
        }
    }

    @Override
    public void result(int reqCode, int resCode, Intent data) {
        if(reqCode == REQ_PICK_PIC && resCode == Activity.RESULT_OK) {
            pics.put(pickPosition, data.getData());
        }
    }
}
