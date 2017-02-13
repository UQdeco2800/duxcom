package uq.deco2800.duxcom.interfaces.gameinterface.minimap;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the mini map controller class
 *
 * @author mtimmo
 */
public class MiniMapControllerTest {

    @Test
    public void testRefreshMiniMap() throws Exception {

        /*
            Any help here building a way to test this function would be awesome!
            From what I can tell, I would need an active game going to test this (with a current hero etc..)
         */

        //assertFalse(MiniMapController.refreshMiniMap(null, null, graphicsPolygons, graphicsOvals));
        //assertFalse(MiniMapController.refreshMiniMap(graphicsContext, null));
        //assertFalse(MiniMapController.refreshMiniMap(null, gameManager));
        //assertTrue(MiniMapController.refreshMiniMap(graphicsContext, gameManager));
    }

    @Test
    public void testScale() throws Exception {
        // Array of test values
        float[] floats = new float[]
                {0f, 1f, 100f, 1000f, 12345f, -5f, -12345f, 0.322f, 345.5326f, -2345.2355f, -43535.22f};

        // Test each of the values in the above array
        for (float f : floats) {
            MiniMapController.setScale(f);
            assertEquals(f, MiniMapController.getScale(), 0);
        }
    }

    @Test
    public void testLeft() throws Exception {
        // Array of test values
        int[] integers = new int[]
                {0, 1, -1, 100, 1000, 1920, 12345, -12345};

        // Test each of the values in the above array
        for (int i : integers) {
            MiniMapController.setLeft(i);
            assertEquals(i, MiniMapController.getLeft(), 0);
        }
    }

    @Test
    public void testTop() throws Exception {
        // Array of test values
        int[] integers = new int[]
                {0, 1, -1, 100, 1000, 1080, 12345, -12345};

        // Test each of the values in the above array
        for (int i : integers) {
            MiniMapController.setTop(i);
            assertEquals(i, MiniMapController.getTop(), 0);
        }
    }

    @Test
    public void testZoom() throws Exception {
        // Array of test values
        int[] integers = new int[]
                {0, 1, -1, 100, 1000, 12345, -12345};

        // Test each of the values in the above array
        for (int i : integers) {
            MiniMapController.setZoom(i);
            assertEquals(i, MiniMapController.getZoom(), 0);
        }
    }

    @Test
    public void testVisible() throws Exception {
        // Test case true
        MiniMapController.setVisible(true);
        assertEquals(true, MiniMapController.isVisible());
        assertNotEquals(false, MiniMapController.isVisible());

        // Test case false
        MiniMapController.setVisible(false);
        assertEquals(false, MiniMapController.isVisible());
        assertNotEquals(true, MiniMapController.isVisible());
    }
}