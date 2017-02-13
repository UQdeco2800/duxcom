package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Dynamic Entity map class
 *
 * @author Alex McLean
 */
public class DynamicEntityTestMapTest {

    private DynamicEntityTestMap dynamicEntityTestMap;

    @Test
    public void testCreation() {
        dynamicEntityTestMap = new DynamicEntityTestMap("test");
        assertNotNull(dynamicEntityTestMap);
    }

}