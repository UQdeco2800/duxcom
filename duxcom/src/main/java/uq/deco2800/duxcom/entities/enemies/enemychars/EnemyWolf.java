package uq.deco2800.duxcom.entities.enemies.enemychars;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.ShortRangeTestAbility;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.EnemyDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyType;

import java.util.ArrayList;

/**
 * Created by jay-grant on 12/10/2016.
 */
public class EnemyWolf extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Wolf'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_WOLF);

    /**
     * Creates a new EnemyWolf.
     *
     * @param x The x coordinate of the Wolf.
     * @param y The y coordinate of the Wolf.
     */
    public EnemyWolf(int x, int y) {
        super(EntityType.ENEMY_WOLF, x, y);
        setStats(EnemyType.ENEMY_WOLF, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        super.abilities = abilities;
    }
}
