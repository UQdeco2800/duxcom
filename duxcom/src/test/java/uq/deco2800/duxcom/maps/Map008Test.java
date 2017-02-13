package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map008 map class
 *
 * @author winstonf6
 */
public class Map008Test {

    private Map008 map;

    @Test
    public void testCreation() {
        map = new Map008("test", false);
        assertNotNull(map);
    }
}
