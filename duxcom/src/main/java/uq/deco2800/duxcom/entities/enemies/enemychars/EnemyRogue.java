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
 * Created by jay-grant on 25/08/2016.
 * <p>
 * Class containing Enemy 'Rogue' stats
 */
public class EnemyRogue extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Rogue'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_ROGUE);

    /**
     * Creates a new EnemyRogue.
     *
     * @param x The x coordinate of the Rogue.
     * @param y The y coordinate of the Rogue.
     */
    public EnemyRogue(int x, int y) {
        super(EntityType.ENEMY_ROGUE, x, y);
        setStats(EnemyType.ENEMY_ROGUE, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        super.abilities = abilities;
    }
}
