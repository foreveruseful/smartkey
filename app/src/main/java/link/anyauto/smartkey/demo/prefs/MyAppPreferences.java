package link.anyauto.smartkey.demo.prefs;

import java.util.ArrayList;

import link.anyauto.smartkey.annotation.Code;
import link.anyauto.smartkey.annotation.SmartSharedPreferences;
import link.anyauto.smartkey.demo.objects.MyContact;
import link.anyauto.smartkey.demo.objects.MyObject;

/**
 * Created by discotek on 17-1-8.
 */

@SmartSharedPreferences
public class MyAppPreferences {

    public int openTimes;

    public String lastVersion;

    public String selectedCity;

    public MyObject myObject;

    @Code(isGeneric = true, genericTypes = {"$T<$T>", "java.util.ArrayList", "link.anyauto.smartkey.demo.objects.MyContact"})
    public ArrayList<MyContact> contacts;

}
