package uq.deco2800.duxcom.tiles;

import org.junit.Before;
import org.junit.Test;
import uq.deco2800.duxcom.entities.heros.BetaTester;

import static org.junit.Assert.*;

/**
 * Test the Tile class
 *
 * @author Alex McLean
 */
public class TileTest {

    private Tile tile;

    /**
     * Sets up the new tile before each test
     */
    @Before
    public void setUp() {
        tile = new Tile(TileType.REAL_QUT);
    }

    /**
     * Tests the initial conditions of the tile
     */
    @Test
    public void testSetUp() {
        assertEquals(TileType.REAL_QUT, tile.getTileType());
        assertFalse(tile.isOccupied());
        assertNull(tile.getMovableEntity());
        assertNotNull(tile);
        tile.setTileType(TileType.REAL_MAGMA);
        assertEquals(TileType.REAL_MAGMA, tile.getTileType());
    }

    /**
     * Tests the addition of an entity to a tile
     */
    @Test
    public void testEntityAddition() {
        BetaTester ibis = new BetaTester(0, 0);
        tile.addEntity(ibis);
        assertEquals(ibis, tile.getMovableEntity());
        assertFalse(tile.addEntity(ibis));
        assertTrue(tile.isOccupied());
        tile.removeMovableEntity();
        assertNull(tile.getMovableEntity());
    }
}