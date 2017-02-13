/**
 *
 */
package uq.deco2800.duxcom.achievements;

import org.junit.Test;

import static org.junit.Assert.*;
import static uq.deco2800.duxcom.achievements.AchievementType.KILL;
import static uq.deco2800.duxcom.achievements.AchievementType.MIS;

/**
 * Test Achievement Object.
 *
 * @author Daniel Gormly
 */
public class AchievementTest {

    @Test
    public void createAchievement() {
        String achievementID = "1A2B3C";
        int achievementScore = 20;
        String type = "TIME";
        String achievementDescription = "Hello";
        String achievementName = "Created New Achievement";

        Achievement achievement = new Achievement(achievementID, achievementName,
                achievementDescription, AchievementType.TIME, achievementScore);
        Achievement achievement2 = new Achievement(achievementID, achievementName,
                achievementDescription, "TIME", achievementScore);

        assertEquals(achievement, achievement2);
        assertNotNull("Achievement ID must not equal null.", achievement.getId());
        assertEquals("Achievement name must match submitted achievement name.", achievementName, achievement.getName());
        assertEquals("Achievement description must equal submitted achievement description.", achievementDescription,
                achievement.getDescription());
        assertEquals("Achievement type must equal submitted achievement type.", type, achievement.getType());
    }

    @Test
    public void setName() {
       Achievement achievement = new Achievement();
        achievement.setName("New achievement");
        achievement.setDescription("Something");
        achievement.setType("TIME");
        achievement.setTypeEnum(AchievementType.TIME);

        assertEquals("Name must match set name", "New achievement", achievement.getName());
        // Update experience
        achievement.setName("Updated name");
        assertEquals("Updated name must make set name.", "Updated name", achievement.getName());
    }

    @Test
    public void hashcode() {
        Achievement achievement = new Achievement("ABC", "Name", "Description", "MIS", 20);
        Achievement achievement2 = new Achievement("ABC", "Name", "Description", "MIS", 20);

        assertEquals("Hashcode for both achievements must match.", achievement.hashCode(), achievement2.hashCode());
    }

    @Test
    public void equals() {
        Achievement achievement = new Achievement("ABC", "Name", "Description", "SCORE", 20);
        Achievement achievement2 = new Achievement("ABC", "Name", "Description", "SCORE", 20);

        assertTrue("Achievement should be equal.", achievement.equals(achievement2));

}

    @Test
    public void emptyAchievement() {
        Achievement achievement = new Achievement();
        assertNull("New achievement without parameters should have a null Id.", achievement.getId());
        assertNull("New Achievement without parameters should have null Name.", achievement.getName());
        assertNull("New Achievement without parameters should have null description.", achievement.getDescription());
        assertEquals("New Achievement without parameters should have 0 score.", achievement.getScore(), 0);
        assertNull("New Achievement without parameters should have null type.", achievement.getType());
    }

    @Test
    public void achievementToString() {
        Achievement achievement = new Achievement("ABC", "Name", "Description", "DEATH", 20);
        String expectedOutput = "ABC^Name^Description^DEATH^20";
        assertEquals(achievement.toString(), expectedOutput);
    }

    @Test
    public void achievementGetterSetters() {
        Achievement achievement = new Achievement();
        achievement.setTypeEnum(KILL);
        achievement.setTypeEnum(AchievementType.SCORE);
        achievement.setTypeEnum(AchievementType.TIME);
        achievement.setTypeEnum(AchievementType.DEATH);
        achievement.setTypeEnum(MIS);

        achievement.setId("ABC");
        achievement.setDescription("desc");
        achievement.setScore(20);
        achievement.setName("test");

        Achievement achievement2 = new Achievement("ABC", "test", "desc", MIS, 20);

        assertEquals("MIS", achievement.getType());
        assertTrue(achievement2.equals(achievement));
    }

    @Test
    public void achievementEquals() {
        Achievement achievement = new Achievement("ABC", "test", "desc", MIS, 20);
        Achievement achievement2 = new Achievement("ABC", "test", "desc", MIS, 20);
        assertTrue(achievement.equals(achievement2));
        assertTrue(achievement.equals(achievement));
        assertTrue(!achievement.equals("Hello World"));

        achievement.setScore(30);
        assertTrue(!achievement.equals(achievement2));

        achievement2.setScore(30);
        achievement.setName("Wrong");
        assertTrue(!achievement.equals(achievement2));


        achievement.setName("test");
        achievement.setDescription("wrong");
        assertTrue(!achievement.equals(achievement2));

        achievement.setDescription("desc");
        achievement.setTypeEnum(KILL);
        assertTrue(!achievement.equals(achievement2));

        achievement.setTypeEnum(MIS);
        achievement.setId("wrong");
        assertTrue(!achievement.equals(achievement2));

    }
}

