package uq.deco2800.duxcom.maps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the OptimisedDemo map class
 *
 * @author Alex McLean
 */
public class OptimisedDemoTest {

    private OptimisedDemo optimisedDemo;

    @Test
    public void testCreation() {
        optimisedDemo = new OptimisedDemo();
        assertNotNull(optimisedDemo);
    }

}