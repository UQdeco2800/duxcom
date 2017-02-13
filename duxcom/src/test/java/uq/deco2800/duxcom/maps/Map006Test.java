package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map006 map class
 *
 * @author winstonf6
 */
public class Map006Test {

    private Map006 map;

    @Test
    public void testCreation() {
        map = new Map006("test", false);
        assertNotNull(map);
    }
}
