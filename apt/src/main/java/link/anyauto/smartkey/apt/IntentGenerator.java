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
import link.anyauto.smartkey.annotation.SmartIntent;

public class IntentGenerator extends Generator {

    public IntentGenerator(ProcessingEnvironment environment) {
        super(environment);
        smartClz = SmartIntent.class;
    }

    protected void genClass(ClassDescription des) {
        ClassName builderName = ClassName.get(des.pkgName, des.simpleClzName + AnnotationConstants.SUFFIX_INTENT_BUILDER);
        ClassName smartName = ClassName.get(des.pkgName, des.simpleClzName);
        FieldSpec smartField = FieldSpec.builder(smartName, "smart")
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec defaultConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("smart = new $T()", smartName)
                .build();

        MethodSpec.Builder intent = MethodSpec.methodBuilder("buildIntent")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ClazzNames.INTENT)
                .addStatement("Intent in = new Intent()");

        MethodSpec newBuilder = MethodSpec.methodBuilder("newBuilder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderName)
                .addStatement("return new $T()", builderName).build();

        MethodSpec newBuilderWithSmart = MethodSpec.methodBuilder("newBuilder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderName)
                .addParameter(smartName, "smart")
                .addStatement("return new $T().replaceSmart(smart)", builderName).build();

        MethodSpec buildBundle = MethodSpec.methodBuilder("buildBundle")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClazzNames.BUNDLE)
                .addStatement("return buildIntent().getExtras()").build();

        MethodSpec fillBundle = MethodSpec.methodBuilder("fillFromSource")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClazzNames.BUNDLE, "source")
                .returns(builderName)
                .addCode("if (source == null) { return this; }\n")
                .addCode("return fillFromSource(new Intent().putExtras(source));").build();

        MethodSpec getSmartBundle = MethodSpec.methodBuilder("getSmart")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(smartName)
                .addParameter(ClazzNames.BUNDLE, "source")
                .addStatement("return new $T().fillFromSource(source).getSmart()", builderName).build();

        MethodSpec getSmartIntent = MethodSpec.methodBuilder("getSmart")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(smartName)
                .addParameter(ClazzNames.INTENT, "source")
                .addStatement("return new $T().fillFromSource(source).getSmart()", builderName)
                .build();

        MethodSpec getSmart = MethodSpec.methodBuilder("getSmart")
                .addModifiers(Modifier.PUBLIC)
                .returns(smartName)
                .addStatement("return smart")
                .build();

        MethodSpec replaceSmart = MethodSpec.methodBuilder("replaceSmart")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(smartName, "smart")
                .returns(builderName)
                .addStatement("this.smart = smart")
                .addStatement("return this")
                .build();

        MethodSpec.Builder fill = MethodSpec.methodBuilder("fillFromSource")
                .addParameter(ClazzNames.INTENT, "source")
                .addModifiers(Modifier.PUBLIC)
                .returns(builderName)
                .addCode("if (source == null) { return this; }\n\n");

        List<MethodSpec> methods = new LinkedList<>();

        for (ElementDescription elem : des.elements) {
            TypeName fieldType = elem.fieldType;
            MethodSpec.Builder builder = MethodSpec.methodBuilder(elem.fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(builderName)
                    .addParameter(fieldType, "value")
                    .addStatement("smart." + elem.fieldName + " = value")
                    .addStatement("return this");
                    methods.add(builder.build());

            processTypes(elem, intent, fill);
        }

        intent.addStatement("return in");
        fill.addStatement("return this");

        TypeSpec type = TypeSpec.classBuilder(builderName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ClazzNames.INTENT_KEY_MAPPER)
                .addField(smartField)
                .addMethod(defaultConstructor)
                .addMethod(newBuilder)
                .addMethod(newBuilderWithSmart)
                .addMethod(replaceSmart)
                .addMethod(getSmartIntent)
                .addMethod(getSmartBundle)
                .addMethod(intent.build())
                .addMethod(buildBundle)
                .addMethod(fill.build())
                .addMethod(fillBundle)
                .addMethod(getSmart)
                .addMethods(methods)
                .build();

        try {
            JavaFile.builder(des.pkgName, type).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not write file " + des.clzName + AnnotationConstants.SUFFIX_INTENT_BUILDER);
        }
    }

