package link.anyauto.smartkey.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <pre>
 *     Used for marking fields that need a custom key.
 *     Generally there's no need to care about the key,
 *     but there are times that the key is already there,
 *     and you have to be compatible with it, or you just
 *     don't want to change it.
 *     Or if the component was not written by you, like the system
 *     components, you can use @Key("key") to work with them.
 * </pre>
 */

@Target(FIELD)
@Retention(CLASS)
public @interface Key {
    String value() default "";
}
