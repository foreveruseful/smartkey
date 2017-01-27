package link.anyauto.smartkey.apt;


import com.google.auto.service.AutoService;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import link.anyauto.smartkey.annotation.SmartIntent;
import link.anyauto.smartkey.annotation.SmartManifest;
import link.anyauto.smartkey.annotation.SmartSharedPreferences;
import link.anyauto.smartkey.annotation.SmartTarget;

@AutoService(Processor.class)
public class Apt extends AbstractProcessor {

    Filer filer;
    Messager messager;
    Generator intentGenerator;
    Generator spGenerator;
    protected Generator targetGenerator;

    public static boolean processed = false;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();

        intentGenerator = new IntentGenerator(processingEnvironment);
        spGenerator = new SharePreferenceGenerator(processingEnvironment);
        targetGenerator = new TargetGenerator(processingEnvironment);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(SmartIntent.class.getCanonicalName());
        types.add(SmartSharedPreferences.class.getCanonicalName());
        types.add(SmartTarget.class.getCanonicalName());
        types.add(SmartManifest.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
       if(processed)
            return true;
        processed = true;
        try {
            intentGenerator.handle(roundEnvironment);
            spGenerator.handle(roundEnvironment);
            if(hasSmartManifest(roundEnvironment)) {
                targetGenerator.handle(roundEnvironment);
            }
        } catch (Throwable e){
            try {
                FileOutputStream fos = new FileOutputStream("/home/discotek/error.txt");
                PrintWriter pw = new PrintWriter(fos);
                e.printStackTrace(pw);
                pw.flush();
                pw.close();
            } catch (Exception e1){}
            throw new RuntimeException(e);
        }
        return true;
    }

    boolean hasSmartManifest(RoundEnvironment roundEnvironment) {
        Set<? extends Element> manifests = roundEnvironment.getElementsAnnotatedWith(SmartManifest.class);
        if(manifests == null || manifests.isEmpty()) {
            return false;
        }
        if(manifests.size() > 1) {
            throw new Error("too many @SmartManifest, only one is allowed: \n" +manifests);
        }
        return manifests.iterator().next().getAnnotation(SmartManifest.class).manifestPath() != null;
    }
}
