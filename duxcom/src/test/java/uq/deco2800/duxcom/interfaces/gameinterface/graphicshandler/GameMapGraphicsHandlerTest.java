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
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Created by LeX on 12/10/2016.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GameMapGraphicsHandlerTest extends ApplicationTest {

    private GameMapGraphicsHandler gameMapGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private Tile tileMock;

    @Test
    public void testUpdateGraphics() {
        when(gameManagerMock.getxOffset()).thenReturn(0.0);
        when(gameManagerMock.getyOffset()).thenReturn(0.0);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(mapAssemblyMock.getWidth()).thenReturn(10);
        when(mapAssemblyMock.getHeight()).thenReturn(10);
        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(true);
        when(mapAssemblyMock.canSelectPoint(0, 0)).thenReturn(true);
        when(gameManagerMock.onScreen(anyInt(), anyInt())).thenReturn(true);
        when(gameManagerMock.onScreen(1, 1)).thenReturn(false);
        when(mapAssemblyMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);
        when(tileMock.getTileType()).thenReturn(TileType.REAL_QUT);
        when(tileMock.isHidden()).thenReturn(false);

        gameMapGraphicsHandler.updateGraphics(graphicsContext);

        when(tileMock.isHidden()).thenReturn(true);

        gameMapGraphicsHandler.updateGraphics(graphicsContext);

        verify(tileMock, atLeastOnce()).isHidden();
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isSelectionChanged()).thenReturn(false);
        assertFalse(gameMapGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isSelectionChanged()).thenReturn(false);
        assertTrue(gameMapGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(false);
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        assertTrue(gameMapGraphicsHandler.needsUpdating());

        when(gameManagerMock.isGameChanged()).thenReturn(true);
        when(gameManagerMock.isSelectionChanged()).thenReturn(true);
        assertTrue(gameMapGraphicsHandler.needsUpdating());
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
        gameMapGraphicsHandler = new GameMapGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}