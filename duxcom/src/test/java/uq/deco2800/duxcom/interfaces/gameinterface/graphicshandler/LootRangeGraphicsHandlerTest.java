package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import java.util.ArrayList;
import java.util.List;
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
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.testfx.api.FxToolkit.setupStage;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.controllers.UserInterfaceController;
import uq.deco2800.duxcom.entities.DeathMagma;
import uq.deco2800.duxcom.entities.PickableEntities;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.loot.LootManager;
import uq.deco2800.duxcom.tiles.Tile;

/**
 * Test LootRangegraphicsHandler
 *
 * @author The_Magic_Karps
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class LootRangeGraphicsHandlerTest extends ApplicationTest {

    private LootRangeGraphicsHandler lootRangeGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private Tile tileMock;
    @Mock
    private LootManager lootManagerMock;
    @Mock
    private DuxComController duxComControllerMock;
    @Mock
    private UserInterfaceController userInterfaceControllerMock;

    @Test
    public void testUpdateGraphics() {
        List<PickableEntities> deadEnemy = new ArrayList<>();
        deadEnemy.add(new DeathMagma(5, 5));
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.isLootStatusChanged()).thenReturn(true);
        when(mapAssemblyMock.getUnlootedDeath()).thenReturn(deadEnemy);
        when(mapAssemblyMock.getLootManager()).thenReturn(lootManagerMock);
        when(gameManagerMock.getController()).thenReturn(duxComControllerMock);
        when(duxComControllerMock.getUIController()).thenReturn(
                userInterfaceControllerMock);
        when(duxComControllerMock.getGamePane()).thenReturn(new AnchorPane());
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(mapAssemblyMock.getWidth()).thenReturn(10);
        when(mapAssemblyMock.getHeight()).thenReturn(10);
        when(mapAssemblyMock.getTile(5, 5)).thenReturn(tileMock);
        when(tileMock.isHidden()).thenReturn(true);
        // no hero near loot range 1st time
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(new Archer(5, 6));
        when(lootManagerMock.getInvInLootArea(any())).thenReturn(new ArrayList<>());
        lootRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(userInterfaceControllerMock, times(0)).setLootButtonVisibility(true);
        verify(userInterfaceControllerMock, times(1)).setLootButtonVisibility(false);
        verify(mapAssemblyMock, times(1)).getUnlootedDeath();
        verify(mapAssemblyMock, times(1)).setLootStatusChanged(false);
        
        // 2nd time when tile is hidden
        when(tileMock.isHidden()).thenReturn(false);
        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(false);
        lootRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(mapAssemblyMock, times(2)).getUnlootedDeath();
        verify(mapAssemblyMock, times(2)).setLootStatusChanged(false);
        
        // 3rd time when a hero in range of loot
        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(true);
        when(lootManagerMock.getInvInLootArea(any())).thenReturn(deadEnemy);
        lootRangeGraphicsHandler.updateGraphics(graphicsContext);
        verify(userInterfaceControllerMock, times(1)).setLootButtonVisibility(true);
        verify(userInterfaceControllerMock, times(2)).setLootButtonVisibility(false);
        verify(mapAssemblyMock, times(3)).getUnlootedDeath();
        verify(mapAssemblyMock, times(3)).setLootStatusChanged(false);
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.isLootStatusChanged()).thenReturn(false);
        assertFalse(lootRangeGraphicsHandler.needsUpdating());

        when(mapAssemblyMock.isLootStatusChanged()).thenReturn(true);
        assertTrue(lootRangeGraphicsHandler.needsUpdating());

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
        lootRangeGraphicsHandler = new LootRangeGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}
