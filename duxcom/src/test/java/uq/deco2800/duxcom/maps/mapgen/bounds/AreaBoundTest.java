package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liamdm on 18/08/2016.
 */
public class AreaBoundTest {
    AreaBound testBound;

    @Before
    public void setUp() throws Exception {
        testBound = new AreaBound(0, 0, 20, 30);
    }

    @Test
    public void contains() throws Exception {
        assertTrue(testBound.contains(0, 0));
        assertTrue(testBound.contains(10, 10));
        assertTrue(testBound.contains(19, 29));

        assertFalse(testBound.contains(20, 20));
        assertFalse(testBound.contains(10, 30));
        assertFalse(testBound.contains(30, 30));
        assertFalse(testBound.contains(-30, -30));
        assertFalse(testBound.contains(300, 300));

    }

    @Test
    public void getWidth() throws Exception {
        assertEquals(20, testBound.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        assertEquals(30, testBound.getHeight());

    }

    @Test
    public void getStartX(){
        assertEquals(0, testBound.getStartX());
    }

    @Test
    public void getEndX(){
        assertEquals(20, testBound.getEndX());
    }

    @Test
    public void getStartY(){
        assertEquals(0, testBound.getStartY());
    }

    @Test
    public void getEndY(){
        assertEquals(30, testBound.getEndY());
    }

    @Test
    public void leastDistanceTo(){
        AreaBound distance10 = new AreaBound(-20, -20, -10, 0);
        AreaBound distance20 = new AreaBound(40, 30, 50, 40);
        AreaBound distance30 = new AreaBound(-40, -40, -30, 0);

        assertEquals(10, testBound.leastDistanceTo(distance10));
        assertEquals(20, testBound.leastDistanceTo(distance20));
        assertEquals(30, testBound.leastDistanceTo(distance30));

    }
}