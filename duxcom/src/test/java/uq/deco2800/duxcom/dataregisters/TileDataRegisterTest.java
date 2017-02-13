package uq.deco2800.duxcom.dataregisters;

import org.junit.Before;
import org.junit.Test;
import uq.deco2800.duxcom.tiles.TileType;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the TileDataRegister
 * @author Alex McLean
 */
public class TileDataRegisterTest {

    private TileDataRegister tileDataRegister;

    @Before
    public void setUp() {
        tileDataRegister = new TileDataRegister();
    }

    @Test
    public void testCreation() {
        assertNotNull(tileDataRegister);
    }

    @Test
    public void testGetData() throws Exception {
        TileDataClass tileDataClass = tileDataRegister.getData(TileType.VOID);
        assertNotNull(tileDataClass);
    }
}