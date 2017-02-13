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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test MovementRangeGraphicsHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MovementRangeGraphicsHandlerTest extends ApplicationTest {

    private MovementRangeGraphicsHandler movementRangeGraphicsHandler;
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
        when(gameManagerMock.isMovementChanged()).thenReturn(true);

        when(gameManagerMock.isPlayerTurn()).thenReturn(true);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getCurrentTurnEntity(true)).thenReturn(abstractEnemyMock);
        movementRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, never()).getScale();

        when(mapAssemblyMock.getCurrentTurnEntity(true)).thenReturn(abstractHeroMock);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(mapAssemblyMock.getWidth()).thenReturn(10);
        when(mapAssemblyMock.getHeight()).thenReturn(10);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        when(abstractHeroMock.getMovementCost()).thenReturn(null);
        movementRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, never()).onScreen(anyInt(), anyInt());

        Float[][] tileAPCosts = new Float[10][10];
        tileAPCosts[0][0] = 1f;
        tileAPCosts[1][1] = 2f;
        when(gameManagerMock.onScreen(anyInt(), anyInt())).thenReturn(true);
        when(gameManagerMock.onScreen(1, 1)).thenReturn(false);
        when(abstractHeroMock.getMovementCost()).thenReturn(tileAPCosts);
        movementRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, never()).setMovementChanged(false);
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isMovementChanged()).thenReturn(false);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(false);
        assertFalse(movementRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isMovementChanged()).thenReturn(false);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(false);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isMovementChanged()).thenReturn(true);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(false);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isMovementChanged()).thenReturn(true);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(false);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());
        
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isMovementChanged()).thenReturn(false);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isMovementChanged()).thenReturn(false);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isMovementChanged()).thenReturn(true);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isMovementChanged()).thenReturn(true);
        when(gameManagerMock.isAbilitySelectedChanged()).thenReturn(true);
        assertTrue(movementRangeGraphicsHandler.needsUpdating());
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
        movementRangeGraphicsHandler = new MovementRangeGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}