package uq.deco2800.duxcom.coop;

import org.junit.Test;
import uq.deco2800.singularity.common.ServerConstants;

import static org.junit.Assert.*;

/**
 * Tests the singularity target
 *
 * Created by liamdm on 21/10/2016.
 */
public class SingularityTargetTest {
    @Test
    public void testSingularityTarget(){
        SingularityTarget.setTargetDebug(false);
        assertEquals(ServerConstants.PRODUCTION_SERVER, SingularityTarget.getCurrentTarget());
        SingularityTarget.setTargetDebug(true);
    }
}