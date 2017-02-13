package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Falador map class
 *
 * @author Tiget
 */
public class FaladorMapTest {

    private Falador falador;
    private Falador faladorBeta;

    @Test
    public void testCreation() {
        falador = new Falador("test", false);
        assertNotNull(falador);
    }

    @Test
    public void testBeta() {
        faladorBeta = new Falador("test", true);
        assertNotNull(faladorBeta);

    }

}
