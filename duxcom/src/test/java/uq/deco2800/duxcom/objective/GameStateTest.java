package uq.deco2800.duxcom.objective;

import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.dynamics.DiscoDoggo;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.maps.EmptyMap;
import uq.deco2800.duxcom.maps.EnemyTestMap;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * Tests out various objects created from GameState for correct initialisation.
 * Created by Tom B on 29/08/2016.
 */
public class GameStateTest {
    @Test
    public void testEnemyGameStateInitialisation() {

        // Set up expected values that the test will be
        // used for comparison
        ObjectiveBuilder builder = new ObjectiveBuilder(null);
        builder.add(EnemyType.ENEMY_GRUNT, 10);
        ScoreSystem scoring = new ScoreSystem();
        builder.add(scoring, 2000);
        DiscoDoggo d = new DiscoDoggo(1, 1);
        builder.add(EntityType.KNIGHT, 1);
        builder.add(EnemyType.ENEMY_DARK_MAGE);
        List<Objective> objectives = builder.getObjectives();
        HashMap<Object, Object> testStatistics = new HashMap<>();

        for (Objective o: objectives) {
            if (o instanceof EnemyKillObjective) {
                EnemyKillObjective e = (EnemyKillObjective)o;
                testStatistics.put(e.getObjectiveTarget(), 0);
            } else if (o instanceof ScoreObjective) {
                ScoreObjective s = (ScoreObjective)o;
                testStatistics.put(s.getObjectiveTarget(), scoring.getScore());
            } else if (o instanceof ProtectionObjective) {
                ProtectionObjective p = (ProtectionObjective)o;
                testStatistics.put(p.getObjectiveTarget(), p.getObjectiveValue());
            } else if (o instanceof EnemyHitObjective) {
                EnemyHitObjective e = (EnemyHitObjective)o;
                testStatistics.put(e.getObjectiveTarget(), e.met());
            }
        }

        // Prepare objectives for game state to use
        // One of enemy kill and money goal
        // Test others as well as needed

        GameState gs = new GameState(objectives);

        // Check that statistics are filled up properly - this
        // will indicate correct initialisation of the Game State instance
        Assert.assertEquals(testStatistics, gs.getStatistics());
        Assert.assertEquals(testStatistics.size(), gs.getStatistics().size());
    }

    @Test
    public void testUpdateAllHeroProtectionGoals() {
        ObjectiveBuilder ob = new ObjectiveBuilder(new EmptyMap("test"));
        ob.add(EntityType.KNIGHT, 2);
        GameState gs = new GameState(ob.getObjectives());
        ObjectiveTracker ot = new ObjectiveTracker(gs, ob.getObjectives());
        HeroManager hm = new HeroManager();
        hm.addHero(new Knight(1, 2));
        hm.addHero(new Knight(1, 3));
        gs.updateAllHeroProtectionGoals(hm);

        Assert.assertTrue(ot.allObjectivesMet());
    }

    @Test
    public void testToString() {
        ObjectiveBuilder ob = new ObjectiveBuilder(new EnemyTestMap("test"));
        GameState gs = new GameState(ob.getObjectives());
        String test = "Game State: " + gs.getStatistics();
        String test2 = gs.toString();
    }

    @Test
    public void testHashCode() {
        ObjectiveBuilder ob = new ObjectiveBuilder(new EnemyTestMap("test"));
        GameState gs = new GameState(ob.getObjectives());
        int test = 23 * gs.getStatistics().hashCode();
        Assert.assertEquals(test, gs.hashCode());
    }

    @Test
    public void testNotEquals() {
        Integer i = new Integer(1);
        ObjectiveBuilder ob = new ObjectiveBuilder(new EnemyTestMap("test"));
        GameState gs = new GameState(ob.getObjectives());
        Assert.assertFalse(gs.equals(i));
    }

    @Test
    public void testNoScoreOrMovementOrEnemyKillObjectives() {
        ObjectiveBuilder ob = new ObjectiveBuilder(new EmptyMap("test"));
        GameState gs = new GameState(ob.getObjectives());
        gs.updateScoreGoals(new ScoreSystem());
        Assert.assertEquals(1, gs.getStatistics().size());
        gs.updateEnemyKills(EnemyType.ENEMY_ARCHER, 2);
        Assert.assertEquals(1, gs.getStatistics().size());
        gs.updateMovementGoal(1, 2);
        Assert.assertEquals(1, gs.getStatistics().size());
    }
}
