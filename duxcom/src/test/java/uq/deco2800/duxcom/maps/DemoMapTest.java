package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Demo map class
 *
 * @author Alex McLean
 */
public class DemoMapTest {

    private DemoMap demoMap;

    @Test
    public void testCreation() {
        demoMap = new DemoMap("test");
        assertNotNull(demoMap);
    }

}