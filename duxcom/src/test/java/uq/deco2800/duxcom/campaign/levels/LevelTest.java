package uq.deco2800.duxcom.campaign.levels;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uq.deco2800.duxcom.campaign.Difficulty;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.EmptyMap;
import uq.deco2800.duxcom.objectives.MovementObjective;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;

/**
 * Created by Elliot on 10/17/2016.
 */
public class LevelTest {

    private AbstractGameMap testMap;
    private AbstractLevel testLevel;

    @Before
    public void setUp() throws Exception {
        testMap = new EmptyMap("testMap");
        testLevel = new LevelOne(testMap, Difficulty.EASY, "Test Level", "Story Text");
    }

    @Test
    public void testLevelConstruction() {
        Assert.assertEquals("Test Level", testLevel.getLevelName());
        Assert.assertEquals("Story Text", testLevel.getStoryText());
        Assert.assertFalse(testLevel.isCompleted());
    }

    @Test
    public void testGetLevelName() {
        testLevel.setLevelName("New Level Name");
        Assert.assertEquals("New Level Name", testLevel.getLevelName());
        testLevel.setLevelName("Another Level Name");
        Assert.assertNotEquals("New Level Name", testLevel.getLevelName());
    }

    @Test
    public void testGetLevelStory() {
        testLevel.setStoryText("New Story Text");
        Assert.assertEquals("New Story Text", testLevel.getStoryText());
        testLevel.setStoryText("Another Story");
        Assert.assertNotEquals("New Story Text", testLevel.getStoryText());
    }

    @Test
    public void testLevelCompletion() {
        testLevel.completeLevel();
        Assert.assertTrue(testLevel.isCompleted());
    }

}
