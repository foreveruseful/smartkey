package link.anyauto.smartkey.apt;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;

import link.anyauto.smartkey.annotation.SmartManifest;
import link.anyauto.smartkey.annotation.SmartTarget;
import link.anyauto.smartkey.apt.manifestanalyzer.ManifestHandler;
import link.anyauto.smartkey.apt.manifestanalyzer.TargetApp;
import link.anyauto.smartkey.apt.manifestanalyzer.TargetDescriptor;

public class TargetGenerator extends Generator {

    TargetApp app;
    String packageName;
    TypeSpec.Builder smartTargets;
    HashMap<String, TypeSpec.Builder> types = new HashMap<>();
    ActivityHandler aHandler;
    ServiceHandler sHandler;

    public TargetGenerator(ProcessingEnvironment env) {
        super(env);
        smartClz = SmartTarget.class;
    }

    @Override
    public void handle(RoundEnvironment env) {
        super.handle(env);
        processApp();
        generateAll();
    }

    void processApp() {
        for (TargetDescriptor target : app.activities) {
            aHandler.addType(target);
        }
        for (TargetDescriptor target : app.activityAliases) {
            aHandler.addAliasType(target);
        }
        for (TargetDescriptor target : app.services) {
            sHandler.addType(target);
        }
    }

    @Override
    protected void genClass(ClassDescription des) {
        aHandler.addType(des);
    }

    @Override
    protected boolean prepareEnv(RoundEnvironment env) {
        super.prepareEnv(env);
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            ManifestHandler handler = new ManifestHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(new FileInputStream(env.getElementsAnnotatedWith(SmartManifest.class)
                    .iterator()
                    .next()
                    .getAnnotation(SmartManifest.class)
                    .manifestPath())));
            app = handler.getApp();
            packageName = handler.getPackageName();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        smartTargets = TypeSpec.classBuilder(ClassName.get(packageName + ".targets", "SmartTargets"))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        MethodSpec.Builder noteDeterminedActivity = MethodSpec.methodBuilder("toNotDeterminedActivityTarget")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClazzNames.NOT_DETERMINED_ACTIVITY_TARGET)
                .addStatement("return new $T()", ClazzNames.NOT_DETERMINED_ACTIVITY_TARGET);
        smartTargets.addMethod(noteDeterminedActivity.build());
        MethodSpec.Builder noteDeterminedService = MethodSpec.methodBuilder("toNotDeterminedServiceTarget")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClazzNames.NOT_DETERMINED_SERVICE_TARGET)
                .addStatement("return new $T()", ClazzNames.NOT_DETERMINED_SERVICE_TARGET);
        smartTargets.addMethod(noteDeterminedService.build());
        MethodSpec.Builder createResult = MethodSpec.methodBuilder("createBackResult")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClazzNames.BACK_RESULT)
                .addStatement("return BackResult.newBackResult()");
        smartTargets.addMethod(createResult.build());
        createResult = MethodSpec.methodBuilder("createBackResult")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.INT, "resultCode")
                .returns(ClazzNames.BACK_RESULT)
                .addStatement("return BackResult.newBackResult(resultCode)");
        smartTargets.addMethod(createResult.build());
        aHandler = new ActivityHandler(smartTargets, types);
        sHandler = new ServiceHandler(smartTargets, types);
        return true;
    }

    void generateAll() {
        try {
            JavaFile.builder(packageName + ".targets", smartTargets.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not write file " + packageName + ".targets.SmartTargets");
        }
        for (String key : types.keySet()) {
            TypeSpec.Builder type = types.get(key);
            String pkg = Util.getPackageName(key);
            TypeSpec spec = type.build();
            try {
                JavaFile.builder(pkg, spec).build().writeTo(filer);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not write file " + pkg + "." + spec.name);
            }
        }
    }
}