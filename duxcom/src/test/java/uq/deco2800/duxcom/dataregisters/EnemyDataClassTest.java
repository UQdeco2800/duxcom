package uq.deco2800.duxcom.dataregisters;

import org.junit.Test;

import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAttitude;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for EntityDataClass
 * @author Alex McLean
 */
public class EnemyDataClassTest {

    /**
     * Tests the entirety of the data class by mutating and checking a single data class
     */
    @Test
    public void testAllMethods() {

        EnemyDataClass testDataClass = new EnemyDataClass("Dark Knight", "enemy_knight", EnemyMode.NORMAL,
                EnemyAttitude.BALANCED, 69, 69, 0.69, 69, 69, 69, 69, 69, 69);

        assertEquals("Dark Knight", testDataClass.getEnemyName());
        assertEquals("enemy_knight", testDataClass.getEnemyGraphic());
        assertEquals(EnemyMode.NORMAL, testDataClass.getMode());
        assertEquals(EnemyAttitude.BALANCED, testDataClass.getAttitude());
        assertTrue(Math.abs(69 - testDataClass.getBaseHealth()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseDamage()) < 0.001d);
        assertTrue(Math.abs(0.69 - testDataClass.getBaseCritChance()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseArmour()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseMagicResist()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseAP()) < 0.001d);
        assertTrue(Math.abs(69 - testDataClass.getBaseSpeed()) < 0.001d);
        assertEquals(69, testDataClass.getMoveRange());
        assertEquals(69, testDataClass.getBaseAttackRange());

        testDataClass.setEnemyName("Enemy Charger");
        assertEquals("Enemy Charger", testDataClass.getEnemyName());

        testDataClass.setEnemyGraphic("enemy_archer");
        assertEquals("enemy_archer", testDataClass.getEnemyGraphic());

        testDataClass.setMode(EnemyMode.ESCORT);
        assertEquals(EnemyMode.ESCORT, testDataClass.getMode());

        testDataClass.setAttitude(EnemyAttitude.DEFENSIVE);
        assertEquals(EnemyAttitude.DEFENSIVE, testDataClass.getAttitude());

        testDataClass.setBaseHealth(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseHealth()) < 0.001d);

        testDataClass.setBaseDamage(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseDamage()) < 0.001d);

        testDataClass.setBaseCritChance(0.420);
        assertTrue(Math.abs(0.420 - testDataClass.getBaseCritChance()) < 0.001d);

        testDataClass.setBaseArmour(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseArmour()) < 0.001d);

        testDataClass.setBaseMagicResist(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseMagicResist()) < 0.001d);

        testDataClass.setBaseAP(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseAP()) < 0.001d);

        testDataClass.setBaseSpeed(420);
        assertTrue(Math.abs(420 - testDataClass.getBaseSpeed()) < 0.001d);

        testDataClass.setMoveRange(420);
        assertEquals(420, testDataClass.getMoveRange());

        testDataClass.setBaseAttackRange(420);
        assertEquals(420, testDataClass.getBaseAttackRange());

    }

}