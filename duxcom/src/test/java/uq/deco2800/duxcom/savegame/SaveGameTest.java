package uq.deco2800.duxcom.savegame;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.maps.Falador;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectives.EnemyKillObjective;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectives.ScoreObjective;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.scoring.ScoreSystem;

public class SaveGameTest {
	
	/**
	 * Checks that there is a saved file in Documents
	 * @throws Exception
	 */
	@Test
	public void SaveToFileTest() throws Exception{
        EnemyKillObjective eObj = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);
        ScoreSystem scoring = new ScoreSystem();
        ScoreObjective s = new ScoreObjective(scoring, 2000);
        List<Objective> testObjectives = new ArrayList<>();
		GameState gs = new GameState(testObjectives);
        testObjectives.add(eObj);
        testObjectives.add(s);
        
		SaveGame sg = new SaveGame();
		SaveOriginator og = new SaveOriginator();
		
		og.writeMap(new MapAssembly(new Falador("demo", false)), testObjectives, gs);
		sg.saveGame(og);

		File file = new File(System.getProperty("user.home")+"/Documents/My Games/DuxCom/DuxCom.dux");
		File file2 = new File(System.getProperty("user.home")+"/Documents/My Games/DuxCom/DuxComObjectives.dux");
		/* check that there is a saved file*/
		assertTrue(file.exists()); 
		assertTrue(file2.exists());
	}
	
	@Test
	public void checkSavedFileTest() throws Exception {
		EnemyKillObjective eObj = new EnemyKillObjective(EnemyType.ENEMY_KNIGHT, 5);
        ScoreSystem scoring = new ScoreSystem();
        ScoreObjective s = new ScoreObjective(scoring, 2000);
        List<Objective> testObjectives = new ArrayList<>(); 
        testObjectives.add(eObj);
        testObjectives.add(s);
		SaveGame sg = new SaveGame();
		SaveOriginator og = new SaveOriginator();
		GameState gs = new GameState(testObjectives);
		og.writeMap(new MapAssembly(new Falador("demo", false)), testObjectives, gs);
		sg.saveGame(og);

		File file = new File(System.getProperty("user.home")+"/Documents/My Games/DuxCom/DuxCom.dux");
		File file2 = new File(System.getProperty("user.home")+"/Documents/My Games/DuxCom/DuxComObjectives.dux");
		checkDuxcom(file);
		checkObjectives(file2);
		
	}
	
	/**
	 * checks that the duxCom.dux saved file contains that correct info
	 * 
	 * @param file the file which the game state is saved in
	 */
	private void checkDuxcom(File file) {
		assertNotNull(file);
		FileReader reader;
		Scanner scanner= null;
		try {
			reader = new FileReader(file);
			scanner = new Scanner(reader);
		} catch (FileNotFoundException e) {
			fail("File doesnt exist");
		}
		int line = 0;
		while(scanner.hasNextLine()){
			ArrayList<String> lineArguments = new ArrayList<>();
			String lineContent = scanner.nextLine();
			Scanner lineScanner = new Scanner(lineContent);
			line = line + 1;
			while (lineScanner.hasNext()) {
				// add each element of a line to a list
				lineArguments.add(lineScanner.next());
			}
			if(line > 1){
				assertNotNull(EntityType.valueOf(lineArguments.get(0)));
			}
			lineScanner.close();
		}
		scanner.close();
	}
	
	/**
	 * checks that the DuxComObjectives.dux saved file contains that correct info
	 * @param file the file which the objectives are saved is
	 */
	private void checkObjectives(File file) {
		assertNotNull(file);
		FileReader reader;
		Scanner scanner= null;
		try {
			reader = new FileReader(file);
			scanner = new Scanner(reader);
		} catch (FileNotFoundException e) {
			fail("File doesnt exist");
		}
		int line = 0;
		while(scanner.hasNextLine()){
			ArrayList<String> lineArguments = new ArrayList<>();
			String lineContent = scanner.nextLine();
			Scanner lineScanner = new Scanner(lineContent);
			line = line + 1;
			while (lineScanner.hasNext()) {
				// add each element of a line to a list
				lineArguments.add(lineScanner.next());
			}
			lineScanner.close();
		}
		assertTrue(line == 2);
		scanner.close();
	}

}

