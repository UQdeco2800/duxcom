package uq.deco2800.duxcom.interfaces.overlaymaker.ui;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 * OverlayMakerUIManagerTest
 *
 * @author The_Magic_Karps
 */
@RunWith(MockitoJUnitRunner.class)
public class OverlayMakerUIManagerTest extends ApplicationTest {

    @Mock
    private GameManager gameManagerMock;

    @Mock
    private DuxComController duxComControllerMock;

    private OverlayMakerUIManager manager;

    private AnchorPane gamePane;

    @Before
    public void setUp() {
        manager = new OverlayMakerUIManager(gameManagerMock, gamePane);
    }

    /**
     * Test of addFXML method, of class OverlayMakerUIManager.
     */
    @Test
    public void testAddFXML() throws IOException {
        manager.addFXML("/ui/fxml/EscMenu.fxml", 0, 0, UIOrder.FRONT);
        OverlayMakerUI ui = manager.getComponent("/ui/fxml/EscMenu.fxml");
        // exist
        assertNotNull(ui);
        // not displayed
        assertFalse(ui.isActive());
        // not draggable
        assertFalse(ui.isDraggable());
        // same parent
        assertEquals(ui.getParent(), gamePane);
        // check order
        assertEquals(UIOrder.FRONT.getOrder(), ui.getZOrder());
    }

    /**
     * Test of showComponents method, of class OverlayMakerUIManager.
     */
    @Test
    public void testShowComponents_String() throws IOException, InterruptedException {
        manager.addFXML("/ui/fxml/EscMenu.fxml", 0, 0, UIOrder.BACK);
        Platform.runLater(() -> {
            manager.showComponents("/ui/fxml/EscMenu.fxml");
        });
        waitForRunLater();
        OverlayMakerUI ui = manager.getComponent("/ui/fxml/EscMenu.fxml");
        // exist
        assertNotNull(ui);
        // displayed
        assertTrue(ui.isActive());
        // not draggable
        assertFalse(ui.isDraggable());
        // same parent
        assertEquals(ui.getParent(), gamePane);
        // same z order
        assertEquals(UIOrder.BACK.getOrder(), ui.getZOrder());
    }

    /**
     * Test of showComponents method, of class OverlayMakerUIManager.
     */
    @Test
    public void testShowComponents_0args() throws IOException, InterruptedException {
        manager.addFXML("/ui/fxml/EscMenu.fxml", 0, 0, UIOrder.MIDDLE);
        Platform.runLater(() -> {
            manager.showComponents();
        });
        waitForRunLater();
        OverlayMakerUI ui = manager.getComponent("/ui/fxml/EscMenu.fxml");
        // exist
        assertNotNull(ui);
        // displayed
        assertTrue(ui.isActive());
        // not draggable
        assertFalse(ui.isDraggable());
        // same parent
        assertEquals(ui.getParent(), gamePane);
        // same z order
        assertEquals(UIOrder.MIDDLE.getOrder(), ui.getZOrder());
        Platform.runLater(() -> {
            ui.hideOverlay();
        });
        waitForRunLater();
        assertFalse(ui.isActive());
        manager.addFXML("/ui/fxml/guidePopUp.fxml", 0, 0, UIOrder.VERY_FRONT);
        OverlayMakerUI ui2 = manager.getComponent("/ui/fxml/guidePopUp.fxml");
        Platform.runLater(() -> {
            manager.showComponents();
        });
        waitForRunLater();
        // exist
        assertNotNull(ui2);
        // displayed
        assertTrue(ui2.isActive());
        // not draggable
        assertFalse(ui2.isDraggable());
        // same parent
        assertEquals(ui2.getParent(), gamePane);
        // same z order
        assertEquals(UIOrder.VERY_FRONT.getOrder(), ui2.getZOrder());
        // ui1 also active
        assertTrue(ui.isActive());
    }

    /**
     * Test of hideComponents method, of class OverlayMakerUIManager.
     */
    @Test
    public void testHideComponents_0args() throws IOException, InterruptedException {
        manager.addFXML("/ui/fxml/EscMenu.fxml", 0, 0, UIOrder.VERY_BACK);
        OverlayMakerUI ui = manager.getComponent("/ui/fxml/EscMenu.fxml");
        // same z order
        assertEquals(UIOrder.VERY_BACK.getOrder(), ui.getZOrder());
        // show ui
        Platform.runLater(() -> {
            ui.showOverlay();
        });
        waitForRunLater();
        assertTrue(ui.isActive());
        Platform.runLater(() -> {
            manager.hideComponents();
        });
        waitForRunLater();
        assertFalse(ui.isActive());
        // second ui
        manager.addFXML("/ui/fxml/guidePopUp.fxml", 0, 0, UIOrder.VERY_FRONT);
        OverlayMakerUI ui2 = manager.getComponent("/ui/fxml/guidePopUp.fxml");
        Platform.runLater(() -> {
            manager.showComponents();
        });
        waitForRunLater();
        assertTrue(ui.isActive());
        assertTrue(ui2.isActive());
        Platform.runLater(() -> {
            manager.hideComponents();
        });
        waitForRunLater();
        assertFalse(ui.isActive());
        assertFalse(ui2.isActive());
    }

    /**
     * Test of hideComponents method, of class OverlayMakerUIManager.
     */
    @Test
    public void testHideComponents_String() throws InterruptedException, IOException {
        manager.addFXML("/ui/fxml/EscMenu.fxml", 0, 0, UIOrder.VERY_BACK);
        OverlayMakerUI ui = manager.getComponent("/ui/fxml/EscMenu.fxml");
        // same z order
        assertEquals(UIOrder.VERY_BACK.getOrder(), ui.getZOrder());
        // show ui
        Platform.runLater(() -> {
            manager.showComponents();
        });
        waitForRunLater();
        assertTrue(ui.isActive());
        Platform.runLater(() -> {
            manager.hideComponents("/ui/fxml/EscMenu.fxml");
        });
        waitForRunLater();
        assertFalse(ui.isActive());
    }

    /**
     * Test of getComponent method, of class OverlayMakerUIManager.
     */
    @Test
    public void testGetComponent() throws IOException {
        manager.addFXML("/ui/fxml/EscMenu.fxml", 0, 0, UIOrder.VERY_BACK);
        OverlayMakerUI ui = manager.getComponent("/ui/fxml/EscMenu.fxml");
        // exist
        assertNotNull(ui);
        // displayed
        assertFalse(ui.isActive());
        // not draggable
        assertFalse(ui.isDraggable());
        // same parent
        assertEquals(ui.getParent(), gamePane);
        // same z order
        assertEquals(UIOrder.VERY_BACK.getOrder(), ui.getZOrder());
        // not exist
        OverlayMakerUI uiNotExist = manager.getComponent("/ui/fxml/abcde.fxml");
        assertNull(uiNotExist);
    }

    @Test
    public void testConst_1Arg() {
        when(gameManagerMock.getController()).thenReturn(duxComControllerMock);
        when(duxComControllerMock.getGamePane()).thenReturn(gamePane);
        manager = new OverlayMakerUIManager(gameManagerMock);
        verify(duxComControllerMock, times(1)).getGamePane();
    }

    @Override
    public void start(Stage stage) throws Exception {
        gamePane = new AnchorPane();
        stage.setScene(new Scene(gamePane, 800, 500));
        stage.show();
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
