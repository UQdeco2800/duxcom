package uq.deco2800.duxcom.overworld;

import static org.junit.Assert.*;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxToolkit.setupStage;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegment;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.overworld.nodes.Node;
@Ignore

@RunWith(MockitoJUnitRunner.class)
public class OverworldControllerTest extends ApplicationTest {
	/**
	 * Mock the InterfaceManager class with Mockito
	 */
	@Mock
	InterfaceManager interfaceManager;

	Node currentNode;

	OverworldController overworldControllerMock;
	
	int maxNodes = 0;

	/**
	 * Initialize the testfx environment.
	 */
	@Override
	@Ignore
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
		currentNode = VirtualMap.getLevels().get(0);
		overworldControllerMock.setTravelTime(0);
		
	}

	/**
	 * Tests that pressing the right arrow key moves the player icon as
	 * intended.
	 */
	@Ignore
	public void testRightKey() {
		Pane overworldPane = find("#overworldPane");
		ImageView playerImage = find("#playerImage");
		double previousX = playerImage.getTranslateX();
		double previousY = playerImage.getTranslateY();
		KeyEvent rightKey = new KeyEvent(KeyEvent.KEY_PRESSED, null, null,
				KeyCode.RIGHT, false, false, false, false);

		overworldPane.fireEvent(rightKey);


		if (currentNode.getNodeRight() == null) {
			assertTrue(previousX - playerImage.getTranslateX() < 0.01);
			assertTrue(previousY - playerImage.getTranslateY() < 0.01);

		} else {
			while (currentNode.getNodeRight() != null) {
				currentNode = currentNode.getNodeRight();
			}
			assertTrue(playerImage.getTranslateX() - VirtualMap
					.getNodeActualPosition(currentNode).getX() < 0.01);
		}
	}

	/**
	 * Tests that pressing the left arrow key moves the player icon as intended.
	 */
	@Ignore
	public void testLeftKey() {
		Pane overworldPane = find("#overworldPane");
		ImageView playerImage = find("#playerImage");
		double previousX = playerImage.getTranslateX();
		double previousY = playerImage.getTranslateY();
		KeyEvent leftKey = new KeyEvent(KeyEvent.KEY_PRESSED, null, null,
				KeyCode.LEFT, false, false, false, false);

		overworldPane.fireEvent(leftKey);

		if (currentNode.getNodeLeft() == null) {
			assertTrue(previousX - playerImage.getTranslateX() < 0.01);
			assertTrue(previousY - playerImage.getTranslateY() < 0.01);

		} else {
			while (currentNode.getNodeLeft() != null) {
				currentNode = currentNode.getNodeLeft();
			}
			assertTrue(playerImage.getTranslateX() - VirtualMap
					.getNodeActualPosition(currentNode).getX() < 0.01);
		}
	}

	/**
	 * Tests that pressing the down arrow key moves the player icon as intended.
	 */
	@Ignore
	public void testDownKey() {
		Pane overworldPane = find("#overworldPane");
		ImageView playerImage = find("#playerImage");
		double previousX = playerImage.getTranslateX();
		double previousY = playerImage.getTranslateY();
		KeyEvent downKey = new KeyEvent(KeyEvent.KEY_PRESSED, null, null,
				KeyCode.DOWN, false, false, false, false);

		overworldPane.fireEvent(downKey);

		if (currentNode.getNodeBelow() == null) {
			assertTrue(previousX - playerImage.getTranslateX() < 0.01);
			assertTrue(previousY - playerImage.getTranslateY() < 0.01);

		} else {
			while (currentNode.getNodeBelow() != null) {
				currentNode = currentNode.getNodeBelow();
			}
			assertTrue(playerImage.getTranslateY() - VirtualMap
					.getNodeActualPosition(currentNode).getY() < 0.01);
		}
	}

	/**
	 * Tests that pressing the up arrow key moves the player icon as intended.
	 */
	@Ignore
	public void testUpKey() {
		Pane overworldPane = find("#overworldPane");
		ImageView playerImage = find("#playerImage");
		double previousX = playerImage.getTranslateX();
		double previousY = playerImage.getTranslateY();
		KeyEvent upKey = new KeyEvent(KeyEvent.KEY_PRESSED, null, null,
				KeyCode.UP, false, false, false, false);

		overworldPane.fireEvent(upKey);

		if (currentNode.getNodeAbove() == null) {
			assertTrue(previousX - playerImage.getTranslateX() < 0.01);
			assertTrue(previousY - playerImage.getTranslateY() < 0.01);

		} else {
			while (currentNode.getNodeAbove() != null) {
				currentNode = currentNode.getNodeAbove();
			}
			assertTrue(playerImage.getTranslateY() - VirtualMap
					.getNodeActualPosition(currentNode).getY() < 0.01);
		}
	}

	/**
	 * Tests that pressing the enter key opens the current level as intended.
	 */
	@Ignore
	public void testEnterKey() {
		Pane overworldPane = find("#overworldPane");
		KeyEvent enterKey = new KeyEvent(KeyEvent.KEY_PRESSED, null, null,
				KeyCode.ENTER, false, false, false, false);

		int levelIndex = overworldControllerMock.nodeIsLevel(currentNode);

		overworldPane.fireEvent(enterKey);
		if (levelIndex != -1) {
			if (VirtualMap.getLevels().get(levelIndex).isUnlocked()) {
				if (VirtualMap.getLevels().get(levelIndex).getLevelId().equals(
						"Tutorial")) {
					verify(interfaceManager).loadSegmentImmediate(eq(
							InterfaceSegmentType.GAME), any(Stage.class), eq(
									VirtualMap.getLevels().get(levelIndex)
											.getLevelId()));
				} else {
					verify(interfaceManager).loadSegmentImmediate(eq(
							InterfaceSegmentType.HERO_SELECT), any(Stage.class),
							eq(VirtualMap.getLevels().get(levelIndex)
									.getLevelId()));

				}
			}
		}

	}

	/**
	 * Tests all possible key inputs in succession for the minimal amount of
	 * coverage.
	 */
	@Test
	@Ignore
	public void quickTestKeyEvents() {
		testRightKey();
		testRightKey();
		testDownKey();
		testUpKey();
		testLeftKey();
		testEnterKey();
	}

	/**
	 * Tests that the level icons on the screen move the player icon on first
	 * click and open the level the icon is tied to on re-click for the minimal
	 * amount of coverage.
	 */
	@Test
	@Ignore
	public void quickTestImageViews() {
		ImageView playerImage = find("#playerImage");

		MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0,
				0, MouseButton.PRIMARY, 1, true, true, true, true, true, true,
				true, true, true, true, null);
		ArrayList<ImageView> imageViews = new ArrayList<ImageView>();

		for (int i = 1; i < imageViews.size(); i += 3) {
			imageViews.get(i).fireEvent(clicked);
			imageViews.get(i).fireEvent(clicked);
			assertTrue(playerImage.getTranslateX() == imageViews.get(i).getX());
			assertTrue(playerImage.getTranslateY() == imageViews.get(i).getY());
			if (VirtualMap.getLevels().get(i).isUnlocked()) {
				if (VirtualMap.getLevels().get(i).getLevelId().equals(
						"Tutorial")) {
					verify(interfaceManager).loadSegmentImmediate(eq(
							InterfaceSegmentType.GAME), any(Stage.class), eq(
									VirtualMap.getLevels().get(i)
											.getLevelId()));
				} else {
					verify(interfaceManager).loadSegmentImmediate(eq(
							InterfaceSegmentType.HERO_SELECT), any(Stage.class),
							eq(VirtualMap.getLevels().get(i).getLevelId()));

				}
			}
		}
	}

	/**
	 * Tests all possible key inputs in succession for full coverage.
	 */
	@Test
	@Ignore
	public void testKeyEvents() {
		testRightKey();
		testDownKey();
		testRightKey();
		testRightKey();
		testDownKey();
		testLeftKey();
		testRightKey();
		testUpKey();
		testUpKey();
		testRightKey();
		testDownKey();
		testLeftKey();
		testDownKey();
		testDownKey();
		testLeftKey();
		testLeftKey();
		testRightKey();
		testUpKey();
		testEnterKey();
	}

	/**
	 * Tests that the level icons on the screen move the player icon on first
	 * click and open the level the icon is tied to on re-click for full
	 * coverage.
	 */
	@Test
	@Ignore
	public void testImageViews() {
		sleep(1000);
		ImageView playerImage = find("#playerImage");

		MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0,
				0, MouseButton.PRIMARY, 1, true, true, true, true, true, true,
				true, true, true, true, null);
		ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < VirtualMap.getLevels().size(); i++) {
			imageViews.add(VirtualMap.getLevels().get(i).getImageView());
		}

		
		for (int i = 0; i < imageViews.size(); i += 1) {
			imageViews.get(i).fireEvent(clicked);
			
			assertTrue(playerImage.getTranslateX() == imageViews.get(i).getX());
			assertTrue(playerImage.getTranslateY() == imageViews.get(i).getY());

			imageViews.get(i).fireEvent(clicked);
			if (VirtualMap.getLevels().get(i).isUnlocked()) {
				if (VirtualMap.getLevels().get(i).getLevelId().equals(
						"tutorial")) {
					verify(interfaceManager).loadSegmentImmediate(eq(
							InterfaceSegmentType.GAME), any(Stage.class), eq(
									VirtualMap.getLevels().get(i)
											.getLevelId()));
					break;
				} else {
					verify(interfaceManager).loadSegmentImmediate(eq(
							InterfaceSegmentType.HERO_SELECT), any(Stage.class),
							eq(VirtualMap.getLevels().get(i).getLevelId()));
					break;
				}
			}
		}
		
		

	}

	/**
	 * Shuts down the testfx environment.
	 * @throws TimeoutException
	 */
	@AfterClass
	@Ignore
	public static void cleanUp() throws TimeoutException {
		VirtualMap.getLevels().clear();
		setupStage(Stage::close);
	}
}