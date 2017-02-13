package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Karamja map class
 *
 * @author Tiget
 */
public class KaramjaTest {

    private Karamja map;

    @Test
    public void testCreation() {
        map = new Karamja("test");
        assertNotNull(map);
    }
}
