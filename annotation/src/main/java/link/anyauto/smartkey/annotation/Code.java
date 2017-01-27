package link.anyauto.smartkey.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     Customizable code.
 *     Generally you don't have to worry about the generated code for putting and getting data
 *      for passing through with Intent, but there are times that is not so easy for the generator
 *      to determine the correct code, here is a chance for you to customize it yourself.
 *     Or if you find that some generated code for you is wrong, you can correct it in this way.
 *     For example, A class that implements both Parcelable and Serializable; or an ArrayList,
 *      the generator hasn't a way of know the inheritance hierarchy of the Generic types in it,
 *      in this case, you can do like this(suppose your field is parcelable array list):
 *      @Code(get="getParcelableArrayListExtra(%1$s)", set="putParcelableArrayListExtra(%1$s, %2$s)")
 *      public ArrayList<Parcelable> myParcelables;
 *     Then the generator will use your code instead of guessing:
 *      when set -> intent.putParcelableArrayListExtra("myParcelables", smart.myParcelables);
 *      when get -> smart.myParcelables = intent.getParcelableArrayListExtra("myParcelables");
 *      In the above, %1$s is the place holder for the key, and %2$s is the place holder or your value.
 * </pre>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Code {
    /**
     * <pre>
     * Custom code for getting value, %1$s is the place holder for key,
     * for instance:
     *  get="getParcelableArrayListExtra(%1$s)"
     * </pre>
     * @return
     */
    String get() default "";

    /**
     * <pre>
     * ustom code for getting value, %1$s is the place holder for key, %2$s is the place holder for value
     * for instance:
     *  set="putParcelableArrayListExtra(%1$s, %2$s)"
     * @return
     */
    String set() default "";

    /**
     * Used in SharedPreferences, denoting this is a generic field, and the getter should be treated differently.
     * @return
     *  true if generic type presents.
     */
    boolean isGeneric() default false;

    /**
     * <pre>
     *  Used in SharedPreferences, when the field is generic, list all the types related so that
     *  the generated code can import types as needed:
     *      for example, if the generic is:
     *          ArrayList<String>
     *      then generic types should be [$T<$T>, java.util.ArrayList, java.lang.String]
     *      or if the generic is:
     *          HashMap<Integer, ArrayList<String>>
     *      then generic types should be [$T<$T, $T<$T>>, java.util.HashMap, java.lang.Integer, java.util.ArrayList, java.lang.String]
     * </pre>
     * @return
     */
    String[] genericTypes() default "";
}
