package uq.deco2800.duxcom.objective;

import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.objectives.EnemyObjectiveType;

/**
 * Created by Tom B on 14/10/2016.
 */
public class EnemyObjectiveTypeTest {

    @Test
    public void testInitialisation() {
        EnemyObjectiveType eot = new EnemyObjectiveType(EnemyType.ENEMY_ARCHER, true);
        EnemyObjectiveType eot2 = new EnemyObjectiveType(EnemyType.ENEMY_DARK_MAGE, false);

        Assert.assertEquals(true, eot.isForDeath());
        Assert.assertEquals(false, eot2.isForDeath());

        Assert.assertEquals(EnemyType.ENEMY_ARCHER, eot.getType());
        Assert.assertEquals(EnemyType.ENEMY_DARK_MAGE, eot2.getType());
    }

    @Test
    public void testEqualsAndHashCode() {
        Integer i = new Integer("1");
        EnemyObjectiveType eot = new EnemyObjectiveType(EnemyType.ENEMY_ARCHER, true);
        EnemyObjectiveType eot2 = new EnemyObjectiveType(EnemyType.ENEMY_ARCHER, true);
        EnemyObjectiveType eot3 = new EnemyObjectiveType(EnemyType.ENEMY_DARK_MAGE, true);
        EnemyObjectiveType eot4 = new EnemyObjectiveType(EnemyType.ENEMY_ARCHER, false);

        // Equals
        Assert.assertTrue(eot.equals(eot2));
        Assert.assertFalse(eot.equals(i));
        Assert.assertFalse(eot.equals(eot3));
        Assert.assertFalse(eot.equals(eot4));

        // Hashcode
        Assert.assertEquals(23 * EnemyType.ENEMY_ARCHER.hashCode() + 3, eot.hashCode());
        Assert.assertEquals(37 * EnemyType.ENEMY_ARCHER.hashCode() + 5, eot4.hashCode());
    }

    @Test
    public void testToString() {
        String test = "EOT: Type - ENEMY_ARCHER; Kill - true";
        EnemyObjectiveType eot = new EnemyObjectiveType(EnemyType.ENEMY_ARCHER, true);
        Assert.assertEquals(test, eot.toString());
    }
}
