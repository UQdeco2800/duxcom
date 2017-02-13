package uq.deco2800.duxcom.interfaces;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test InterfaceSegmentType enum accessibility
 *
 * @author Alex McLean
 */
public class InterfaceSegmentTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(InterfaceSegmentType.GAME);
    }

}