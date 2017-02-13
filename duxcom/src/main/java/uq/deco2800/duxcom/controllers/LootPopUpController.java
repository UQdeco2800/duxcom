package uq.deco2800.duxcom.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.PickableEntities;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.inventory.LootInventory;
import uq.deco2800.duxcom.items.Item;

/**
 * FXML Controller class
 *
 * @author The_Magic_Karps
 */
public class LootPopUpController extends OverlayMaker implements Initializable {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(LootPopUpController.class);
    /**
     * The loot the current hero can access
     */
    private List<PickableEntities> currentLootArea = new ArrayList<>();
    /**
     * The current turn hero
     */
    private AbstractHero currentHero;
    /**
     * Currently selected loot wallet
     */
    private List<LootInventory> selectedWallets = new ArrayList<>();
    /**
     * HashMap of dead entity with loot inventory
     */
    private HashMap<PickableEntities, List<Item>> selectedLootMap = new HashMap<>();
    /**
     * The Content of the scrollpane
     */
    private VBox lootBoxContent;
    /**
     * Boxes that are selected (used to prevent mistaking items with same
     * reference
     */
    private List<HBox> selectedBoxes = new ArrayList<>();
    /**
     * Warning message when inventory is full
     */
    @FXML
    private Label message;

    /**
     * The scrollpane containing the loots
     */
    @FXML
    private ScrollPane lootBox;
    /**
     * Buttons to pick up all items
     */
    @FXML
    private Button pickAllBtn;
    /**
     * Button to pick up selected items
     */
    @FXML
    private Button pickSelectedBtn;
    /**
     * Discard selected item button
     */
    @FXML
    private Button discardBtn;

    /**
     * Pick up all items in the current area
     */
    @FXML
    private void pickUpAll() {
        for (PickableEntities enemy : currentLootArea) {
            if (!gameManager.getMap().getLootManager().takeAllLoot(enemy)) {
                failureMessage();
                return;
            }
        }
        successMessage();
    }

    /**
     * Pick up all the selected items
     */
    @FXML
    public void takeSelectedItems() {
        for (PickableEntities entity : selectedLootMap.keySet()) {
            if (gameManager.getMap().getCurrentTurnHero().getInventory()
                    .getSpace() < selectedLootMap.get(entity).size()) {
                failureMessage();
                return;
            }
            for (Item item : selectedLootMap.get(entity)) {
                if (!gameManager.getMap().getLootManager().takeItemFromLoot(entity, item)) {
                    failureMessage();
                    return;
                }
            }
        }
        for (LootInventory lootInventory : selectedWallets) {
            gameManager.getMap().getLootManager().takeCoins(lootInventory);
        }
        successMessage();
    }

    /**
     * Discard all the selected items from loot inventory
     */
    @FXML
    private void discardSelectedItems() {
        for (PickableEntities entity : selectedLootMap.keySet()) {
            for (Item item : selectedLootMap.get(entity)) {
                gameManager.getMap().getLootManager().discardItem(entity, item);
            }
        }
        for (LootInventory lootInventory : selectedWallets) {
            gameManager.getMap().getLootManager().discardCoins(lootInventory);
        }
        refresh();
    }

    /**
     * Update current hero data with current turn hero
     */
    public void updateHero() {
        currentHero = gameManager.getMap().getCurrentTurnHero();
    }

    /**
     * Obtain a list of loots with the the area of the current hero
     */
    public void getLootInArea() {
        currentLootArea = gameManager.getMap().getLootManager()
                .getInvInLootArea(currentHero);
    }

    /**
     * Displays a success message when looting is successful
     */
    public void successMessage() {
        // Display loot success message 
        message.setText("Loot Successfully Added to Inventory!");
        message.setStyle("-fx-font-weight:bold;-fx-text-fill:darkgreen;");
        message.setVisible(true);
        HeroPopUpController.getHeroPopUpController().reDraw();
        refresh();
    }

    /**
     * Displays a unsuccess message when looting is unsuccessful
     */
    public void failureMessage() {
        // Display loot failure message
        message.setText("Please Check Your Inventory!");
        message.setStyle("-fx-font-weight:bold;-fx-text-fill:crimson;");
        message.setVisible(true);
        refresh();
    }

    /**
     * Updates the controller with newer data
     */
    @Override
    public void refresh() {
        updateHero();
        getLootInArea();
        selectedLootMap.clear();
        if (currentLootArea.isEmpty()) {
            hide();
        }
        drawLootsOnPopUp();
    }

    /**
     * Make overlay visible
     */
    @Override
    public void show() {
        if (!popUp.isActive()) {
            message.setVisible(false);
            refresh();
            popUp.showOverlay();
        } else {
            hide();
        }
    }

    /**
     * Draw the items onto the loot window interface
     */
    public void drawLootsOnPopUp() {
        lootBoxContent.getChildren().clear();
        for (PickableEntities enemyLoot : currentLootArea) {
            VBox content = new VBox();
            TitledPane enemySection = new TitledPane("LootBox", new Button());
            enemySection.prefWidthProperty().bind(lootBox.widthProperty());
            LootInventory lootInventory = gameManager.getMap().getLootManager()
                    .getLoot(enemyLoot);
            for (Item item : lootInventory.getItems().values()) {
                if (item != null) {
                    content.getChildren().add(drawItemBox(enemyLoot, item));
                }
            }
            if (lootInventory.getCoinBalance() > 0) {
                content.getChildren().add(drawCoinBox(lootInventory));
            }
            enemySection.setContent(content);
            lootBoxContent.getChildren().add(enemySection);
        }
    }

