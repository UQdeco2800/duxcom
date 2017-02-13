package uq.deco2800.duxcom.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the Point util
 *
 * @author Alex McLean
 */
public class Array2DTest {

    private Array2D<String> array2D;

    @Test
    public void testConstructor() {
        array2D = new Array2D<>(10,10);
        assertEquals(10, array2D.getWidth());
        assertEquals(10, array2D.getHeight());

        assertNull(array2D.get(0,0));

        array2D.set(0,0,"test");
        assertEquals("test", array2D.get(0,0));

        int exceptionCount = 0;
        try {
            array2D.set(20,20,"oob");
        } catch (IndexOutOfBoundsException e) {
            exceptionCount++;
        }
        try {
            array2D.get(20,20);
        } catch (IndexOutOfBoundsException e) {
            exceptionCount++;
        }
        assertEquals(2, exceptionCount);

        assertEquals("test", array2D.getList().get(0));
    }

}