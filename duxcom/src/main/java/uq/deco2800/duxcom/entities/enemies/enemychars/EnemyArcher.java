package uq.deco2800.duxcom.entities.enemies.enemychars;

import java.util.ArrayList;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.LongRangeTestAbility;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.dataregisters.EnemyDataClass;
import uq.deco2800.duxcom.entities.enemies.EnemyType;

/**
 * Created by jay-grant on 24/08/2016.
 *
 * Class for holding attribute values for Enemy Archer
 */
public class EnemyArcher extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Archer'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_ARCHER);

    /**
	 * Creates a new EnemyArcher.
	 * 
	 * @param x
	 *            The x coordinate of the Archer.
	 * @param y
	 *            The y coordinate of the Archer.
	 */
    public EnemyArcher(int x, int y) {
        super(EntityType.ENEMY_ARCHER, x, y);
        setStats(EnemyType.ENEMY_ARCHER, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new LongRangeTestAbility());
        super.abilities = abilities;
    }
}
