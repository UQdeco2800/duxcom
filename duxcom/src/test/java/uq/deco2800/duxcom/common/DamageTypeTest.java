package uq.deco2800.duxcom.common;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test DamageType enum accessibility
 *
 * @author Alex McLean
 */
public class DamageTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(DamageType.EXPLOSIVE);
    }

}