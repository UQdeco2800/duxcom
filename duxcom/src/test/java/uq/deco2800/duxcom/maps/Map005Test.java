package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map005 map class
 *
 * @author winstonf6
 */
public class Map005Test {

    private Map005 map;

    @Test
    public void testCreation() {
        map = new Map005("test", false);
        assertNotNull(map);
    }
}
