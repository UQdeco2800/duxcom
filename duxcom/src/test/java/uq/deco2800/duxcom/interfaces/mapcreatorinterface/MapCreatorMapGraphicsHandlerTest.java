package uq.deco2800.duxcom.interfaces.mapcreatorinterface;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.AfterClass;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Created by LeX on 12/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class MapCreatorMapGraphicsHandlerTest extends ApplicationTest {

    private MapCreatorMapGraphicsHandler mapCreatorMapGraphicsHandler;
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
        when(tileMock.isHidden()).thenReturn(true);

        mapCreatorMapGraphicsHandler.updateGraphics(graphicsContext);

        verify(tileMock, atLeastOnce()).isHidden();
    }

    @Test
    public void testNeedsUpdating() {
        assertTrue(mapCreatorMapGraphicsHandler.needsUpdating());
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
        mapCreatorMapGraphicsHandler = new MapCreatorMapGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);

    }
}