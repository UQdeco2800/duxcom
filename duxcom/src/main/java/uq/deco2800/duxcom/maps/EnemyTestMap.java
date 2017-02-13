package uq.deco2800.duxcom.maps;


import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.dynamics.WaterBarrel;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyGrunt;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemySupport;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.tiles.*;

/**
 * Created by jay-grant on 25/08/2016.
 *
 * An empty map for testing all things relating to Enemy entities
 */
public class EnemyTestMap extends AbstractGameMap {

    private static final int MAP_HEIGHT = 25;
    private static final int MAP_WIDTH = 25;

    /**
     * Creates the enemy test map
     *
     * @param name name of the map
     */
    public EnemyTestMap(String name) {
        super.name = name;
        super.mapType = MapType.ENEMY_TEST;
        super.initialiseEmptyCheckeredMap(TileType.GRASS_1, TileType.GRASS_2, MAP_WIDTH, MAP_HEIGHT);

        addStackableScenery(SceneryType.RS_ROCK_VANTAGE, 24, 11);
        addStackableScenery(SceneryType.RS_ROCK_VANTAGE, 24, 11);
        addStackableScenery(SceneryType.RS_ROCK_VANTAGE, 22, 11);
        addStackableScenery(SceneryType.RS_ROCK_VANTAGE, 22, 11);

        // Spawn hero 1 - a knight
        addHero(new Knight(24, 11));

        addEntity(new WaterBarrel(23, 11));

        // Spawn hero 2 - another knight
        addHero(new Knight(12, 11));

        addLiveTile(new LavaTile(12, 13));
        addLiveTile(new LavaTile(11, 13));
        addLiveTile(new LavaTile(12, 14));
        addLiveTile(new LavaTile(11, 14));

        AbstractEnemy badDude = new EnemyKnight(3, 11);
        addEnemy(badDude);
        badDude = new EnemyKnight(0, 0);
        addEnemy(badDude);
        badDude = new EnemyGrunt(14,16);
        addEnemy(badDude);
        badDude = new EnemySupport(10,10);
        addEnemy(badDude);

        // Add an objective to kill 1 knights, and one to hit a single knight
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 1);
        addEnemyHitObjective(EnemyType.ENEMY_KNIGHT);

        // Try to add objective to kill 3 warlocks - won't happen!
        addEnemyKillObjective(EnemyType.ENEMY_KNIGHT, 3);

        // Add a score objective for 1000 points
        addScoreObjective(1000);

        // Try to add another score objective - won't work!
        addScoreObjective(100);

        // Add a movement objective
        //addMovementObjective(20, 9);

        // Add a protection objective
        addProtectionObjective(EntityType.KNIGHT, 1);
    }
}
