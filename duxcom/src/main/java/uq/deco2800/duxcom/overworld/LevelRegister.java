package uq.deco2800.duxcom.overworld;

import javafx.stage.Stage;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.overworld.nodes.Level;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * The public interface for the Overworld controller.
 *
 * @author Sceub
 * @author mtimmo
 */
public class LevelRegister {

	private static Level currentLevel = null;

	/**
	 * Private constructor required to fix sonar smell.
	 */
	private LevelRegister() {
	}

	/**
	 * ADD LEVELS TO BE LOADED AT TIME OF CREATION HERE.
	 * USE THE TEMPLATE BELOW WHEN ADDING YOUR LEVEL SO THAT OTHER PEOPLE CAN EASILY LINK THEIR LEVELS TOO.
	 *
	 * Level levelName = addLevel(String levelId, Point2D coordinates, Level parentLevel, Level[] unlockedAfter)
	 *
	 * levelName:		Your unique identifier for the level so others can use it as a parent or prerequisite.
	 * levelId:			The name of the map to be loaded.
	 * coordinates: 	The VIRTUAL coordinates to place the level on the Overworld (Check the nodesHigh and nodesWide
	 * 					variables in the VirtualMap class before hand so you know the bounds of these coordinates!).
	 * parentLevel:     The level that your new level will map a path from. Can be null, but not recommended (except
	 * 					for the tutorial). Be reasonable so that other people can also add their levels!
	 * unlockedAfter:	An array of level objects that must be conquered before your level can be played. Can be NULL.
	 */
	static void addLevels() {
		Level tutorial = addLevel("tutorial", new Point2D.Double(1, 1), null, null, "Tutorial", "Hero's are made, not born. Complete the tutorial to prepare for the adventure ahead!");
		Level level1 = addLevel("camp", new Point2D.Double(1, 6), tutorial, new Level[]{tutorial}, "The Camp", "Escaping from the enemy camp - our heroes pursue freedom and revenge from their long-standing capture.");
		Level level2 = addLevel("lyon_ambush", new Point2D.Double(6, 4), level1, new Level[]{level1}, "Ambush of Lyon", "After escaping the camp our heroes escape to the River of Lyon.");
		Level level3 = addLevel("coast", new Point2D.Double(6, 10), level2, new Level[]{level2}, "The Coast", "A group of heroes have been separated from the pack - they must escape the clutches of the enemy!");
		addLevel("karamja", new Point2D.Double(2, 10), level3, new Level[]{level3}, "Karamja", "Having finally lost their pursuers, the separated heroes make camp in a forest clearing.");
		Level level4 = addLevel("long_dry", new Point2D.Double(9, 7), level2, new Level[]{level2}, "The Long Dry", "Our heroes have sailed from the coat and find themselves in a barren land.");
		addLevel("falador", new Point2D.Double(9, 10), level4, new Level[]{level2}, "Falador", "Lost in the desert our heroes discover a location of legend, Falador! Countless treasures may lie ahead, but there's no guarantee that they are the first to discover it...");
		Level level5 = addLevel("enemy_hands", new Point2D.Double(12, 2), level4, new Level[]{level4}, "In Enemy Hands", "Our heroes move to the enemy castle seeking revenge for their ensnarement.");
		Level level6 = addLevel("village", new Point2D.Double(13, 4), level5, new Level[]{level5}, "The Village", "The local village is being attacked! Help save the villagers!");
		Level level7 = addLevel("south", new Point2D.Double(16, 7), level6, new Level[]{level6}, "South!", "Our heroes are chased from the village down South to further enemy borders!");
		addLevel("further_south", new Point2D.Double(13, 10), level7, new Level[]{level7}, "Further South!", "The final battle rages in a snowy region - will our heroes finally gain redemption?");
	}

	/**
	 * Adds a level to the Overworld's virtual map. This can be called from other classes if it needs to,
	 * but be careful because it may mess up the other levels and/or the display of all levels.
	 *
	 * @param levelId       The unique name of the level to add - will need to match to the name of the map to load.
	 * @param coordinates   The virtual coordinates of the Overworld. Check the nodesHigh and nodesWide variables in
	 *                      the VirtualMap class before hand so you know the bounds of these coordinates!
	 * @param parentLevel   The level that this new level will physically branch from when mapping a path on the map.
	 * @param unlockedAfter A list of levels that need to be conquered before this level can be played.
	 * @return The Level object created for this newly added level.
	 */
	public static Level addLevel(String levelId, Point2D coordinates, Level parentLevel, Level[] unlockedAfter, String labelText,
			String levelDescription) {
		return VirtualMap.registerLevel(levelId, coordinates, parentLevel, unlockedAfter, labelText, levelDescription);
	}

	/**
	 * Gets the most recently played level, according to Overworld.
	 *
	 * @return the Level object marked as the current level.
	 */
	public static Level getCurrentLevel() {
		return currentLevel;
	}
	
	/**
	 * Overworld's method to loading a campaign level. This way when the level is marked as "complete" overworld knows
	 * what levels to unlock.
	 *
	 * @param interfaceManager instance of interface manager to call when loading the level.
	 * @param stage            the primary stage to load onto.
	 * @param level            the unique level identifier that loads the correct level onto the stage.
	 */
	public static void loadLevel(InterfaceManager interfaceManager, Stage stage, Level level) {
		if (level.getLevelId().equals("tutorial")) {
			currentLevel = level;
			interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, stage, level.getLevelId());
		} else {
			if (level.isUnlocked()) {
				currentLevel = level;
				interfaceManager.loadSegmentImmediate(InterfaceSegmentType.HERO_SELECT, stage, level.getLevelId());
			}
		}
	}

	/**
	 * Sets the status of the current level (most recently played) to "conquered", allowing for later missions to be
	 * unlocked. This will be useful for the save game team to load saved progress.
	 */
	public static void conquerLevel() {
		if (currentLevel != null) {
			conquerLevel(currentLevel);
		}
	}

	/**
	 * Sets the status of a level to "conquered", allowing for later missions to be unlocked. This will be useful for
	 * the save game team to load saved progress.
	 *
	 * @param level    the level object to mark as conquered.
	 */
	public static void conquerLevel(Level level) {
		VirtualMap.conquerLevel(level);
	}

	/**
	 * Refreshes the state of the levels in Overworld. Only here in case of bugs - it shouldn't be needed.
	 */
	public static void refreshLevels() {
		VirtualMap.refreshLevels();
	}

	/**
	 * Creates a comma separated list of level ids for levels that have been conquered. This is primarily designed for
	 * the save game team to be able to save the state of the overworld campaign.
	 *
	 * @return a string comma separated level ids.
	 */
	public static String saveCompletedLevels() {
		String output = "";
		for (Level level : VirtualMap.getLevels()) {
			if (level.isConquered()) {
				output += level.getLevelId() + ",";
			}
		}

		if (output.length() == 0) {
			return output;
		}

		return output.substring(0, output.length()-1);
	}

	/**
	 * Takes a string of comma separated level ids and if any of the loaded levels match any of the level ids, then
	 * that level will be marked as conquered. This is primarily designed for the save game team to restore the state
	 * of the overworld campaign.
	 *
	 * @param input    the input string of comma separated values.
	 */
	public static void loadCompletedLevels(String input) {
		List<Level> levels = VirtualMap.getLevels();
		for (String str : input.split(",")) {
			for (Level level : levels) {
				if (level.getLevelId().equals(str)) {
					level.setConquered(true);
				}
			}
		}
		refreshLevels();
	}
}
