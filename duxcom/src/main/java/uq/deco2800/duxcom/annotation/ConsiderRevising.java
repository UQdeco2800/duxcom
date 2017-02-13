package uq.deco2800.duxcom.annotation;

import java.lang.annotation.*;

/**
 * Used to suggest revisions
 *
 * Created by liamdm on 9/10/2016.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.FIELD,
        ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE})
@Inherited
public @interface ConsiderRevising {
    /**
     * What could change
     */
    String description();

    /**
     * Suggested change
     */
    String suggestion() default "";

    /**
     * Date added
     */
    String date();

    /**
     * The ID of the person suggesting
     */
    String suggestor();
}
