package uq.deco2800.duxcom.annotation;

import java.lang.annotation.*;

/**
 * Used to denote a constructor in a utility class that will not be instantiated.
 *
 * Created by liamdm on 9/10/2016.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR})
@Inherited
public @interface UtilityConstructor {
}
