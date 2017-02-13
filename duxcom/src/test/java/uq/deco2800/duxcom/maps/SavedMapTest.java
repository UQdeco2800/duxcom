package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Saved map class
 *
 * @author Alex McLean
 */
public class SavedMapTest {

    private SavedMap savedMap;

    @Test
    public void testCreation() {
        savedMap = new SavedMap("test", 40, 40);
        assertNotNull(savedMap);
    }

}