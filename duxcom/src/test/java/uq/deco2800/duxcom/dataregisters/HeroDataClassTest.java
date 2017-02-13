package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for HeroDataClass
 *
 * @author Alex McLean
 */
public class HeroDataClassTest {

    /**
     * Tests the entirety of the data class by mutating and checking a single data class
     */
    @Test
    public void testAllMethods() {

        HeroDataClass testDataClass = new HeroDataClass("Knight", "knight", 69, 69, 69, 69, 69, 69);

        assertEquals("Knight", testDataClass.getHeroName());
        assertEquals("knight", testDataClass.getHeroGraphic());
        assertTrue(Math.abs(69 - testDataClass.getBaseHealth()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseArmour()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseAP()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseSpeed()) < 0.001d);
        assertEquals(69, testDataClass.getBaseAP());
        assertEquals(69, testDataClass.getBaseRechargeAP());
        assertTrue(Math.abs(69 - testDataClass.getBaseVisibilityRange()) < 0.001d);

        testDataClass.setHeroName("Cavalier");
        assertEquals("Cavalier", testDataClass.getHeroName());

        testDataClass.setBaseHealth(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseHealth()) < 0.001d);

        testDataClass.setBaseArmour(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseArmour()) < 0.001d);

        testDataClass.setBaseAP(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseAP()) < 0.001d);

        testDataClass.setBaseSpeed(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseSpeed()) < 0.001d);

        testDataClass.setBaseAP(420);
        assertEquals(420, testDataClass.getBaseAP());

        testDataClass.setBaseRechargeAP(420);
        assertEquals(420, testDataClass.getBaseRechargeAP());

        testDataClass.setBaseVisibilityRange(421);
        assertTrue(Math.abs(421 - testDataClass.getBaseVisibilityRange()) < 0.001d);

        testDataClass.setBaseVisibilityRange(420);
        assertTrue(Math.abs(421 - testDataClass.getBaseVisibilityRange()) < 0.001d);

        testDataClass.setBaseVisibilityRange(-5);
        assertTrue(Math.abs(1 - testDataClass.getBaseVisibilityRange()) < 0.001d);

        testDataClass.setHeroGraphic("cavalier");
        assertEquals("cavalier", testDataClass.getHeroGraphic());

    }
}