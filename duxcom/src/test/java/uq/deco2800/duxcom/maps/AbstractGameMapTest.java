package uq.deco2800.duxcom.maps;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.RealWoodStack;
import uq.deco2800.duxcom.entities.WoodStack;
import uq.deco2800.duxcom.entities.dynamics.DiscoDoggo;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyArcher;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyDarkMage;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.heros.*;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.util.Array2D;

import static org.junit.Assert.*;
import static uq.deco2800.duxcom.entities.enemies.EnemyType.*;

/**
 * Tests AbstractGameMap
 *
 * @author Alex McLean
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractGameMapTest {

    private AbstractGameMap abstractGameMap;

    @Test
    public void testBasicMethods() {
        abstractGameMap = new EmptyMap("test");
        assertEquals("test", abstractGameMap.getName());
        assertNull(abstractGameMap.getCurrentTurnHero());

        assertEquals(TileType.GRASS_1, abstractGameMap.getTile(0,0).getTileType());
        assertEquals(TileType.GRASS_2, abstractGameMap.getTile(0,1).getTileType());
        abstractGameMap.turnTick();

        abstractGameMap.setTile(0,0, TileType.GRASS_3);
        assertEquals(TileType.GRASS_3, abstractGameMap.getTile(0,0).getTileType());
        assertEquals(TileType.GRASS_3, abstractGameMap.getTile(0, 0).getTileType());

        assertEquals(25, abstractGameMap.getWidth());
        assertEquals(25, abstractGameMap.getHeight());

        assertEquals(HeroManager.class, abstractGameMap.getHeroManager().getClass());
        assertEquals(EnemyManager.class, abstractGameMap.getEnemyManager().getClass());
        assertEquals(Array2D.class, abstractGameMap.getTiles().getClass());

        Entity woodStack = new WoodStack(0,0);
        Entity realWoodStack = new RealWoodStack(1,1);
        abstractGameMap.addEntity(woodStack);
        abstractGameMap.addEntity(realWoodStack);
        assertNotNull(abstractGameMap.getEntities());

        AbstractHero hero1 = new Archer(3,3);
        assertTrue(abstractGameMap.addHero(hero1));

        AbstractEnemy enemy1 = new EnemyArcher(4,4);
        assertTrue(abstractGameMap.addEnemy(enemy1));

        Float[][] movementCost = new Float[25][25];
        movementCost[5][5] = 1.0f;
        movementCost[7][7] = 1.0f;
        hero1.setMovementCost(movementCost);

    }

    // Tests below added by Thomas Bricknell, 9/10/16
    @Test
    public void testObjectiveListInitialisation() {
        abstractGameMap = new EmptyMap("test");
        Assert.assertEquals(0, abstractGameMap.getObjectives().size());
    }

    @Test
    public void testAddingObjectivesSuccessfully()  {
        abstractGameMap = new EmptyMap("test");
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        abstractGameMap.addEnemy(new EnemyKnight(1, 2));
        abstractGameMap.addEnemy(new EnemyDarkMage(3, 4));
        abstractGameMap.addEnemy(new EnemyDarkMage(5, 6));
        abstractGameMap.addEntity(new DiscoDoggo(1, 2));

        // Enemy Hit Objective
        builder.add(ENEMY_KNIGHT);
        boolean result = abstractGameMap.addEnemyHitObjective(ENEMY_KNIGHT);
        Assert.assertTrue(result);
        Assert.assertEquals(builder.getObjectives(), abstractGameMap.getObjectives());

        // Enemy Kill Objective
        builder.add(ENEMY_DARK_MAGE, 2);
        result = abstractGameMap.addEnemyKillObjective(ENEMY_DARK_MAGE, 2);
        Assert.assertTrue(result);
        Assert.assertEquals(builder.getObjectives(), abstractGameMap.getObjectives());

        // Protection Objective
        builder.add(EntityType.KNIGHT, 1);
        abstractGameMap.addHero(new Knight(1, 2));
        result = abstractGameMap.addProtectionObjective(EntityType.KNIGHT, 1);
        Assert.assertTrue(result);
        Assert.assertEquals(builder.getObjectives(), abstractGameMap.getObjectives());

        // Score Objective
        builder.add(abstractGameMap.getScoreSystem(), 1000);
        result = abstractGameMap.addScoreObjective(1000);
        Assert.assertTrue(result);
        Assert.assertEquals(builder.getObjectives(), abstractGameMap.getObjectives());
    }

    @Test
    public void testAddingDuplicateEnemyKillObjective() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addEnemy(new EnemyKnight(1, 2));
        boolean result = abstractGameMap.addEnemyKillObjective(ENEMY_KNIGHT, 1);
        Assert.assertTrue(result);

        // Attempt to add same objective again
        result = abstractGameMap.addEnemyKillObjective(ENEMY_KNIGHT, 1);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingDuplicateEnemyHitObjective() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addEnemy(new EnemyKnight(1, 2));
        boolean result = abstractGameMap.addEnemyHitObjective(ENEMY_KNIGHT);
        Assert.assertTrue(result);

        // Attempt to add same objective
        result = abstractGameMap.addEnemyHitObjective(ENEMY_KNIGHT);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingDuplicateProtectionObjective() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addHero(new Knight(1, 2));
        boolean result = abstractGameMap.addProtectionObjective(EntityType.KNIGHT, 1);
        Assert.assertTrue(result);

        // Attempt to add same objective
        result = abstractGameMap.addProtectionObjective(EntityType.KNIGHT, 1);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingScoreObjectiveAlreadyExisting() {
        abstractGameMap = new EmptyMap("test");
        boolean result = abstractGameMap.addScoreObjective(1000);
        Assert.assertTrue(result);

        // Attempt to add another score objective
        result = abstractGameMap.addScoreObjective(100);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingInvalidEnemyKillObjective() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addEnemy(new EnemyArcher(1, 2));
        boolean result = abstractGameMap.addEnemyKillObjective(ENEMY_ARCHER, 2);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingEnemyKillObjectiveNoEnemies() {
        abstractGameMap = new EmptyMap("test");
        boolean result = abstractGameMap.addEnemyKillObjective(ENEMY_CAVALIER, 10);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingEnemyKillObjectiveNonExistentType() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addEnemy(new EnemyDarkMage(1, 3));
        boolean result = abstractGameMap.addEnemyKillObjective(ENEMY_CAVALIER, 2);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingEnemyHitObjectiveNoEnemies() {
        abstractGameMap = new EmptyMap("test");
        boolean result = abstractGameMap.addEnemyHitObjective(ENEMY_CAVALIER);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingEnemyHitObjectiveNonExistentType() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addEnemy(new EnemyDarkMage(1, 3));
        boolean result = abstractGameMap.addEnemyHitObjective(ENEMY_CAVALIER);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingProtectionObjectiveNoEntities() {
        abstractGameMap = new EmptyMap("test");
        boolean result = abstractGameMap.addProtectionObjective(EntityType.KNIGHT, 1);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddingProtectionObjectiveNonExistentEntity() {
        abstractGameMap = new EmptyMap("test");
        abstractGameMap.addHero(new Warlock(1, 2));
        boolean result = abstractGameMap.addProtectionObjective(EntityType.KNIGHT, 1);
        Assert.assertFalse(result);
    }

}