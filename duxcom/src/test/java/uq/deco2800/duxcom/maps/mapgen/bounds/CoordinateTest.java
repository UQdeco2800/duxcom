package uq.deco2800.duxcom.maps.mapgen.bounds;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for coordinates
 *
 * Created by liamdm on 25/08/2016.
 */
public class CoordinateTest {

    @Test
    public void testCoordinates(){
        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(3, 4);
        Coordinate c3 = new Coordinate(0, 0);

        assertNotEquals(c1.hashCode(), c2.hashCode());
        assertFalse(c1.equals(c2));

        assertEquals(c1.hashCode(), c3.hashCode());
        assertTrue(c1.equals(c3) && c3.equals(c1));

        assertEquals(0, c1.x);
        assertEquals(0, c1.y);
        assertEquals(3, c2.x);
        assertEquals(4, c2.y);
    }
}