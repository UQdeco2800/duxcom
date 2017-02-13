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
 * Copyright infringement
 */
public class EnemyGrunt extends AbstractEnemy {

    /**
     * Attributes of Enemy 'Grunt'
     */
    private static final EnemyDataClass enemyDataClass =
            DataRegisterManager.getEnemyDataRegister().getData(EnemyType.ENEMY_GRUNT);

    /**
     * Creates a new EnemyGrunt.
     *
     * @param x The x coordinate of the Grunt.
     * @param y The y coordinate of the Grunt.
     */
    public EnemyGrunt(int x, int y) {
        super(EntityType.ENEMY_GRUNT, x, y);
        setStats(EnemyType.ENEMY_GRUNT, enemyDataClass);

        ArrayList<AbstractAbility> abilities = new ArrayList<>();
        abilities.add(new ShortRangeTestAbility());
        super.abilities = abilities;
    }
}
