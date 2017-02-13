package uq.deco2800.duxcom.entities.enemies.enemychars;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.EnemyShortTestAbility;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.EnemyDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyType;

import java.util.ArrayList;

/**
 * Created by jay-grant on 24/08/2016.
 * <p>
 * Class for holding attribute values for Knight
 */
public class EnemyKnight extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Knight'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_KNIGHT);

    /**
     * Creates a new EnemyKnight.
     *
     * @param x The x coordinate of the Knight.
     * @param y The y coordinate of the Knight.
     */
    public EnemyKnight(int x, int y) {
        super(EntityType.ENEMY_KNIGHT, x, y);
        setStats(EnemyType.ENEMY_KNIGHT, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new EnemyShortTestAbility());
        super.abilities = abilities;

        super.setHealth(10);
    }
}
