package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.shop.PlayerWallet;
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PopUpMenuControllerTestSetUp extends ApplicationTest {

    /* Controllers to be tested */
	protected HeroPopUpController controller;
	protected HeroPopUpUIController uiController;

    /* JavaFX nodes to be tested */
 /* Mockito mock objects */
    @Mock
    protected MapAssembly mapAssemblyMock;

    @Mock
    protected GameManager gameManagerMock;

    @Mock
    protected MapAssembly mapMock;

    @Mock
    protected HeroManager heroManagerMock;

    @Mock
    private DuxComController duxComControllerMock;

    protected List<AbstractHero> heros;
    protected AbstractHero currentHero;

    private OverlayMakerPopUp popUp;
    
    protected AnchorPane rootPane;
    protected FlowPane flowPane;

    protected Pane leftPane;
    protected Pane rightPane;
    protected Pane footPane;
    protected Button equippedButton;
    protected Button localChestButton;
    protected Button abilitiesButton;

    public PopUpMenuControllerTestSetUp() {
        heros = new ArrayList<AbstractHero>();
        heros.add(new Knight(0, 1));
        heros.add(new Archer(0, 2));
        heros.add(new Knight(0, 3));
        heros.add(new Knight(0, 4));
        currentHero = heros.get(0);

    }

    /**
     * Set up the TestFX environment.
     */
    public void start(Stage stage) throws Exception {
        /* Replace some of the calls for testing  */
        AnchorPane parentPane = new AnchorPane();
        new GameLoop(100, new AtomicBoolean(false), gameManagerMock);
        when(gameManagerMock.getController()).thenReturn(duxComControllerMock);
        when(duxComControllerMock.getGamePane()).thenReturn(parentPane);
        when(gameManagerMock.getMap()).thenReturn(mapMock);
        when(gameManagerMock.getMap().getHeroManager()).thenReturn(heroManagerMock);
        when(gameManagerMock.getMap().getHeroManager().getHeroList()).thenReturn(heros);
        when(gameManagerMock.getMap().getHeroManager().getCurrentHero()).thenReturn(currentHero);
        when(gameManagerMock.getGameWallet()).thenReturn(new PlayerWallet(500));

        /* Setup the logic controller */
        controller = new HeroPopUpController(gameManagerMock);
        popUp = OverlayMakerPopUp.makeWithGameManager(gameManagerMock.getController().getGamePane(), "/ui/fxml/HeroPopUp.fxml", gameManagerMock);

        /* Setup GUI */
        uiController = (HeroPopUpUIController) popUp.getController();
        stage.setTitle("DuxCom Hero Pop Up");
        stage.setScene(new Scene(gameManagerMock.getController().getGamePane()));
        stage.show();
        
        
    }
    
    /**
     * Initialises the JavaFX nodes before each test
     * @throws InterruptedException 
     */
    @Before
    public void setUp() throws IOException, InterruptedException {
    	// because javafx application has to run on javafx thread, always wrap it in platform.runlater
        Platform.runLater(() -> {
            uiController.show();
        });
        /* Semaphore used to wait for javafx thread to complete */
        waitForRunLater();
        
        rootPane = find("#rootPane");
        flowPane = find("#navigationButtons");
        leftPane = find("#left");
        rightPane = find("#right");
        footPane = find("#heroSelector");
        
        /* set up buttons */
        equippedButton = (Button) flowPane.getChildren().get(0);
        localChestButton = (Button) flowPane.getChildren().get(1);
        abilitiesButton = (Button) flowPane.getChildren().get(2);
    }

    /**
     * Closes JavaFx application
     *
     * @throws TimeoutException If closing stage times out
     */
    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
    

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be
     * updates
     *
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }
	
	@Test
	public void stubTest() {
		
	}
}
