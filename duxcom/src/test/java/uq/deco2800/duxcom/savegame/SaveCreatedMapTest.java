package uq.deco2800.duxcom.savegame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.EnemyTestMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class SaveCreatedMapTest {
	
	
	// Expected Exception
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public ExpectedException fileExists = ExpectedException.none();
	
	String path = "src/main/resources/levels/";
	File file = new File(path+"test.txt");
	
	/**
	 * Tests the saving of the map
	 */
	@Test
	public void saveMapTest() throws IOException {
		AbstractGameMap mapDemo = new DemoMap("Save Test");
		MapAssembly map = new MapAssembly(mapDemo);
		
		File fileTest = new File(path+"saveTestOutput.txt");
		File demoFile = new File(path+"DemoMap.txt");
		SaveCreatedMap.saveMap(map, fileTest,true);
		SaveCreatedMap.saveMap(map, demoFile,true);
		checkSavedFile(fileTest);
		fileTest.delete();
	}
	
	/**
	 * Tests the loading of a map
	 */
	@Test
	public void loadMapTest() throws IOException { 

		File file2 = new File(path+"LoadTest2.txt");
		AbstractGameMap map2 = new DemoMap("Demo Map");
		SaveCreatedMap.saveMap(new MapAssembly(map2),file2,true);
		AbstractGameMap savedMap2 = SaveCreatedMap.loadMap("LoadTest2.txt");
		
		assertEquals(savedMap2.getEntities(),map2.getEntities());
		assertEquals(savedMap2.getHeight(), map2.getHeight());
		assertEquals(savedMap2.getWidth(), map2.getWidth());
		file2.delete();
	}
	
	/**
	 * Tests Invalid save file format, with invalid dimensions for the map
	 */
	@Test
	public void invalidMapTest() throws Exception{
		invalidDimensions();
		thrown.expect(InvalidFormatException.class);
		SaveCreatedMap.loadMap("test.txt");
		file.delete();
	}
	
	/**
	 * Test to check that invalid entity cannot be added
	 */
	@Test
	public void invalidEntityTest() throws Exception{
		addInvalidEntity();
		thrown.expect(InvalidFormatException.class);
		SaveCreatedMap.loadMap("test.txt");
		file.delete();
		
	}
	
	/**
	 * Tests to check that invalid Tile cannot be added 
	 */
	@Test
	public void invalidTileTest() throws Exception{
		
		addInvalidTile();
		try {
			SaveCreatedMap.loadMap("test.txt");
		} catch (InvalidFormatException e){
			file.delete();
		}
	}
	
	/**
	 * Tests to see maps with same name cannot be overwritten
	 */
	@Test
	public void overwriteTest() throws Exception{
		AbstractGameMap demo = new DemoMap("Demo");
		//File file = new File()
		fileExists.expect(IOException.class);
		File demoFile = new File(path+"DemoMap.txt"); 
		SaveCreatedMap.saveMap(new MapAssembly(demo), demoFile,false);
	}
	
	/**
	 * Tests to check if saved maps can be overwritten with the correct flags
	 */
	@Test
	public void overwriteAllowedTest() throws Exception {
		AbstractGameMap map1 = new EnemyTestMap("Enemy Map");
		AbstractGameMap demo = new DemoMap("Demo");
		SaveCreatedMap.saveMap(new MapAssembly(demo), new File(path+"DemoMap.txt"), true);
		
		File enemyMap = new File(path+"OverwriteTest.txt");
		SaveCreatedMap.saveMap(new MapAssembly(map1), enemyMap, false);
		
		SaveCreatedMap.saveMap(new MapAssembly(demo), enemyMap, true);
		File file = new File(path+"OverwriteTest.txt");
		checkSavedFile(file);
		file.delete();
	}
	
	/*-------Helper Methods-----------*/
	
	/**
	 * File with an invalid Tile
	 */
	private void addInvalidTile() throws Exception{
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("Dimensions 40 40" + System.lineSeparator()+"Tiles" + System.lineSeparator() + "Invalid Tile");
		pw.close();
	}
	
	/**
	 * File with an invalid Entity
	 */
	private void addInvalidEntity() throws Exception{
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("Invalid Entoty");
		pw.close();
	}
	
	/**
	 * Invalid map dimensions
	 */
	private void invalidDimensions() throws Exception{
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		pw.println("Dimensions a b");
		pw.close();
	}
	
	/**
	 * Checks that the map being saved is saved in the correct format
	 * 
	 * @param file
	 * 			the file to checks
	 */
	private void checkSavedFile(File file) {
		assertNotNull(file);
		File demoMap = new File(path+"DemoMap.txt");
		FileReader actualReader;
		FileReader expectedReader;
		Scanner actualScanner= null;
		Scanner expectedScanner= null;
		try {
			actualReader = new FileReader(file);
			actualScanner = new Scanner(actualReader);
			expectedReader = new FileReader(demoMap);
			expectedScanner = new Scanner(expectedReader);
		} catch (FileNotFoundException e) {
			fail("File doesnt exist");
		}
		while(expectedScanner.hasNextLine()){
			assertEquals(actualScanner.nextLine(), expectedScanner.nextLine());
		}
		actualScanner.close();
		expectedScanner.close();
	}
}
