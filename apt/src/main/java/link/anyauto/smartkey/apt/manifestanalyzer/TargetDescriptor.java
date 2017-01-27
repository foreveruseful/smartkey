package link.anyauto.smartkey.apt.manifestanalyzer;

import java.util.ArrayList;

public class TargetDescriptor {
    public String name;
    public String aliasName;
    public ArrayList<TargetFilter> filters = new ArrayList<>();

    public String getSimpleName() {
        return name.substring(name.lastIndexOf('.') + 1);
    }
}