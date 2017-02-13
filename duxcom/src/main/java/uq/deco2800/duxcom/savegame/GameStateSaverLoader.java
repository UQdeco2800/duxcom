package uq.deco2800.duxcom.savegame;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains functions for saving and loading game state statistics
 * that are used in conjunction with objectives
 * Created by Tom B on 18/10/2016.
 */
public class GameStateSaverLoader {

    // Constants for save string
    public static final String ENEMY_HIT = ObjectiveSaverLoader.ENEMY_HIT;
    public static final String ENEMY_KILL = ObjectiveSaverLoader.ENEMY_KILL;
    public static final String ITEM = ObjectiveSaverLoader.ITEM;
    public static final String SCORE = ObjectiveSaverLoader.SCORE;
    public static final String MOVEMENT = ObjectiveSaverLoader.MOVEMENT;
    public static final String PROTECTION = ObjectiveSaverLoader.PROTECTION;
    public static final String SEPARATOR = ObjectiveSaverLoader.SEPARATOR;
    public static final String SCORE_SEPARATOR = ObjectiveSaverLoader.SCORE_SEPARATOR;

    /**
     * Ignore this - all methods are static (prevents a code smell)
     */
    protected GameStateSaverLoader() {

    }

    /**
     * USE THESE TWO METHODS TO ASSIST WITH SAVING/LOADING GAME STATE
     * STATISTICS AND OBJECTIVES:
     */

    /**
     * Creates an ObjectiveTracker that can be used in loading a saved game.
     * @param objectiveContents - list of objective save strings
     * @param gameStateContents - list of game state save strings
     * @return an ObjectiveTracker object if the objective and game state save contents
     *          are valid, else null
     * @require objectiveContents != null && objectiveContents.size() > 0 &&
     *          gameStateContents != null && gameStateContents.size() > 0 &&
     *          both lists don't have null elements
     */
    public static ObjectiveTracker loadExistingObjectiveState(List<String> objectiveContents,
                                                              List<String> gameStateContents) {
        // Check and build objectives
        for (String s: objectiveContents) {
            boolean acceptable = ObjectiveSaverLoader.checkSaveString(s);
            if (!acceptable) {
                return null;
            }
        }
        List<Objective> objectives = ObjectiveSaverLoader.loadObjectives(objectiveContents);

        // Check and build game state
        for (String s: gameStateContents) {
            boolean acceptable = checkGameStateSaveString(s);
            if (!acceptable) {
                return null;
            }
        }
        GameState gameState = loadGameState(gameStateContents);

        return new ObjectiveTracker(gameState, objectives);
    }

    /**
     * Collects all of the objectives and game state statistics and creates a list
     * of two string lists: 1st list is objective save strings, 2nd list is game
     * state statistic save strings.
     * @param ot - objective tracker containing information to save
     * @return a list of objective save strings and a list of game state save strings
     * @require ot != null
     * @ensure 1st list returned is valid objective save strings and 2nd one is valid
     *         game state save strings
     */
    public static List<List<String>> saveExistingObjectiveState(ObjectiveTracker ot) {
        List<List<String>> allSaveContents = new ArrayList<>();

        // Get objectives for saving
        List<Objective> objectives = ot.getObjectives();
        List<String> objectiveSaveContents = ObjectiveSaverLoader.saveObjectives(objectives);
        allSaveContents.add(objectiveSaveContents);

        // Get game state for saving
        GameState gs = ot.getGameState();
        List<String> gameStateSaveContents = gameStateToStringList(gs);
        allSaveContents.add(gameStateSaveContents);

        return allSaveContents;
    }

    /**
     * HELPER METHODS - DON'T USE THESE DIRECTLY
     * NOTE: Many checking methods would behave exactly the same for statistics,
     * so are directly used from ObjectiveSaverLoader
     */

    /**
     * Checks to make sure that a given save string is valid.
     * To be valid, the following requirements must be met:
     * 1) Number of tokens in save string is 3
     * 2) Save string constant (first token) must be in ObjectiveSaverLoader's
     *    saveStringConstants list
     * 3) Format of string is valid given the statistic it is trying to
     *    represent.
     * @param saveString string being examined
     * @return
     */
    public static boolean checkGameStateSaveString(String saveString) {
        if (!ObjectiveSaverLoader.checkSaveStringConstant(saveString)) {
            return false;
        }

        if (!checkTokenCount(saveString)) {
            return false;
        }

        return ObjectiveSaverLoader.checkGeneralFormat(saveString);

    }

