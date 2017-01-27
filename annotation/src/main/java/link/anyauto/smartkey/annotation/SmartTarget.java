package link.anyauto.smartkey.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  Mark an activity as a target.
 *  Be careful:
 *      1. If you haven't a @SmartManifest in some class, this annotation will be ignored.
 *      2. This annotation is a supplement of the @SmartManifest, it takes a req field that
 *          will help you manage the creation of the specific IntentBuilder.
 *          For example, if you have a class that annotate with @SmartIntent called com.example.ABC
 *          then:
 *          @SmartTarget(req="com.example.ABC") in your XXX activity
 *          will generate the following method in the generated XXXATarget class:
 *          public ABCIBuilder newMapperBuilder() {
 *            return ABCIBuilder.newBuilder();
 *          }
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SmartTarget {
    String req() default "";
}
