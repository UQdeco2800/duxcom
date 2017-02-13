package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map004 map class
 *
 * @author winstonf6
 */
public class Map004Test {

    private Map004 map;

    @Test
    public void testCreation() {
        map = new Map004("test", false);
        assertNotNull(map);
    }
}
