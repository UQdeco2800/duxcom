package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for EntityDataClass
 *
 * @author Alex McLean
 */
public class EntityDataClassTest {

    /**
     * Tests the entirety of the data class by mutating and checking a single data class
     */
    @Test
    public void testAllMethods() {

        EntityDataClass testDataClass = new EntityDataClass("testEntity", "testTexture", "testMinimapColor");
        assertEquals("testEntity", testDataClass.getEntityTypeName());
        assertEquals("testTexture", testDataClass.getEntityTextureName());
        assertEquals("testMinimapColor", testDataClass.getMinimapColorString());

        testDataClass.setEntityTypeName("newName");
        assertEquals("newName", testDataClass.getEntityTypeName());

        testDataClass.setEntityTextureName("newTexture");
        assertEquals("newTexture", testDataClass.getEntityTextureName());

        testDataClass.setMinimapColorString("newColor");
        assertEquals("newColor", testDataClass.getMinimapColorString());

    }

}