package uq.deco2800.duxcom.savegame;

import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import java.util.*;
import static uq.deco2800.duxcom.savegame.ObjectiveSaverLoader.*;

/**
 * Created by Tom B on 10/10/2016.
 */
public class ObjectiveSaverLoaderTest {

    // Test save strings

    // Valid
    public static final String SAVE_STRING_1 = "EK/ENEMY_KNIGHT/10/false";
    public static final String SAVE_STRING_2 = "EK/ENEMY_KNIGHT/10/true";
    public static final String EH_SAVE_STRING_1 = "EH/ENEMY_KNIGHT/false/false";
    public static final String EH_SAVE_STRING_2 = "EH/ENEMY_KNIGHT/true/true";
    public static final String EH_SAVE_STRING_3 = "EH/ENEMY_KNIGHT/false/true";
    public static final String I_SAVE_STRING_1 = "I/AXE/2/false";
    public static final String I_SAVE_STRING_2 = "I/AXE/2/true";
    public static final String S_SAVE_STRING_1 = "S/Scoring System:0/1000/false";
    public static final String S_SAVE_STRING_2 = "S/Scoring System:0/1000/true";
    public static final String M_SAVE_STRING_1 = "M/1,1/false";
    public static final String M_SAVE_STRING_2 = "M/1,1/true";
    public static final String P_SAVE_STRING_1 = "P/KNIGHT/3/true";
    public static final String P_SAVE_STRING_2 = "P/KNIGHT/3/false";

    // Invalid
    public static final String BAD_FORMAT_STRING_1 = "jk/ENEMY_KNIGHT/10/false";
    public static final String BAD_FORMAT_MOVEMENT_INCORRECT_TOKENS = "M/1,1";
    public static final String BAD_FORMAT_OTHER_INCORRECT_TOKENS = "EK/ENEMY_KNIGHT/10";
    public static final String BAD_FORMAT_LAST_TOKEN_NOT_BOOLEAN_1 = "EK/ENEMY_KNIGHT/10/hello";
    public static final String BAD_FORMAT_LAST_TOKEN_NOT_BOOLEAN_2 = "M/1,1/there";
    public static final String BAD_EK_NOT_ENEMY_TYPE = "EK/POTION/10/false";
    public static final String BAD_EK_NOT_INTEGER = "EK/ENEMY_KNIGHT/ten/false";
    public static final String BAD_EK_NEGATIVE_INTEGER = "EK/ENEMY_KNIGHT/0/false";
    public static final String BAD_EH_NOT_ENEMY_TYPE = "EH/POTION/false/false";
    public static final String BAD_I_NOT_ITEM_TYPE = "I/ENEMY_KNIGHT/10/false";
    public static final String BAD_I_NOT_INTEGER = "I/POTION/two/false";
    public static final String BAD_I_NEGATIVE_INTEGER = "I/POTION/-1/false";
    public static final String BAD_P_NOT_ENTITY_TYPE = "P/myself/1/false";
    public static final String BAD_P_NOT_INTEGER = "P/KNIGHT/one/false";
    public static final String BAD_P_NEGATIVE_INTEGER = "P/KNIGHT/-1/false";
    public static final String BAD_S_INVALID_TARGET_TOKEN_COUNT = "S/Score System/100/false";
    public static final String BAD_S_SCORE_SYSTEM_NOT_INT = "S/Score System:ten/100/false";
    public static final String BAD_S_SCORE_SYSTEM_NEGATIVE = "S/Score System:-1000/100/false";
    public static final String BAD_S_NOT_INTEGER = "S/Score System:100/one hundred/false";
    public static final String BAD_S_NEGATIVE_INTEGER = "S/Score System:100/-1000/false";
    public static final String BAD_M_INVALID_TOKEN_COUNT = "M/1/true";
    public static final String BAD_M_X_NOT_INTEGER = "M/two,1/true";
    public static final String BAD_M_Y_NOT_INTEGER = "M/1,two/false";
    public static final String BAD_M_X_NEGATIVE_INTEGER = "M/-1,5/true";
    public static final String BAD_M_Y_NEGATIVE_INTEGER = "M/1,-4/true";

