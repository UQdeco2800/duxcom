package uq.deco2800.duxcom.overworld;

import static org.junit.Assert.*;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegment;
import uq.deco2800.duxcom.overworld.nodes.Level;

@Ignore
public class LevelRegisterTest extends ApplicationTest {

	@Mock
	InterfaceManager interfaceManager;

	OverworldController overworldControllerMock;

	Stage stage;

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
		this.stage = stage;

		InterfaceSegment.showStage(root, stage, interfaceManager);
		stage.setTitle("Overworld");
		overworldControllerMock.getFocus();

		overworldControllerMock.setTravelTime(1);

	}

	/**
	 * Tests the addition of Levels to LevelRegister.
	 */
	@Test
	public void testLevelAddition() {
		VirtualMap.scaleVirtualMap(720, 1280, 6, 5);

		assertNull(LevelRegister.addLevel("new", new Point2D.Double(-4, 10),
				null, null, "test", "test"));
		assertNull(LevelRegister.addLevel("new", new Point2D.Double(5, -3),
				null, null, "test", "test"));
		assertNull(LevelRegister.addLevel("new", new Point2D.Double(5, 5000),
				null, null, "test", "test"));
		assertNull(LevelRegister.addLevel("new", new Point2D.Double(200, 2),
				null, null, "test", "test"));

		Level testLevel1 = LevelRegister.addLevel("new", new Point2D.Double(1,
				1), null, null, "test", "test");
		Level testLevel2 = LevelRegister.addLevel("new", new Point2D.Double(2,
				3), null, null, "test", "test");
		Level testLevel3 = LevelRegister.addLevel("new", new Point2D.Double(3,
				3), null, null, "test", "test");

		assertEquals(VirtualMap.getLevels().get(VirtualMap.getLevels().size()
				- 3).getCoordinates(), testLevel1.getCoordinates());
		assertEquals(VirtualMap.getLevels().get(VirtualMap.getLevels().size()
				- 2).getCoordinates(), testLevel2.getCoordinates());
		assertEquals(VirtualMap.getLevels().get(VirtualMap.getLevels().size()
				- 1).getCoordinates(), testLevel3.getCoordinates());

	}

	/**
	 * Tests the conquering of Levels within LevelRegister.
	 */
	@Test
	public void testConquerLevels() {
		VirtualMap.scaleVirtualMap(720, 1280, 6, 5);

		Level testLevel1 = LevelRegister.addLevel("new", new Point2D.Double(1,
				1), null, null, "test", "test");
		Level testLevel2 = LevelRegister.addLevel("new", new Point2D.Double(2,
				3), testLevel1, null, "test", "test");
		Level testLevel3 = LevelRegister.addLevel("new", new Point2D.Double(3,
				3), testLevel2, null, "test", "test");
		Level testLevel4 = LevelRegister.addLevel("tutorial",
				new Point2D.Double(1, 4), testLevel3, null, "test", "test");
		MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0,
				0, MouseButton.PRIMARY, 1, true, true, true, true, true, true,
				true, true, true, true, null);

		testLevel2.getImageView().fireEvent(clicked);
		sleep(1000);

		testLevel2.getImageView().fireEvent(clicked);

		assertTrue(testLevel2.isUnlocked());
		assertFalse(testLevel2.isConquered());
		LevelRegister.conquerLevel(testLevel2);
		assertTrue(testLevel2.isConquered());

		testLevel2.getImageView().fireEvent(clicked);
		sleep(1000);

		testLevel2.getImageView().fireEvent(clicked);

		assertFalse(testLevel3.isConquered());
		LevelRegister.conquerLevel(testLevel3);
		assertTrue(testLevel3.isConquered());

		LevelRegister.refreshLevels();
		assertTrue(testLevel3.isConquered());
		assertTrue(testLevel2.isConquered());
		assertFalse(testLevel1.isConquered());
		assertFalse(testLevel4.isConquered());

	}

	/**
	 * Tests the LevelRegister converts to the save-format correctly.
	 */
	@Test
	public void testSave() {
		VirtualMap.getLevels().clear();
		VirtualMap.scaleVirtualMap(720, 1280, 6, 5);

		Level testLevel1 = LevelRegister.addLevel("level1", new Point2D.Double(
				1, 1), null, null, "test", "test");
		assertEquals(LevelRegister.saveCompletedLevels(), "");
		LevelRegister.conquerLevel(testLevel1);
		assertEquals(LevelRegister.saveCompletedLevels(), "level1");

		Level testLevel2 = LevelRegister.addLevel("level2", new Point2D.Double(
				2, 3), testLevel1, null, "test", "test");
		assertEquals(LevelRegister.saveCompletedLevels(), "level1");

		LevelRegister.conquerLevel(testLevel2);
		assertEquals(LevelRegister.saveCompletedLevels(), "level1,level2");

		Level testLevel3 = LevelRegister.addLevel("level3", new Point2D.Double(
				3, 3), testLevel2, null, "test", "test");
		assertEquals(LevelRegister.saveCompletedLevels(), "level1,level2");

		LevelRegister.conquerLevel(testLevel3);
		assertEquals(LevelRegister.saveCompletedLevels(),
				"level1,level2,level3");

		Level testLevel4 = LevelRegister.addLevel("tutorial",
				new Point2D.Double(1, 4), testLevel3, null, "test", "test");
		assertEquals(LevelRegister.saveCompletedLevels(),
				"level1,level2,level3");

		LevelRegister.conquerLevel(testLevel4);
		assertEquals(LevelRegister.saveCompletedLevels(),
				"level1,level2,level3,tutorial");
	}

	/**
	 * Tests the LevelRegister extracts and loads levels from the save-format
	 * correctly.
	 */
	@Test
	public void testLoad() {
		VirtualMap.getLevels().clear();
		Level testLevel1 = LevelRegister.addLevel("level1", new Point2D.Double(
				1, 1), null, null, "test", "test");
		Level testLevel2 = LevelRegister.addLevel("level2", new Point2D.Double(
				2, 3), testLevel1, null, "test", "test");
		Level testLevel3 = LevelRegister.addLevel("level3", new Point2D.Double(
				3, 3), testLevel2, null, "test", "test");
		Level testLevel4 = LevelRegister.addLevel("tutorial",
				new Point2D.Double(1, 4), testLevel3, null, "test", "test");

		LevelRegister.loadCompletedLevels("a,b,c,d");

		assertFalse(testLevel1.isConquered());
		assertFalse(testLevel2.isConquered());
		assertFalse(testLevel3.isConquered());
		assertFalse(testLevel4.isConquered());

		LevelRegister.loadCompletedLevels("level1,b,c,d");

		assertTrue(testLevel1.isConquered());
		assertFalse(testLevel2.isConquered());
		assertFalse(testLevel3.isConquered());
		assertFalse(testLevel4.isConquered());

		LevelRegister.loadCompletedLevels("level1,level2,tutorial");

		assertTrue(testLevel1.isConquered());
		assertTrue(testLevel2.isConquered());
		assertFalse(testLevel3.isConquered());
		assertTrue(testLevel4.isConquered());
	}

	/**
	 * Shuts down the testfx environment.
	 * 
	 * @throws TimeoutException
	 */
	@AfterClass
	public static void cleanUp() throws TimeoutException {
		VirtualMap.getLevels().clear();
		setupStage(Stage::close);
	}
}
