package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.Menus;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.Item;

/**
 * FXML controller for the Inventory UI menu.
 *
 * @author Ducksters
 */
public class InventoryUIController extends ItemViewUIController {

	/*
     * Below are the FXML element in the 'inventory' UI.
	 */

    // Logger
    private static Logger logger = LoggerFactory.getLogger(InventoryUIController.class);

    /* Button for the user to move items in the inventory */
    @FXML
    private Button moveButton;

    /*
     * A custom action button to have the inventory interact with the left
     * menu. The buttons text and action change depending on with menu is
     * on the left.
     */
    @FXML
    private Button customButton;

    /* Shows the selected item of with will have an action done on it */
    @FXML
    private ImageView selectedItemImage;

    /*
     * An information box to give a brief description of the item.
     */
    @FXML
    private Text itemTextInfo;


    // Rainbow text!
    final String STYLEOFDESCRIPTION = "-fx-fill: linear-gradient(from 50% 0% to 50% 100%, repeat, " +
            " black 0%, black 29%, darkBlue 30%, darkGreen 63%, darkRed 99%, black);";

    private InventoryController inventoryController;

    public InventoryUIController() {
        logger = LoggerFactory.getLogger(InventoryUIController.class);
        logger.debug("Constructing...");
    }

    @Override
    void setupControllers() {
        controller = (ItemViewController) HeroPopUpController.getHeroPopUpController().getController(Menus.INVENTORY);
        inventoryController = (InventoryController) controller;
        inventoryController.setUIController(this);
        parentController = HeroPopUpController.getHeroPopUpController();

        /*
        Set the style of the text box. Wrap at 175 pixel width, make text rainbow.
         */
        itemTextInfo.setWrappingWidth(175);
        Color darkRed = Color.rgb(100, 0, 0);
        Color darkGreen = Color.rgb(0, 100, 0);
        Color darkBlue = Color.rgb(0, 0, 100);
        itemTextInfo.setStyle(STYLEOFDESCRIPTION);
        clearHighlighting();
    }

    /*
         * Handles the event of the user clicking the 'Move' button in the inventory
         * UI.
         *
         * How this button should be used:
         *  1. User selects item
         *  2. Item is highlighted.
         *  3. User clicks the 'Move' Button to indicate they want to move the item.
         *  4. User clicks an item slot and the previously selected item is moved to
         *     that slot. If there is an item already in the slot then the item
         *     position is swapped.
         */
    public void moveAction() {
        clearHighlighting();
        inventoryController.moveAction();
    }

    /**
     * this action changes depending on what is on the LHS
     */
    public void customAction() {
        clearHighlighting();
        if (inventoryController.isEquippedMenuSelected()) {
            inventoryController.equipStagedItem();
        } else if (parentController.getLeftMenu() == Menus.CHEST) {
            inventoryController.depositStagedItem();
        }
    }


    /**
     * Fills in all the image slots with their item art (or empty if there is no item in that slot)
     * Adds the Staged Item image and its description to the top above the slots Also, rewrites the
     * text on the custom button
     */
    @Override
    public void draw() {
        HeroInventory currentInventory = inventoryController.getSelectedInventory();
        Boolean stagedItemEmpty = parentController.isSelectedItemEmpty();
        if (currentInventory == null) {
            return;
        }
        int i = 0;

		/*
        Updates all sprite art in the inventory to reflect the current state of the heroInventory
        eg. if a sword is in slot1 in the inventory, the sprite art for it should be in slot1 in the
        UI
    	*/
        for (ImageView imageView : imageViews) {
            i++;
            if (imageView == null) {
                logger.debug("imageView is null!!!");
                continue;
            }
            if (currentInventory.getItemFromSlot(i) != null) {
                try {
                    imageView.setImage(TextureRegister.getTexture(
                            currentInventory.getItemFromSlot(i).getInvetorySpriteName()));
                } catch (Exception e) {

                }
            } else {
                imageView.setImage(TextureRegister.getTexture(EMPTY));
            }
        }
		/*
		Adds the image/description of the currently selected item
		 */
        Item selectedItem = parentController.getSelectedItem();
        if (!stagedItemEmpty && selectedItem != null) {
            selectedItemImage.setImage(TextureRegister.getTexture(
                    selectedItem.getInvetorySpriteName()));
            itemTextInfo.setText(selectedItem.getName() + "\n" + selectedItem.getDescription() +
                    "\n \n    ---STATS---\n" + selectedItem.getStatsString());
        } else {
            selectedItemImage.setImage(TextureRegister.getTexture(EMPTY));
            itemTextInfo.setText("");
        }

		/* Change the button text */
        String customActoinText = null;
        switch (HeroPopUpController.getHeroPopUpController().getLeftMenu()) {
            case EQUIPPED:
                customActoinText = "Equip";
                break;
            case CHEST:
                customActoinText = "Deposit";
                break;
            case ABILITIES:
                break;
            case INVENTORY:
                break;
            default:
                break;
        }
        customButton.setText(customActoinText);
    }

}
