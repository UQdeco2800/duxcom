package uq.deco2800.duxcom.tiles;

import org.junit.Test;
import uq.deco2800.duxcom.tiles.TileType;

import static org.junit.Assert.assertNotNull;

/**
 * Test TileType enum accessibility
 * @author Alex McLean
 */
public class TileTypeTest {

    @Test
    public void testAccess() {
        assertNotNull(TileType.REAL_QUT);
    }

}