    // Test objective list and save string list
    protected static final List<String> saveContents = new ArrayList<String>() {{
        add(SAVE_STRING_1);
        add(EH_SAVE_STRING_1);
        add(M_SAVE_STRING_1);
        add(S_SAVE_STRING_1);
        add(P_SAVE_STRING_1);
        add(I_SAVE_STRING_1);
    }};

    protected static final List<String> badSaveContents = new ArrayList<String>() {{
        add(SAVE_STRING_1);
        add(BAD_FORMAT_STRING_1);
        add(M_SAVE_STRING_1);
        add(S_SAVE_STRING_1);
        add(P_SAVE_STRING_1);
        add(I_SAVE_STRING_1);
    }};

    protected static List<Objective> testObjectives = new ArrayList<Objective>() {{
        add(new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 10));
        add(new EnemyHitObjective(EnemyType.ENEMY_KNIGHT));
        add(new MovementObjective(new ObjectiveCoordinate(1, 1)));
        add(new ScoreObjective(new ScoreSystem(), 1000));
        add(new ProtectionObjective(EntityType.KNIGHT, 3));
        add(new ItemObjective(ItemType.AXE, 2));
    }};

    @Test
    public void testUselessConstructor() {
        ObjectiveSaverLoader osl = new ObjectiveSaverLoader();
    }

    @Test
    public void testBooleanToString() {
        String result = booleanToString(true);
        Assert.assertEquals(TRUE, result);
        result = booleanToString(false);
        Assert.assertEquals(FALSE, result);
    }

    @Test
    public void testGetObjectiveTypeStringRepresentation() {
        ObjectiveBuilder b = new ObjectiveBuilder(null);

        // Enemy Kill
        b.add(EnemyType.ENEMY_ARCHER, 2);
        String test1 = getObjectiveTypeStringRepresentation(b.getObjectives().get(0));
        Assert.assertEquals(ENEMY_KILL, test1);

        // Enemy Hit
        b.clearObjectives();
        b.add(EnemyType.ENEMY_ARCHER);
        test1 = getObjectiveTypeStringRepresentation(b.getObjectives().get(0));
        Assert.assertEquals(ENEMY_HIT, test1);

        // Score
        b.clearObjectives();
        ScoreSystem s = new ScoreSystem();
        b.add(s, 100);
        test1 = getObjectiveTypeStringRepresentation(b.getObjectives().get(0));
        Assert.assertEquals(SCORE, test1);

        // Movement
        b.clearObjectives();
        b.add(1, 2);
        test1 = getObjectiveTypeStringRepresentation(b.getObjectives().get(0));
        Assert.assertEquals(MOVEMENT, test1);

        // Protection
        b.clearObjectives();
        b.add(EntityType.WOOD_STACK, 1);
        test1 = getObjectiveTypeStringRepresentation(b.getObjectives().get(0));
        Assert.assertEquals(PROTECTION, test1);

        // Item
        b.clearObjectives();
        ItemType sword = ItemType.SWORD;
        b.add(sword, 3);
        test1 = getObjectiveTypeStringRepresentation(b.getObjectives().get(0));
        Assert.assertEquals(ITEM, test1);
    }

    @Test
    public void testBuildObjectiveSaveString() {
        ObjectiveBuilder b = new ObjectiveBuilder(null);

        // Enemy Kill Objectives
        b.add(EnemyType.ENEMY_KNIGHT, 10); // not met
        String test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(SAVE_STRING_1, test1);
        b.getObjectives().get(0).complete();
        String test2 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(SAVE_STRING_2, test2);

        // Enemy Hit
        b.clearObjectives();
        b.add(EnemyType.ENEMY_KNIGHT);
        ObjectiveTracker t = new ObjectiveTracker(new GameState(b.getObjectives()), b.getObjectives());
        test1 = buildObjectiveSaveString(t.getObjectives().get(0));
        Assert.assertEquals(EH_SAVE_STRING_1, test1);
        t.getGameState().updateEnemyHitGoals(EnemyType.ENEMY_KNIGHT);
        test1 = buildObjectiveSaveString(t.getObjectives().get(0));
        Assert.assertEquals(EH_SAVE_STRING_2, test1);

        // Item
        b.clearObjectives();
        b.add(ItemType.AXE, 2);
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(I_SAVE_STRING_1, test1);
        b.getObjectives().get(0).complete();
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(I_SAVE_STRING_2, test1);

        // Protection
        b.clearObjectives();
        b.add(EntityType.KNIGHT, 3);
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(P_SAVE_STRING_1, test1);
        b.getObjectives().get(0).fail();
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(P_SAVE_STRING_2, test1);

        // Movement
        b.clearObjectives();
        b.add(1, 1);
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(M_SAVE_STRING_1, test1);
        b.getObjectives().get(0).complete();
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(M_SAVE_STRING_2, test1);

        // Score
        b.clearObjectives();
        b.add(new ScoreSystem(), 1000);
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(S_SAVE_STRING_1, test1);
        b.getObjectives().get(0).complete();
        test1 = buildObjectiveSaveString(b.getObjectives().get(0));
        Assert.assertEquals(S_SAVE_STRING_2, test1);
    }

    @Test
    public void testLoadEnemyKillObjective() {
        // Not met
        EnemyKillObjective ekTest = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 10);
        Assert.assertEquals(ekTest, loadObjective(SAVE_STRING_1));

        // Met
        ekTest.complete();
        Assert.assertEquals(ekTest, loadObjective(SAVE_STRING_2));
    }

    @Test
    public void testLoadEnemyHitObjective() {
        // Not met
        EnemyHitObjective ehTest = new EnemyHitObjective(EnemyType.ENEMY_KNIGHT);
        Assert.assertEquals(ehTest, loadObjective(EH_SAVE_STRING_1));

        // Met
        ehTest.complete();
        Assert.assertEquals(ehTest, loadObjective(EH_SAVE_STRING_2));

        ehTest.setObjectiveValue(new Boolean(false));
        Assert.assertEquals(ehTest, loadObjective(EH_SAVE_STRING_3));
    }

    @Test
    public void testLoadItemObjective() {
        // Not met
        ItemObjective iTest = new ItemObjective(ItemType.AXE, 3);
        Assert.assertEquals(iTest, loadObjective(I_SAVE_STRING_1));

        // Met
        iTest.complete();
        Assert.assertEquals(iTest, loadObjective(I_SAVE_STRING_2));
    }

    @Test
    public void testLoadMovementObjective() {
        // Not met
        MovementObjective mTest = new MovementObjective(new ObjectiveCoordinate(1, 1));
        Assert.assertEquals(mTest, loadObjective(M_SAVE_STRING_1));

        // Met
        mTest.complete();
        Assert.assertEquals(mTest, loadObjective(M_SAVE_STRING_2));
    }

    @Test
    public void testLoadScoreObjective() {
        // Not met
        ScoreObjective sTest = new ScoreObjective(new ScoreSystem(), 1000);
        Assert.assertEquals(sTest, loadObjective(S_SAVE_STRING_1));

        // Met
        sTest.complete();
        Assert.assertEquals(sTest, loadObjective(S_SAVE_STRING_2));
    }

    @Test
    public void testLoadProtectionObjective() {
        // Met
        ProtectionObjective pTest = new ProtectionObjective(EntityType.KNIGHT, 3);
        Assert.assertEquals(pTest, loadObjective(P_SAVE_STRING_1));

        // Not met
        pTest.fail();
        Assert.assertEquals(pTest, loadObjective(P_SAVE_STRING_2));

    }

    @Test
    public void testLoadAndSaveObjectives() {
        // Loading
        Assert.assertEquals(testObjectives, loadObjectives(saveContents));

        // Saving
        Assert.assertEquals(saveContents, saveObjectives(testObjectives));
    }

    @Test
    public void testCheckSaveString() {

        // Formatted properly
        Assert.assertTrue(checkSaveString(EH_SAVE_STRING_1));

        // Non-existant objective save string constant
        Assert.assertFalse(checkSaveString(BAD_FORMAT_STRING_1));

        // Movement objective string: incorrect token count
        Assert.assertFalse(checkSaveString(BAD_FORMAT_MOVEMENT_INCORRECT_TOKENS));

        // Other objective string: incorrect token count
        Assert.assertFalse(checkSaveString(BAD_FORMAT_OTHER_INCORRECT_TOKENS));

        // Last token not a boolean
        Assert.assertFalse(checkSaveString(BAD_FORMAT_LAST_TOKEN_NOT_BOOLEAN_1));
        Assert.assertFalse(checkSaveString(BAD_FORMAT_LAST_TOKEN_NOT_BOOLEAN_2));

        // OVERALL FORMAT FAILURE

        // EK - invalid enemy type
        Assert.assertFalse(checkSaveString(BAD_EK_NOT_ENEMY_TYPE));

        // EK - non-int
        Assert.assertFalse(checkSaveString(BAD_EK_NOT_INTEGER));

        // EK - int <= 0
        Assert.assertFalse(checkSaveString(BAD_EK_NEGATIVE_INTEGER));

        // EH - invalid enemy type
        Assert.assertFalse(checkSaveString(BAD_EH_NOT_ENEMY_TYPE));

        // I - invalid item type
        Assert.assertFalse(checkSaveString(BAD_I_NOT_ITEM_TYPE));

        // I - non-int
        Assert.assertFalse(checkSaveString(BAD_I_NOT_INTEGER));

        // I - int <= 0
        Assert.assertFalse(checkSaveString(BAD_I_NEGATIVE_INTEGER));

        // P - invalid entity type
        Assert.assertFalse(checkSaveString(BAD_P_NOT_ENTITY_TYPE));

        // P - non-int
        Assert.assertFalse(checkSaveString(BAD_P_NOT_INTEGER));

        // P - int <= 0
        Assert.assertFalse(checkSaveString(BAD_P_NEGATIVE_INTEGER));

        // S - target not consisting of two tokens
        Assert.assertFalse(checkSaveString(BAD_S_INVALID_TARGET_TOKEN_COUNT));

        // S - second part of target not int
        Assert.assertFalse(checkSaveString(BAD_S_SCORE_SYSTEM_NOT_INT));

        // S - second part less than zero
        Assert.assertFalse(checkSaveString(BAD_S_SCORE_SYSTEM_NEGATIVE));

        // S - value not int
        Assert.assertFalse(checkSaveString(BAD_S_NOT_INTEGER));

        // S - value <= 0
        Assert.assertFalse(checkSaveString(BAD_S_NEGATIVE_INTEGER));

        // M - coordinate string not consisting of two tokens
        Assert.assertFalse(checkSaveString(BAD_M_INVALID_TOKEN_COUNT));

        // M - x coordinate not int
        Assert.assertFalse(checkSaveString(BAD_M_X_NOT_INTEGER));

        // M - y coordinate not int
        Assert.assertFalse(checkSaveString(BAD_M_Y_NOT_INTEGER));

        // M - x coordinate < 0
        Assert.assertFalse(checkSaveString(BAD_M_X_NEGATIVE_INTEGER));

        // M - y coordinate < 0
        Assert.assertFalse(checkSaveString(BAD_M_Y_NEGATIVE_INTEGER));
    }
}