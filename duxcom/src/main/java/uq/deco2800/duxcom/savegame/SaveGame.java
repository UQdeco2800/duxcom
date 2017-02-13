package uq.deco2800.duxcom.savegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.maps.*;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectivesystem.GameState;

public class SaveGame {

	private static Logger logger = LoggerFactory.getLogger(SaveGame.class);
	private final String home = System.getProperty("user.home");
	private Object obj;
	private boolean saveClick = false;
	
	/**
	 * Constructor
	 */
	public SaveGame() {
		// Empty Constructor
		
	}

	/**
	 * saves the state of the game
	 */
	public void saveGame(SaveOriginator saveOriginator) {
		this.obj = saveOriginator.save();
		saveClick = true;
		// pass objectives for saving
		
		saveToFile(saveOriginator.get(),
				saveOriginator.getSavedObjectives(), saveOriginator.getSavedGameState());
	}

	/**
	 * loads the game when it was last saved
	 * @throws IOException if game cannot be loaded
	 * 
	 */
	public void loadGame(SaveOriginator saveOriginator) throws IOException {
		if (saveClick) {
			// Load from in game State
			saveOriginator.load(this.obj);

		} else {
			// load from Local file
			AbstractGameMap savedMap = loadmap();
			List<Objective> savedObjectives = loadObjectives();
			GameState gameState = loadGameState();
			saveOriginator.writeAbstractGameMap(savedMap, savedObjectives, gameState);
			// saveOriginator.writeMap(new MapAssembly(savedMap));

		}
	}

	/*---- Helper Methods -----*/

	/**
	 * Saves the given map to file located in local disk
	 * 
	 * @param map
	 *            The map to be saved
	 */
	private void saveToFile(MapAssembly map, List<Objective> objectives, GameState gameState) {
		File saveDirectory = new File(home + "/Documents/My Games/DuxCom");
		saveDirectory.mkdirs();
		File saveFile = new File(home + "/Documents/My Games/DuxCom/DuxCom.dux");
		File objectivesFile = new File(home + "/Documents/My Games/DuxCom/DuxComObjectives.dux");
		File gameStateFile = new File(home + "/Documents/My Games/DuxCom/DuxComGameState.dux");
		/* if the file already exists delete it */
		if (saveFile.exists()) {
			saveFile.delete();
		} 
		if (objectivesFile.exists()) {
			objectivesFile.delete();
		}

		if (gameStateFile.exists()) {
			objectivesFile.delete();
		}

		/* Fresh copy of save file */
		saveFile = new File(home + "/Documents/My Games/DuxCom/DuxCom.dux");
		objectivesFile = new File(home + "/Documents/My Games/DuxCom/DuxComObjectives.dux");
		gameStateFile = new File(home + "/Documents/My Games/DuxCom/DuxComGameState.dux");
		try {
			startWriting(saveFile, map);
			saveObjectives(objectivesFile, objectives);
			saveGameState(gameStateFile, gameState);
		} catch (IOException e) {
			logger.error("Unable to save game", e);
		}
	}

	/**
	 * loads the map when it was saved
	 * @return returnds the saved map
	 * @throws IOException if map cannot be loaded
	 */
	private AbstractGameMap loadmap() throws IOException {
		AbstractGameMap finalMap = null;
		File saveFile = new File(home + "/Documents/My Games/DuxCom/DuxCom.dux");
		FileReader fileReader = new FileReader(saveFile);
		int line = 0;
		/* list of all saved entities */
		ArrayList<Entity> entities = new ArrayList<>();
		try (Scanner fileScanner = new Scanner(fileReader)) {
			while (fileScanner.hasNextLine()) {
				ArrayList<String> lineArguments = new ArrayList<>();
				String lineContent = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(lineContent);
				line = line + 1;
				while (lineScanner.hasNext()) {
					// add each element of a line to a list
					lineArguments.add(lineScanner.next());
				}
				if (line == 1) {
					MapType mapType = MapType.valueOf(lineArguments.get(0));
					finalMap = getMapFromType(mapType);
					lineScanner.close();
					continue;
				}
				addEntities(entities, lineArguments);
				lineScanner.close();
			}
		} catch (IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
			logger.error("Error loading map", e);
			throw new InvalidFormatException();
		} finally {
			fileReader.close();
		}
		replaceEntities(finalMap, entities);
		return finalMap;
	}
	
	/**
	 * loads objectives from the file
	 */
	private List<Objective> loadObjectives() throws IOException {
		File saveFile = new File(home + "/Documents/My Games/DuxCom/DuxComObjectives.dux");
		FileReader fileReader = new FileReader(saveFile);
		/* list of all saved objectives */
		List<String> objectives = new ArrayList<>();
		
		try (Scanner fileScanner = new Scanner(fileReader)) {
			
			while (fileScanner.hasNextLine()) {
				String lineContent = fileScanner.nextLine();
				objectives.add(lineContent);
			}
			
		} catch (IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
			logger.error("Error loading map", e);
			throw new InvalidFormatException();
		} finally {
			fileReader.close();
		}
		return ObjectiveSaverLoader.loadObjectives(objectives);
	}

	private GameState loadGameState() throws IOException {
		File saveFile = new File(home + "/Documents/My Games/DuxCom/DuxComGameState.dux");
		FileReader fileReader = new FileReader(saveFile);

		/* list of all saved game state statistics */
		List<String> gameStateStatistics = new ArrayList<>();

		try (Scanner fileScanner = new Scanner(fileReader)) {

			while (fileScanner.hasNextLine()) {
				String lineContent = fileScanner.nextLine();
				gameStateStatistics.add(lineContent);
			}

		} catch (IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
			logger.error("Error loading map", e);
			throw new InvalidFormatException();
		} finally {
			fileReader.close();
		}
		return GameStateSaverLoader.loadGameState(gameStateStatistics);
	}

