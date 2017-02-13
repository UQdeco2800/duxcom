package uq.deco2800.duxcom.controllers.esccontrollers;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerSuperPopUp;

/**
 * EscMenuController Test
 *
 * @author The_Magic_Karps
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class EscMenuControllerTest extends ApplicationTest {

    private AnchorPane parentPane;
    private OverlayMakerSuperPopUp popUp;
    private EscMenuController controller;
    private VBox buttonBox;
    public boolean complete;
    private Button glossaryButton;
    private Button soundToggleButton;
    private Button mapToggleButton;
    private HBox iconBox;
    private AnchorPane basePane;

    @Mock
    private InterfaceManager interfaceManagerMock;

    @Mock
    private GameManager gameManagerMock;

    @Mock
    private DuxComController duxComControllerMock;

    @Mock
    private Stage stageMock;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        parentPane = new AnchorPane();

        popUp = OverlayMakerSuperPopUp.makeSuperPopUpGM(parentPane, "/ui/fxml/EscMenu.fxml", gameManagerMock);

        controller = (EscMenuController) popUp.getController();

        stage.setScene(new Scene(popUp.getOverlay(), 1280, 720));
        stage.show();

    }

    @Before
    public void setUp() throws InterruptedException, IOException {
        buttonBox = find("#buttonBox");
        glossaryButton = find("#glossaryButton");
        soundToggleButton = find("#soundToggleButton");
        mapToggleButton = find("#mapToggleButton");
        iconBox = find("#iconBox");
        basePane = find("#basePane");
        when(gameManagerMock.getController()).thenReturn(duxComControllerMock);
        controller.addInterface(interfaceManagerMock);
    }

    @Test
    public void baseBoxesExist() {
        verifyThat("#buttonBox", isNotNull());
        verifyThat("#glossaryButton", isNotNull());
        verifyThat("#soundToggleButton", isNotNull());
        verifyThat("#mapToggleButton", isNotNull());
        verifyThat("#iconBox", isNotNull());
    }

    @Test
    public void testMainGameButtons() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertTrue(controller instanceof EscMenuController);

        Platform.runLater(() -> {
            controller.addInterface(interfaceManagerMock);
        });
        waitForRunLater();
        assertTrue(buttonBox.getChildren().isEmpty());

        Platform.runLater(() -> {
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        assertTrue(!buttonBox.getChildren().isEmpty());
        for (Node button : buttonBox.getChildren()) {
            assertTrue(button instanceof Button);
        }
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertFalse(parentPane.getChildren().contains(popUp.getOverlay()));
    }

    @Test
    public void testOverworldButtons() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertTrue(controller instanceof EscMenuController);

        Platform.runLater(() -> {
            controller.addInterface(interfaceManagerMock);
        });
        waitForRunLater();
        assertTrue(buttonBox.getChildren().isEmpty());

        Platform.runLater(() -> {
            controller.setSegment(EscSegmentType.OVERWORLD);
        });
        waitForRunLater();
        assertTrue(!buttonBox.getChildren().isEmpty());
        for (Node button : buttonBox.getChildren()) {
            assertTrue(button instanceof Button);
        }
    }

    @Test
    public void testLoadScreenButtons() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertTrue(controller instanceof EscMenuController);

        Platform.runLater(() -> {
            controller.addInterface(interfaceManagerMock);
        });
        waitForRunLater();
        assertTrue(buttonBox.getChildren().isEmpty());

        Platform.runLater(() -> {
            controller.setSegment(EscSegmentType.LOADSCREEN);
        });
        waitForRunLater();
        assertFalse(buttonBox.getChildren().isEmpty());
        for (Node button : buttonBox.getChildren()) {
            assertTrue(button instanceof Button);
        }
    }

    @Test
    public void testShowHideToggle() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertTrue(popUp.isActive());

        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertFalse(popUp.isActive());

        Platform.runLater(() -> {
            controller.hide();
        });
        waitForRunLater();
        assertFalse(popUp.isActive());

        Platform.runLater(() -> {
            controller.hide();
        });
        waitForRunLater();
        assertFalse(popUp.isActive());

        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertTrue(popUp.isActive());

        Platform.runLater(() -> {
            controller.hide();
        });
        waitForRunLater();
        assertFalse(popUp.isActive());
    }

    @Test
    public void testInitialised() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        assertEquals(buttonBox.getChildren().size(), 7);
        Platform.runLater(() -> {
            controller.setSegment(EscSegmentType.LOADSCREEN);
        });
        waitForRunLater();
        assertNotEquals(buttonBox.getChildren().size(), 3);
        assertEquals(buttonBox.getChildren().size(), 7);
    }

    @Test
    public void testReturnToGame() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        Platform.runLater(() -> {
            ((Button) buttonBox.getChildren().get(0)).fire();
        });
        waitForRunLater();
        assertFalse(popUp.isActive());
    }

    @Test
    public void testSaveGame() throws InterruptedException {
    	Mockito.doNothing().when(gameManagerMock).saveGame();
    	Mockito.doNothing().when(gameManagerMock).saveStatsLocally();
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
            ((Button) buttonBox.getChildren().get(1)).fire();
        });
        waitForRunLater();
        verify(gameManagerMock, times(1)).saveGame();
        verify(gameManagerMock, times(1)).saveStatsLocally();
    }

    @Test
    public void testLoadGame() throws InterruptedException {
        Mockito.doNothing().when(interfaceManagerMock).loadSegmentImmediate(
                any(), any(), any());
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
            ((Button) buttonBox.getChildren().get(2)).fire();
        });
        waitForRunLater();
        verify(interfaceManagerMock, times(1)).loadSegmentImmediate(
                any(), any(), any());
    }

    @Test
    public void testHideIcon() throws InterruptedException {
        Platform.runLater(() -> {
            controller.hideIcons();
        });
        waitForRunLater();
        assertFalse(popUp.getParent().getChildren().contains(iconBox));        
    }

    @Test
    public void testKeyBoardShortcutHelp() throws IOException, InterruptedException {
        when(duxComControllerMock.getGamePane()).thenReturn(parentPane);
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);

            ((Button) buttonBox.getChildren().get(3)).fire();
        });
        waitForRunLater();
        OverlayMakerPopUp guidePopUp = OverlayMakerPopUp
                .makeWithoutGameManager(duxComControllerMock.getGamePane(), "/ui/fxml/guidePopUp.fxml");
        assertTrue(guidePopUp.isActive());
    }

    @Test
    public void testBackToLoadScreen() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        Mockito.doNothing().when(duxComControllerMock).stopGame();
        Mockito.doNothing().when(interfaceManagerMock).returnLoadScreen();
        Platform.runLater(() -> {
            ((Button) buttonBox.getChildren().get(4)).fire();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(1)).stopGame();
        verify(interfaceManagerMock, times(1)).returnLoadScreen();
    }

    @Test
    public void testLogOut() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        Mockito.doNothing().when(duxComControllerMock).stopGame();
        Mockito.doNothing().when(interfaceManagerMock).restartGame();
        Platform.runLater(() -> {
            ((Button) buttonBox.getChildren().get(5)).fire();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(1)).stopGame();
        verify(interfaceManagerMock, times(1)).restartGame();
    }

    @Test
    public void testCloseGame() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        Mockito.doNothing().when(duxComControllerMock).stopGame();
        Mockito.doNothing().when(interfaceManagerMock).killInterfaces();
        when(interfaceManagerMock.getStage()).thenReturn(stageMock);
        Mockito.doNothing().when(stageMock).close();
        Platform.runLater(() -> {
            ((Button) buttonBox.getChildren().get(6)).fire();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(1)).stopGame();
        verify(interfaceManagerMock, times(1)).killInterfaces();
    }

    @Test
    public void testBackToOverworld() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
            controller.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        Mockito.doNothing().when(duxComControllerMock).stopGame();
        Mockito.doNothing().when(interfaceManagerMock).returnOverworldScreen();
        Platform.runLater(() -> {
            controller.returnToOverworld();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(1)).stopGame();
        verify(interfaceManagerMock, times(1)).returnOverworldScreen();
    }

    @Test
    public void testNoManager() throws IOException, InterruptedException {
        AnchorPane parentPane2 = new AnchorPane();
        EscMenuController controller2
                = (EscMenuController) OverlayMakerSuperPopUp.makeSuperPopUpNoGM(
                        parentPane2, "/ui/fxml/EscMenu.fxml").getController();
        Platform.runLater(() -> {
            controller2.addInterface(interfaceManagerMock);
            controller2.show();
            controller2.setSegment(EscSegmentType.MAIN_GAME);
        });
        waitForRunLater();
        Mockito.doNothing().when(duxComControllerMock).stopGame();
        Mockito.doNothing().when(interfaceManagerMock).killInterfaces();
        Mockito.doNothing().when(interfaceManagerMock).returnLoadScreen();
        Mockito.doNothing().when(interfaceManagerMock).restartGame();
        when(interfaceManagerMock.getStage()).thenReturn(stageMock);
        Mockito.doNothing().when(stageMock).close();

        // load screen
        Platform.runLater(() -> {
            controller2.returnToLoad();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(0)).stopGame();
        verify(interfaceManagerMock, times(1)).returnLoadScreen();

        // logout
        Platform.runLater(() -> {
            controller2.logOut();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(0)).stopGame();
        verify(interfaceManagerMock, times(1)).restartGame();

        //close game
        Platform.runLater(() -> {
            controller2.closeGame();
        });
        waitForRunLater();
        verify(duxComControllerMock, times(0)).stopGame();
        verify(interfaceManagerMock, times(1)).killInterfaces();
    }

    @Test
    public void testOpenGlossary() throws InterruptedException, IOException {
        when(duxComControllerMock.getGamePane()).thenReturn(parentPane);
        Platform.runLater(() -> {
            glossaryButton.fire();
        });
        waitForRunLater();
        OverlayMakerPopUp glossary = OverlayMakerPopUp.makeWithGameManager(
                duxComControllerMock.getGamePane(), "/ui/fxml/Glossary.fxml",
                gameManagerMock);
        assertTrue(parentPane.getChildren().contains(glossary.getOverlay()));

        Platform.runLater(() -> {
            glossaryButton.fire();
        });
        waitForRunLater();
        assertTrue(parentPane.getChildren().contains(glossary.getOverlay()));
    }

    @Test
    public void testToggleMute() throws InterruptedException {
        when(gameManagerMock.toggleMute()).thenReturn(true);
        Platform.runLater(() -> {
            soundToggleButton.fire();
        });
        waitForRunLater();
        verify(gameManagerMock, times(1)).toggleMute();

        when(gameManagerMock.toggleMute()).thenReturn(false);
        Platform.runLater(() -> {
            soundToggleButton.fire();
        });
        waitForRunLater();
        verify(gameManagerMock, times(2)).toggleMute();
    }

    @Test
    public void testToggleMap() throws InterruptedException {
        when(gameManagerMock.toggleMiniMap()).thenReturn(true);
        Platform.runLater(() -> {
            mapToggleButton.fire();
        });
        waitForRunLater();
        verify(gameManagerMock, times(1)).toggleMiniMap();
        when(gameManagerMock.toggleMiniMap()).thenReturn(false);
        Platform.runLater(() -> {
            mapToggleButton.fire();
        });
        waitForRunLater();
        verify(gameManagerMock, times(2)).toggleMiniMap();
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
