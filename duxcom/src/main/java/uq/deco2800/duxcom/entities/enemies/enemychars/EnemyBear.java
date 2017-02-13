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
public class EnemyBear extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Bear'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_BEAR);

    /**
     * Creates a new EnemyBear.
     *
     * @param x The x coordinate of the Bear.
     * @param y The y coordinate of the Bear.
     */
    public EnemyBear(int x, int y) {
        super(EntityType.ENEMY_BEAR, x, y);
        setStats(EnemyType.ENEMY_BEAR, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        super.abilities = abilities;
    }
}
