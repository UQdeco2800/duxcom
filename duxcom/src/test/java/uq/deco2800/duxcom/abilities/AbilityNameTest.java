package uq.deco2800.duxcom.abilities;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test AbilityName enum accessibility
 *
 * @author Alex McLean
 */
public class AbilityNameTest {

    @Test
    public void testAccess() {
        assertNotNull(AbilityName.BOMB);
    }
}