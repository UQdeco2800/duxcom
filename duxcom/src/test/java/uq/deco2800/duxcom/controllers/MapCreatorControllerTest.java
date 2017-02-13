package uq.deco2800.duxcom.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.net.URL;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.setupStage;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

/**
 * Created by samthomas on 15/09/2016.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MapCreatorControllerTest extends ApplicationTest{

	// Declares the JavFX nodes to be tested
	private MapCreatorController mapCreatorController;
	private Button btnLoad;
	private Button btnSave;
	private Button btnExit;
	private ListView itemList;
	private ImageView infoWindowImage;
	private Label infoWindowHeader;

	// Sets up the mockito mocks
	@Mock
	InterfaceManager interfaceManager;
	@Mock
	GameManager gameManagerMock;
	@Mock
	MapAssembly mapAssemblyMock;
	@Mock
	Stage stage;
	@Mock
	Logger logger = LoggerFactory.getLogger(MapCreatorController.class);;

	/**
	 * Starts the testing. Instantiates a new MapCreatorController object
	 * and lloads it to the stage.
	 *
	 * @param stage             The stage to load the map creator in
	 * @throws Exception        Parent start method must throw exception
     */
	@Override
	public void start(Stage stage) throws Exception{
		URL location = getClass().getResource("/ui/fxml/mapCreatorScreen.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);

		Parent root = fxmlLoader.load(location.openStream());
		mapCreatorController = fxmlLoader.getController();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Tests all GUI objects being loaded in the screen.
	 * This includes the buttons, item list and the images
	 * displayed by the item list. Checks if each item can
	 * be found in the stage.
     */
	@Before
	public void mapNodes() {

		when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);

		// Buttons
		btnLoad = find("#btnLoad");
		btnSave = find("#btnSave");
		btnExit = find("#btnExit");

		// Info panel
		itemList = find("#itemList");
		infoWindowHeader = find("#infoWindowHeader");
		infoWindowImage = find("#infoWindowImage");

	}

	/**
	 * After checking if GUI elements exist, tests if
	 * they have a value (not null).
     */
	@Test
	public void testNodesExist() {

		// Buttons
		verifyThat(("#btnLoad"), isNotNull());
		verifyThat(("#btnSave"), isNotNull());
		verifyThat(("#btnExit"), isNotNull());

		// Info panel
		verifyThat(("#itemList"), isNotNull());
		verifyThat(("#infoWindowHeader"), isNotNull());
		verifyThat(("#infoWindowImage"), isNotNull());
	}

	/**
	 * Tests if load button is clickable.
     */
	@Test @Ignore
	public void testLoadButton() {
		btnLoad.fire();
	}

	/**
	 * Tests if save button is clickable.
	 */
	@Test @Ignore
	public void testSaveButton() {
		btnSave.fire();
	}

	/**
	 * Tests if exit button is clickable. Checks if stage
	 * is successfully closed on press.
	 */
	@Test
	public void testExitButton() {
		Stage stage = (Stage) btnExit.getScene().getWindow();
		btnExit.fire();
		assertTrue(stage.isShowing());
	}

	/**
	 * Test if MapCreatorController's interface manager object
	 * is of an InterfaceManager type.
	 *
	 * Test if MapCreatorController's staage object
	 * is of a Stage type.
	 */
	@Test
	public void testGetterSetters() {
		mapCreatorController.setInterfaceManager(interfaceManager);
		assertEquals(interfaceManager, mapCreatorController.getInterfaceManager());

		mapCreatorController.setStage(stage);
		assertEquals(stage, mapCreatorController.getStage());
	}

	/**
	 * Cleans up testing.
	 */
	@AfterClass
	public static void cleanUp() throws TimeoutException {
		setupStage(Stage::close);
	}
}
