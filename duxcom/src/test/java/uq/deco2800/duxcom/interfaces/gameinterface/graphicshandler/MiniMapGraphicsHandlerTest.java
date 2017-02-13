package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test MiniMapGraphicsHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MiniMapGraphicsHandlerTest extends ApplicationTest {

    private MiniMapGraphicsHandler miniMapGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private AbstractHero abstractHeroMock;

    @Test
    public void testUpdateGraphics() {
        when(gameManagerMock.isMiniMapChanged()).thenReturn(true);

        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        miniMapGraphicsHandler.updateGraphics(graphicsContext);

        verify(gameManagerMock, times(1)).setMiniMapChanged(false);
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isMiniMapChanged()).thenReturn(false);
        when(gameManagerMock.isMiniMapVisible()).thenReturn(false);
        assertFalse(miniMapGraphicsHandler.needsUpdating());

        when(gameManagerMock.isMiniMapChanged()).thenReturn(true);
        when(gameManagerMock.isMiniMapVisible()).thenReturn(false);
        assertFalse(miniMapGraphicsHandler.needsUpdating());

        when(gameManagerMock.isMiniMapChanged()).thenReturn(false);
        when(gameManagerMock.isMiniMapVisible()).thenReturn(true);
        assertFalse(miniMapGraphicsHandler.needsUpdating());

        when(gameManagerMock.isMiniMapChanged()).thenReturn(true);
        when(gameManagerMock.isMiniMapVisible()).thenReturn(true);
        assertTrue(miniMapGraphicsHandler.needsUpdating());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(canvas);
        Scene scene = new Scene(anchorPane, 1280, 720);
        stage.setScene(scene);
        stage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        new GameLoop(10, new AtomicBoolean(false), gameManagerMock);
        miniMapGraphicsHandler = new MiniMapGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}