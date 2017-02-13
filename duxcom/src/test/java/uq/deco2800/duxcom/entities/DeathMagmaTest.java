package uq.deco2800.duxcom.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by LeX on 18/10/2016.
 */
public class DeathMagmaTest {

    @Test
    public void getImageName() throws Exception {
        DeathMagma deathMagma = new DeathMagma(0, 2);
        assertEquals(0, deathMagma.getX());
        assertEquals(2, deathMagma.getY());
        assertEquals(1, deathMagma.getXLength());
        assertEquals(1, deathMagma.getYLength());
        assertEquals("rs_magma", deathMagma.getImageName());
    }

}