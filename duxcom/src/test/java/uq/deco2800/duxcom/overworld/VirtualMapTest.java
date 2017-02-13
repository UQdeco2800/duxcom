package uq.deco2800.duxcom.overworld;

import static org.junit.Assert.*;
import static org.testfx.api.FxToolkit.setupStage;

import java.awt.geom.Point2D;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegment;
import uq.deco2800.duxcom.overworld.nodes.Level;

@Ignore
public class VirtualMapTest extends ApplicationTest{
	
	@Mock
	InterfaceManager interfaceManager;

	OverworldController overworldControllerMock;

	/**
	 * Initialize the testfx environment.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		URL location = getClass().getResource("/ui/fxml/overworld.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);

		Parent root = fxmlLoader.load(location.openStream());
		overworldControllerMock = fxmlLoader.getController();
		overworldControllerMock.setStage(stage);
		overworldControllerMock.setInterfaceManager(interfaceManager);

		InterfaceSegment.showStage(root, stage, interfaceManager);
		stage.setTitle("Overworld");

		overworldControllerMock.getFocus();
	
		overworldControllerMock.setTravelTime(5);
	}
	
	/**
	 * Tests the addition of Levels to VirtualMap.
	 */
	@Test
	public void testLevelAddition() {
		VirtualMap.scaleVirtualMap(720, 1280, 6, 5);
		
		assertNull(VirtualMap.registerLevel("new", new Point2D.Double(-4, 10), null, null, "test", "test"));
		assertNull(VirtualMap.registerLevel("new", new Point2D.Double(5, -3), null, null, "test", "test"));
		assertNull(VirtualMap.registerLevel("new", new Point2D.Double(5, 5000), null, null, "test", "test"));
		assertNull(VirtualMap.registerLevel("new", new Point2D.Double(200, 2), null, null, "test", "test"));
		
		Level testLevel1 = VirtualMap.registerLevel("new", new Point2D.Double(1, 1), null, null, "test", "test");
		Level testLevel2 = VirtualMap.registerLevel("new", new Point2D.Double(2, 3), null, new Level[]{testLevel1}, "test", "test");
		Level testLevel3 = VirtualMap.registerLevel("new", new Point2D.Double(3, 3), null, null, "test", "test");
		assertTrue(testLevel1.isUnlocked());
		assertTrue(testLevel3.isUnlocked());
		assertFalse(testLevel2.isUnlocked());
		
		assertEquals(testLevel1.isConquered(), false);
		VirtualMap.conquerLevel(testLevel1);
		assertEquals(testLevel1.isConquered(), true);
	
		assertEquals(testLevel2.isConquered(), false);
		VirtualMap.conquerLevel(testLevel2);
		assertEquals(testLevel2.isConquered(), true);
		
		assertEquals(testLevel3.isConquered(), false);
		VirtualMap.conquerLevel(testLevel3);
		assertEquals(testLevel3.isConquered(), true);
		
		assertEquals(VirtualMap.getLevels().get(VirtualMap.getLevels().size() - 3).getCoordinates(), testLevel1.getCoordinates());
		assertEquals(VirtualMap.getLevels().get(VirtualMap.getLevels().size() - 2).getCoordinates(), testLevel2.getCoordinates());
		assertEquals(VirtualMap.getLevels().get(VirtualMap.getLevels().size() - 1).getCoordinates(), testLevel3.getCoordinates());
		
		assertEquals(VirtualMap.getNodeActualPosition(testLevel1), new Point2D.Double(1 * 60, 1 * 60));
		assertEquals(VirtualMap.getNodeActualPosition(testLevel2), new Point2D.Double(2 * 60, 3 * 60));
		assertEquals(VirtualMap.getNodeActualPosition(testLevel3), new Point2D.Double(3 * 60, 3 * 60));
		
		assertEquals(VirtualMap.getNodesWide(), 5);
		assertEquals(VirtualMap.getNodesHigh(), 6);
		
	}
	
	/**
	 * Shuts down the testfx environment.
	 * @throws TimeoutException
	 */
	@AfterClass
	public static void cleanUp() throws TimeoutException {
		VirtualMap.getLevels().clear();
		setupStage(Stage::close);
	}
}
