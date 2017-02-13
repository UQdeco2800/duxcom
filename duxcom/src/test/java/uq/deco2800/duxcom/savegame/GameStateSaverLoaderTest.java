package uq.deco2800.duxcom.savegame;

import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.EnemyObjectiveType;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.scoring.ScoreSystem;

import java.util.*;

import static uq.deco2800.duxcom.savegame.GameStateSaverLoader.*;

/**
 * Created by Tom B on 18/10/2016.
 */
public class GameStateSaverLoaderTest {

    // Valid
    public static final String EK_SAVE_STRING = "EK/ENEMY_KNIGHT/2";
    public static final String EH_SAVE_STRING = "EH/ENEMY_KNIGHT/true";
    public static final String I_SAVE_STRING = "I/POTION/2";
    public static final String P_SAVE_STRING = "P/KNIGHT/2";
    public static final String M_SAVE_STRING = "M/6,2/true";
    public static final String S_SAVE_STRING = "S/Scoring System:89/89";
    public static final String S_SAVE_STRING_2 = "S/Scoring System:0/0";

    // Invalid
    public static final String BAD_SAVE_STRING = "aff/ENEMY_KNIGHT/2";
    public static final String BAD_SAVE_STRING_2 = "EK/ENEMY_KNIGHT";
    public static final String BAD_EK_SAVE_STRING_1 = "EK/hello/2";
    public static final String BAD_EK_SAVE_STRING_2 = "EK/ENEMY_KNIGHT/two";
    public static final String BAD_EK_SAVE_STRING_3 = "EK/ENEMY_KNIGHT/-1";
    public static final String BAD_EH_SAVE_STRING_1 = "EH/hello/true";
    public static final String BAD_EH_SAVE_STRING_2 = "EH/ENEMY_KNIGHT/hello";
    public static final String BAD_I_SAVE_STRING_1 = "I/hi/2";
    public static final String BAD_I_SAVE_STRING_2 = "I/POTION/two";
    public static final String BAD_I_SAVE_STRING_3 = "I/POTION/-1";
    public static final String BAD_S_SAVE_STRING_1 = "S/Scoring System/1000";
    public static final String BAD_S_SAVE_STRING_2 = "S/Scoring System:hundred/1000";
    public static final String BAD_S_SAVE_STRING_3 = "S/Scoring System:-11/1000";
    public static final String BAD_S_SAVE_STRING_4 = "S/Scoring System:1000/hi";
    public static final String BAD_S_SAVE_STRING_5 = "S/Scoring System:1000/-11";
    public static final String BAD_P_SAVE_STRING_1 = "S/hello/2";
    public static final String BAD_P_SAVE_STRING_2 = "S/KNIGHT/0";
    public static final String BAD_P_SAVE_STRING_3 = "S/KNIGHT/hi";
    public static final String BAD_M_SAVE_STRING_1 = "M/1/true";
    public static final String BAD_M_SAVE_STRING_2 = "M/x,1/true";
    public static final String BAD_M_SAVE_STRING_3 = "M/-1,1/true";
    public static final String BAD_M_SAVE_STRING_4 = "M/1,y/true";
    public static final String BAD_M_SAVE_STRING_5 = "M/1,-1/true";
    public static final String BAD_M_SAVE_STRING_6 = "M/1,2/hello";

    protected static final List<String> testSaveStrings = new ArrayList<String>() {{
        add(EK_SAVE_STRING);
        add(EH_SAVE_STRING);
        add(I_SAVE_STRING);
        add(M_SAVE_STRING);
        add(S_SAVE_STRING_2);
        add(P_SAVE_STRING);
    }};

    protected static final List<String> testBadSaveStrings = new ArrayList<String>() {{
        add(BAD_SAVE_STRING);
        add(EH_SAVE_STRING);
        add(I_SAVE_STRING);
        add(M_SAVE_STRING);
        add(S_SAVE_STRING_2);
        add(P_SAVE_STRING);
    }};
    
