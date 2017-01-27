package link.anyauto.smartkey.apt;

import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Generator base class
 */

public abstract class Generator {
    protected Filer filer;
    protected Elements elements;
    protected Messager messager;
    protected List<ClassDescription> clzs;
    protected Class<? extends Annotation> smartClz;

    public Generator(ProcessingEnvironment env) {
        filer = env.getFiler();
        elements = env.getElementUtils();
        messager = env.getMessager();
        clzs = new LinkedList<>();
    }

    public void handle(RoundEnvironment env) {
        clzs.clear();
        if(!prepareEnv(env)) {
            return;
        }
        genCodes();
    }

    protected boolean prepareEnv(RoundEnvironment env) {
        for (Element elem : env.getElementsAnnotatedWith(smartClz)) {
            ClassDescription clzD = new ClassDescription((TypeElement) elem, elements);
            clzs.add(clzD);
        }
        return !clzs.isEmpty();
    }

    protected void genCodes() {
        for (ClassDescription des : clzs) {
            genClass(des);
        }
    }

    protected abstract void genClass(ClassDescription des);
}
