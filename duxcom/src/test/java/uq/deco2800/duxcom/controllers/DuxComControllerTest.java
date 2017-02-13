package uq.deco2800.duxcom.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.exceptions.DuplicateStartGameException;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.AreaBound;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockGroup;
import uq.deco2800.duxcom.time.DayNightClock;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.setupStage;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

/**
 * Tests the DuxComController class
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class DuxComControllerTest extends ApplicationTest {

    // Initialises the class logger
    private static Logger logger = LoggerFactory.getLogger(DuxComControllerTest.class);

    // Declares the JavFX nodes to be tested
    private static AnchorPane gamePane;

    private DuxComController duxComController;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private BlockGroup blockGroupMock;
    @Mock
    private AreaBound areaBoundMock;
    @Mock
    private ExecutorService executorServiceMock;
    @Mock
    private AnchorPane gamePaneMock;
    @Mock
    private ObservableList<Node> childrenMock;

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be updates
     *
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

    /**
     * Sets up the JavaFX variables before each test is run
     */
    @Before
    public void setUp() throws IOException {
        gamePane = find("#gamePane");
    }

    /**
     * Check that all necessary nodes exist
     */
    @Test
    public void testAllNodesExist() {
        verifyThat("#gamePane", isNotNull());
    }


    /**
     * Tests the ability to start the game
     *
     * Currently ignored due to breaking builds on jenkins
     */
    @Ignore
    @Test
    public void testStartGame() throws DuplicateStartGameException, InterruptedException, IOException {
        when(gameManagerMock.getDayNightClock()).thenReturn(new DayNightClock(10, 20));
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getWidth()).thenReturn(30);
        when(mapAssemblyMock.getHeight()).thenReturn(30);
        when(mapAssemblyMock.getBlockCoordinateMap()).thenReturn(blockGroupMock);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(new Archer(1, 1));
        when(blockGroupMock.getGroupBound()).thenReturn(areaBoundMock);
        when(areaBoundMock.getStartX()).thenReturn(0);
        when(areaBoundMock.getStartY()).thenReturn(0);
        when(areaBoundMock.getEndX()).thenReturn(30);
        when(areaBoundMock.getEndY()).thenReturn(30);
        when(gamePaneMock.getChildren()).thenReturn(childrenMock);
        duxComController.gamePane = gamePaneMock;
        duxComController.executor = executorServiceMock;
        duxComController.startGame(gameManagerMock);

        boolean exceptionThrown = false;
        try {
            duxComController.startGame(gameManagerMock);
        } catch (DuplicateStartGameException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

    }

    /**
     * Tests the ability to stop the game
     */
    @Test
    public void testStopGame() {
        duxComController.gameManager = gameManagerMock;
        duxComController.quit = new AtomicBoolean(false);
        duxComController.executor = executorServiceMock;
        duxComController.stopGame();
        assertNull(duxComController.gameManager);
        assertTrue(duxComController.quit.get());
        verify(executorServiceMock, times(1)).shutdown();
    }

    /**
     * Sets up the TestFX environment
     */
    @Override
    public void start(Stage stage) throws Exception {

        URL location = getClass().getResource("/ui/fxml/duXCOM.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        duxComController = fxmlLoader.getController();
        gameManagerMock.setController(duxComController);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Closes JavaFx application
     *
     * @throws TimeoutException If closing stage times out
     */
    @After
    public void cleanUp() throws TimeoutException, IOException {
        duxComController.stopGame();
        setupStage(Stage::close);
    }

}