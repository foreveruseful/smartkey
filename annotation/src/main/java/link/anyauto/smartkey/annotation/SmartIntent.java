package link.anyauto.smartkey.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for classes that need to generate intent helper classes.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SmartIntent {
}
