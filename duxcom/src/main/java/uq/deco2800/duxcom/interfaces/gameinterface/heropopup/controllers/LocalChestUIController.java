package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.inventory.Inventory;

/**
 * FXML controller for the local chest UI menu.
 *
 * @author Ducksters
 */
public class LocalChestUIController extends ItemViewUIController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(LocalChestUIController.class);

    /*
     * FXML item panes
     */
    @FXML
    private Pane item13Pane;

    @FXML
    private Pane item14Pane;

    @FXML
    private Pane item15Pane;

    @FXML
    private Pane item16Pane;

    /*
     * FXML item ImageViews
     */
    @FXML
    private ImageView itemImage13;

    @FXML
    private ImageView itemImage14;

    @FXML
    private ImageView itemImage15;

    @FXML
    private ImageView itemImage16;


    
    /*
     * Elements for the chest
     */

    @FXML
    private Pane chestPane;

    @FXML
    private ImageView chestImage;
    
    /*
     * Buttons
     */

    @FXML
    private Button takeButton;

    @FXML
    private Button takeAllButton;
    private final String CHEST = "chest";
    private LocalChestController localChestController;


    @Override
    void setupControllers() {
        parentController = HeroPopUpController.getHeroPopUpController();
        controller = parentController.getLocalChestController();
        localChestController = (LocalChestController) controller;
        localChestController.setUIController(this);

        /*
        Add the remaining panes/images
         */
        itemPanes.add(item13Pane);
        itemPanes.add(item14Pane);
        itemPanes.add(item15Pane);
        itemPanes.add(item16Pane);

        imageViews.add(itemImage13);
        imageViews.add(itemImage14);
        imageViews.add(itemImage15);
        imageViews.add(itemImage16);
        clearHighlighting();
    }

    /**
     * Update all the slots with the item in the chests inventory. Update the chest graphic.
     */
    @Override
    public void draw() {
        Inventory currentInventory = localChestController.getSelectedInventory();
        int i = 0;
        /* for each slot, if an item exists in that slot, update the sprite art */
        for (ImageView imageView : imageViews) {
            i++;
            if (imageView == null) {
                logger.debug("imageView is null!!!");
                continue;
            }
            if (currentInventory != null && currentInventory.getItemFromSlot(i) != null) {
                imageView.setImage(TextureRegister.getTexture(
                        currentInventory.getItemFromSlot(i).getInvetorySpriteName()));
            } else {
                imageView.setImage(TextureRegister.getTexture(EMPTY));
            }
        }
		/* If there is a chest being used, set the image to a chest. if not, make it empty	 */
        if (currentInventory == null) {
            chestImage.setImage(TextureRegister.getTexture(EMPTY));
        } else {
            chestImage.setImage(TextureRegister.getTexture(CHEST));
        }

    }

    /**
     * method called when the take button is clicked
     */
    public void takeClicked() {
        localChestController.takeClicked();
    }

    /**
     * method called with the takeAll button is clicked
     */
    public void takeAllClicked() {
        localChestController.takeAllClicked();
    }

}
