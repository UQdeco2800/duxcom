package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map002 (nice name btw) map class
 *
 * @author Tiget
 */
public class Map002Test {

    private Map002 map;

    @Test
    public void testCreation() {
        map = new Map002("test", false);
        assertNotNull(map);
    }
}
