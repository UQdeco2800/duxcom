package uq.deco2800.duxcom.annotation;

import java.lang.annotation.*;

/**
 * Requires annotation to denote class preconditions.
 *
 * Created by liamdm on 9/10/2016.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Inherited
public @interface MethodContract {
    /**
     * The pre-condition of the method.
     */
    String[] precondition() default "null";

    /**
     * The post condition.
     */
    String[] postcondition() default "null";

    /**
     * If the condition is enforced in code.
     */
    boolean enforced() default false;
}
