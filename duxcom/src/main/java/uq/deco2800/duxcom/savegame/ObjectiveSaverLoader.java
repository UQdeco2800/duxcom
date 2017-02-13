package uq.deco2800.duxcom.savegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.objectives.*;
import uq.deco2800.duxcom.objectivesystem.ObjectiveCoordinate;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains functions that can be used in saving/loading objectives from/to
 * the game - NOT ACTUAL SAVING/LOADING, more like methods to carry out the
 * process of transforming strings to objectives and vice versa.
 * Created by Tom B on 10/10/2016.
 */
public final class ObjectiveSaverLoader {

    // Objective type save string constants
    public static final String ENEMY_HIT = "EH";
    public static final String ENEMY_KILL = "EK";
    public static final String ITEM = "I";
    public static final String SCORE = "S";
    public static final String MOVEMENT = "M";
    public static final String PROTECTION = "P";

    // String separators and true/false flags
    public static final String SEPARATOR = "/";
    public static final String SCORE_SEPARATOR = ":";
    public static final String MOVEMENT_SEPARATOR = ",";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    // Error messages
    public static final String NOT_INT = "Target value not an integer";
    public static final String NOT_INT_2 = "Target value and current score must be integers";
    public static final String NOT_INT_3 = "Coordinates used must be integers";
    public static final String NOT_ENEMY_TYPE = "Target type not part of EnemyType";
    public static final String NOT_ITEM_TYPE = "Target type not part of EnemyType";

    private static Logger logger = LoggerFactory.getLogger(ObjectiveSaverLoader.class);

    // List for use in checking save strings are valid
    protected static final List<String> SAVE_CONSTANTS = new ArrayList<>();
    static {
        SAVE_CONSTANTS.add(ENEMY_HIT);
        SAVE_CONSTANTS.add(ENEMY_KILL);
        SAVE_CONSTANTS.add(ITEM);
        SAVE_CONSTANTS.add(SCORE);
        SAVE_CONSTANTS.add(MOVEMENT);
        SAVE_CONSTANTS.add(PROTECTION);
    }


    /**
     * Ignore this - all methods are static
     */
    protected ObjectiveSaverLoader() {

    }

    /**
     * HELPER METHODS - DON'T USE THESE DIRECTLY
     * Instead, go to GameStateSaverLoader and use the methods
     * loadExistingObjectiveState() and saveExistingObjectiveState(),
     * which will use all of the methods below within their
     * implementations.
     */

    /**
     * Checks to make sure that a given save string is valid.
     * To be valid, a save string must meet the following requirements:
     * 1) Token count must either be three (representing MovementObjective)
     *    or four (others)
     * 2) 1st token must be in saveStringConstants list
     * 3) Last token must be "true" or "false"
     * 4) Format of whatever objective is attempting to be represented
     *    is valid (see individual calls in checkGeneralFormat())
     * @param saveString - string being examined
     * @return whether or not the given save string
     * @require saveString.length() > 0 && saveString != null
     */
    public static boolean checkSaveString(String saveString) {
        if (!checkSaveStringConstant(saveString)) {
            return false;
        }

        if (!checkTokenCount(saveString)) {
            return false;
        }

        if (!checkLastToken(saveString)) {
            return false;
        }

        if (!checkGeneralFormat(saveString)) {
            return false;
        }
        return true;
    }

    /**
     * Creates a list of objectives given a list of objective save strings.
     *
     * @param saveContents - list of save strings for objectives
     * @return list of objectives corresponding to the save strings given
     * @require saveContents != null && saveContents.size() > 0
     * && no nulls in saveContents
     */
    public static List<Objective> loadObjectives(List<String> saveContents) {
        List<Objective> created = new ArrayList<>();

        for (String s : saveContents) {
            Objective o = loadObjective(s);
            created.add(o);
        }
        return created;
    }

    /**
     * Creates a list of save strings that can be placed into a file/sent
     * over Singularity from a list of objectives.
     * @param objectives objectives that will eventually be saved
     * @return list of save strings from objectives
     * @require objectives != null && no nulls in objectives
     */
    public static List<String> saveObjectives(List<Objective> objectives) {
        List<String> saveContents = new ArrayList<>();

        for (Objective o : objectives) {
            String line = buildObjectiveSaveString(o);
            saveContents.add(line);
        }
        return saveContents;
    }

