package link.anyauto.smartkey.apt;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

import link.anyauto.smartkey.annotation.AnnotationConstants;
import link.anyauto.smartkey.annotation.SmartSharedPreferences;

/**
 * <pre>
 *  Generate helper classes and methods for manipulating shared preferences.
 *  Recommend way:
 *      Write only one class describing all data needed for the whole application,
 *      then read and write anything through the generated class' helper methods.
 * </pre>
 */
public class SharePreferenceGenerator extends Generator {
    public SharePreferenceGenerator(ProcessingEnvironment env) {
        super(env);
        smartClz = SmartSharedPreferences.class;
    }

    @Override
    protected void genClass(ClassDescription des) {
        String clzName = des.simpleClzName + AnnotationConstants.SUFFIX_SHARED_PREFERENCES_BUILDER;
        ClassName builderName = ClassName.get(des.pkgName, clzName);

        ClassName app = ClazzNames.APPLICATION;
        FieldSpec appField = FieldSpec.builder(app, "app")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        MethodSpec setApp = MethodSpec.methodBuilder("setApp")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(app, "app")
                .addStatement(clzName + ".app = app").build();

        MethodSpec getApp = MethodSpec.methodBuilder("getApp")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(app)
                .addStatement("return app")
                .build();

        List<MethodSpec> methods = new LinkedList<>();
        for (ElementDescription elem : des.elements) {
            MethodSpec.Builder get = MethodSpec.methodBuilder(elem.fieldName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(elem.fieldType);
            MethodSpec.Builder save = MethodSpec.methodBuilder(elem.fieldName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(elem.fieldType, "value");
            processSave(elem, save);
            processGet(elem, get);
            methods.add(get.build());
            methods.add(save.build());
        }

        TypeSpec type = TypeSpec.classBuilder(builderName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(appField)
                .addMethod(setApp)
                .addMethod(getApp)
                .addMethods(methods)
                .build();

        try {
            JavaFile.builder(des.pkgName, type)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not write file " + des.clzName + AnnotationConstants.SUFFIX_SHARED_PREFERENCES_BUILDER);
        }
    }

    void processGet(ElementDescription elem, MethodSpec.Builder builder) {
        if(elem.code != null && elem.code.isGeneric()) {
            String[] types = elem.code.genericTypes();
            Object[] args = new Object[types.length + 2];
            args[0] = ClazzNames.GSON_HELPER;
            args[1] = ClazzNames.STORAGE_HELPER;
            args[2] = ClazzNames.TYPE_TOKEN;
            for (int i=3; i<args.length; i++) {
                args[i] = ClassName.bestGuess(types[i-2]);
            }
            builder.addStatement("return $T.fromJson($T.get(app, \""
                    + elem.key + "\"), new $T<" + elem.code.genericTypes()[0]+">(){}.getType())",
                    args);
        } else if(isBool(elem)) {
            builder.addStatement("return $T.getBoolean(app, \"" + elem.key + "\")", ClazzNames.STORAGE_HELPER);
        } else if(isFloat(elem)) {
            builder.addStatement("return $T.getFloat(app, \"" + elem.key + "\")", ClazzNames.STORAGE_HELPER);
        } else if(isInt(elem)) {
            builder.addStatement("return $T.getInt(app, \"" + elem.key + "\")", ClazzNames.STORAGE_HELPER);
        } else if(isLong(elem)) {
            builder.addStatement("return $T.getLong(app, \"" + elem.key + "\")", ClazzNames.STORAGE_HELPER);
        } else if(isString(elem)) {
            builder.addStatement("return $T.get(app, \"" + elem.key + "\")", ClazzNames.STORAGE_HELPER);
        } else if(isStringSet(elem)) {
            builder.addStatement("return $T.getStringSet(app, \"" + elem.key + "\")", ClazzNames.STORAGE_HELPER);
        } else {
            builder.addStatement("return $T.fromJson($T.get(app, \""
                    + elem.key + "\"), " + elem.fieldType.toString()+".class)",
                    ClazzNames.GSON_HELPER,
                    ClazzNames.STORAGE_HELPER);
        }
    }

    void processSave(ElementDescription elem, MethodSpec.Builder builder) {
        if(isRaw(elem)) {
            builder.addStatement("$T.save(app, \"" + elem.key + "\", ($T)value)", ClazzNames.STORAGE_HELPER, elem.fieldType);
        } else {
            builder.addStatement("$T.save(app, \"" + elem.key + "\", $T.toJson(value))", ClazzNames.STORAGE_HELPER, ClazzNames.GSON_HELPER);
        }
    }

    boolean isRaw(ElementDescription elem) {
        return isBool(elem)
                || isFloat(elem)
                || isInt(elem)
                || isLong(elem)
                || isString(elem)
                || isStringSet(elem);
    }

    boolean isBool(ElementDescription elem) {
        return elem.fieldType.equals(ClassName.BOOLEAN);
    }

    boolean isFloat(ElementDescription elem) {
        return elem.fieldType.equals(ClassName.FLOAT);
    }

    boolean isString(ElementDescription elem) {
        return elem.fieldType.equals(ClazzNames.STRING);
    }

    boolean isInt(ElementDescription elem) {
        return elem.fieldType.equals(ClassName.INT);
    }

    boolean isLong(ElementDescription elem) {
        return elem.fieldType.equals(ClassName.LONG);
    }

    boolean isStringSet(ElementDescription elem) {
        return elem.fieldType.equals(ClazzNames.STRING_SET);
    }
}
