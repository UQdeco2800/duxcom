/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.interfaces.overlaymaker;

import org.junit.Ignore;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 *
 * @author user
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class OverlayMakerPopUpTest extends ApplicationTest {

    // logger
    private static Logger logger = LoggerFactory.getLogger(OverlayMakerPopUpTest.class);

    private OverlayMakerPopUp overlayMakerHandler;

    private OverlayMaker controller;

    private AnchorPane parentPane;

    @Mock
    private GameManager gameManagerMock;

    @Mock
    private MapAssembly mapAssemblyMock;

    @Override
    public void start(Stage primaryStage) throws Exception {
        parentPane = new AnchorPane();
        try {
            // Creates a new instance of overlayMakerHandler
            overlayMakerHandler = OverlayMakerPopUp.makeWithoutGameManager(parentPane, "/ui/fxml/guidePopUp.fxml");
            // Retrieves its controller
            controller = overlayMakerHandler.getController();
            // Add required data into the controller
            controller.show();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            assertTrue(false);
        }
        primaryStage.setScene(new Scene(parentPane, 800, 500));
        primaryStage.show();
    }

    @Test
    public void testCenter() {
        overlayMakerHandler.setCenter(true);
        assertTrue(overlayMakerHandler.isCenter());
        overlayMakerHandler.setCenter(false);
        assertFalse(overlayMakerHandler.isCenter());
    }

    @Test
    public void checkOverlay() {
        Pane overlay = overlayMakerHandler.getOverlay();
        assertNotNull(overlay);
    }

    @Test
    public void checkParent() {
        Pane parent = overlayMakerHandler.getParent();
        overlayMakerHandler.setDisableBackground(true);
        assertEquals(parent, parentPane);
    }

    @Test
    public void checkController() {
        assertNotNull(controller);
        assertTrue((controller instanceof OverlayMaker));
    }

    @Test
    public void testWithGameManager() {
        try {
            OverlayMakerPopUp popUp = OverlayMakerPopUp.makeWithGameManager(parentPane, "/ui/fxml/EscMenu.fxml", gameManagerMock);
            OverlayMaker control = popUp.getController();
            Pane parent = overlayMakerHandler.getParent();
            assertEquals(parent, parentPane);
            assertNotNull(control);
            assertNotNull(controller);
            assertTrue((controller instanceof OverlayMaker));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            assertTrue(false);
        }
    }

    @Test
    public void testDraggable() {
        Platform.runLater(() -> {
            overlayMakerHandler.setDraggable(overlayMakerHandler.getOverlay(), true);
            assertTrue(overlayMakerHandler.isDraggable());
            overlayMakerHandler.setDraggable(overlayMakerHandler.getOverlay(), false);
            assertFalse(overlayMakerHandler.isDraggable());
        });
    }

    @Test(expected = Exception.class)
    public void testNonExistentFileWithGM() throws Exception {
        OverlayMakerPopUp popUp = OverlayMakerPopUp
                .makeWithoutGameManager(parentPane, "DuckDuckIbis.fxml");
    }

    @Test(expected = Exception.class)
    public void testNonExistentFileWithNoGM() throws Exception {
        OverlayMakerPopUp popUp = OverlayMakerPopUp
                .makeWithGameManager(parentPane, "DuckDuckIbis.fxml", gameManagerMock);
    }

    @Test
    public void testCache() throws IOException {
        OverlayMakerPopUp popUp = OverlayMakerPopUp
                .makeWithoutGameManager(parentPane, "/ui/fxml/guidePopUp.fxml");
        OverlayMakerPopUp popUp2 = OverlayMakerPopUp
                .makeWithoutGameManager(parentPane, "/ui/fxml/guidePopUp.fxml");
        assertEquals(popUp, popUp2);

        OverlayMakerPopUp.removeCache("/ui/fxml/guidePopUp.fxml");

        OverlayMakerPopUp popUp3 = OverlayMakerPopUp
                .makeWithoutGameManager(parentPane, "/ui/fxml/guidePopUp.fxml");
        assertNotEquals(popUp3, popUp2);
        assertNotEquals(popUp3, popUp);
    }

    @Test
    public void showHideOverlayTest() throws InterruptedException {
        assertTrue(overlayMakerHandler.isActive());
        overlayMakerHandler.showOverlay();
        assertTrue(parentPane.getChildren().contains(overlayMakerHandler.getOverlay()));
        Platform.runLater(() -> {
            overlayMakerHandler.hideOverlay();
        });
        waitForRunLater();
        assertFalse(overlayMakerHandler.isActive());
        assertFalse(parentPane.getChildren().contains(overlayMakerHandler.getOverlay()));
    }

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be
     * updates
     *
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }
}
