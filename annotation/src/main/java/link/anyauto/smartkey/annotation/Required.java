package link.anyauto.smartkey.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <pre>
 * Just an annotation for your user to read your generated codes.
 * Currently no automatic processing of the @Required annotation yet.
 * </pre>
 */
@Target(FIELD)
@Retention(CLASS)
public @interface Required {
}