    /**
     * Prepare to load GameState.
     * @param savedStatistics statistics being used in a loaded GameState
     * @return GameState with given statistics
     * @require savedStatistics != null && no nulls in savedStatistics
     *          && each statistic string is valid.
     */
    public static GameState loadGameState(List<String> savedStatistics) {
        Map<Object, Object> statistics = new HashMap<>();

        for (String s: savedStatistics) {
            Map<Object, Object> stat = buildStatisticFromString(s);
            statistics.putAll(stat);
        }
        return new GameState(statistics);
    }

    /**
     * Prepare to save GameState.
     * @param gs GameState to save
     * @return list of strings based on statistics from given GameState.
     * @require gs != null
     */
    public static List<String> gameStateToStringList(GameState gs) {
        List<String> toSave = new ArrayList<>();
        Map<Object, Object> statistics = gs.getStatistics();

        for (Map.Entry<Object, Object> entry: statistics.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            toSave.add(buildStatisticSaveString(key, value));
        }
        return toSave;
    }

    /**
     * Returns a string representation of the type of game state statistic being
     * dealt with, based on the given key and value
     * @param key - object representing the statistic's target
     * @param value - object representing the statistic's value
     * @return a short string corresponding to the type of game state statistic detected.
     * @require (value instanceof Integer || value instanceof Boolean) && key != null
     *          && value != null && value >= 0 && (key instanceof EnemyObjectiveType ||
     *          key instanceof ItemType || key instanceof ObjectiveCoordinate ||
     *          key instanceof ScoreSystem || key instanceof EntityType
     * @ensure statistic string is of a valid format based on the given key and value
     */
    public static String buildStatisticSaveString(Object key, Object value) {

        // Get pieces
        String type = getStatisticTypeStringRepresentation(key, value);
        String target;
        if (key instanceof ScoreSystem) {
            ScoreSystem s = (ScoreSystem)key;
            target = s.getName() + ":" + s.getScore();
        } else if (key instanceof EnemyObjectiveType) {
            EnemyObjectiveType et = (EnemyObjectiveType)key;
            target = et.getType().toString();
        } else if (key instanceof ItemType) {
            ItemType it = (ItemType)key;
            target = it.toString();
        } else if (key instanceof EntityType){
            EntityType et = (EntityType)key;
            target = et.toString();
        } else {
            ObjectiveCoordinate oc = (ObjectiveCoordinate)key;
            target = oc.toString();
        }
        String valueFound = value.toString();

        // Put together
        return type + SEPARATOR + target + SEPARATOR + valueFound;
    }

