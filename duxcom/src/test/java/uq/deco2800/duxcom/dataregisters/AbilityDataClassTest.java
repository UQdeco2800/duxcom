package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;
import uq.deco2800.duxcom.abilities.AbilityType;
import uq.deco2800.duxcom.common.DamageType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for AbilityDataClass
 *
 * @author Alex McLean
 */
public class AbilityDataClassTest {

    /**
     * Tests the entirety of the data class by mutating and checking a single data class
     */
    @Test
    public void testAllMethods() {

        AbilityDataClass testDataClass = new AbilityDataClass(AbilityType.ARCHER, "boom",
                "boom boom", 69, 69, 69, 69, 69, DamageType.EXPLOSIVE, false, true, false);

        assertEquals(AbilityType.ARCHER, testDataClass.getAbilityType());
        assertEquals("boom", testDataClass.getName());
        assertEquals("boom boom", testDataClass.getDescription());
        assertEquals(69, testDataClass.getCostAP());
        assertEquals(69, testDataClass.getCooldownTurns());
        assertEquals(69, testDataClass.getRange());
        assertEquals(69, testDataClass.getAoeRange());
        assertTrue(Math.abs(69 - testDataClass.getDamage()) < 0.001d);
        assertEquals(DamageType.EXPLOSIVE, testDataClass.getDamageType());

        testDataClass.setAbilityType(AbilityType.CAVALIER);
        assertEquals(AbilityType.CAVALIER, testDataClass.getAbilityType());

        testDataClass.setName("boomer");
        assertEquals("boomer", testDataClass.getName());

        testDataClass.setDescription("boomer boomer");
        assertEquals("boomer boomer", testDataClass.getDescription());

        testDataClass.setCostAP(420);
        assertEquals(420, testDataClass.getCostAP());

        testDataClass.setCooldownTurns(420);
        assertEquals(420, testDataClass.getCooldownTurns());

        testDataClass.setRange(420);
        assertEquals(420, testDataClass.getRange());
        
        testDataClass.setAoeRange(420);
        assertEquals(420, testDataClass.getAoeRange());

        testDataClass.setDamage(420);
        assertTrue(Math.abs(420 - testDataClass.getDamage()) < 0.001d);

        testDataClass.setDamageType(DamageType.CRUSHING);
        assertEquals(DamageType.CRUSHING, testDataClass.getDamageType());

    }
}