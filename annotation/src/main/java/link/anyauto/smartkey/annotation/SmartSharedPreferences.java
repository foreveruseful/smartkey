package link.anyauto.smartkey.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for classes that need to generate shared preferences helper classes.
 *  Recommend way:
 *      Write only one class describing all data needed for the whole application in your common layer,
 *      add @SmartSharedPreferences at that class,
 *      and then read and write anything through the generated class' helper methods.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SmartSharedPreferences {
}