    /**
     * Creates a single game state statistic from a string.
     * @param saveLine game state statistic save string to build statistic from
     * @return game state statistic corresponding to given string.
     * @require saveLine != null && saveLine is a valid game state statistic save
     *          string
     */
    public static Map<Object, Object> buildStatisticFromString(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);
        String start = parts[0];
        switch (start) {
            case ENEMY_KILL:
                return buildEnemyKillStatistic(saveLine);
            case ENEMY_HIT:
                return buildEnemyHitStatistic(saveLine);
            case ITEM:
                return buildItemStatistic(saveLine);
            case MOVEMENT:
                return buildMovementStatistic(saveLine);
            case SCORE:
                return buildScoreStatistic(saveLine);
            default:
                return buildProtectionStatistic(saveLine);
        }
    }

    /**
     * Gets one of the save string constants as mentioned in
     * ObjectiveSaverLoader according to the statistic key and value.
     * @param key key being examined
     * @param value value being examined
     * @return save string constant from given key and value
     * @require k != null && value != null
     * @ensure save string constant given is one of the provided constants
     *          defined in ObjectiveSaverLoader
     */
    public static String getStatisticTypeStringRepresentation(Object key, Object value) {
        if (key instanceof EnemyObjectiveType && value instanceof Integer) {
            return ENEMY_KILL;
        }
        if (key instanceof EnemyObjectiveType && value instanceof Boolean) {
            return ENEMY_HIT;
        }
        if (key instanceof ItemType) {
            return ITEM;
        }
        if (key instanceof ObjectiveCoordinate) {
            return MOVEMENT;
        }
        if (key instanceof EntityType) {
            return PROTECTION;
        }
        return SCORE;
    }

    /**
     * Checks to see that the save string provided has the correct number
     * of tokens - should be three.
     *
     * @param saveString string being examined.
     * @return true if above conditions are met, else false
     * @require saveString != null && saveString.length() > 0 &&
     * checkSaveStringConstant(saveString)
     */
    public static boolean checkTokenCount(String saveString) {
        String[] parts = saveString.split(SEPARATOR);

        return parts.length == 3;
    }

    /**
     * Creates an enemy kill statistic given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new enemy kill statistic.
     * @return a new enemy kill statistic from the given save string
     * @require saveLine starts with "EK", saveLine has three parts split by '/',
     * type given in saveLine is an EnemyType, value in saveLine is an
     * integer > 0
     */
    private static Map<Object, Object> buildEnemyKillStatistic(String saveLine) {
        Map<Object, Object> statistic = new HashMap<>();
        String[] parts = saveLine.split(SEPARATOR);

        EnemyType type = EnemyType.valueOf(parts[1]);
        EnemyObjectiveType et = new EnemyObjectiveType(type, true);
        int value = Integer.parseInt(parts[2]);

        statistic.put(et, value);
        return statistic;
    }

    /**
     * Creates an enemy hit statistic given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new enemy hit statistic.
     * @return a new enemy hit statistic from the given save string
     * @require saveLine starts with "EH", saveLine has three parts split by '/',
     * type given in saveLine is an EnemyType, value in saveLine
     * is either "true" or "false"
     */
    private static Map<Object, Object> buildEnemyHitStatistic(String saveLine) {
        Map<Object, Object> statistic = new HashMap<>();
        String[] parts = saveLine.split(SEPARATOR);

        EnemyType type = EnemyType.valueOf(parts[1]);
        EnemyObjectiveType et = new EnemyObjectiveType(type, false);
        boolean value = Boolean.parseBoolean(parts[2]);

        statistic.put(et, value);
        return statistic;
    }

    /**
     * Creates an item statistic given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new item statistic.
     * @return a new item statistic from the given save string
     * @require saveLine starts with "I", saveLine has three parts split by '/',
     * type given in saveLine is an ItemType, value in saveLine is an integer
     * and value >= 0
     */
    private static Map<Object, Object> buildItemStatistic(String saveLine) {
        Map<Object, Object> statistic = new HashMap<>();
        String[] parts = saveLine.split(SEPARATOR);

        ItemType type = ItemType.valueOf(parts[1]);
        int value = Integer.parseInt(parts[2]);

        statistic.put(type, value);
        return statistic;
    }

    /**
     * Creates a movement statistic given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new movement statistic.
     * @return a new movement statistic from the given save string
     * @require saveLine starts with "M", saveLine has three parts split by '/',
     * type given is a valid set of coordinates, value is either "true" or "false"
     */
    private static Map<Object, Object> buildMovementStatistic(String saveLine) {
        Map<Object, Object> statistic = new HashMap<>();
        String[] parts = saveLine.split(SEPARATOR);

        String[] coordinateParts = parts[1].split(",");
        int x = Integer.parseInt(coordinateParts[0]);
        int y = Integer.parseInt(coordinateParts[1]);

        boolean value = Boolean.parseBoolean(parts[2]);

        statistic.put(new ObjectiveCoordinate(x, y), value);
        return statistic;
    }

    /**
     * Creates a score statistic given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new score statistic.
     * @return a new score statistic from the given save string
     * @require saveLine starts with "S", saveLine has four parts split by '/',
     * second part is split by a ':' with the last part being an integer >=0,
     * last part is either "true" or "false"
     */
    private static Map<Object, Object> buildScoreStatistic(String saveLine) {
        Map<Object, Object> statistic = new HashMap<>();
        String[] parts = saveLine.split(SEPARATOR);

        String[] scoreSystemParts = parts[1].split(SCORE_SEPARATOR);
        ScoreSystem s = new ScoreSystem();
        s.setName(scoreSystemParts[0]);
        int score = Integer.parseInt(scoreSystemParts[1]);
        s.changeScore(score, '+');

        int value = Integer.parseInt(parts[2]);

        statistic.put(s, value);
        return statistic;
    }

    /**
     * Creates a protection statistic given the save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new protection statistic.
     * @return a new protection statistic from the given save string
     * @require saveLine starts with "P", saveLine has three parts split by '/',
     * second part is an EntityType, third part is an integer >=0,
     */
    private static Map<Object, Object> buildProtectionStatistic(String saveLine) {
        Map<Object, Object> statistic = new HashMap<>();
        String[] parts = saveLine.split(SEPARATOR);

        EntityType et = EntityType.valueOf(parts[1]);
        int value = Integer.parseInt(parts[2]);

        statistic.put(et, value);
        return statistic;
    }
}
