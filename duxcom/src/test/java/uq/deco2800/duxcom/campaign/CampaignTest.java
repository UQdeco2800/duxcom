package uq.deco2800.duxcom.campaign;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uq.deco2800.duxcom.campaign.levels.AbstractLevel;
import uq.deco2800.duxcom.campaign.levels.LevelOne;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.EmptyMap;

/**
 * Created by Elliot on 10/17/2016.
 */
public class CampaignTest {

    private Campaign testCampaign;
    private AbstractLevel testLevel;

    @Before
    public void setUp() throws Exception {
        testCampaign = new Campaign("Test Campaign", "Test Description");
        AbstractGameMap testMap = new EmptyMap("testMap");
        testLevel = new LevelOne(testMap, Difficulty.EASY, "Test Level", "Story Text");
        testCampaign.addLevelToCampaign(testLevel);
    }

    @Test
    public void testIsCompleted() {
        Assert.assertFalse(testCampaign.isCompleted());
        testCampaign.getLevel(1).completeLevel();
        Assert.assertTrue(testCampaign.isCompleted());
    }

    @Test
    public void testAddLevelToCampaign() {
        AbstractGameMap testMap = new EmptyMap("Another Map");
        AbstractLevel newLevel = new LevelOne(testMap, Difficulty.MEDIUM, "Test Level 2", "Story Text 2");
        testCampaign.addLevelToCampaign(newLevel);
        Assert.assertEquals(newLevel ,testCampaign.getLevel(2));
    }

    @Test
    public void testGetCampaignName() {
        Assert.assertEquals("Test Campaign", testCampaign.getCampaignName());
    }

    @Test
    public void testGetCampaignDescription() {
        Assert.assertEquals("Test Description", testCampaign.getCampaignDescription());
    }
}
