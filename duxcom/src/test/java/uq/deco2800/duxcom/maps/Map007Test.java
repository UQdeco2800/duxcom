package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Map007 map class
 *
 * @author winstonf6
 */
public class Map007Test {

    private Map007 map;

    @Test
    public void testCreation() {
        map = new Map007("test", false);
        assertNotNull(map);
    }
}
