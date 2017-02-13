package uq.deco2800.duxcom.particlesystem;

import org.junit.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


/**
 * Created by s2739357 on 23/10/2016.
 */
public class ParticleGenTest {
    private ParticleGen particleGen;

    @Test
    public void testCreation() {

        this.particleGen = new ParticleGen (null, null, 0);
    }

    //tests if isRunning defaults to false
    @Ignore
    public void testGetRunning() {
        assertFalse(this.particleGen.getRunning());
    }

    //tests updating isRunning
    @Ignore
    public void testSetRunning() {
        this.particleGen.setRunning(true);
        assertTrue(this.particleGen.getRunning());
    }
}