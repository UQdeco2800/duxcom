package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map001 (nice name btw) map class
 *
 * @author Tiget
 */
public class Map001Test {

    private Map001 map;

    @Test
    public void testCreation() {
        map = new Map001("test", false);
        assertNotNull(map);
    }
}
