package uq.deco2800.duxcom.savegame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.Falador;
import uq.deco2800.duxcom.maps.MapType;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectives.EnemyKillObjective;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectives.ScoreObjective;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.scoring.ScoreSystem;

public class SaveOriginatorTest {
	
	@Test 
	public void MapTypeTest() throws Exception{
		EnemyKillObjective eObj = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);
        ScoreSystem scoring = new ScoreSystem();
        ScoreObjective s = new ScoreObjective(scoring, 2000);
        List<Objective> testObjectives = new ArrayList<>(); 
        testObjectives.add(eObj);
        testObjectives.add(s);

		GameState gs = new GameState(testObjectives);
		// careTake
		MapAssembly map = new MapAssembly(new Falador("",true));
		SaveGame sg = new SaveGame();
		SaveOriginator so = new SaveOriginator();
		
		// First map is FALADOR
		so.writeMap(map, testObjectives, gs);
		sg.saveGame(so); // save the game at this point (So save Falador)
		
		// CHANGE THE MAP (eg state changes) not map is DEMOMAP
		map = new MapAssembly(new DemoMap(""));
		so.writeMap(map, testObjectives, gs);

		// LOAD WHEN IT WAS SAVED (NOW IT IS FALADOR)
		sg.loadGame(so);
		assertEquals(MapType.FALADOR, so.get().getMapType());
		
		/* change to DEMO and save and load */
		map = new MapAssembly(new DemoMap(""));
		so.writeMap(map, testObjectives, gs);
		sg.saveGame(so);
		sg.loadGame(so);
		assertEquals(MapType.DEMO, so.get().getMapType());
		
	}
	
	/**
	 *  Checks that the loading using the SaveOriginator works
	 */
	@Test
	public void testGameStateLoad() throws Exception{
		EnemyKillObjective eObj = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);
        ScoreSystem scoring = new ScoreSystem();
        ScoreObjective s = new ScoreObjective(scoring, 2000);
        List<Objective> testObjectives = new ArrayList<>(); 
        testObjectives.add(eObj);
        testObjectives.add(s);

		// careTake
		MapAssembly map = new MapAssembly(new Falador("",true));
		SaveGame saveGame = new SaveGame();
		SaveOriginator saveOriginator = new SaveOriginator();
		GameState gs = new GameState(testObjectives);

		saveOriginator.writeMap(map, testObjectives, gs);
		saveGame.saveGame(saveOriginator);
		/* change the map */
		map.addHero(new Knight(1,1));
		
		/* load the previous state (so without the hero knight) */
		saveGame.loadGame(saveOriginator);
		assertNotEquals(saveOriginator.get().getEntities(), map.getEntities());
		assertEquals(saveOriginator.get().getMapType(), map.getMapType());
		
	}

}
