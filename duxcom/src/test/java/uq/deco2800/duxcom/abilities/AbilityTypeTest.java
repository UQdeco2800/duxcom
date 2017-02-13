package uq.deco2800.duxcom.abilities;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test AbilityType enum accessibility
 *
 * @author Alex McLean
 */
public class AbilityTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(AbilityType.UTILITY);
    }

}