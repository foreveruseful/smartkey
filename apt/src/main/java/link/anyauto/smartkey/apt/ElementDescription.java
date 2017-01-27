package link.anyauto.smartkey.apt;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

import link.anyauto.smartkey.annotation.Code;
import link.anyauto.smartkey.annotation.Key;

public class ElementDescription {
    public String key;
    public Code code;
    public VariableElement field;
    public TypeName fieldType;
    public String fieldName;

    public ElementDescription(VariableElement field) {
        this.field = field;
        key = field.getSimpleName().toString();
        Key keyAno = field.getAnnotation(Key.class);
        if(keyAno != null) {
            String annoValue = keyAno.value();
            if(annoValue != null && !"".equals(annoValue)) {
                key = annoValue;
            }
        }
        code = field.getAnnotation(Code.class);
        fieldType = ClassName.get(field.asType());
        fieldName = field.getSimpleName().toString();
    }

    public String getterCode() {
        if (code != null) {
            String get = code.get();
            if(!isEmpty(get)) {
                return get;
            }
        }
        return null;
    }

    public String setterCode() {
        if (code != null) {
            String set = code.set();
            if(!isEmpty(set)) {
                return set;
            }
        }
        return null;
    }

    boolean isEmpty(String src) {
        return src == null || src.length() == 0 || src.trim().length() == 0;
    }
}
