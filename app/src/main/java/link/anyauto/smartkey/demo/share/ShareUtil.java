package link.anyauto.smartkey.demo.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * <pre>
 *    分享内容工具类,能满足大部分时候的分享要求,
 *    但是有个特殊的要求这种方法没法满足,
 *    这个要求就是分享内容带的尾巴,比如分享到微博后,下面有个"来自xxx",这个几个字,
 *    因为这些尾巴要求你的应用先向相关平台申请app key, secret之类的一大堆东西,
 *    而这个分享类是万能通用的,不要求任何key,能分享到任何平台,
 *    硬是要加的话,只好自己写到内容里面去.
 * </pre>
 */

public class ShareUtil {

    /**
     * 分享内容,最多包含1个文件
     * @param sfs
     *  分享的内容
     * @param mimeType
     *  分享内容的类型,比如text/plain, image/png ... ...
     * @param startingPoint
     *  发起分享的上下文对象,可以是Activity, ApplicationContext等.
     * @param menuTitle
     *  分享到应用选择菜单标题
     */
    public static void shareSingle(SingleFileShare sfs, String mimeType, Context startingPoint, String menuTitle) {
//        Intent in = SingleFileShareIBuilder.newBuilder().replaceSmart(sfs).buildIntent();
//        in.setAction(Intent.ACTION_SEND);
//        share(in, mimeType, startingPoint, menuTitle);
    }

    /**
     * <pre>
     *  分享内容,允许分享多个文件,多个文件时,需要提供多个文件的uri,
     *  多个文件可以分别有各自的文本描述等,text字段按uri顺序对应.
     * </pre>
     * @param mfs
     *  分享的内容
     * @param mimeType
     *  分享内容的类型,比如text/plain, image/png,
     *  如果分享的多个文件的类型不同,请使用最大可能的公共类型,
     *  比如如果有image/png, image/jpg,请使用image/*,
     *  或者最特殊的情况下,使用*\/*.
     * @param startingPoint
     *  发起分享的上下文对象,可以是Activity, ApplicationContext等.
     * @param menuTitle
     *  分享到应用选择菜单标题
     */
    public static void shareMultiple(MultiFileShare mfs, String mimeType, Context startingPoint, String menuTitle) {
//        Intent in = MultiFileShareIBuilder.newBuilder().replaceSmart(mfs).buildIntent();
//        in.setAction(Intent.ACTION_SEND_MULTIPLE);
//        share(in, mimeType, startingPoint, menuTitle);
    }

    static void share(Intent in, String mimeType, Context startingPoint, String menuTitle) {
        in.setType(TextUtils.isEmpty(mimeType) ? "*/*" : mimeType);
        in.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(!(startingPoint instanceof Activity)) {
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startingPoint.startActivity(Intent.createChooser(in, menuTitle));
    }

}