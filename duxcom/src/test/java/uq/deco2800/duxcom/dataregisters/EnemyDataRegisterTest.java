package uq.deco2800.duxcom.dataregisters;

import org.junit.Before;
import org.junit.Test;

import uq.deco2800.duxcom.entities.enemies.EnemyType;

import static org.junit.Assert.assertNotNull;


/**
 * Tests the EnemyDataRegister
 * @author Alex McLean
 */
public class EnemyDataRegisterTest {

    private EnemyDataRegister enemyDataRegister;

    @Before
    public void setUp() {
        enemyDataRegister = new EnemyDataRegister();
    }

    @Test
    public void testCreation() {
        assertNotNull(enemyDataRegister);
    }

    @Test
    public void testGetData() throws Exception {
        EnemyDataClass enemyDataClass = enemyDataRegister.getData(EnemyType.ENEMY_ARCHER);
        assertNotNull(enemyDataClass);
    }
}