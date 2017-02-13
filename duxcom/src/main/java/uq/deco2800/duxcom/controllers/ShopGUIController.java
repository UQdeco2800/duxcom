/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.shop.ShopManager;

/**
 *
 * @author user
 */
public class ShopGUIController extends OverlayMaker implements Initializable {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(ShopGUIController.class);
    // ShopManager to handle inventory and shelve
    private ShopManager manager; //model
    // Inventory of the player
    private HeroInventory inventory;
    // Items displayed on the shop GUI
    private HashMap<HBox, Item> itemDisplay;
    // Item selected by the user
    private Item selectedItem;

    // Button for purchasing selected item
    @FXML
    private Button buyButton;
    // Category ComboBox that displays the item types on the shelve
    @FXML
    private ComboBox<String> categoryBox;
    // The panel in which to display the shop GUI
    @FXML
    private FlowPane itemPane;
    // Label that displays the current money balance
    @FXML
    private Label playerWallet;

    /**
     * Starts the Overlay
     *
     * @param gameManager GameManager of the current game
     * @param overlayMaker handler used to create the overlay
     */
    @Override
    public void startOverlay(GameManager gameManager, OverlayMakerHandler overlayMaker) {
        super.startOverlay(gameManager, overlayMaker);
        this.manager = gameManager.getShopManager();
        this.inventory = gameManager.getMap().getCurrentTurnHero().getInventory();
        setCategory();
        drawBalance();
    }

    /**
     * EventListener for when the ComboBox is clicked
     *
     */
    @FXML
    private void getCategory() {
        selectedItem = null;
        buyButton.setDisable(true);
        itemPane.getChildren().clear();
        FlowPane.setMargin(itemPane, new Insets(5, 5, 5, 5));
        setItem(categoryBox.getValue().replaceAll(" ", "_"));
    }

    /**
     * Event listener for buy button
     */
    @FXML
    private void makePurchase() {
        logger.info(gameManager.getGameWallet().getBalance() + " money left");
        logger.info(selectedItem.getCost() + "");
        if (manager.buyItem(inventory, selectedItem)) {
            logger.info(selectedItem.getName() + " bought");
            logger.info(gameManager.getGameWallet().getBalance() + " money left");
        } else {
            logger.error("failed buying");
        }
        refresh();
    }
    
    /**
     * Hides the overlay on button press
     */
    @FXML
    private void closeOverlay() {
        show();
    }

    /**
     * Set and display items onto the scrollbar pane
     *
     * @param category the type of item selected
     */
    private void setItem(String category) {
        List<Item> items = manager.getShelves().getAllItemsByType(category);
        itemDisplay = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            HBox box = this.makeItemPane(items.get(i), i);
            HBox.setHgrow(box, Priority.ALWAYS);
            itemDisplay.put(box, items.get(i));
            itemPane.getChildren().add(box);
        }
    }

    /**
     * Retrieves the selected item the player clicked on
     *
     * @param event MouseEvent triggered when clicking on the box
     */
    private void getSelection(MouseEvent event) {
        buyButton.setDisable(false);
        HBox hbox = (HBox) event.getSource();
        Item item = itemDisplay.get(hbox);
        selectedItem = item;
        for (HBox boxId : itemDisplay.keySet()) {
            boxId.setStyle("-fx-border-color: #2e8b57;");
        }
        hbox.setStyle("-fx-border-color: red;");
    }

    /**
     * Makes an HBox pane for the item
     *
     * @param item the item to be put into the pane
     * @param index of the item in order
     * @return the HBox box with item description in it
     */
    private HBox makeItemPane(Item item, int index) {
        HBox box = new HBox();
        box.getStyleClass().add("box");
        box.setId("box" + index);
        box.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getSelection(event);
                event.consume();
            }
        });
        box.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                box.setCursor(Cursor.HAND);
                event.consume();
            }
        });
        Text text = new Text(item.getName()
                + System.getProperty("line.separator")
                + item.getCost() + " DollaryDoos"
        );
        text.setId("label");
        text.setWrappingWidth(110.0);
        text.getStyleClass().add("itemText");
        VBox imgWrapper;
        try {
            imgWrapper = setImages(item);
        } catch (Exception exception) {
            logger.error("Invalid image sprite", exception);
            imgWrapper = setDummyImage();
        }
        box.getChildren().addAll(text, imgWrapper);
        return box;
    }

    /**
     * Makes the image and add into box
     *
     * @return imgWrapper that contains the image
     */
    private VBox setImages(Item item) {
        VBox imgWrapper = new VBox();
        Image img = TextureRegister.getTexture(item.getInvetorySpriteName());
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);
        imgWrapperSetting(imgWrapper);
        imgWrapper.getChildren().add(imageView);
        return imgWrapper;
    }

    /**
     * Set dummy image
     *
     * @return imgWrapper that contains the image
     */
    private VBox setDummyImage() {
        VBox imgWrapper = new VBox();
        Image img = TextureRegister.getTexture("buff_placeholder");
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);
        imgWrapperSetting(imgWrapper);
        imgWrapper.getChildren().add(imageView);
        return imgWrapper;
    }

    /**
     * Setting of the image wrapper
     *
     * @param imgWrapper the image wrapper
     */
    private void imgWrapperSetting(VBox imgWrapper) {
        imgWrapper.setPrefWidth(40);
        imgWrapper.setPrefHeight(40);
        imgWrapper.setMaxWidth(40);
        imgWrapper.setMaxHeight(40);
        imgWrapper.setAlignment(Pos.CENTER);
    }

    /**
     * Create and set the category inside the ComboBox
     */
    public void setCategory() {
        for (String type : manager.getShelves().getShelfNames()) {
            categoryBox.getItems().add(type);
        }
    }

    /**
     * Initiation of the GUI
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buyButton.setDisable(true);

    }

    /**
     * Exist for Unit Testing
     */
    public void testCategory() {
        selectedItem = null;
        buyButton.setDisable(true);
        itemPane.getChildren().clear();
        FlowPane.setMargin(itemPane, new Insets(5, 5, 5, 5));
        setItem(categoryBox.getValue().replaceAll(" ", "_"));
    }

    /**
     * Redraws the UI
     */
    @Override
    public void refresh() {
        HeroPopUpController.getHeroPopUpController().reDraw();
        drawBalance();
    }

    /**
     * Override OverlayMaker show()
     */
    @Override
    public void show() {
        if (!popUp.isActive()) {
            popUp.showOverlay();
        } else {
            hide();
        }
        refresh();
    }

    /**
     * Draws the current balance
     */
    private void drawBalance() {
        playerWallet.setText(" " + gameManager.getGameWallet().getBalance());
        ImageView imageView = new ImageView(
                TextureRegister.getTexture("medium_coin"));
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(true);
        playerWallet.setGraphic(imageView);
    }
}
