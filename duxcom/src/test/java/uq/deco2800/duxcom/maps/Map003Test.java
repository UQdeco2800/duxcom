package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map003 (nice name btw) map class
 *
 * @author Tiget
 */
public class Map003Test {

    private Map003 map;

    @Test
    public void testCreation() {
        map = new Map003("test", false);
        assertNotNull(map);
    }
}
