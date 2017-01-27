package link.anyauto.smartkey.apt.manifestanalyzer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import link.anyauto.smartkey.annotation.AnnotationConstants;

public class ManifestHandler extends DefaultHandler {
    static final String MANIFEST = "manifest";
    static final String APP = "application";
    static final String ACT = AnnotationConstants.TARGET_TYPE_ACTIVITY;
    static final String ACT_ALIAS = AnnotationConstants.TARGET_TYPE_ACTIVITY_ALIAS;
    static final String SERVICE = AnnotationConstants.TARGET_TYPE_SERVICE;

    static final String FILTER = "intent-filter";
    static final String ACTION = "action";
    static final String CATEGORY = "category";

    String lastTag;
    String packageName;

    TargetApp app;

    public TargetApp getApp() {
        return app;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        switch (qName) {
            case MANIFEST:
                getPackageName(attributes);
                break;
            case APP:
                app = new TargetApp();
                break;
            case ACT:
                lastTag = qName;
                TargetDescriptor activity = new TargetDescriptor();
                app.activities.add(activity);
                parseName(attributes);
                break;
            case ACT_ALIAS:
                lastTag = qName;
                TargetDescriptor activityAlias = new TargetDescriptor();
                app.activityAliases.add(activityAlias);
                parseName(attributes);
                break;
            case SERVICE:
                lastTag = qName;
                TargetDescriptor service = new TargetDescriptor();
                app.services.add(service);
                parseName(attributes);
                break;

            case FILTER:
                createFilter();
                break;
            case ACTION:
                parseAction(attributes);
                break;
            case CATEGORY:
                parseCategory(attributes);
                break;

        }
    }

    void createFilter() {
        if(ACT.equals(lastTag)) {
            last(app.activities).filters.add(new TargetFilter());
        } else if(SERVICE.equals(lastTag)) {
            last(app.services).filters.add(new TargetFilter());
        } else if(ACT_ALIAS.equals(lastTag)) {
            last(app.activityAliases).filters.add(new TargetFilter());
        }
    }

    void parseCategory(Attributes attributes) {
        if(ACT.equals(lastTag)) {
            last(last(app.activities).filters).categories.add(getNameValue(attributes));
        } else if(SERVICE.equals(lastTag)) {
            last(last(app.services).filters).categories.add(getNameValue(attributes));
        } else if(ACT_ALIAS.equals(lastTag)) {
            last(last(app.activityAliases).filters).categories.add(getNameValue(attributes));
        }
    }

    void parseAction(Attributes attributes) {
        if(ACT.equals(lastTag)) {
            last(last(app.activities).filters).actions.add(getNameValue(attributes));
        } else if(SERVICE.equals(lastTag)) {
            last(last(app.services).filters).actions.add(getNameValue(attributes));
        } else if(ACT_ALIAS.equals(lastTag)) {
            last(last(app.activityAliases).filters).actions.add(getNameValue(attributes));
        }
    }

    void parseName(Attributes attributes) {
        TargetDescriptor td = null;
        if(ACT.equals(lastTag)) {
            td = last(app.activities);
            td.name = getNameValue(attributes);
        } else if(SERVICE.equals(lastTag)) {
            td = last(app.services);
            td.name = getNameValue(attributes);
        } else if(ACT_ALIAS.equals(lastTag)) {
            td = last(app.activityAliases);
            td.name = attributes.getValue("android:targetActivity");
            td.aliasName = getNameValue(attributes);
        }
        if(td != null && td.name.startsWith(".")) {
            td.name = packageName + td.name;
        }
    }

    void getPackageName(Attributes attributes) {
        packageName = attributes.getValue("package");
    }

    String getNameValue(Attributes attributes) {
        return attributes.getValue("android:name");
    }

    <T> T last(ArrayList<T> arr) {
        return arr.get(arr.size() - 1);
    }
}
