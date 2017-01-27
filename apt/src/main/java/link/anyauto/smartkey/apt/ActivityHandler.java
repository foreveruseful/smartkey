package link.anyauto.smartkey.apt;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.lang.model.element.Modifier;

import link.anyauto.smartkey.annotation.AnnotationConstants;
import link.anyauto.smartkey.annotation.SmartTarget;
import link.anyauto.smartkey.apt.manifestanalyzer.TargetDescriptor;
import link.anyauto.smartkey.apt.manifestanalyzer.TargetFilter;

public class ActivityHandler {

    TypeSpec.Builder smartTargets;
    HashMap<String, TypeSpec.Builder> types;

    public ActivityHandler(TypeSpec.Builder smartTargets, HashMap<String, TypeSpec.Builder> typesHolder) {
        this.smartTargets = smartTargets;
        types = typesHolder;
    }

    public void addType(ClassDescription des) {
        ClassName activityType = ClassName.get(des.pkgName, des.simpleClzName);
        ClassName builderName = ClassName.get(des.pkgName, des.simpleClzName + AnnotationConstants.SUFFIX_ACTIVITY_TARGET);
        addType(des.simpleClzName, des.clzName, activityType, builderName, des.clz.getAnnotation(SmartTarget.class).req());
    }

    public void addType(TargetDescriptor des) {
        TypeSpec.Builder type = types.get(des.name);
        if (type == null) {
            ClassName activityType = ClassName.bestGuess(des.name);
            ClassName builderName = ClassName.bestGuess(des.name + AnnotationConstants.SUFFIX_ACTIVITY_TARGET);
            addType(des.getSimpleName(), des.name, activityType, builderName, null);
        }
        if (des.filters.isEmpty() || des.filters.get(0).actions.isEmpty()) {
            return;
        }
        for (TargetFilter filter : des.filters) {
            for(String action : filter.actions) {
                if("android.intent.action.MAIN".equals(action))
                    continue;
                addGoActivityMethod(des.name,
                        "goWithAction_" + action.replace('.', '_').replace(" ", "").toUpperCase(),
                        action, filter.categories);
                addGoActivityMethod(des.name,
                        "goForResultWithAction_" + action.replace('.', '_').replace(" ", "").toUpperCase(),
                        action, filter.categories);
            }
        }
    }

    public void addAliasType(TargetDescriptor target) {
        if (target.filters.isEmpty() || target.filters.get(0).actions.isEmpty()) {
            return;
        }
        for (TargetFilter filter : target.filters) {
            for(String action : filter.actions) {
                addGoActivityMethod(target.name,
                        "goWithAlias_" + target.aliasName.toUpperCase() + "_AndAction_" + action.replace('.', '_').replace(" ", "").toUpperCase(),
                        action, filter.categories);
                addGoActivityMethod(target.name,
                        "goForResultWithAlias_" + target.aliasName.toUpperCase() + "_AndAction_" + action.replace('.', '_').replace(" ", "").toUpperCase(),
                        action, filter.categories);
            }
        }
    }

    void addGoActivityMethod(String targetClz, String methodName, String action, ArrayList<String> cats) {
        TypeSpec.Builder type = types.get(targetClz);
        MethodSpec.Builder actionMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T in = prepare(src)", ClazzNames.INTENT)
                .addStatement("in.setAction(\"" + action + "\")");
        for (String cat : cats) {
            actionMethod.addStatement("in.addCategory(\"" + cat + "\")");
        }
        if(methodName.contains("ForResult")) {
            actionMethod.addParameter(ClazzNames.ACTIVITY, "src")
                    .addParameter(ClassName.INT, "reqCode");
            actionMethod.addStatement("realGoForResult(src, in, reqCode)");
        } else {
            actionMethod.addParameter(ClazzNames.CONTEXT, "src");
            actionMethod.addStatement("realGo(src, in)");
        }
        type.addMethod(actionMethod.build());
    }

    void addType(String name, String forName, ClassName activityType, ClassName builderType, String param) {
        MethodSpec.Builder smartMethod = MethodSpec.methodBuilder("to" + Util.capFirstLetter(name)+ AnnotationConstants.SUFFIX_ACTIVITY_TARGET)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderType)
                .addStatement("return new $T()", builderType);
        smartTargets.addMethod(smartMethod.build());
        TypeSpec.Builder type = TypeSpec.classBuilder(builderType)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ParameterizedTypeName.get(ClazzNames.ACTIVITY_TARGET, (TypeName)builderType));
        if(param != null && !"".equals(param)) {
            String pkg = param.substring(0, param.lastIndexOf("."));
            String lName = param.substring(param.lastIndexOf(".") + 1);
            MethodSpec.Builder newMapperBuilder = MethodSpec.methodBuilder("newMapperBuilder")
                    .returns(ClassName.get(pkg, lName + AnnotationConstants.SUFFIX_INTENT_BUILDER))
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return $T.newBuilder()", ClassName.get(pkg, lName + AnnotationConstants.SUFFIX_INTENT_BUILDER));
            type.addMethod(newMapperBuilder.build());
        }
        MethodSpec.Builder clazz = MethodSpec.methodBuilder("getActivityClass")
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClazzNames.CLAZZ, activityType))
                .addStatement("return $T.class", activityType);
        type.addMethod(clazz.build());
        types.put(forName, type);
    }
}