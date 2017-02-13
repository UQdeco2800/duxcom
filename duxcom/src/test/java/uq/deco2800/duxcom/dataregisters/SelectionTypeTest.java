package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test SelectionType enum accessibility
 *
 * @author Alex McLean
 */
public class SelectionTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(SelectionType.SELECTION_BASIC);
    }

}