    /**
     * Builds a string for saving an objective to file, according
     * to the following format:
     * TYPE/TARGET/VALUE/MET
     *
     * @param o objective string representation to form
     * @return string to save objective with according to the above
     * format
     * @require o != null
     * @ensure \return is of the format above
     */
    public static String buildObjectiveSaveString(Objective o) {

        // Get pieces
        String type = getObjectiveTypeStringRepresentation(o);
        String target;

        // Start building parts
        if (o instanceof ScoreObjective) {
            ScoreSystem s = (ScoreSystem) o.getObjectiveTarget();
            target = s.getName() + ":" + s.getScore();
        } else if (o instanceof EnemyHitObjective || o instanceof EnemyKillObjective) {
            EnemyObjectiveType eot = (EnemyObjectiveType) o.getObjectiveTarget();
            target = eot.getType().toString();
        } else if (o instanceof MovementObjective) {
            return type + SEPARATOR + o.getObjectiveTarget() + SEPARATOR + o.met();
        } else {
            target = o.getObjectiveTarget().toString();
        }
        String value = o.getObjectiveValue().toString();
        String met = booleanToString(o.met());

        // Put together
        return type + SEPARATOR + target + SEPARATOR + value +
                SEPARATOR + met;
    }

    /**
     * Gets one of the objective type save string constants as mentioned
     * at the top of this class according to the type of objective provided.
     * @param o objective being examined
     * @return save string constant from given objective's type
     * @require o != null
     * @ensure save string constant given is one of the provided constants
     *          defined in this class
     */
    public static String getObjectiveTypeStringRepresentation(Objective o) {
        if (o instanceof EnemyHitObjective) {
            return ENEMY_HIT;
        }
        if (o instanceof EnemyKillObjective) {
            return ENEMY_KILL;
        }
        if (o instanceof ItemObjective) {
            return ITEM;
        }
        if (o instanceof ScoreObjective) {
            return SCORE;
        }
        if (o instanceof MovementObjective) {
            return MOVEMENT;
        }
        return PROTECTION;
    }