	/**
	 * adds all the entities to the map
	 * @param map map to add the entitites to
	 * @param lineArguments the entities to add
	 */
	private void addEntities(ArrayList<Entity> entities, ArrayList<String> lineArguments) {
		EntityType type = EntityType.valueOf(lineArguments.get(0));
		Entity entity = EntityConstructor.generateEntity(type, 1, 1);
		entity.decode(lineArguments);
		if(entity instanceof AbstractHero) {
			((AbstractHero) entity).changeStat(HeroStat.SPEED, Double.parseDouble(lineArguments.get(5)));
			((AbstractHero) entity).changeStat(HeroStat.DAMAGE, Double.parseDouble(lineArguments.get(6)));
			((AbstractHero) entity).changeStat(HeroStat.HEALTH, Double.parseDouble(lineArguments.get(7)));
			((AbstractHero) entity).changeStat(HeroStat.BASE_AP, Double.parseDouble(lineArguments.get(8)));
			((AbstractHero) entity).changeStat(HeroStat.RECHARGE_AP, Double.parseDouble(lineArguments.get(9)));
			((AbstractHero) entity).changeStat(HeroStat.ACTION_POINTS, Double.parseDouble(lineArguments.get(10)));
			((AbstractHero) entity).changeStat(HeroStat.ARMOUR, Double.parseDouble(lineArguments.get(11)));
			((AbstractHero) entity).changeStat(HeroStat.LEVEL, Double.parseDouble(lineArguments.get(12)));
			((AbstractHero) entity).changeStat(HeroStat.XP, Double.parseDouble(lineArguments.get(13)));
			((AbstractHero) entity).changeStat(HeroStat.BASE_HEALTH, Double.parseDouble(lineArguments.get(14)));
			((AbstractHero) entity).changeStat(HeroStat.VISIBILITY_RANGE, Double.parseDouble(lineArguments.get(15)));
		}
		entities.add(entity);
	}

	/**
	 * replaces all the entities to the ones when it was saved
	 * @param map
	 * 			the map tp replace entities from
	 */
	private void replaceEntities(AbstractGameMap map, ArrayList<Entity> entities) {
		map.removeEntity();
		for (Entity entity : entities) {
			if (entity instanceof AbstractHero) {
				map.addHero((AbstractHero) entity);
			} else {
				map.addEnemy((AbstractEnemy) entity);
			}
		}
	}

	/**
	 * saves the game to the file
	 * @param file the file to save the game state is
	 * 
	 * @throws IOException if game cannot be saved
	 */
	private void startWriting(File file, MapAssembly map) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		/* Add the name of the map */
		printWriter.println(map.getMapType());

		/* Add Entities name x y xlength ylength */
		for (Entity entity : map.getEntities()) {
			if(entity instanceof AbstractHero) {
				AbstractHero hero = (AbstractHero) entity;
				Map<HeroStat, Double> stats = hero.getStats();
				printWriter.println(entity.encode() + " " + 
									stats.get(HeroStat.SPEED) + " " + 
									stats.get(HeroStat.DAMAGE) + " " +
									stats.get(HeroStat.HEALTH) + " " +
									stats.get(HeroStat.BASE_AP) + " " +
									stats.get(HeroStat.RECHARGE_AP) + " " +
									stats.get(HeroStat.ACTION_POINTS) + " " +
									stats.get(HeroStat.ARMOUR) + " " +
									stats.get(HeroStat.LEVEL) + " " +
									stats.get(HeroStat.XP) + " " +
									stats.get(HeroStat.BASE_HEALTH) + " " +
									stats.get(HeroStat.VISIBILITY_RANGE) + " " );
			} else if (entity instanceof AbstractEnemy) {
				printWriter.println(entity.encode());
			}
		}
		printWriter.close();
	}
	
	/**
	 *  Saves the objectives to the file locally
	 * @throws IOException is file cannot be written to
	 * 
	 */
	private void saveObjectives(File file, List<Objective> objectives) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		List<String> saveString = ObjectiveSaverLoader.saveObjectives(objectives);
		for(String objective: saveString) {
			printWriter.println(objective);
		}
		
		printWriter.close();
	}

	private void saveGameState(File file, GameState gs) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		List<String> saveString = GameStateSaverLoader.gameStateToStringList(gs);
		for(String objective: saveString) {
			printWriter.println(objective);
		}

		printWriter.close();
	}

	/**
	 * Map factory
	 * @param type the type of map wanted
	 * @return returns the map of type type.
	 */
	private AbstractGameMap getMapFromType(MapType type) {
		switch (type) {
			case FALADOR:
				return new Falador("", false);
			case DEMO:
				return new DemoMap("");
			case EMPTY:
				return new EmptyMap("");
			case KARAMJA:
				return new Karamja("");
			case MAP001:
				return new Map001("", false);
			case MAP002:
				return new Map002("", false);
			case MAP003:
				return new Map003("", false);
			case MAP004:
				return new Map004("", false);
			case MAP005:
				return new Map005("", false);
			case MAP006:
				return new Map006("", false);
			case MAP007:
				return new Map007("", false);
			case MAP008:
				return new Map008("", false);
			case OPTIMISE_DEMO:
				return new OptimisedDemo();
			case SAVED:
				return new SavedMap("", 50, 50);
			case TUTORIAL:
				return new TutorialMap("");
			case MULTIPLAYER:
				return new MultiplayerMap();
			default:
					return null;
		
		}
		
	}

}
