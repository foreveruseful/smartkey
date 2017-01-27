package link.anyauto.smartkey.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  Used for generating SmartTargets class for the module.
 *  This annotation can only apply at most once in a module.
 *  Although it can be placed anywhere in a module, it is recommended to place it in your Application.
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SmartManifest {
    /**
     * <pre>
     * The path of your AndroidManifest file.
     * It is a relative path starting from your project root.
     * Like:
     *  app/src/main/AndroidManifest.xml
     * </pre>
     * @return
     */
    String manifestPath();
}
