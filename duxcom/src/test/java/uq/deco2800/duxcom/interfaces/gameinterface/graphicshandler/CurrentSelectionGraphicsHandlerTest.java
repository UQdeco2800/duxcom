package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests CurrentHeroGraphicsHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CurrentSelectionGraphicsHandlerTest extends ApplicationTest {

    private CurrentSelectionGraphicsHandler currentSelectionGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private AbstractEnemy abstractEnemyMock;

    @Test

    public void testRender() {
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);

        when(mapAssemblyMock.getCurrentTurnEntity(true)).thenReturn(abstractHeroMock);
        when(mapAssemblyMock.getCurrentTurnEntity(false)).thenReturn(abstractEnemyMock);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(gameManagerMock.getxOffset()).thenReturn(10.0);
        when(gameManagerMock.getyOffset()).thenReturn(10.0);
        when(gameManagerMock.getSelectionX()).thenReturn(0);
        when(gameManagerMock.getSelectionY()).thenReturn(0);
        when(gameManagerMock.getHoverX()).thenReturn(2);
        when(gameManagerMock.getHoverY()).thenReturn(2);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        when(mapAssemblyMock.getHeight()).thenReturn(20);
        when(mapAssemblyMock.getMovableEntity(2, 2)).thenReturn(abstractHeroMock);
        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        when(gameManagerMock.isPlayerTurn()).thenReturn(true);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(1)).setSelectionChanged(false);

        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        when(mapAssemblyMock.getMovableEntity(2, 2)).thenReturn(null);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(2)).setSelectionChanged(false);

        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        when(gameManagerMock.getHoverX()).thenReturn(-4);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(3)).setSelectionChanged(false);

        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        when(gameManagerMock.getHoverX()).thenReturn(21);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(4)).setSelectionChanged(false);

        when(gameManagerMock.getHoverX()).thenReturn(2);

        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        when(gameManagerMock.getHoverY()).thenReturn(-4);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(5)).setSelectionChanged(false);

        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        when(gameManagerMock.getHoverY()).thenReturn(21);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(6)).setSelectionChanged(false);

        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        when(gameManagerMock.isPlayerTurn()).thenReturn(false);
        currentSelectionGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(7)).setSelectionChanged(false);

    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isSelectionChanged()).thenReturn(false);
        assertFalse(currentSelectionGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isSelectionChanged()).thenReturn(false);
        assertTrue(currentSelectionGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        assertTrue(currentSelectionGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        assertTrue(currentSelectionGraphicsHandler.needsUpdating());
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
        currentSelectionGraphicsHandler = new CurrentSelectionGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}