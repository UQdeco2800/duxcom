package uq.deco2800.duxcom.objective;
import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.dynamics.DiscoDoggo;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * Created by Tom B on 25/08/2016.
 * Contains tests for testing all sorts of objectives.
 */
public class ObjectiveTest {

    // Enemy Kill Objective
    @Test
    public void testEnemyKillObjectiveCreation() {
        // Create a new enemy kill objective
        EnemyKillObjective eObj = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);

        // Does enemy type, kill target, description and met status match?
        Assert.assertEquals(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, true), eObj.getObjectiveTarget()); // Enemy Type
        Assert.assertEquals(5, eObj.getObjectiveValue()); // Kill target
        Assert.assertEquals("Kill 5 enemy knight(s)", eObj.getDescription()); // Description
        Assert.assertFalse(eObj.met()); // Met status
        Assert.assertEquals(0, eObj.getDisplayed());
        eObj.incrementDisplayed();
        Assert.assertEquals(1, eObj.getDisplayed());
    }

    @Test
    public void testEnemyKillObjectiveComplete() {
        // Create a new enemy kill objective
        EnemyKillObjective eObj = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);

        // Testing complete() method
        Assert.assertFalse(eObj.met()); // Met status initially
        eObj.complete();
        Assert.assertTrue(eObj.met()); // Met status should now be true
    }

    @Test
    public void testEqualsAndHashCodeMethodEnemyKillObjective() {
        // TEST OBJECTIVES
        // Equal cases
        EnemyKillObjective eObj1 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);
        EnemyKillObjective eObj2 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 10);
        EnemyKillObjective eObj3 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 3);

        // Not equal cases
        EnemyKillObjective eObj4 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 3);
        EnemyKillObjective eObj5 = new EnemyKillObjective(EnemyType.ENEMY_DARK_MAGE, 3);
        EnemyKillObjective eObj6 = new EnemyKillObjective(EnemyType.ENEMY_ARCHER, 2);
        Integer i = new Integer(2);

        // equals() should return true, and hash codes should be equal
        // Reflexivity
        Assert.assertTrue(eObj1.equals(eObj1));
        Assert.assertEquals(eObj1.hashCode(), eObj1.hashCode());

        // Associativity
        Assert.assertTrue(eObj1.equals(eObj2));
        Assert.assertEquals(eObj1.hashCode(), eObj2.hashCode());
        Assert.assertTrue(eObj2.equals(eObj1));
        Assert.assertEquals(eObj2.hashCode(), eObj1.hashCode());

        // Transitivity
        Assert.assertTrue(eObj1.equals(eObj2));
        Assert.assertEquals(eObj1.hashCode(), eObj2.hashCode());
        Assert.assertTrue(eObj2.equals(eObj3));
        Assert.assertEquals(eObj2.hashCode(), eObj3.hashCode());
        Assert.assertTrue(eObj1.equals(eObj3));
        Assert.assertEquals(eObj1.hashCode(), eObj3.hashCode());

        // equals() should return false, and hash codes shouldn't match

        // Different types (not instance of Objective)
        Assert.assertFalse(eObj1.equals(i));

        // Associativity
        Assert.assertFalse(eObj4.equals(eObj5));
        Assert.assertNotEquals(eObj4.hashCode(), eObj5.hashCode());
        Assert.assertFalse(eObj5.equals(eObj4));
        Assert.assertNotEquals(eObj5.hashCode(), eObj4.hashCode());

        // Transitivity
        Assert.assertFalse(eObj4.equals(eObj5));
        Assert.assertNotEquals(eObj4.hashCode(), eObj5.hashCode());
        Assert.assertFalse(eObj5.equals(eObj6));
        Assert.assertNotEquals(eObj5.hashCode(), eObj6.hashCode());
        Assert.assertFalse(eObj4.equals(eObj6));
        Assert.assertNotEquals(eObj4.hashCode(), eObj6.hashCode());
    }

    @Test
    public void testToStringMethod() {
        EnemyKillObjective eObj1 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);
        EnemyKillObjective eObj2 = new EnemyKillObjective(EnemyType.ENEMY_DARK_MAGE, 5);
        EnemyKillObjective eObj3 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 10);
        EnemyKillObjective eObj4 = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 10);
        eObj4.complete();

        // Test string
        String eObj1Expected = "Target Type: ENEMY_KNIGHT, Value: 5; Description: Kill 5 enemy knight(s); Met: false";

        // Equal:
        Assert.assertEquals(eObj1Expected, eObj1.toString());

        // Not equal:
        // Different type
        Assert.assertNotEquals(eObj1Expected, eObj2.toString());

        // Different value
        Assert.assertNotEquals(eObj1Expected, eObj3.toString());

        // Different met status
        Assert.assertNotEquals(eObj1Expected, eObj4.toString());

        // Item Objective (not an EnemyKillObjective)
        ItemObjective io = new ItemObjective(ItemType.POTION, 2);
        String test = "Target Type: POTION, Value: 2; Description: " +
                io.getDescription() + "; Met: false";
        Assert.assertEquals(test, io.toString());
    }

    // ScoreObjective
    @Test
    public void testCreateScoreObjective() {
        ScoreSystem scoring = new ScoreSystem();
        ScoreObjective s = new ScoreObjective(scoring, 2000);
        Assert.assertEquals(2000, s.getObjectiveValue());
        Assert.assertEquals("Reach 2000 points", s.getDescription());
        Assert.assertEquals(scoring, s.getObjectiveTarget());
        Assert.assertFalse(s.met());
    }

    // ProtectionObjective
    @Test
    public void testCreateProtectionObjective() {
        ProtectionObjective p = new ProtectionObjective(EntityType.KNIGHT, 1);
        Assert.assertEquals(EntityType.KNIGHT, p.getObjectiveTarget());
        Assert.assertEquals(1, p.getObjectiveValue());
        String value = p.getObjectiveValue().toString();
        String type = p.getObjectiveTarget().toString().toLowerCase().replace('_', ' ');
        Assert.assertEquals("Protect at least " + value + " " + type + " from being destroyed", p.getDescription());
        Assert.assertTrue(p.met());
        p.failed();
        Assert.assertFalse(p.met());
    }

    @Test
    public void testCreateEnemyHitObjective() {
        // Create a new enemy kill objective
        EnemyHitObjective eObj = new EnemyHitObjective(EnemyType.ENEMY_KNIGHT);

        // Does enemy type, kill target, description and met status match?
        Assert.assertEquals(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, false), eObj.getObjectiveTarget()); // Enemy Type
        Assert.assertEquals(false, eObj.getObjectiveValue()); // Redundancy check
        String testDescription = "Hit an enemy knight";
        Assert.assertEquals(testDescription, eObj.getDescription()); // Description
        Assert.assertFalse(eObj.met()); // Met status
        eObj.hit();
        Assert.assertTrue(eObj.met()); // SHould be true after hitting enemy once
    }

    @Test
    public void testEnemyHitToStringMethod() {
        String test = "Target Type: ENEMY_KNIGHT, Value: false; " +
                "Description: Hit an enemy knight; Met: false";
        EnemyHitObjective eh = new EnemyHitObjective(EnemyType.ENEMY_KNIGHT);
        Assert.assertEquals(test, eh.toString());
    }

    // Item Objective
    @Test
    public void testMovementObjectiveCreation() {
        // Create a new Movement Objective
        ObjectiveCoordinate position = new ObjectiveCoordinate(1, 2);
        MovementObjective moveObj = new MovementObjective(position);

        // Does Tile, target value, description and met status match?
        Assert.assertEquals(position, moveObj.getObjectiveTarget());
        Assert.assertEquals(false, moveObj.getObjectiveValue());
        Assert.assertEquals("Reach the tile at position (1, 2)", moveObj.getDescription());
        Assert.assertFalse(moveObj.met());
    }

    @Test
    public void testMovementObjectiveComplete() {
        // Create a new Movement Objective
        ObjectiveCoordinate position = new ObjectiveCoordinate(1, 2);
        MovementObjective moveObj = new MovementObjective(position);

        // Testing complete() method
        Assert.assertFalse(moveObj.met());
        moveObj.complete();
        Assert.assertTrue(moveObj.met());
    }

    @Test
    public void testEqualsAndHashCodeMethodMovementObjective() {
        // Creating positions for MovementObjectives
        ObjectiveCoordinate oc1 = new ObjectiveCoordinate(1, 2);
        ObjectiveCoordinate oc2 = new ObjectiveCoordinate(3, 4);
        ObjectiveCoordinate oc3 = new ObjectiveCoordinate(5, 6);
        ObjectiveCoordinate oc4 = new ObjectiveCoordinate(7, 8);

        // Creating objectives for equal cases
        MovementObjective obj1 = new MovementObjective(oc1);
        MovementObjective obj2 = new MovementObjective(oc1);
        MovementObjective obj3 = new MovementObjective(oc1);

        // Creating objectives for inequivalent cases
        MovementObjective obj4 = new MovementObjective(oc2);
        MovementObjective obj5 = new MovementObjective(oc3);
        MovementObjective obj6 = new MovementObjective(oc1);
        Integer i = new Integer(4);

        // Reflexivity
        Assert.assertTrue(obj1.equals(obj1));
        Assert.assertEquals(obj1.hashCode(), obj1.hashCode());

        // Associativity
        Assert.assertTrue(obj2.equals(obj3));
        Assert.assertEquals(obj2.hashCode(), obj3.hashCode());
        Assert.assertTrue(obj3.equals(obj2));
        Assert.assertEquals(obj3.hashCode(), obj2.hashCode());

        // Transitivity
        Assert.assertTrue(obj1.equals(obj2));
        Assert.assertEquals(obj1.hashCode(), obj2.hashCode());
        Assert.assertTrue(obj2.equals(obj3));
        Assert.assertEquals(obj2.hashCode(), obj3.hashCode());
        Assert.assertTrue(obj1.equals(obj3));
        Assert.assertEquals(obj1.hashCode(), obj3.hashCode());

        // Different types (should return false)
        Assert.assertFalse(obj4.equals(i));

        // Associativity
        Assert.assertFalse(obj4.equals(obj5));
        Assert.assertNotEquals(obj4.hashCode(), obj5.hashCode());
        Assert.assertFalse(obj5.equals(obj4));
        Assert.assertNotEquals(obj5.hashCode(), obj4.hashCode());

        // Transitivity
        Assert.assertFalse(obj4.equals(obj5));
        Assert.assertNotEquals(obj4.hashCode(), obj5.hashCode());
        Assert.assertFalse(obj5.equals(obj6));
        Assert.assertNotEquals(obj5.hashCode(), obj6.hashCode());
        Assert.assertFalse(obj4.equals(obj6));
        Assert.assertNotEquals(obj4.hashCode(), obj6.hashCode());
    }

    // Item Objective
    @Test
    public void testItemObjectiveCreation() {
        // Create a new Item Objective
        ItemObjective itemObj = new ItemObjective(ItemType.SWORD, 2);
        
        // Does sword type, target value, description and met status match?
        Assert.assertEquals(ItemType.SWORD, itemObj.getObjectiveTarget());
        Assert.assertEquals(2, itemObj.getObjectiveValue());
        Assert.assertEquals("Collect 2 swords.", itemObj.getDescription());
        Assert.assertFalse(itemObj.met());
    }

    @Test
    public void testItemObjectiveComplete() {
        // Create a new Item Objective
        ItemObjective itemObj = new ItemObjective(ItemType.SWORD, 2);

        // Testing complete() method
        Assert.assertFalse(itemObj.met());
        itemObj.complete();
        Assert.assertTrue(itemObj.met());
    }

    @Test
    public void testEqualsAndHashCodeMethodItemObjective() {
        ItemType ironSword = ItemType.SWORD;
        ItemType bow = ItemType.BOW;
        ItemType axe = ItemType.AXE;
        ItemType hammer = ItemType.HAMMER;

        // Creating objectives for equal cases
        ItemObjective obj1 = new ItemObjective(ironSword, 1);
        ItemObjective obj2 = new ItemObjective(ironSword, 2);
        ItemObjective obj3 = new ItemObjective(ironSword, 4);

        // Creating objectives for inequivalent cases
        ItemObjective obj4 = new ItemObjective(axe, 8);
        ItemObjective obj5 = new ItemObjective(hammer, 16);
        ItemObjective obj6 = new ItemObjective(bow, 32);
        Integer i = new Integer(4);

        // Reflexivity
        Assert.assertTrue(obj1.equals(obj1));
        Assert.assertEquals(obj1.hashCode(), obj1.hashCode());

        // Associativity
        Assert.assertTrue(obj2.equals(obj3));
        Assert.assertEquals(obj2.hashCode(), obj3.hashCode());
        Assert.assertTrue(obj3.equals(obj2));
        Assert.assertEquals(obj3.hashCode(), obj2.hashCode());

        // Transitivity
        Assert.assertTrue(obj1.equals(obj2));
        Assert.assertEquals(obj1.hashCode(), obj2.hashCode());
        Assert.assertTrue(obj2.equals(obj3));
        Assert.assertEquals(obj2.hashCode(), obj3.hashCode());
        Assert.assertTrue(obj1.equals(obj3));
        Assert.assertEquals(obj1.hashCode(), obj3.hashCode());

        // Different types (should return false)
        Assert.assertFalse(obj4.equals(i));

        // Associativity
        Assert.assertFalse(obj4.equals(obj5));
        Assert.assertNotEquals(obj4.hashCode(), obj5.hashCode());
        Assert.assertFalse(obj5.equals(obj4));
        Assert.assertNotEquals(obj5.hashCode(), obj4.hashCode());

        // Transitivity
        Assert.assertFalse(obj4.equals(obj5));
        Assert.assertNotEquals(obj4.hashCode(), obj5.hashCode());
        Assert.assertFalse(obj5.equals(obj6));
        Assert.assertNotEquals(obj5.hashCode(), obj6.hashCode());
        Assert.assertFalse(obj4.equals(obj6));
        Assert.assertNotEquals(obj4.hashCode(), obj6.hashCode());
    }


}
