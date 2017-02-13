package uq.deco2800.duxcom.coop;

import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.singularity.common.ServerConstants;

/**
 * Controls the server target for singularity.
 *
 * Created by liamdm on 16/10/2016.
 */
public class SingularityTarget {
    private static String currentTarget = ServerConstants.PRODUCTION_SERVER;

    @UtilityConstructor
    private SingularityTarget() {}

    /**
     * Set the singularity server to target your local server if true, and remote otherwise
     * @param debug if you are debugging
     */
    public static void setTargetDebug(boolean debug){
        SingularityTarget.currentTarget = debug ? ServerConstants.LOCAL_HOST : ServerConstants.PRODUCTION_SERVER;
    }

    /**
     * Get the current singularity target
     */
    public static String getCurrentTarget() {
        return currentTarget;
    }
}
