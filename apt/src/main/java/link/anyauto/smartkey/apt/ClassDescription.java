package link.anyauto.smartkey.apt;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ClassDescription {

    public TypeElement clz;
    public PackageElement pkg;
    public String pkgName;
    public String simpleClzName;
    public String clzName;
    public Set<ElementDescription> elements;

    public ClassDescription(TypeElement clz, Elements elems) {
        elements = new LinkedHashSet<>();
        this.clz = clz;
        pkg = elems.getPackageOf(clz);
        pkgName = pkg.getQualifiedName().toString();
        simpleClzName = clz.getSimpleName().toString();
        clzName = clz.getQualifiedName().toString();
        for (Element elem : clz.getEnclosedElements()) {
            if (ElementKind.FIELD.equals(elem.getKind()) && !(elem.getModifiers().contains(Modifier.STATIC)) && !(elem.getModifiers().contains(Modifier.FINAL))) {
                ElementDescription des = new ElementDescription((VariableElement) elem);
                elements.add(des);
            }
        }
    }
}
