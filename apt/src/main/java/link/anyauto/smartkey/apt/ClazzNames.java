package link.anyauto.smartkey.apt;

import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.Set;

public class ClazzNames {

    public static final ClassName APPLICATION = ClassName.get("android.app", "Application");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName CLAZZ = ClassName.get("java.lang", "Class");
    public static final ClassName INTENT = ClassName.get("android.content", "Intent");
    public static final ClassName BUNDLE = ClassName.get("android.os", "Bundle");
    public static final ClassName INTENT_KEY_MAPPER = ClassName.get("link.anyauto.smartkey.sdks", "IntentKeyMapper");
    public static final ClassName INTENT_KEY_MAPPER_BASE = ClassName.get("link.anyauto.smartkey.sdks", "BaseIntentKeyMapper");
    public static final ClassName ARRAYLIST = ClassName.get("java.util", "ArrayList");
    public static final ClassName SET = ClassName.get("java.util", "Set");

    public static final TypeName BOOLEAN = ClassName.get("java.lang", "Boolean");
    public static final TypeName BYTE = ClassName.get("java.lang", "Byte");
    public static final TypeName SHORT = ClassName.get("java.lang", "Short");
    public static final TypeName CHARACTER = ClassName.get("java.lang", "Character");
    public static final TypeName INTEGER = ClassName.get("java.lang", "Integer");
    public static final TypeName LONG = ClassName.get("java.lang", "Long");
    public static final TypeName FLOAT = ClassName.get("java.lang", "Float");
    public static final TypeName DOUBLE = ClassName.get("java.lang", "Double");
    public static final Set<TypeName> WRAPPERS = Sets.newHashSet(BOOLEAN, BYTE, SHORT, CHARACTER, INTEGER, LONG, FLOAT, DOUBLE);

    public static final TypeName STRING = ClassName.get("java.lang", "String");
    public static final TypeName STRING_SET = ParameterizedTypeName.get(SET, STRING);
    public static final TypeName CHARSEQUENCE = ClassName.get("java.lang", "CharSequence");
    public static final TypeName PARCELABLE = ClassName.get("android.os", "Parcelable");
    public static final TypeName URI = ClassName.get("android.net", "Uri");


    public static final ParameterizedTypeName PARCELABLE_ARRAYLIST = ParameterizedTypeName.get(ARRAYLIST, PARCELABLE);
    public static final ParameterizedTypeName URI_ARRAYLIST = ParameterizedTypeName.get(ARRAYLIST, URI);
    public static final ParameterizedTypeName INTEGER_ARRAYLIST = ParameterizedTypeName.get(ARRAYLIST, INTEGER);
    public static final ParameterizedTypeName STRING_ARRAYLIST = ParameterizedTypeName.get(ARRAYLIST, STRING);
    public static final ParameterizedTypeName CHARSEQUENCE_ARRAYLIST = ParameterizedTypeName.get(ARRAYLIST, CHARSEQUENCE);

    public static final TypeName INTENT_VALUE_GETTER = ClassName.get("link.anyauto.smartkey.sdks", "IntentValueGetter");
    public static final TypeName STORAGE_HELPER = ClassName.get("link.anyauto.smartkey.sdks", "StorageHelper");
    public static final TypeName GSON_HELPER = ClassName.get("link.anyauto.smartkey.sdks", "GsonHelper");

    public static final TypeName TYPE_TOKEN = ClassName.get("com.google.gson.reflect", "TypeToken");

    public static final ClassName ACTIVITY_TARGET = ClassName.get("link.anyauto.smartkey.sdks.targets", "ActivityTarget");
    public static final ClassName SERVICE_TARGET = ClassName.get("link.anyauto.smartkey.sdks.targets", "ServiceTarget");
    public static final ClassName NOT_DETERMINED_ACTIVITY_TARGET = ClassName.get("link.anyauto.smartkey.sdks.targets", "NotDeterminedActivityTarget");
    public static final ClassName NOT_DETERMINED_SERVICE_TARGET = ClassName.get("link.anyauto.smartkey.sdks.targets", "NotDeterminedServiceTarget");
    public static final ClassName BACK_RESULT = ClassName.get("link.anyauto.smartkey.sdks.targets", "BackResult");
}
