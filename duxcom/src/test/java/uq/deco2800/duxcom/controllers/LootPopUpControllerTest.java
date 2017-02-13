package uq.deco2800.duxcom.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.DeathMagma;
import uq.deco2800.duxcom.entities.PickableEntities;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.inventory.LootInventory;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.loot.LootManager;
import uq.deco2800.duxcom.loot.LootRarity;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 *
 * @author The_Magic_Karps
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class LootPopUpControllerTest extends ApplicationTest {

    private OverlayMakerHandler popUp;

    private LootPopUpController controller;

    private AnchorPane parentPane;

    private Stage stage;

    private final List<PickableEntities> currentLootArea = new ArrayList<>();

    private Button pickAllBtn;

    private Button pickSelectedBtn;

    @Mock
    private MapAssembly mapAssemblyMock;

    @Mock
    private GameManager gameManagerMock;

    @Mock
    private LootManager lootManagerMock;

    @Mock
    private Archer currentTurnHeroMock;

    private ScrollPane lootBox;

    private Label message;

    private Button discardBtn;

    @Before
    public void setUp() {
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getCurrentTurnHero()).thenReturn(new Archer(5, 5));
        when(mapAssemblyMock.getLootManager()).thenReturn(lootManagerMock);
        when(lootManagerMock.getInvInLootArea(mapAssemblyMock
                .getCurrentTurnHero())).thenReturn(currentLootArea);
        lootBox = find("#lootBox");
        pickAllBtn = find("#pickAllBtn");
        pickSelectedBtn = find("#pickSelectedBtn");
        message = find("#message");
        discardBtn = find("#discardBtn");
    }

    @Test
    public void testNodesExist() {
        verifyThat("#lootBox", isNotNull());
        verifyThat("#pickAllBtn", isNotNull());
        verifyThat("#pickSelectedBtn", isNotNull());
        verifyThat("#message", isNotNull());
        verifyThat("#discardBtn", isNotNull());
    }

    @Test
    public void testStartShow() throws InterruptedException {
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        verify(mapAssemblyMock, times(2)).getCurrentTurnHero();
        verify(lootManagerMock, times(1)).getInvInLootArea(mapAssemblyMock
                .getCurrentTurnHero());
        assertTrue(lootBox.getContent() instanceof VBox);
        VBox lootBoxContent = (VBox) lootBox.getContent();
        assertTrue(lootBoxContent.getChildren().isEmpty());
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        assertFalse(parentPane.getChildren().contains(popUp.getOverlay()));
    }

    @Test
    public void testDraw() throws InterruptedException {
        PickableEntities deadEnemy = new DeathMagma(5, 6);
        LootInventory lootInventory = new LootInventory(5);
        lootInventory.addLoot(LootRarity.RARE);
        currentLootArea.add(deadEnemy);
        when(lootManagerMock.getLoot(deadEnemy)).thenReturn(lootInventory);
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        VBox lootBoxContent = (VBox) lootBox.getContent();
        assertFalse(lootBoxContent.getChildren().isEmpty());
        assertTrue(lootBoxContent.getChildren().get(0) instanceof TitledPane);
        TitledPane titledPane = (TitledPane) lootBoxContent.getChildren().get(0);
        assertTrue(titledPane.getContent() instanceof VBox);
        VBox content = (VBox) titledPane.getContent();
        assertFalse(content.getChildren().isEmpty());
    }

    @Test
    public void testPickUpAll() throws InterruptedException {
        PickableEntities deadEnemy = new DeathMagma(5, 6);
        LootInventory lootInventory = new LootInventory(5);
        lootInventory.addLoot(LootRarity.RARE);
        currentLootArea.add(deadEnemy);
        when(lootManagerMock.getLoot(deadEnemy)).thenReturn(lootInventory);
        Platform.runLater(() -> {
            controller.show();
            pickAllBtn.fire();
        });
        waitForRunLater();
        verify(lootManagerMock, times(1)).takeAllLoot(deadEnemy);
    }

    @Test
    public void testTakeSelectedItems() throws InterruptedException {
        PickableEntities deadEnemy = new DeathMagma(5, 6);
        LootInventory lootInventory = new LootInventory(5);
        lootInventory.addLoot(LootRarity.RARE);
        currentLootArea.add(deadEnemy);
        when(lootManagerMock.getLoot(deadEnemy)).thenReturn(lootInventory);
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        VBox lootBoxContent = (VBox) lootBox.getContent();
        TitledPane titledPane = (TitledPane) lootBoxContent.getChildren().get(0);
        VBox content = (VBox) titledPane.getContent();
        when(mapAssemblyMock.getCurrentTurnHero())
                .thenReturn(currentTurnHeroMock);
        when(currentTurnHeroMock.getInventory())
                .thenReturn(new HeroInventory(1));
        MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null);
        // Too Many Loot for inventory size
        List<Item> selectedItems = new ArrayList<>();
        selectedItems.add(lootInventory.getItemFromSlot(1));
        selectedItems.add(lootInventory.getItemFromSlot(2));
        controller.getSelectedLootMap().put(deadEnemy, selectedItems);
        assertFalse((controller.getSelectedLootMap().get(deadEnemy).isEmpty()));
        Item item = controller.getSelectedLootMap().get(deadEnemy).get(0);
        Platform.runLater(() -> {
            pickSelectedBtn.fire();
        });
        waitForRunLater();
        verify(lootManagerMock, times(0)).takeItemFromLoot(item);

        // Good
        when(lootManagerMock.takeItemFromLoot(any(), any())).thenReturn(true);
        controller.getSelectedWallets().add(lootInventory);
        Platform.runLater(() -> {
            Event.fireEvent(content.getChildren().get(0), clicked);
        });
        waitForRunLater();
        Platform.runLater(() -> {
            pickSelectedBtn.fire();
        });
        waitForRunLater();
        verify(lootManagerMock, times(1)).takeItemFromLoot(any(), any());
        verify(lootManagerMock, times(1)).takeCoins(any());
        assertEquals(message.getText(), "Loot Successfully Added to Inventory!");

        //full inv
        when(lootManagerMock.takeItemFromLoot(any(), any())).thenReturn(false);
        selectedItems = new ArrayList<>();
        selectedItems.add(lootInventory.getItemFromSlot(2));
        controller.getSelectedLootMap().put(deadEnemy, selectedItems);
        Platform.runLater(() -> {
            pickSelectedBtn.fire();
        });
        waitForRunLater();
        assertEquals(message.getText(), "Please Check Your Inventory!");
    }

    @Test
    public void testDiscardSelectedItems() throws InterruptedException {
        PickableEntities deadEnemy = new DeathMagma(5, 6);
        LootInventory lootInventory = new LootInventory(5);
        lootInventory.addLoot(LootRarity.RARE);
        currentLootArea.add(deadEnemy);
        when(lootManagerMock.getLoot(deadEnemy)).thenReturn(lootInventory);
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        VBox lootBoxContent = (VBox) lootBox.getContent();
        TitledPane titledPane = (TitledPane) lootBoxContent.getChildren().get(0);
        VBox content = (VBox) titledPane.getContent();
        when(mapAssemblyMock.getCurrentTurnHero())
                .thenReturn(currentTurnHeroMock);
        when(currentTurnHeroMock.getInventory())
                .thenReturn(new HeroInventory(1));
        MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null);

        
        List<Item> selectedItems = new ArrayList<>();
        selectedItems.add(lootInventory.getItemFromSlot(1));
        selectedItems.add(lootInventory.getItemFromSlot(2));
        controller.getSelectedLootMap().put(deadEnemy, selectedItems);
        controller.getSelectedWallets().add(lootInventory);
        Platform.runLater(() -> {
            discardBtn.fire();
        });
        waitForRunLater();
        verify(lootManagerMock, times(2)).discardItem(any(), any());
        verify(lootManagerMock, times(1)).discardCoins(any());
    }

    @Test
    public void testToggleSelect() throws InterruptedException {
        PickableEntities deadEnemy = new DeathMagma(5, 6);
        LootInventory lootInventory = new LootInventory(5);
        lootInventory.addLoot(LootRarity.RARE);
        currentLootArea.add(deadEnemy);
        when(lootManagerMock.getLoot(deadEnemy)).thenReturn(lootInventory);
        Platform.runLater(() -> {
            controller.show();
        });
        waitForRunLater();
        VBox lootBoxContent = (VBox) lootBox.getContent();
        TitledPane titledPane = (TitledPane) lootBoxContent.getChildren().get(0);
        VBox content = (VBox) titledPane.getContent();
        when(mapAssemblyMock.getCurrentTurnHero())
                .thenReturn(currentTurnHeroMock);
        MouseEvent clicked = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null);
        // select
        Platform.runLater(() -> {
            for (int i = 0; i < lootInventory.getItemCount() + 1; i++) {
                Event.fireEvent(content.getChildren().get(i), clicked);
            }
        });
        waitForRunLater();
        assertFalse(controller.getSelectedLootMap().isEmpty());
        assertFalse(controller.getSelectedWallets().isEmpty());
        assertFalse(controller.getSelectedBoxes().isEmpty());
        // deselect
        Platform.runLater(() -> {
            for (int i = 0; i < lootInventory.getItemCount() + 1; i++) {
                Event.fireEvent(content.getChildren().get(i), clicked);
            }
        });
        waitForRunLater();
        assertTrue(controller.getSelectedLootMap().isEmpty());
        assertTrue(controller.getSelectedWallets().isEmpty());
        assertTrue(controller.getSelectedBoxes().isEmpty());
    }

    @Override
    public void start(Stage stage) throws Exception {
        parentPane = new AnchorPane();

        popUp = OverlayMakerPopUp.makeWithGameManager(parentPane, "/ui/fxml/lootPopUp.fxml", gameManagerMock);

        controller = (LootPopUpController) popUp.getController();
        this.stage = stage;

        stage.setScene(new Scene(popUp.getOverlay(), 1280, 720));
        stage.show();
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
