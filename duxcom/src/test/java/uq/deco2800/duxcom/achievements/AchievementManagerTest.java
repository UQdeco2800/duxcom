package uq.deco2800.duxcom.achievements;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Gormly on 20/10/2016.
 */
public class AchievementManagerTest {

    @Test
    public void mapAchievementTest() {
        AchievementManager.reset();
        String line = "kill1^The Littlest Genocide^Pubstomp 25 Enemies.^KILL^25";
        String invalidLine = "This is an ^ invalid ^ line";
        Achievement mappedAchievement = AchievementManager.mapAchievement(line);
        Achievement invalidAchievement = AchievementManager.mapAchievement(invalidLine);

        Achievement achievement = new Achievement("kill1", "The Littlest Genocide", "Pubstomp 25 Enemies.", "KILL", 25);

        assertEquals(achievement, mappedAchievement);
        assertEquals(null, invalidAchievement);

    }

    @Test
    public void splitLine() {
        AchievementManager.reset();
        String line = "kill1^The Littlest Genocide^Pubstomp 25 Enemies.^KILL^25";
        String[] lineSplit = line.split("\\^", -1);
        System.out.println(lineSplit[1]);
        String[] actualSplit = {"kill1", "The Littlest Genocide", "Pubstomp 25 Enemies.", "KILL", "25"};

        Assert.assertArrayEquals(lineSplit, actualSplit);
    }

    @Test
    public void addAchievement() {
        AchievementManager.reset();
        Achievement achievement = null;
        AchievementManager.addAchievement(achievement);
        achievement = new Achievement("ABC", "Name", "Description", AchievementType.TIME, 20);
        Achievement achievement2 = new Achievement("ABC", "Name", "Description", AchievementType.TIME, 20);
        AchievementManager.addAchievement(achievement);
        AchievementManager.addAchievement(achievement2);
        List<Achievement> actualList = new ArrayList<>();
        List<Achievement> finalList = AchievementManager.getAllAchievements();
        actualList.add(achievement);
        assertEquals(actualList, finalList);
    }

    @Test
    public void saveAndLoadTest() {
        AchievementManager.reset();
        /* Clear currently saved achievements. */
        AchievementManager.saveAccountAchievements();

        Achievement achievement = new Achievement("ABC", "Name", "Description", AchievementType.TIME, 20);
        AchievementManager.addAchievementToAccount(achievement);

        /* Losd Achievements from file. */
        AchievementManager.loadAccountAchievements();

        List<Achievement> finalList = AchievementManager.getAccountAchievements();
        List<Achievement> expectedList = new ArrayList<>();
        expectedList.add(achievement);

        assertEquals(finalList, expectedList);
    }

    @Test
    public void checkUnlocked() {
        AchievementManager.reset();
        Achievement achievement = new Achievement("ABC", "Unlocked", "Description", AchievementType.TIME, 20);
        Achievement achievement2 = new Achievement("BBB", "Not Unlocked", "Description", AchievementType.TIME, 20);
        AchievementManager.addAchievement(achievement);
        AchievementManager.addAchievement(achievement2);
        AchievementManager.addAchievementToAccount(achievement);

        assertTrue(AchievementManager.isUnlocked(achievement));
        Assert.assertFalse(AchievementManager.isUnlocked(achievement2));
    }

    @Test
    public void unlockAllAchievements() {
        AchievementManager.reset();
        Achievement achievement = new Achievement("ABC", "Name", "Description", AchievementType.TIME, 20);
        AchievementManager.addAchievement(achievement);
        AchievementManager.unlockAll();
        assertEquals(AchievementManager.getAllAchievements(), AchievementManager.getAccountAchievements());
    }

    @Test
    public void uniqueAchievementIds() {
        AchievementManager.reset();
        Achievement achievement = new Achievement("ABC", "Name", "Description", AchievementType.TIME, 20);
        Achievement achievement2 = new Achievement("ABC", "Different", "Description", AchievementType.KILL, 20);
        AchievementManager.addAchievement(achievement);
        AchievementManager.addAchievement(achievement2);

        assertEquals(1, AchievementManager.getAllAchievements().size());
        assertTrue(AchievementManager.getAllAchievements().contains(achievement));
    }

    @Test
    public void setUsernameTest() {
        AchievementManager.setUsername("Hello World");
        assertEquals("Hello World", AchievementManager.getUsername());
    }

    @Test
    public void getAchievementById() {
        Achievement achievement = new Achievement("ABC", "Unlocked", "Description", AchievementType.TIME, 20);
        Achievement errorAchievement = new Achievement("error", "Error", "Could not load achievement.",
                AchievementType.KILL, 1);

        AchievementManager.addAchievement(achievement);

        assertEquals(achievement, AchievementManager.getAchievement("ABC"));
        assertEquals(errorAchievement, AchievementManager.getAchievement("error"));
    }
}