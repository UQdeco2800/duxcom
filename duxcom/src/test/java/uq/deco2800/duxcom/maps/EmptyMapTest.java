package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the Empty map class
 *
 * @author Alex McLean
 */
public class EmptyMapTest {

    private EmptyMap emptyMap;

    @Test
    public void testCreation() {
        emptyMap = new EmptyMap("test");
        assertNotNull(emptyMap);
    }

}