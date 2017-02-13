package uq.deco2800.duxcom.interfaces.gameinterface;

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
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.VisionGraphicsHandler;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.AreaBound;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockGroup;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class VisionGraphicsHandlerTests extends ApplicationTest {

    private VisionGraphicsHandler visionGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private AbstractEnemy abstractEnemyMock;
    @Mock
    private BlockGroup blockGroupMock;
    @Mock
    private AreaBound areaBoundMock;
    @Mock
    private Tile tileMock;
    @Mock
    private HeroManager heroManagerMock;
    @Mock
    private EnemyManager enemyManagerMock;

    @Test
    public void testRender() {
        visionGraphicsHandler.render(graphicsContext);
        assertNotNull(visionGraphicsHandler);
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isVisionChanged()).thenReturn(false);
        assertFalse(visionGraphicsHandler.needsUpdating());

        when(gameManagerMock.isVisionChanged()).thenReturn(true);
        assertTrue(visionGraphicsHandler.needsUpdating());
    }

    @Test
    public void testUpdateGraphics() {
        gameManagerMock.setVisionChanged(true);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        when(mapAssemblyMock.getHeight()).thenReturn(20);

        int[][] vision = new int[20][20];
        vision[1][1] = 1;
        when(mapAssemblyMock.getVisibilityArray()).thenReturn(vision);
        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(true);
        when(mapAssemblyMock.canSelectPoint(0, 0)).thenReturn(false);
        when(mapAssemblyMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);

        visionGraphicsHandler.updateGraphics(graphicsContext);

        verify(gameManagerMock, times(1)).setVisionChanged(false);
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
        visionGraphicsHandler = new VisionGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }

}