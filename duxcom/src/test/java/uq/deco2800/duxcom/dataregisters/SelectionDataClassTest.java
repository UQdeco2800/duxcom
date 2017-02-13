package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for SelectionDataClass
 * @author Alex McLean
 */
public class SelectionDataClassTest {

    /**
     * Tests the entirety of the data class by mutating and checking a single data class
     */
    @Test
    public void testAllMethods() {

        SelectionDataClass testDataClass = new SelectionDataClass("testSelection", "testTexture");
        assertEquals("testSelection", testDataClass.getSelectionTypeName());
        assertEquals("testTexture", testDataClass.getSelectionTextureName());

        testDataClass.setSelectionTypeName("newName");
        assertEquals("newName", testDataClass.getSelectionTypeName());

        testDataClass.setSelectionTextureName("newTexture");
        assertEquals("newTexture", testDataClass.getSelectionTextureName());

    }

}