    void processTypes(ElementDescription elem, MethodSpec.Builder intent, MethodSpec.Builder fill) {
        String left = "smart." + elem.fieldName;
        String right = left + ")";

        String key = elem.key;
        TypeName fieldType = elem.fieldType;
        String set = elem.setterCode();
        String get = elem.getterCode();

        if(Util.textNotNull(set) && Util.textNotNull(get)) {
            set = String.format(set, "\"" + elem.key + "\"", left);
            get = String.format(get, "\"" + elem.key + "\"");
            intent.addStatement("in." + set);
            fill.addStatement(left + " = source." + get);
        } else if(fieldType.equals(ClazzNames.PARCELABLE_ARRAYLIST)) {
            intent.addStatement("in.putParcelableArrayListExtra(\"" + key +"\", " + right);
            fill.addStatement(left + " = source.getParcelableArrayListExtra(\"" + key + "\")");
        } else if(fieldType.equals(ClazzNames.INTEGER_ARRAYLIST)) {
            intent.addStatement("in.putIntegerArrayListExtra(\"" + key + "\", " + right);
            fill.addStatement(left + " = source.getIntegerArrayListExtra(\"" + key + "\")");
        } else if(fieldType.equals(ClazzNames.STRING_ARRAYLIST)) {
            intent.addStatement("in.putStringArrayListExtra(\"" + key + "\", " + right);
            fill.addStatement(left + " = source.getStringArrayListExtra(\"" + key + "\")");
        } else if(fieldType.equals(ClazzNames.CHARSEQUENCE_ARRAYLIST)) {
            intent.addStatement("in.putCharSequenceArrayListExtra(\"" + key + "\", " + right);
            fill.addStatement(left + " = source.getCharSequenceArrayListExtra(\"" + key + "\")");
        } else {
            intent.addStatement("in.putExtra(\"" + key + "\", " + right);
            processNonSetTypes(elem, fill);
        }
    }

    void processNonSetTypes(ElementDescription elem, MethodSpec.Builder fill) {
        TypeName fieldType = elem.fieldType;
        String key = elem.key;
        String left = "smart." + elem.fieldName;
        if(Util.isWrapper(fieldType)) { // 包装类型需要使用Serializable才能保持null值的正确传递
            fill.addStatement(left + " = (" + elem.fieldType + ") source.getSerializableExtra(\"" + key + "\")");
        } else if(fieldType.equals(TypeName.BOOLEAN)) {
            fill.addStatement(left + " = source.getBooleanExtra(\"" + key + "\", false)");
        } else if(fieldType.equals(TypeName.BYTE)) {
            fill.addStatement(left + " = source.getByteExtra(\"" + key + "\", (byte) 0)");
        } else if(fieldType.equals(TypeName.CHAR)) {
            fill.addStatement(left + " = source.getCharExtra(\"" + key + "\", '\\0')");
        } else if(fieldType.equals(TypeName.SHORT)) {
            fill.addStatement(left + " = source.getShortExtra(\"" + key + "\", (short) 0)");
        } else if(fieldType.equals(TypeName.INT)) {
            fill.addStatement(left + " = source.getIntExtra(\"" + key + "\", 0)");
        } else if(fieldType.equals(TypeName.LONG)) {
            fill.addStatement(left + " = source.getLongExtra(\"" + key + "\", 0)");
        } else if(fieldType.equals(TypeName.FLOAT)) {
            fill.addStatement(left + " = source.getFloatExtra(\"" + key + "\", 0.0f)");
        } else if(fieldType.equals(TypeName.DOUBLE)) {
            fill.addStatement(left + " = source.getDoubleExtra(\"" + key + "\", 0.0)");
        } else if(fieldType.equals(ClazzNames.STRING)) {
            fill.addStatement(left + " = source.getStringExtra(\"" + key + "\")");
        } else if(fieldType.equals(ClazzNames.CHARSEQUENCE)) {
            fill.addStatement(left + " = source.getCharSequenceExtra(\"" + key + "\")");
        } else {
            fill.addStatement(left + " = ($T) $T.getValue(source, \"" + key + "\", " + fieldType.toString() + ".class)", fieldType, ClazzNames.INTENT_VALUE_GETTER);
        }
    }
}
