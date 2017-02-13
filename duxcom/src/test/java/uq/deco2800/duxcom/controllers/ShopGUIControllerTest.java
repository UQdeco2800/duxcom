/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.shop.ShopManager;
import uq.deco2800.duxcom.shop.ShopShelve;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.setupStage;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.shop.PlayerWallet;

/**
 * @author TROLL
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ShopGUIControllerTest extends ApplicationTest {

    // Declares the JavFX nodes to be tested
    private ShopGUIController controller;
    private static Logger logger = LoggerFactory.getLogger(ShopGUIController.class);
    private Button buyButton;
    private ComboBox categoryBox;
    private FlowPane itemPane;
    private Button closeButton;
    private AnchorPane rootPane;
    private AnchorPane parentPane;
    private OverlayMakerPopUp popUp;
    private PlayerWallet wallet;

    // Sets up the mockito mocks
    @Mock
    private ShopManager shopManagerMock;
    @Mock
    private HeroInventory inventory;
    @Mock
    private HashMap<HBox, Item> itemDisplay;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private GameManager gameManagerMock;
    @Mock
    private Archer heroMock;

    /**
     * Sets up the TestFX environment
     */
    @Override
    public void start(Stage stage) throws Exception {
        when(gameManagerMock.getShopManager()).thenReturn(new ShopManager(gameManagerMock));
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(heroMock);
        when(heroMock.getInventory()).thenReturn(new HeroInventory(10));
        wallet = new PlayerWallet(500);
        when(gameManagerMock.getGameWallet()).thenReturn(wallet);
        parentPane = new AnchorPane();
        
        popUp = OverlayMakerPopUp.makeWithGameManager(
                parentPane, "/ui/fxml/ShopGUIFXML.fxml", gameManagerMock);
        controller = (ShopGUIController) popUp.getController();
        stage.setScene(new Scene(popUp.getOverlay()));
        stage.show();
    }

    /**
     * Initialises the JavaFX nodes before each test
     */
    @Before
    public void setUp() throws IOException {
        buyButton = find("#buyButton");
        categoryBox = find("#categoryBox");
        itemPane = find("#itemPane");
        rootPane = find("#rootPane");
        closeButton = find("#closeButton");
    }

    /**
     * Tests the buying of an item using TestFX robots
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testBuyingItem() throws InterruptedException {
        MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null);
        verifyThat(buyButton, (Button button) -> {
            return button.isDisable();
        });
        Platform.runLater(() -> {
            categoryBox.getSelectionModel().select(5);
            controller.testCategory();
        });
        waitForRunLater();
        verifyThat(categoryBox, (ComboBox box) -> {
            return box.getItems().contains(ItemType.SHIELD.name()
                    .replaceAll(" ", "_"));
        });
        verifyThat(itemPane, (FlowPane pane) -> {
            return !pane.getProperties().isEmpty();
        });
        Platform.runLater(() -> {
            Event.fireEvent(itemPane.getChildren().get(0), clicked);
        });
        waitForRunLater();
        verifyThat("#box1", (HBox itemBox) -> {
            return !itemBox.getProperties().isEmpty();
        });
        verifyThat(buyButton, (Button button) -> {
            return !button.isDisable();
        });
        int prevBalance = wallet.getBalance();
        when(shopManagerMock.buyItem(any(), any())).thenReturn(true);
        Platform.runLater(() -> {
            buyButton.fire();
        });
        waitForRunLater();
        assertNotEquals(prevBalance, wallet.getBalance());
        prevBalance = wallet.getBalance();
        // buy fail
        when(shopManagerMock.buyItem(any(), any())).thenReturn(false);
        Platform.runLater(() -> {
            buyButton.fire();
        });
        assertEquals(prevBalance, wallet.getBalance());
    }
    
    /**
     * Test showing
     */
    @Test
    public void testShow() throws InterruptedException {
        Platform.runLater(() -> {
           controller.show(); 
        });
        waitForRunLater();
        assertTrue(popUp.isActive());
        Platform.runLater(() -> {
           controller.show(); 
        });
        waitForRunLater();
        assertFalse(popUp.isActive());
        // close button
        Platform.runLater(() -> {
           controller.show(); 
        });
        waitForRunLater();
        assertTrue(popUp.isActive());
        Platform.runLater(() -> {
            closeButton.fire();
        });
        waitForRunLater();
        assertFalse(popUp.isActive());
    }

    /**
     * Tests the correct category is displayed
     */
    @Test
    public void checkCategory() {
        stub(shopManagerMock.getShelves()).toReturn(new ShopShelve());
        for (String type : shopManagerMock.getShelves().getShelfNames()) {
            assertTrue(categoryBox.getItems().contains(type
                    .replaceAll(" ", "_")));
        }
    }

    /**
     * Tests that all necessary JavaFX nodes exist
     */
    @Test
    public void testNodesExist() throws InterruptedException {
        verifyThat("#buyButton", isNotNull());
        verifyThat("#categoryBox", isNotNull());
        verifyThat("#itemPane", isNotNull());
        verifyThat("#rootPane", isNotNull());
        verifyThat("#closeButton", isNotNull());
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
}
