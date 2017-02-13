package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for TileDataClass
 * @author Alex McLean
 */
public class TileDataClassTest {

    /**
     * Tests the entirety of the data class by mutating and checking a single data class
     */
    @Test
    public void testAllMethods() {

        TileDataClass testDataClass1 = new TileDataClass("testTile", "testTexture", "testMinimapColor");
        assertEquals("testTile", testDataClass1.getTileTypeName());
        assertEquals("testTexture", testDataClass1.getTileTextureName());
        assertEquals("testMinimapColor", testDataClass1.getMinimapColorString());
        assertEquals(TileDataClass.DEFAULT_MOVEMENT_MODIFIER, testDataClass1.getMovementModifier());

        TileDataClass testDataClass2 = new TileDataClass("testTile", "testTexture", "testMinimapColor", 2);
        assertEquals("testTile", testDataClass2.getTileTypeName());
        assertEquals("testTexture", testDataClass2.getTileTextureName());
        assertEquals("testMinimapColor", testDataClass2.getMinimapColorString());
        assertEquals(2, testDataClass2.getMovementModifier());

        testDataClass2.setTileTypeName("newName");
        assertEquals("newName", testDataClass2.getTileTypeName());

        testDataClass2.setTileTextureName("newTexture");
        assertEquals("newTexture", testDataClass2.getTileTextureName());

        testDataClass2.setMinimapColorString("newColor");
        assertEquals("newColor", testDataClass2.getMinimapColorString());

        testDataClass2.setMovementModifier(1337);
        assertEquals(1337, testDataClass2.getMovementModifier());

    }

}