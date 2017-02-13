package uq.deco2800.duxcom.interfaces;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.exceptions.DuplicateStartGameException;
import uq.deco2800.duxcom.maps.*;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.AreaBound;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockGroup;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockPointMapper;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.time.DayNightClock;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests GameInterface
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GameInterfaceTest extends ApplicationTest {

    private GameInterface gameInterface;
    private Stage stage;
    private boolean created = false;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private InterfaceManager interfaceManagerMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private BlockGroup blockGroupMock;
    @Mock
    private AreaBound areaBoundMock;
    @Mock
    private Tile tileMock;

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be updates
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

    @Before
    public void setUpBlockPointMapper() {
        BlockPointMapper.clearCache();
        created = false;
        gameInterface = new GameInterface();
        new GameLoop(10, new AtomicBoolean(false), gameManagerMock);

        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);when(gameManagerMock.getDayNightClock()).thenReturn(new DayNightClock(10));
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(abstractHeroMock);
        when(mapAssemblyMock.getTile(anyInt(), anyInt())).thenReturn(tileMock);
        when(mapAssemblyMock.getBlockCoordinateMap()).thenReturn(blockGroupMock);
        when(mapAssemblyMock.canSelectPoint(anyInt(), anyInt())).thenReturn(true);
        when(mapAssemblyMock.getMovableEntity(anyInt(), anyInt())).thenReturn(abstractHeroMock);
        when(blockGroupMock.getGroupBound()).thenReturn(areaBoundMock);
        when(areaBoundMock.getStartX()).thenReturn(0);
        when(areaBoundMock.getStartY()).thenReturn(0);
        when(areaBoundMock.getEndX()).thenReturn(10);
        when(areaBoundMock.getEndY()).thenReturn(10);
        when(tileMock.getTileType()).thenReturn(TileType.REAL_QUT);
        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    @Ignore
    @Test
    public void testDemoCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "old", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test(expected = DuplicateStartGameException.class)
    public void testDuplicateCreated() throws IOException, InterruptedException {
        GameManager gameManager = new GameManager();
        new GameLoop(10, new AtomicBoolean(false), gameManager);
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "old", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        gameManager.getController().startGame(gameManagerMock);
    }

    @Ignore
    @Test
    public void testAutoCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "auto", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testEnemyCreated() throws IOException, InterruptedException {
        when(gameManagerMock.isMiniMapVisible()).thenReturn(false);
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "enemy", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testDynamicCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "dynamic", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testOptimisedCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "else", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testFaladorCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "falador", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testFaladorBetaCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "falador_beta", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testTutorialCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "tutorial", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testKaramjaCreated() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                created = gameInterface.loadInterface(stage, "karamja", interfaceManagerMock);
            } catch (IOException e) {
                Platform.exit();
            }
        });
        waitForRunLater();
        assertTrue(created);
        assertNotNull(gameInterface.getGameManager().getMap());
    }

    @Ignore
    @Test
    public void testDestroy() throws InterruptedException {
        gameInterface.destroyInterface();
        assertNotNull(gameInterface);
    }

    @Ignore
    @Test
    public void testManagerInitialisation() {
        gameInterface.initialiseGameManager();
        assertNotNull(gameInterface.getGameManager());
    }

    @Ignore
    @Test(timeout = 25000)
    public void testFaladorLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new Falador("", false)));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testFaladorBetaLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new Falador("", true)));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testKaramjaLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new Karamja("")));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testDemoMapLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new DemoMap("")));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testOptimisedDemoLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new OptimisedDemo()));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testEnemyTestMapLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new EnemyTestMap("")));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testAutoGeneratedMapLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new AutogenerateDemo()));
        gameManager.loadAllTextures();
    }

    @Ignore
    @Test(timeout = 25000)
    public void testDynamiEntityTestMapLoads() {
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(new DynamicEntityTestMap("")));
        gameManager.loadAllTextures();
    }

    @After
    public void resetGameManager() {
        new GameLoop(1, null, null);
    }

    /**
     * Closes JavaFx application
     *
     * @throws TimeoutException If closing stage times out
     */
    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
    }
}