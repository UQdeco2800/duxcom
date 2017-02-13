package uq.deco2800.duxcom.entities.enemies.enemychars;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.EnemyShortTestAbility;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.EnemyDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyType;

import java.util.ArrayList;

/**
 * Created by jay-grant on 24/08/2016.
 * <p>
 * Class for holding attribute values for Support
 */
public class EnemySupport extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Support'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_SUPPORT);

    /**
     * Creates a new EnemySupport.
     *
     * @param x The x coordinate of the Support.
     * @param y The y coordinate of the Support.
     */
    public EnemySupport(int x, int y) {
        super(EntityType.ENEMY_SUPPORT, x, y);
        setStats(EnemyType.ENEMY_SUPPORT, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new Heal());
        abilities.add(new EnemyShortTestAbility());
        super.abilities = abilities;
    }
}
