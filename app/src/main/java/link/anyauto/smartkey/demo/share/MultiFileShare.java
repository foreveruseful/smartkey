package link.anyauto.smartkey.demo.share;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

import link.anyauto.smartkey.annotation.Code;
import link.anyauto.smartkey.annotation.Key;
import link.anyauto.smartkey.annotation.SmartIntent;

/**
 * 分享多个文件.
 */
@SmartIntent
public class MultiFileShare {
    /**
     * 发送邮件时的私密抄送邮件地址列表
     */
    @Key(Intent.EXTRA_BCC)
    public String[] bcc;

    /**
     * 发送邮件时的草送邮件地址列表
     */
    @Key(Intent.EXTRA_CC)
    public String[] cc;

    /**
     * 发送邮件时的收件人邮件地址列表
     */
    @Key(Intent.EXTRA_EMAIL)
    public String[] email;


    /**
     * 分享HTML格式文档时的内容,注意的是,还需要同时提供text用作兼容不能分享html文本的情况.
     */
    @Key(Intent.EXTRA_HTML_TEXT)
    public ArrayList<String> htmlText;

    /**
     * 分享单个文件时的文件内容Uri, 非必须,当没有文件需要分享时可以为空.
     */
    @Key(Intent.EXTRA_STREAM)
    @Code(get = "getParcelableArrayListExtra(%1$s)", set = "putParcelableArrayListExtra(%1$s, %2$s)")
    public ArrayList<Uri> uri;

    /**
     * 分享内容的主题
     */
    @Key(Intent.EXTRA_SUBJECT)
    public String subject;

    /**
     * 分享的文本内容,如果有HTML格式的,可以同时设置htmlText字段
     */
    @Key(Intent.EXTRA_TEXT)
    public ArrayList<String> text;

    /**
     * 当用户分享时应用选择列表的标题
     */
    @Key(Intent.EXTRA_TITLE)
    public CharSequence title;
}
