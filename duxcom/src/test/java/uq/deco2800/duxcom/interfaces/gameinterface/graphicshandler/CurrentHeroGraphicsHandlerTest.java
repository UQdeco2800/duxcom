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
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests CurrentHeroGraphicsHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CurrentHeroGraphicsHandlerTest extends ApplicationTest{

    private CurrentHeroGraphicsHandler currentHeroGraphicsHandler;
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
    public void testUpdateGraphics() {
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(gameManagerMock.getxOffset()).thenReturn(10.0);
        when(gameManagerMock.getyOffset()).thenReturn(10.0);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        when(gameManagerMock.isPlayerTurn()).thenReturn(true);
        when(mapAssemblyMock.getCurrentTurnEntity(true)).thenReturn(abstractHeroMock);
        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        currentHeroGraphicsHandler.updateGraphics(graphicsContext);
        verify(mapAssemblyMock, times(4)).getCurrentTurnEntity(true);

        when(gameManagerMock.isPlayerTurn()).thenReturn(false);
        when(mapAssemblyMock.getCurrentTurnEntity(false)).thenReturn(abstractEnemyMock);
        when(abstractEnemyMock.getX()).thenReturn(1);
        when(abstractEnemyMock.getY()).thenReturn(1);
        currentHeroGraphicsHandler.updateGraphics(graphicsContext);
        verify(mapAssemblyMock, times(4)).getCurrentTurnEntity(false);
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isSelectionChanged()).thenReturn(false);
        assertFalse(currentHeroGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isSelectionChanged()).thenReturn(false);
        assertTrue(currentHeroGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        assertTrue(currentHeroGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        assertTrue(currentHeroGraphicsHandler.needsUpdating());
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
        currentHeroGraphicsHandler = new CurrentHeroGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }

}