package link.anyauto.smartkey.apt;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;

import javax.lang.model.element.Modifier;

import link.anyauto.smartkey.annotation.AnnotationConstants;
import link.anyauto.smartkey.apt.manifestanalyzer.TargetDescriptor;

public class ServiceHandler {
    TypeSpec.Builder smartTargets;
    HashMap<String, TypeSpec.Builder> types;
    public ServiceHandler(TypeSpec.Builder smartTargets, HashMap<String, TypeSpec.Builder> typesHolder) {
        this.smartTargets = smartTargets;
        types = typesHolder;
    }

    public void addType(TargetDescriptor des) {
        ClassName serviceType = ClassName.bestGuess(des.name);
        ClassName builderName = ClassName.bestGuess(des.name + AnnotationConstants.SUFFIX_SERVICE_TARGET);
        addType(des.getSimpleName(), des.name, serviceType, builderName, null);
    }

    void addType(String name, String forName, ClassName serviceType, ClassName builderType, String param) {
        MethodSpec.Builder smartMethod = MethodSpec.methodBuilder("to" + Util.capFirstLetter(name)+ AnnotationConstants.SUFFIX_SERVICE_TARGET)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderType)
                .addStatement("return new $T()", builderType);
        smartTargets.addMethod(smartMethod.build());
        TypeSpec.Builder type = TypeSpec.classBuilder(builderType)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ParameterizedTypeName.get(ClazzNames.SERVICE_TARGET, (TypeName)builderType));
        MethodSpec.Builder clazz = MethodSpec.methodBuilder("getServiceClass")
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClazzNames.CLAZZ, serviceType))
                .addStatement("return $T.class", serviceType);
        type.addMethod(clazz.build());
        types.put(forName, type);
    }
}
