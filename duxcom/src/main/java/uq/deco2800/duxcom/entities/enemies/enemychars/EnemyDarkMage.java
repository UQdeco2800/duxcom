package uq.deco2800.duxcom.entities.enemies.enemychars;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.LongRangeTestAbility;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.EnemyDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyType;

import java.util.ArrayList;

/**
 * Created by jay-grant on 24/08/2016.
 * <p>
 * Class for holding attribute values for Dark Mage
 */
public class EnemyDarkMage extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Dark Mage'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_DARK_MAGE);

    /**
     * Creates a new EnemyDarkMage.
     *
     * @param x The x coordinate of the DarkMage.
     * @param y The y coordinate of the DarkMage.
     */
    public EnemyDarkMage(int x, int y) {
        super(EntityType.ENEMY_DARK_MAGE, x, y);
        setStats(EnemyType.ENEMY_DARK_MAGE, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new LongRangeTestAbility());
        super.abilities = abilities;
    }
}
