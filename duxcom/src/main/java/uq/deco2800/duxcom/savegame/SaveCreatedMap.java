package uq.deco2800.duxcom.savegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.SavedMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.util.Array2D;

import java.io.*;
import java.util.*;

/**
 * Save and load the maps created using the UI 
 * 
 * @author Wise Quackers
 *
 */
public class SaveCreatedMap {

	private static Logger logger = LoggerFactory.getLogger(SaveCreatedMap.class);

	// Instance variables
	private static final String PATH = "src/main/resources/levels/";
	
	/**
	 * Constructor
	 */
	private SaveCreatedMap() {
		/* Empty Constructor */
	}
	
	/**
	 * Save the map to the file in resources with the name filename
	 * 
	 * @param map
	 * 			the map needs to be saved
	 * 
	 * @param file
	 * 			the file to save the map to
	 * 
	 * @param overwrite
	 * 			flag to decide whether to overwrite an existing saved map
	 * 
	 * @throws IOException
	 */
	public static void saveMap(MapAssembly map, File file, boolean overwrite) throws IOException {
		
		/*
		 * Properties needs to save 
		 * All the entities on the map 
		 * Tile */
		if(!overwrite && file.exists()) {
			throw new IOException("File already Exists");
		}
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		
		Map<Integer, Array2D<Tile>> tileMap = map.getTileMap();
		
		List<Entity> entities = map.getEntities();
		printWriter.println("Dimensions " +map.getWidth()+" "+map.getHeight());
		/* for each entity save it to the file */
		for (Entity entity : entities) {
			/* EntityType xPos yPos xLength yLength */
			printWriter.println(entity.encode());
		}
		/* Save all the tiles on the map */
		printWriter.println("Tiles");
		Set<Integer> keys = tileMap.keySet();
		for(Integer key: keys){
			Array2D<Tile> mapTiles = tileMap.get(key);
			for (int y = 0; y < mapTiles.getWidth(); y++) {
	            for (int x = 0; x < mapTiles.getHeight(); x++) {
	                Tile individualTile = mapTiles.get(x, y);
	                // TileType xPos yPos
	                printWriter.println(individualTile.getTileType().toString() + " " + x + " " + y);
	            }
	        }
		}
		printWriter.close();
	}

	/**
	 * 	Returns the map Object which was saved in the given file
	 * 
	 * @param fileName
	 * 			the fileName to which the maps needs to be loaded from
	 * 
	 * @return
	 * 			A map which was saved in the file with the given filename
	 * 
	 */
	public static AbstractGameMap loadMap(String fileName) throws IOException, InvalidFormatException{
		int line = 0; // line count of file
		SavedMap map = new SavedMap("",50,50);
		String filePath = PATH+fileName;
		FileReader fileReader = new FileReader(filePath);
		boolean tileFlag = false;
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

				if (line == 1 && "Dimensions".equals(lineArguments.get(0))) {
					map = getMap(fileName, lineArguments.get(1), lineArguments.get(2));
					lineScanner.close();
					continue;
				} else if ("Tiles".equals(lineArguments.get(0))) {
					tileFlag = true;
					lineScanner.close();
					continue;
				}
				if (tileFlag) {
					// Add tile to the map
					addTile(map, lineArguments);
				} else {
					// Add entity to the map
					addEntity(map, lineArguments);

				}
				lineScanner.close();
			}
		} catch (IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
			logger.error("Error loading map", e);
			throw new InvalidFormatException();
		} finally {
			fileReader.close();
		}
		return map;
	}

	/**
	 * returns the map Object with the given dimensions 
	 * 
	 * @param name
	 * 		the name of the map
	 * 
	 * @param convertWidth
	 * 			width of the map 
	 * @param convertHeight
	 * 			height of the map
	 * @return 
	 * 		the map with the given dimensions and the given name
	 * 		
	 * @throws InvalidFormatException
	 * 			if the height and width are not valid
	 */
	private static SavedMap getMap(String name, String convertWidth, String convertHeight) {
		SavedMap map;
		int width = Integer.parseInt(convertWidth);
		int height = Integer.parseInt(convertHeight);
		map = new SavedMap(name, width, height);
		return map;
	}
	
	/**
	 * Add tile to the given map
	 * 
	 * @param map
	 * 			map to add the tile too
	 * @param lineArguments
	 * 			The tile which needs to be added
	 * @throws InvalidFormatException
	 * 
	 */
	private static void addTile(SavedMap map, ArrayList<String> lineArguments) {
		TileType convertTile;
		convertTile = TileType.valueOf(lineArguments.get(0));
		int convertX = Integer.parseInt(lineArguments.get(1));
		int convertY = Integer.parseInt(lineArguments.get(2));
		map.setTile(convertX, convertY, convertTile);
	}

	/**
	 * Adds the entity to the map
	 * @param map
	 * 			map to add Entity to
	 * @param lineArguments
	 * 			The entity which needs to be added
	 * @throws InvalidFormatException
	 * 			If the Entity being added does not exit in game,
	 * 			If the position of the entity is invalid
	 * 			If the Entity is being added at an invalid position
	 */			
	private static void addEntity(SavedMap map, ArrayList<String> lineArguments) {
		/* Get the Entity Object from the Entity type String */
		Entity entity = EntityConstructor.generateEntity(EntityType.valueOf(lineArguments.get(0)), 1, 1);
		
		/* Set the state of the Entity in the same state as saved */
		entity.decode(lineArguments); 
		
		/* add the entity to the map */
		map.addEntity(entity);

	}
}