    /**
     * Draws the coin VBox to be displayed
     *
     * @param lootInventory where wallet resides
     * @return box to be drawn
     */
    private HBox drawCoinBox(LootInventory lootInventory) {
        HBox box = new HBox();
        box.setSpacing(10);
        box.getStyleClass().add("itemBox");
        Text text = new Text(String.valueOf(lootInventory.getCoinBalance())
                + " DollaryDoos");
        text.getStyleClass().add("lootText");
        box.getChildren().addAll(addCoinImage(lootInventory), text);
        box.setPrefWidth(lootBoxContent.widthProperty().doubleValue());
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler() {
            @Override
            public void handle(Event event) {
                toggleSelectedWallet(box, lootInventory);
            }
        });
        return box;
    }

    /**
     * Draws the item VBox to be displayed
     *
     * @param item item to be represent
     * @return box to be drawn
     */
    private HBox drawItemBox(PickableEntities entity, Item item) {
        HBox box = new HBox();
        box.setSpacing(15);
        box.getStyleClass().add("itemBox");
        Text text = new Text(item.getName());
        text.getStyleClass().add("lootText");
        box.getChildren().addAll(addItemImage(item), text);
        box.setPrefWidth(lootBoxContent.widthProperty().doubleValue());
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler() {
            @Override
            public void handle(Event event) {
                toggleSelectedItem(box, entity, item);
            }
        });
        return box;
    }

    /**
     * Adds the Item Images
     *
     * @param item item to retrieve its image
     * @return the imageview of the item image
     */
    private HBox addItemImage(Item item) {
        Image img;
        try {
            img = TextureRegister.getTexture(item.getInvetorySpriteName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            img = TextureRegister.getTexture("buff_placeholder");
        }
        return makeWrapper(img);
    }

    /**
     * Retrieve coin sprite from texture register
     *
     * @param lootInventory lootinventory to retrieve wallet amount
     * @return imageWrapper with the coin image
     */
    private HBox addCoinImage(LootInventory lootInventory) {
        Image img;
        if (lootInventory.getCoinBalance() > 350) {
            img = TextureRegister.getTexture("large_coin");
        } else if (lootInventory.getCoinBalance() > 250) {
            img = TextureRegister.getTexture("medium_coin");
        } else {
            img = TextureRegister.getTexture("small_coin");
        }
        return makeWrapper(img);
    }

    /**
     * Make a imageview pane wrapper given image
     *
     * @param img image to be added to wrapper
     * @return imgWrapper that contains the image
     */
    private HBox makeWrapper(Image img) {
        HBox imgWrapper = new HBox();
        imgWrapper.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        imgWrapper.setPrefWidth(30);
        imgWrapper.setPrefHeight(30);
        imgWrapper.setMaxWidth(30);
        imgWrapper.setMaxHeight(30);
        imgWrapper.getChildren().add(imageView);
        return imgWrapper;
    }

    /**
     * Toggle selected wallets
     *
     * @param box the box that is selected
     * @param lootInventory loot inventory where the wallet resides
     */
    private void toggleSelectedWallet(HBox box, LootInventory lootInventory) {
        if (selectedWallets.contains(lootInventory)) {
            // deselect
            selectedWallets.remove(lootInventory);
            // change border color
            box.setStyle("-fx-border-color:#2e8b57");

        } else {
            // select
            selectedWallets.add(lootInventory);
            // change border color
            box.setStyle("-fx-border-color:red");
        }
    }

    /**
     * Toggle selected items
     *
     * @param box the box that is selected
     * @param item the item that the box selected represents
     */
    private void toggleSelectedItem(HBox box, PickableEntities entity, Item item) {
        if (selectedLootMap.containsKey(entity)) {
            List<Item> selectedItem = selectedLootMap.get(entity);
            if (selectedItem.contains(item) && selectedBoxes.contains(box)) {
                selectedItem.remove(item);
                selectedBoxes.remove(box);
                box.setStyle("-fx-border-color:#2e8b57;");
                if (selectedItem.isEmpty()) {
                    selectedLootMap.remove(entity);
                }
            } else {
                selectedItem.add(item);
                selectedBoxes.add(box);
                box.setStyle("-fx-border-color:red;");
            }
        } else {
            List<Item> selectedItem = new ArrayList<>();
            selectedItem.add(item);
            selectedBoxes.add(box);
            selectedLootMap.put(entity, selectedItem);
            box.setStyle("-fx-border-color:red;");
        }
    }

    /**
     * Retrieves the selected lootHash
     *
     * @return selectedLootMap the selected hash of loots
     */
    public Map<PickableEntities, List<Item>> getSelectedLootMap() {
        return selectedLootMap;
    }

    /**
     * Retrieves the selected wallets
     *
     * @return the wallet selected
     */
    public List<LootInventory> getSelectedWallets() {
        return selectedWallets;
    }
    
    /**
     * Retrieves the selected Boxes
     * 
     * @return selectedBoxes array of boxes selected
     */
    public List<HBox> getSelectedBoxes() {
        return selectedBoxes;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lootBox.setFitToWidth(false);
        lootBox.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        lootBoxContent = new VBox();
        lootBox.setContent(lootBoxContent);
        message.setVisible(false);
    }

}
