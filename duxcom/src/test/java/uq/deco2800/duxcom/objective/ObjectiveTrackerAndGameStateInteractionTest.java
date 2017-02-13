package uq.deco2800.duxcom.objective;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.dynamics.DiscoDoggo;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.maps.EmptyMap;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * Tests the interaction between an objective tracker and the game state it is monitoring
 * Created by Tom B on 4/09/2016.
 */

public class ObjectiveTrackerAndGameStateInteractionTest {

    @Test
    public void testGetAndDeleteObservers() {
        ObjectiveBuilder ob = new ObjectiveBuilder(new EmptyMap("text"));
        GameState gs = new GameState(ob.getObjectives());
        ObjectiveTracker ot = new ObjectiveTracker(gs, ob.getObjectives());
        List<Observer> testObservers = new ArrayList<>();
        testObservers.add(ot);
        Assert.assertEquals(testObservers, gs.getObservers());
        gs.deleteObserver(ot);
        Assert.assertEquals(new ArrayList<Observer>(), gs.getObservers());
    }

    @Test
    public void testInteractionTrackerAndState() {
        // Preparation
        ObjectiveBuilder b = new ObjectiveBuilder(new EmptyMap("test"));

        // Extra resources for objectives
        EnemyType enemyType = EnemyType.ENEMY_KNIGHT;
        EnemyType enemyTypeTwo = EnemyType.ENEMY_ARCHER;
        ScoreSystem scoring = new ScoreSystem();

        // Build objectives and obtain them
        b.add(enemyType);
        b.add(enemyTypeTwo);
        b.add(scoring, 1000);
        b.add(EntityType.KNIGHT, 1);

        List<Objective> objectives = b.getObjectives();

        // Create game state - check that stats are properly initialised
        GameState gs = new GameState(objectives);
        HashMap<Object, Object> testStatistics = new HashMap<>();
        for (Objective o: objectives) {
            if (o instanceof EnemyKillObjective) {
                EnemyKillObjective e = (EnemyKillObjective)o;
                testStatistics.put(e.getObjectiveTarget(), 0);
            }  else if (o instanceof ScoreObjective) {
                ScoreObjective s = (ScoreObjective)o;
                testStatistics.put(s.getObjectiveTarget(), 0);
            } else if (o instanceof ProtectionObjective) {
                ProtectionObjective p = (ProtectionObjective)o;
                testStatistics.put(p.getObjectiveTarget(), p.getObjectiveValue());
            } else if (o instanceof EnemyHitObjective) {
                EnemyHitObjective e = (EnemyHitObjective)o;
                testStatistics.put(e.getObjectiveTarget(), e.met());
            }
        }
        Assert.assertEquals(testStatistics.size(), gs.getStatistics().size());

        // Create tracker - check that it is monitoring the specified game state
        ObjectiveTracker ot = new ObjectiveTracker(gs, objectives);
        Assert.assertEquals(gs, ot.getGameState());
        Assert.assertEquals(objectives, ot.getObjectives());

        // Update different objectives

        // ROUND 1
        // Hit nobody and gain 200 points
        scoring.changeScore(200, '+');
        gs.updateScoreGoals(scoring);

        // Check to see if any are met - should all be false (apart from protection objective)
        for (Objective o: ot.getObjectives()) {
            if (!(o instanceof ProtectionObjective)) {
                Assert.assertFalse(o.met());
            } else {
                Assert.assertTrue(o.met());
            }

        }

        // Check to see if all are met or not - should be false
        Assert.assertFalse(ot.allObjectivesMet());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 25) < 0.001);

        // ROUND 2
        // Hit an archer and a knight, gain 200 points
        gs.updateEnemyHitGoals(enemyTypeTwo);
        gs.updateEnemyHitGoals(enemyType);
        scoring.changeScore(200, '+');
        gs.updateScoreGoals(scoring);

        // Check to see if any are met - should be true (enemies)
        for (Objective o: ot.getObjectives()) {
            if (o.getObjectiveTarget().equals(new EnemyObjectiveType(enemyType, false))||
                    o.getObjectiveTarget().equals(new EnemyObjectiveType(enemyTypeTwo, false)) ||
                    (o instanceof ProtectionObjective)) {
                Assert.assertTrue(o.met());
            } else {
                Assert.assertFalse(o.met());
            }
        }

        // Check to see if all are met or not - should be false
        Assert.assertFalse(ot.allObjectivesMet());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 75) < 0.001);

        // ROUND 3
        // Gain 400 points
        scoring.changeScore(400, '+');
        gs.updateScoreGoals(scoring);

        // Check to see if any are met - should be true (money)
        // Check to see if all are met or not - should be false
        for (Objective o: ot.getObjectives()) {
            if (o.getObjectiveTarget().equals(new EnemyObjectiveType(enemyTypeTwo, false))||
                    o.getObjectiveTarget().equals(new EnemyObjectiveType(enemyType, false)) ||
                    (o instanceof ProtectionObjective)) {
                Assert.assertTrue(o.met());
            } else {
                Assert.assertFalse(o.met());
            }
        }

        // Check to see if all are met or not - should be false
        Assert.assertFalse(ot.allObjectivesMet());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 75) < 0.001);

        // ROUND 4
        // Gain 200 points
        scoring.changeScore(200, '+');
        gs.updateScoreGoals(scoring);

        // Check to see if any are met - should be true (score)
        for (Objective o: ot.getObjectives()) {
            Assert.assertTrue(o.met());
        }

        // Check to see if all are met or not - should be true
        Assert.assertTrue(ot.allObjectivesMet());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 100) < 0.001);
    }

    @Test
    public void testMeetingObjectivesProtectionHitMovementItem() {
        // Preparation
        ObjectiveBuilder b = new ObjectiveBuilder(null);

        // Extra resources for objectives
        EnemyType enemyType = EnemyType.ENEMY_KNIGHT;
        DiscoDoggo dd = new DiscoDoggo(1, 2);
        ItemType type = ItemType.SWORD;
        ObjectiveCoordinate position1 = new ObjectiveCoordinate(1, 2);

        // Build objectives and obtain them
        b.add(enemyType);
        b.add(EntityType.KNIGHT, 1);
        b.add(1, 2);
        b.add(type, 2);
        List<Objective> objectives = b.getObjectives();

        // Create game state - check that stats are properly initialised
        GameState gs = new GameState(objectives);
        HashMap<Object, Object> testStatistics = new HashMap<>();
        for (Objective o: objectives) {
            if (o instanceof EnemyKillObjective) {
                EnemyKillObjective e = (EnemyKillObjective)o;
                testStatistics.put(e.getObjectiveTarget(), 0);
            } else if (o instanceof ScoreObjective) {
                ScoreObjective s = (ScoreObjective)o;
                testStatistics.put(s.getObjectiveTarget(), 0);
            } else if (o instanceof ProtectionObjective) {
                ProtectionObjective p = (ProtectionObjective)o;
                testStatistics.put(p.getObjectiveTarget(), p.getObjectiveValue());
            } else if (o instanceof EnemyHitObjective) {
                EnemyHitObjective e = (EnemyHitObjective)o;
                testStatistics.put(e.getObjectiveTarget(), e.met());
            } else if (o instanceof ItemObjective) {
                ItemObjective i = (ItemObjective) o;
                testStatistics.put(i.getObjectiveTarget(), i.getObjectiveValue());
            } else if (o instanceof MovementObjective) {
                MovementObjective m = (MovementObjective) o;
                testStatistics.put(m.getObjectiveTarget(), o.met());
            }
        }
        Assert.assertEquals(testStatistics.size(), gs.getStatistics().size());

        // Create tracker - check that it is monitoring the specified game state
        ObjectiveTracker ot = new ObjectiveTracker(gs, objectives);
        Assert.assertEquals(gs, ot.getGameState());
        Assert.assertEquals(objectives, ot.getObjectives());

        // Round 1 - doggo protected, enemy not hit, 1 item found, position reached
        // Check to see if any are met - should all be false (apart from protection and movement objectives)
        gs.updateMovementGoal(1, 2);
        gs.updateItemGoal(type);
        for (Objective o: ot.getObjectives()) {
            if (o instanceof ProtectionObjective || o instanceof MovementObjective) {
                Assert.assertTrue(o.met());
            } else {
                Assert.assertFalse(o.met());
            }
        }

        // Check to see if all are met or not - should be false
        Assert.assertFalse(ot.allObjectivesMet());
        System.out.println(ot.getCurrentCompletionPercentage());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 50) < 0.001);

        // Round 2 - enemy hit, doggo protected, other item found, position not reached yet
        gs.updateEnemyHitGoals(EnemyType.ENEMY_KNIGHT);
        gs.updateItemGoal(type);

        for (Objective o: ot.getObjectives()) {
            Assert.assertTrue(o.met());
        }

        // Check to see if all are met or not - should be true
        Assert.assertTrue(ot.allObjectivesMet());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 100) < 0.001);

        // Round 3 - doggo destroyed
        gs.updateProtectionGoals(EntityType.KNIGHT, 0);
        for (Objective o: ot.getObjectives()) {
            if (o instanceof ProtectionObjective) {
                Assert.assertFalse(o.met());
            } else {
                Assert.assertTrue(o.met());
            }
        }

        // Now should not be all met anymore
        Assert.assertFalse(ot.allObjectivesMet());
        Assert.assertTrue((ot.getCurrentCompletionPercentage() - 75) < 0.001);
    }

    @Test
    public void testGetCompletedAndIncompleteObjectives() {

        // Setup
        EnemyType enemyType = EnemyType.ENEMY_KNIGHT;
        ObjectiveBuilder ob = new ObjectiveBuilder(null);
        ob.add(enemyType, 10); // 2nd
        List<Objective> objectives = ob.getObjectives();
        GameState gs = new GameState(objectives);
        ObjectiveTracker ot = new ObjectiveTracker(gs, objectives);

        // Test - incomplete
        gs.updateEnemyKills(enemyType, 5);
        Assert.assertEquals(0, ot.getCompletedObjectives().size());
        Assert.assertEquals(1, ot.getIncompleteObjectives().size());

        // Test - completed
        gs.updateEnemyKills(enemyType, 5);
        Assert.assertEquals(1, ot.getCompletedObjectives().size());
        Assert.assertEquals(0, ot.getIncompleteObjectives().size());
    }
}
