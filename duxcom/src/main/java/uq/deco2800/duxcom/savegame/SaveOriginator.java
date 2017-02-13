package uq.deco2800.duxcom.savegame;

import java.util.ArrayList;
import java.util.List;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectivesystem.GameState;

/**
 * class to store the state of the game
 * 
 * @author abhijagtap, tbric123
 *
 */
public class SaveOriginator {
	
	private MapAssembly map;
	
	private AbstractGameMap abstractMap;
	private List<Objective> objectives;
	private GameState gameState;
	
	/**
	 * Constructor
	 */
	public SaveOriginator(){
		// empty Constructor
	}
	
	/**
	 * updates the current state of the game
	 * @param map
	 */
	public void writeMap(MapAssembly map, List<Objective> objectives, GameState gameState){
		this.map = map;
		this.objectives = objectives;
		this.gameState = gameState;
	}
	
	/**
	 * updates the current state of the game
	 * @param map
	 */
	public void writeAbstractGameMap(AbstractGameMap map,
									 List<Objective> objectives,
									 GameState gameState) {
		this.map = new MapAssembly(map);
		this.abstractMap = map;
		this.objectives = objectives;
		this.gameState = gameState;
	}

	/**
	 * save the current state of the game
	 */
	public Memento save() {
		return new Memento(this.map, this.objectives, this.gameState);
	}
	
	/**
	 * loads the current state of the game
	 */
	public void load(Object obj){
		Memento memento = (Memento) obj;
		this.map = memento.map;
		this.objectives = memento.objectives;
		this.gameState = memento.gameState;
	}

	/**
	 *  the map of the saved game state
	 * @return the map when game was saved
	 */
	public MapAssembly get(){
		return this.map;
	}
	
	/**
	 * the map of the saved game state
	 * @return the map when game was saved
	 */
	public AbstractGameMap getAbstract() {
		return this.abstractMap;
	}
	/**
	 * returns saved objectives
	 * @return the saved objectives
	 */
	public List<Objective> getSavedObjectives(){
		return this.objectives;
	}

	public GameState getSavedGameState() {
		return this.gameState;
	}

	/**
	 * 
	 * @author abhijagtap
	 *
	 */
	private class Memento{
		private MapAssembly map;
		private List<Objective> objectives;
		private GameState gameState;
		/**
		 *  Constructor
		 * @param map
		 */
		public Memento(MapAssembly map, List<Objective> objectives, GameState gameState){
			AbstractGameMap abstractGameMap = MapConstructor.getMap(map.getMapType(), false);
			this.map = new MapAssembly(abstractGameMap);
			this.objectives = new ArrayList<>(objectives);
			this.gameState = gameState;
		}
	}
	
}
