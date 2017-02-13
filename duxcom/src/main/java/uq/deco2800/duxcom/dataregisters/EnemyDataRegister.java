package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAttitude;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyMode;

/**
 * Links each enemy type from the EnemyType enum with a set of data from EnemyDataClass.
 *
 * @author Alex McLean
 */
public class EnemyDataRegister extends AbstractDataRegister<EnemyType, EnemyDataClass> {
    /**
     * Package private constructor
     * <p>
     * Should only be called by DataRegisterManager
     */
    EnemyDataRegister() {
        super();

        linkDataToType(EnemyType.ENEMY_KNIGHT,
                new EnemyDataClass("Dark Knight", "enemy_knight", EnemyMode.NORMAL,
                        EnemyAttitude.BALANCED, 12, 50, 0, 10, 10, 50, 10, 5, 1));

        linkDataToType(EnemyType.ENEMY_SUPPORT,
                new EnemyDataClass("Plague Doctor", "enemy_support", EnemyMode.NORMAL,
                        EnemyAttitude.SUPPORTIVE, 12, 10, 0, 10, 10, 50, 10, 5, 1));

        linkDataToType(EnemyType.ENEMY_TANK,
                new EnemyDataClass("Paladin", "enemy_tank", EnemyMode.NORMAL,
                        EnemyAttitude.DEFENSIVE, 18, 15, 0, 25, 15, 50, 10, 3, 1));

        linkDataToType(EnemyType.ENEMY_ARCHER,
                new EnemyDataClass("Enemy Archer", "enemy_archer", EnemyMode.NORMAL,
                        EnemyAttitude.EVASIVE, 12, 60, 0.15, 10, 10, 50, 10, 8, 1));

        linkDataToType(EnemyType.ENEMY_CAVALIER,
                new EnemyDataClass("Enemy Charger", "enemy_cavalier", EnemyMode.NORMAL,
                        EnemyAttitude.BALANCED, 12, 45, 0.1, 8, 8, 50, 10, 7, 1));

        linkDataToType(EnemyType.ENEMY_DARK_MAGE,
                new EnemyDataClass("Necromancer", "enemy_dark_mage", EnemyMode.NORMAL,
                        EnemyAttitude.BALANCED, 12, 75, 0.1, 5, 10, 50, 10, 8, 1));

        linkDataToType(EnemyType.ENEMY_GRUNT,
                new EnemyDataClass("Grunt", "enemy_grunt", EnemyMode.NORMAL,
                        EnemyAttitude.EVASIVE, 12, 5, 0, 5, 5, 50, 10, 3, 1));

        linkDataToType(EnemyType.ENEMY_ROGUE,
                new EnemyDataClass("Rogue Bandit", "enemy_rogue", EnemyMode.NORMAL,
                        EnemyAttitude.BALANCED, 12, 75, 0.15, 10, 8, 50, 10, 8, 1));

        linkDataToType(EnemyType.ENEMY_BEAR,
                new EnemyDataClass("Enemy Bear", "bear", EnemyMode.NORMAL,
                        EnemyAttitude.BALANCED, 12, 45, 0.1, 8, 8, 50, 10, 7, 1));

        linkDataToType(EnemyType.ENEMY_WOLF,
                new EnemyDataClass("Enemy Wolf", "fox", EnemyMode.NORMAL,
                        EnemyAttitude.BALANCED, 12, 50, 0, 10, 10, 50, 10, 5, 1));

    }

}
