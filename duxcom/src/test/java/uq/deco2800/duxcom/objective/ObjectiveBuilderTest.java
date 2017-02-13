package uq.deco2800.duxcom.objective;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.dynamics.DiscoDoggo;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.EnemyHitObjective;
import uq.deco2800.duxcom.objectives.EnemyKillObjective;
import uq.deco2800.duxcom.objectives.EnemyObjectiveType;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * Created by Tom B on 26/08/2016.
 */
public class ObjectiveBuilderTest {

    @Test
    public void testObjectiveBuilderCreation() {
        // Newly created builder should start off at level 1 with no
        // objectives or level 1 base values
        ObjectiveBuilder objBuilder = new ObjectiveBuilder(null);
        Assert.assertEquals(0, objBuilder.getObjectives().size());
    }

    @Test
    public void testAddEnemyObjectiveNotExistingAlready() {
        ObjectiveBuilder objBuilder = new ObjectiveBuilder(null);
        List<Objective> testObjectives = new ArrayList<>();

        // Enemy Kill Objectives
        testObjectives.add(new EnemyKillObjective(EnemyType.ENEMY_CAVALIER, 10));
        objBuilder.add(EnemyType.ENEMY_CAVALIER, 10);
        Assert.assertEquals(testObjectives, objBuilder.getObjectives());

        // Adding another one
        testObjectives.add(new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5));
        objBuilder.add(EnemyType.ENEMY_KNIGHT, 5);
        Assert.assertEquals(testObjectives, objBuilder.getObjectives());
    }

    @Test
    public void testAddScoreObjectiveNotExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        ScoreSystem s = new ScoreSystem();

        // Preparation
        builder.add(s, 10000);

        // Testing
        Assert.assertTrue(builder.contains(s));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddScoreObjectiveAlreadyExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        ScoreSystem s = new ScoreSystem();

        // Preparation
        builder.add(s, 10000);
        builder.add(s, 10000);

        // Testing
        Assert.assertTrue(builder.contains(s));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddProtectionObjectiveNotExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);

        // Preparation
        builder.add(EntityType.KNIGHT, 1);

        // Testing
        Assert.assertTrue(builder.contains(EntityType.KNIGHT));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddProtectionObjectiveAlreadyExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);

        // Preparation
        builder.add(EntityType.KNIGHT, 1);
        builder.add(EntityType.KNIGHT, 1);

        // Testing
        Assert.assertTrue(builder.contains(EntityType.KNIGHT));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testNotContainsProtectionObjectiveSpecified() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        builder.add(EntityType.KNIGHT, 2);
        Assert.assertFalse(builder.contains(EntityType.ARCHER));
    }

    @Test
    public void testAddEnemyHitObjectiveNotExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);

        // Preparation
        builder.add(EnemyType.ENEMY_CAVALIER);
        builder.add(EnemyType.ENEMY_CAVALIER);

        // Testing
        Assert.assertTrue(builder.contains(new EnemyObjectiveType(EnemyType.ENEMY_CAVALIER, false)));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddEnemyHitObjectiveAlreadyExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);

        // Preparation
        builder.add(EnemyType.ENEMY_CAVALIER);
        builder.add(EnemyType.ENEMY_CAVALIER);
        // Testing
        Assert.assertTrue(builder.contains(new EnemyObjectiveType(EnemyType.ENEMY_CAVALIER, false)));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddItemObjectiveNotExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        ItemType sword = ItemType.SWORD;

        // Preparation
        builder.add(sword, 5);

        // Testing
        Assert.assertTrue(builder.contains(sword));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddItemObjectiveAlreadyExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        ItemType sword = ItemType.SWORD;

        // Preparation
        builder.add(sword, 5);
        builder.add(sword, 5);

        // Testing
        Assert.assertTrue(builder.contains(sword));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddMovementObjectiveNotExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        ObjectiveCoordinate position1 = new ObjectiveCoordinate(1, 2);

        // Preparation
        builder.add(1, 2);

        // Testing
        Assert.assertTrue(builder.contains(position1));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testAddMovementObjectiveAlreadyExisting() {
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        ObjectiveCoordinate position1 = new ObjectiveCoordinate(1, 2);

        // Preparation
        builder.add(1, 2);
        builder.add(1, 2);

        // Testing
        Assert.assertTrue(builder.contains(position1));
        Assert.assertEquals(1, builder.getObjectives().size());
    }

    @Test
    public void testContainsObjectiveLevel1() {
        ObjectiveBuilder objBuilder = new ObjectiveBuilder(null);

        // Preparation
        objBuilder.add(EnemyType.ENEMY_GRUNT, 5);

        // Testing
        Assert.assertTrue(objBuilder.contains(new EnemyObjectiveType(EnemyType.ENEMY_GRUNT, true)));
    }

    @Test
    public void testClearObjectives() {
        ObjectiveBuilder objBuilder = new ObjectiveBuilder(null);
        objBuilder.add(EnemyType.ENEMY_GRUNT, 5);
        Assert.assertTrue(objBuilder.contains(new EnemyObjectiveType(EnemyType.ENEMY_GRUNT, true)));
        objBuilder.clearObjectives();
        Assert.assertFalse(objBuilder.contains(new EnemyObjectiveType(EnemyType.ENEMY_GRUNT, true)));
        Assert.assertTrue(objBuilder.getObjectives().isEmpty());
    }

}