    protected static final Map<Object, Object> testStatistics;
    static {
        testStatistics = new HashMap<>();
        testStatistics.put(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, true), 2);
        testStatistics.put(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, false), true);
        testStatistics.put(ItemType.POTION, 2);
        testStatistics.put(new ObjectiveCoordinate(6, 2), true);
        testStatistics.put(EntityType.KNIGHT, 2);
        testStatistics.put(new ScoreSystem(), 0);
    }


    protected static final GameState testGameState = new GameState(testStatistics);

    protected static final ObjectiveTracker testTracker =
            new ObjectiveTracker(testGameState, ObjectiveSaverLoaderTest.testObjectives);

    protected static final List<List<String>> allSaveContents = new ArrayList<List<String>>() {{
        add(testSaveStrings);
        add(ObjectiveSaverLoaderTest.saveContents);
    }};

    @Test
    public void testUselessConstructor() {
        GameStateSaverLoader gssl = new GameStateSaverLoader();
    }

    @Test
    public void testGetStatisticTypeStringRepresentation() {
        Assert.assertEquals(ENEMY_KILL,
                getStatisticTypeStringRepresentation(new EnemyObjectiveType(
                        EnemyType.ENEMY_ARCHER, true), 1));
        Assert.assertEquals(ENEMY_HIT,
                getStatisticTypeStringRepresentation(new EnemyObjectiveType(
                        EnemyType.ENEMY_ARCHER, false), true));
        Assert.assertEquals(SCORE,
                getStatisticTypeStringRepresentation(new ScoreSystem(), 200));
        Assert.assertEquals(ITEM,
                getStatisticTypeStringRepresentation(ItemType.ARMOUR, 2));
        Assert.assertEquals(MOVEMENT,
                getStatisticTypeStringRepresentation(new ObjectiveCoordinate(1, 2), true));
        Assert.assertEquals(PROTECTION,
                getStatisticTypeStringRepresentation(EntityType.KNIGHT, 2));
    }

    @Test
    public void testBuildStatisticSaveString() {
        Assert.assertEquals(EK_SAVE_STRING,
                buildStatisticSaveString(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, true), 2));
        Assert.assertEquals(EH_SAVE_STRING,
                buildStatisticSaveString(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, false), true));
        Assert.assertEquals(I_SAVE_STRING,
                buildStatisticSaveString(ItemType.POTION, 2));
        Assert.assertEquals(P_SAVE_STRING,
                buildStatisticSaveString(EntityType.KNIGHT, 2));
        Assert.assertEquals(M_SAVE_STRING,
                buildStatisticSaveString(new ObjectiveCoordinate(6, 2), true));
        ScoreSystem s = new ScoreSystem();
        s.changeScore(89, '+');
        Assert.assertEquals(S_SAVE_STRING,
                buildStatisticSaveString(s, 89));
    }

    @Test
    public void testCheckStatisticSaveString() {
        // VALID
        Assert.assertTrue(checkGameStateSaveString(EK_SAVE_STRING));
        Assert.assertTrue(checkGameStateSaveString(EH_SAVE_STRING));
        Assert.assertTrue(checkGameStateSaveString(I_SAVE_STRING));
        Assert.assertTrue(checkGameStateSaveString(P_SAVE_STRING));
        Assert.assertTrue(checkGameStateSaveString(S_SAVE_STRING));
        Assert.assertTrue(checkGameStateSaveString(M_SAVE_STRING));

        // Invalid save string constant
        Assert.assertFalse(checkGameStateSaveString(BAD_SAVE_STRING));

        // Invalid token count
        Assert.assertFalse(checkGameStateSaveString(BAD_SAVE_STRING_2));

        // Invalid general format
        Assert.assertFalse(checkGameStateSaveString(BAD_EK_SAVE_STRING_1));
        Assert.assertFalse(checkGameStateSaveString(BAD_EK_SAVE_STRING_2));
        Assert.assertFalse(checkGameStateSaveString(BAD_EK_SAVE_STRING_3));
        Assert.assertFalse(checkGameStateSaveString(BAD_EH_SAVE_STRING_1));
        Assert.assertFalse(checkGameStateSaveString(BAD_EH_SAVE_STRING_2));
        Assert.assertFalse(checkGameStateSaveString(BAD_I_SAVE_STRING_1));
        Assert.assertFalse(checkGameStateSaveString(BAD_I_SAVE_STRING_2));
        Assert.assertFalse(checkGameStateSaveString(BAD_I_SAVE_STRING_3));
        Assert.assertFalse(checkGameStateSaveString(BAD_P_SAVE_STRING_1));
        Assert.assertFalse(checkGameStateSaveString(BAD_P_SAVE_STRING_2));
        Assert.assertFalse(checkGameStateSaveString(BAD_P_SAVE_STRING_3));
        Assert.assertFalse(checkGameStateSaveString(BAD_M_SAVE_STRING_1));
        Assert.assertFalse(checkGameStateSaveString(BAD_M_SAVE_STRING_2));
        Assert.assertFalse(checkGameStateSaveString(BAD_M_SAVE_STRING_3));
        Assert.assertFalse(checkGameStateSaveString(BAD_M_SAVE_STRING_4));
        Assert.assertFalse(checkGameStateSaveString(BAD_M_SAVE_STRING_5));
        Assert.assertFalse(checkGameStateSaveString(BAD_M_SAVE_STRING_6));
        Assert.assertFalse(checkGameStateSaveString(BAD_S_SAVE_STRING_1));
        Assert.assertFalse(checkGameStateSaveString(BAD_S_SAVE_STRING_2));
        Assert.assertFalse(checkGameStateSaveString(BAD_S_SAVE_STRING_3));
        Assert.assertFalse(checkGameStateSaveString(BAD_S_SAVE_STRING_4));
        Assert.assertFalse(checkGameStateSaveString(BAD_S_SAVE_STRING_5));

    }

    @Test
    public void testBuildEnemyKillStatistic() {
        Map<Object, Object> stat = new HashMap<>();
        stat.put(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, true), 2);
        Assert.assertEquals(stat, buildStatisticFromString(EK_SAVE_STRING));
    }

    @Test
    public void testBuildEnemyHitStatistic() {
        Map<Object, Object> stat = new HashMap<>();
        stat.put(new EnemyObjectiveType(EnemyType.ENEMY_KNIGHT, false), true);
        Assert.assertEquals(stat, buildStatisticFromString(EH_SAVE_STRING));
    }

    @Test
    public void testBuildItemStatistic() {
        Map<Object, Object> stat = new HashMap<>();
        stat.put(ItemType.POTION, 2);
        Assert.assertEquals(stat, buildStatisticFromString(I_SAVE_STRING));
    }

    @Test
    public void testBuildScoreStatistic() {
        Map<Object, Object> stat = new HashMap<>();
        ScoreSystem s = new ScoreSystem();
        s.changeScore(89, '+');
        stat.put(s, 89);
        Assert.assertEquals(stat, buildStatisticFromString(S_SAVE_STRING));
    }

    @Test
    public void testBuildProtectionStatistic() {
        Map<Object, Object> stat = new HashMap<>();
        stat.put(EntityType.KNIGHT, 2);
        Assert.assertEquals(stat, buildStatisticFromString(P_SAVE_STRING));
    }

    @Test
    public void testBuildMovementStatistic() {
        Map<Object, Object> stat = new HashMap<>();
        stat.put(new ObjectiveCoordinate(6, 2), true);
        Assert.assertEquals(stat, buildStatisticFromString(M_SAVE_STRING));
    }

    @Test
    public void testGameStateToStringList() {
        Assert.assertEquals(new HashSet<>(testSaveStrings),
                new HashSet<>(gameStateToStringList(testGameState)));
    }

    @Test
    public void testStringListToGameState() {
        Assert.assertEquals(testGameState, loadGameState(testSaveStrings));
    }

    @Test
    public void testLoadAndSavePreviousObjectiveState() {
        Assert.assertEquals(testTracker,
                loadExistingObjectiveState(ObjectiveSaverLoaderTest.saveContents, testSaveStrings));
        Assert.assertEquals(null, loadExistingObjectiveState(ObjectiveSaverLoaderTest.saveContents, testBadSaveStrings));
        Assert.assertEquals(null, loadExistingObjectiveState(ObjectiveSaverLoaderTest.badSaveContents, testSaveStrings));

        // Prepare to test equality of lists within lists (order matters otherwise)

        // Expected
        Set<String> testSaveObjectiveContents = new HashSet<>(allSaveContents.get(0));
        Set<String> testSaveGameStateContents = new HashSet<>(allSaveContents.get(1));
        Set<Set<String>> testAllSaveContents = new HashSet<>();
        testAllSaveContents.add(testSaveObjectiveContents);
        testAllSaveContents.add(testSaveGameStateContents);

        // Actual
        List<List<String>> actualAllSaveContentsList = saveExistingObjectiveState(testTracker);
        Set<String> actualSaveObjectiveContents = new HashSet<>(actualAllSaveContentsList.get(0));
        Set<String> actualSaveGameStateContents = new HashSet<>(actualAllSaveContentsList.get(1));
        Set<Set<String>> actualAllSaveContents = new HashSet<>();
        actualAllSaveContents.add(actualSaveObjectiveContents);
        actualAllSaveContents.add(actualSaveGameStateContents);

        // Finally test pre-saving
        Assert.assertEquals(testAllSaveContents, actualAllSaveContents);
    }
}
