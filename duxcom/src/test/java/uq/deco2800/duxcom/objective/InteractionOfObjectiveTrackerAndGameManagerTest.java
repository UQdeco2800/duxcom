package uq.deco2800.duxcom.objective;

import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.maps.EnemyTestMap;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;

/**
 * Test suite for testing that game manager is able to
 * be notified whenever the objective tracker has marked some
 * objectives as completed, and when all objectives have been completed.
 * Created by Tom B on 7/10/2016.
 */
public class InteractionOfObjectiveTrackerAndGameManagerTest {

    private EnemyTestMap testMap;
    private ObjectiveBuilder oBuilder;
    private GameState gs;
    private ObjectiveTracker tracker;
    private GameManager testManager;

    @Test
    public void testInitialisationGameManagerAndObjectiveTracker() {
        setUpTestSuite();

        // Checking
        Assert.assertEquals(oBuilder.getObjectives(), testManager.getObjectives()); // Objectives match?
        Assert.assertEquals(gs, testManager.getGameState()); // Game state matches?

        // Is the game manager's tracker watching that very same game manager?
        Assert.assertTrue(testManager.getTracker().getObservers().contains(testManager));
    }

    /**
     * Helper method for initialisation of test suite - run this before doing any checks!
     */
    public void setUpTestSuite() {
        // Map to use
        testMap = new EnemyTestMap("test");

        // Building objectives, game state and tracker to test against
        oBuilder = new ObjectiveBuilder(testMap);
        gs = new GameState(oBuilder.getObjectives());
        tracker = new ObjectiveTracker(gs, oBuilder.getObjectives());

        // Initialise GameManager
        testManager = new GameManager();
        testManager.setInitialGameInternalFramework(oBuilder);
        gs.getStatistics().clear();
        gs.fillUpStatistics(oBuilder.getObjectives());
    }
}
