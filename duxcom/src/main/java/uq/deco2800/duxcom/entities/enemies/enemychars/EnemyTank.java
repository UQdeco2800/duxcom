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
 * Created by jay-grant on 24/08/2016.
 * <p>
 * Class for holding attribute values for Tank
 */
public class EnemyTank extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Tank'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_TANK);

    /**
     * Creates a new EnemyTank.
     *
     * @param x The x coordinate of the Tank.
     * @param y The y coordinate of the Tank.
     */
    public EnemyTank(int x, int y) {
        super(EntityType.ENEMY_TANK, x, y);
        setStats(EnemyType.ENEMY_TANK, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        super.abilities = abilities;
    }
}