    /**
     * Recreates an objective from an objective save string.
     *
     * @param saveLine line whose information is being extracted.
     * @return a new objective given the save string
     * @require saveLine != null && saveLine.length() > 0
     */
    public static Objective loadObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);
        String start = parts[0];

        switch (start) {
            case ENEMY_KILL:
                return loadEnemyKillObjective(saveLine);
            case ENEMY_HIT:
                return loadEnemyHitObjective(saveLine);
            case ITEM:
                return loadItemObjective(saveLine);
            case MOVEMENT:
                return loadMovementObjective(saveLine);
            case SCORE:
                return loadScoreObjective(saveLine);
            default:
                return loadProtectionObjective(saveLine);
        }
    }

    /**
     * Creates an enemy kill objective given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new enemy kill objective.
     * @return a new enemy kill objective from the given save string
     * @require saveLine starts with "EK", saveLine has four parts split by '/',
     * type given in saveLine is an EnemyType, value in saveLine is an
     * integer > 0, last part is either "true" or "false"
     */
    private static EnemyKillObjective loadEnemyKillObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);

        EnemyType type = EnemyType.valueOf(parts[1]);
        int value = Integer.parseInt(parts[2]);
        boolean met = Boolean.parseBoolean(parts[3]);

        EnemyKillObjective ek = new EnemyKillObjective(type, value);
        if (met) {
            ek.complete();
        }

        return ek;
    }

    /**
     * Creates an enemy hit objective given a save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new enemy hit objective.
     * @return a new enemy hit objective from the given save string
     * @require saveLine starts with "EH", saveLine has four parts split by '/',
     * type given in saveLine is an EnemyType, value in saveLine is either
     * "true" or "false", last part is either "true" or "false"
     */
    private static EnemyHitObjective loadEnemyHitObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);

        EnemyType type = EnemyType.valueOf(parts[1]);
        boolean value = Boolean.parseBoolean(parts[2]);
        boolean met = Boolean.parseBoolean(parts[3]);

        EnemyHitObjective eh = new EnemyHitObjective(type);
        if (met && value) {
            eh.complete();
            eh.setObjectiveValue(value);
        }

        return eh;
    }

    /**
     * Creates an item objective from the given save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new item objective.
     * @return a new item objective from the given save string
     * @require saveLine starts with "I", saveLine has four parts split by '/',
     * type given in saveLine is an EnemyType, value in saveLine is an integer,
     * last part is either "true" or "false"
     */
    private static ItemObjective loadItemObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);

        ItemType type = ItemType.valueOf(parts[1]);
        int value = Integer.parseInt(parts[2]);
        boolean met = Boolean.parseBoolean(parts[3]);

        ItemObjective io = new ItemObjective(type, value);
        if (met) {
            io.complete();
        }
        return io;
    }

    /**
     * Creates a movement objective given the save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new movement objective.
     * @return a new movement objective from the given save string
     * @require saveLine starts with "M", saveLine has three parts split by '/',
     * second part is two comma separated integers >= 0,
     * last part is either "true" or "false"
     */
    private static MovementObjective loadMovementObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);

        String[] coordinateParts = parts[1].split(",");
        int x = Integer.parseInt(coordinateParts[0]);
        int y = Integer.parseInt(coordinateParts[1]);
        boolean met = Boolean.parseBoolean(parts[2]);
        MovementObjective mo = new MovementObjective(new ObjectiveCoordinate(x, y));
        if (met) {
            mo.complete();
        }
        return mo;
    }

    /**
     * Creates a score objective given the save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new score objective.
     * @return a new score objective from the given save string
     * @require saveLine starts with "S", saveLine has four parts split by '/',
     * second part is split by a ':' with the last part being an integer >=0,
     * last part is either "true" or "false"
     */
    private static ScoreObjective loadScoreObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);

        // Extracting values
        String[] scoreSystemParts = parts[1].split(SCORE_SEPARATOR);
        ScoreSystem s = new ScoreSystem();
        s.setName(scoreSystemParts[0]);
        int score = Integer.parseInt(scoreSystemParts[1]);
        s.changeScore(score, '+');
        int value = Integer.parseInt(parts[2]);
        boolean met = Boolean.parseBoolean(parts[3]);

        // Form objective
        ScoreObjective so = new ScoreObjective(s, value);
        if (met) {
            so.complete();
        }

        return so;
    }

    /**
     * Creates a protection objective given the save string.
     *
     * @param saveLine - save string whose information will be used for creating
     *                 the new protection objective.
     * @return a new protection objective from the given save string
     * @require saveLine starts with "P", saveLine has four parts split by '/',
     * second part is an EntityType, third part is an integer >=0,
     * last part is either "true" or "false"
     */
    private static ProtectionObjective loadProtectionObjective(String saveLine) {
        String[] parts = saveLine.split(SEPARATOR);

        // Extracting values
        EntityType et = EntityType.valueOf(parts[1]);
        int value = Integer.parseInt(parts[2]);
        boolean met = Boolean.parseBoolean(parts[3]);

        // Form objective
        ProtectionObjective po = new ProtectionObjective(et, value);
        if (!met) {
            po.fail();
        }
        return po;
    }


    /**
     * Utility method to convert a given boolean into its
     * string form - TRUE or FALSE.
     *
     * @param b the boolean to be converted
     * @return converted boolean string representation
     */
    public static String booleanToString(boolean b) {
        if (b) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public static boolean isBoolean(String s) {
        return s.equalsIgnoreCase(TRUE) || s.equalsIgnoreCase(FALSE);
    }

    /**
     * Checks to see whether or not the first token in a given
     * objective save string is valid - it must be in the list
     * called "saveStringConstants"
     * @param saveString string being examined
     * @return false if save string's first token isn't in the
     * list of save string constants, else true
     * @require saveString.length() > 0 && saveString != null
     */
    public static boolean checkSaveStringConstant(String saveString) {
        String newSaveString = saveString.toLowerCase();
        String[] parts = newSaveString.split(SEPARATOR);
        String objectiveToken = parts[0].trim();

        // First part must match one of the objective type constants
        for (String s : SAVE_CONSTANTS) {
            if (s.equalsIgnoreCase(objectiveToken)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see that the save string provided has the correct number
     * of tokens - Movement Objectives should have three, other types four.
     *
     * @param saveString
     * @return true if above conditions are met, else false
     * @require saveString != null && saveString.length() > 0 &&
     * checkSaveStringConstant(saveString)
     */
    public static boolean checkTokenCount(String saveString) {
        String[] parts = saveString.split(SEPARATOR);
        String objectiveToken = parts[0].trim();

        if (objectiveToken.equals(MOVEMENT)) {
            return parts.length == 3;
        } else {
            return parts.length == 4;
        }
    }

    /**
     * Checks to see whether not the last token is a boolean.
     * @param saveString save string being examined
     * @return true if the last token is a boolean, else false
     * @require saveString != null && saveString.length() > 0
     */
    public static boolean checkLastToken(String saveString) {
        String[] parts = saveString.split(SEPARATOR);
        String test = parts[parts.length - 1];
        test = test.toLowerCase().trim();
        return isBoolean(test);
    }

    /**
     * Given the type of objective that a save string is representing,
     * checks to see that its target and value conform to data type rules
     * specified by the individual method call rules (see below this method).
     * @param saveString string being examined
     * @return whether or not the string as a whole fits the format
     * of the objective it's trying to represent
     * @require saveString != null && saveString.length() > 0
     */
    public static boolean checkGeneralFormat(String saveString) {

        // Separate parts
        String[] parts = saveString.split(SEPARATOR);
        String type = parts[0].trim();
        String target = parts[1].trim();
        String value = parts[2].trim();

        switch (type) {
            case ENEMY_KILL:
                return checkEnemyKillFormat(target, value);
            case ENEMY_HIT:
                return checkEnemyHitFormat(target, value);
            case ITEM:
                return checkItemFormat(target, value);
            case MOVEMENT:
                return checkMovementFormat(target, value);
            case SCORE:
                return checkScoreFormat(target, value);
            default:
                return checkProtectionFormat(target, value);
        }
    }

    /**
     * Checks that a given save string's target and value for
     * representing an EnemyKillObjective is valid, according to the
     * following format:
     * EK - Target: EnemyType, Value: Integer
     * @param type target being checked
     * @param value value being checked
     * @return whether or not the save string type and value properly
     *          represent an EnemyKillObjective
     */
    public static boolean checkEnemyKillFormat(String type, String value) {

        try {
            EnemyType.valueOf(type.trim());
            int v = Integer.parseInt(value);
            if (v <= 0) {
                return false;
            }
        } catch (NumberFormatException ne) {
            logger.info(NOT_INT, ne);
            return false;
        } catch (IllegalArgumentException ie) {
            logger.info(NOT_ENEMY_TYPE, ie);
            return false;
        }
        return true;
    }

    /**
     * Checks that a given save string's target and value for
     * representing an EnemyHitObjective is valid, according to the
     * following format:
     * EH - Target: EnemyType, Value: Boolean
     * @param type target being checked
     * @param value value being checked
     * @return whether or not the save string type and value properly
     *          represent an EnemyHitObjective
     */
    public static boolean checkEnemyHitFormat(String type, String value) {
        try {
            EnemyType.valueOf(type.trim());
        } catch (IllegalArgumentException ie) {
            logger.info(NOT_ENEMY_TYPE, ie);
            return false;
        }
        return isBoolean(value);
    }

    /**
     * Checks that a given save string's target and value for
     * representing an ItemObjective is valid, according to the
     * following format:
     * I - Target: ItemType, Value: Integer
     * @param type target being checked
     * @param value value being checked
     * @return whether or not the save string type and value properly
     *          represent an ItemObjective
     */
    public static boolean checkItemFormat(String type, String value) {
        try {
            ItemType.valueOf(type.trim());
            int w = Integer.parseInt(value.trim());
            if (w <= 0) {
                return false;
            }
        } catch (NumberFormatException nexception) {
            logger.info(NOT_INT, nexception);
            return false;
        } catch (IllegalArgumentException iexception) {
            logger.info(NOT_ITEM_TYPE, iexception);
            return false;
        }
        return true;
    }

    /**
     * Checks that a given save string's target and value for
     * representing a ProtectionObjective is valid, according to the
     * following format:
     * P - Target: EntityType, Value: Integer
     * @param type target being checked
     * @param value value being checked
     * @return whether or not the save string type and value properly
     *          represent a ProtectionObjective
     */
    public static boolean checkProtectionFormat(String type, String value) {
        try {
            EntityType.valueOf(type.trim());
            int v = Integer.parseInt(value.trim());
            if (v <= 0) {
                return false;
            }
        } catch (NumberFormatException ne) {
            logger.info(NOT_INT, ne);
            return false;
        } catch (IllegalArgumentException ie) {
            logger.info(NOT_ITEM_TYPE, ie);
            return false;
        }
        return true;
    }

    /**
     * Checks that a given save string's target and value for
     * representing a ScoreObjective is valid, according to the
     * following format:
     * S - Target: ScoreSystem, Value: Integer
     * @param type target being checked
     * @param value value being checked
     * @return whether or not the save string type and value properly
     *          represent a ScoreObjective
     */
    public static boolean checkScoreFormat(String type, String value) {
        String[] scoreParts = type.trim().split(SCORE_SEPARATOR);
        if (scoreParts.length != 2) {
            return false;
        }

        String scoreFound = scoreParts[1].trim();
        try {
            int score = Integer.parseInt(scoreFound);
            if (score < 0) {
                return false;
            }
            int targetValue = Integer.parseInt(value);
            if (targetValue < 0) {
                return false;
            }
        } catch (NumberFormatException ne) {
            logger.info(NOT_INT_2, ne);
            return false;
        }
        return true;
    }

    /**
     * Checks that a given save string's target and value for
     * representing a MovementObjective is valid, according to the
     * following format:
     * M - Target: ObjectiveCoordinate, Value: Boolean
     * @param type target being checked
     * @param value value being checked
     * @return whether or not the save string type and value properly
     *          represent a MovementObjective
     */
    private static boolean checkMovementFormat(String type, String value) {
        String[] coordinateParts = type.trim().split(MOVEMENT_SEPARATOR);
        if (coordinateParts.length != 2) {
            return false;
        }
        String xFound = coordinateParts[0].trim();
        String yFound = coordinateParts[1].trim();

        try {
            int x = Integer.parseInt(xFound);
            int y = Integer.parseInt(yFound);
            if (x < 0 || y < 0) {
                return false;
            }

        } catch (NumberFormatException ne) {
            logger.info(NOT_INT_3, ne);
            return false;
        }
        return isBoolean(value);
    }
}
