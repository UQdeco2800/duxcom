package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.Menus;
import uq.deco2800.duxcom.inventory.Inventory;

/**
 * Logic controller for the local chest UI.
 *
 * @author dusksters
 */
public class LocalChestController extends ItemViewController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(LocalChestController.class);
    InventoryController inventoryController;
    HeroPopUpController parentController;
    LocalChestUIController currentUIController;


    public LocalChestController() {
        parentController = HeroPopUpController.getHeroPopUpController();
        inventoryController = (InventoryController) parentController.getController(Menus.INVENTORY);
    }

    /**
     * called when a user selects a slot in the chest inventory
     *
     * @param slotNum the slot number that was selected
     */
    @Override
    public void slotClicked(int slotNum) {
        if (selectedInventory == null) {
            return;
        }
        currentUIController.clearHighlighting();
        currentUIController.highlight(slotNum);
        stageItem(slotNum);
    }

    @Override
    public void updateControllers() {
        parentController.selectItem(stagedItem, Menus.CHEST);
        inventoryController.draw();
    }

    /**
     * Called when the take button is clicked. If the user has selected an item in the chest, "take"
     * will move that item to their inventory
     */
    public void takeClicked() {
        if (selectedInventory == null) {
            return;
        }
        if (stagedItemEmpty) {
            return;
        } else {
            selectedInventory.transferItem(inventoryController.getSelectedInventory(), stagedItem);
            unstageItem();
            inventoryController.draw();
            draw();
        }
    }

    /**
     * called when the user presses "take all". Moves as many items as it can from the chest to the
     * players inventory.
     */
    public void takeAllClicked() {
        if (selectedInventory == null) {
            return;
        }
        selectedInventory.transferAllItems(inventoryController.getSelectedInventory());
        inventoryController.draw();
        draw();
    }

    /**
     * stages null. Used whenever the currently staged item was removed/equipped /sold
     */
    public void unstageItem() {
        if (!stagedItemEmpty) {
            stagedItem = null;
            stagedItemSlotNum = 0;
            stagedItemEmpty = true;
            draw();
        }
    }

    /**
     * get the inventory this controller is using
     *
     * @return the chests inventory that this controller is currently using
     */
    public Inventory getSelectedInventory() {
        return selectedInventory;
    }

    /**
     * change the inventory this controller uses.
     *
     * @param selectedInventory the inventory that the selected hero is closest to.
     */
    public void setSelectedInventory(Inventory selectedInventory) {
        this.selectedInventory = selectedInventory;
        if (currentUIController != null) {
            currentUIController.draw();
        }
    }

    /**
     * This controller needs to calls functions from the equipped UI controller
     *
     * Note: this method is called by equipped UI controller once it is finished making itself
     *
     * @param localChestUIController the equipped UI controller.
     */
    public void setUIController(LocalChestUIController localChestUIController) {
        currentUIController = localChestUIController;
    }

    /**
     * clear all highlights
     */
    public void clearHighlights() {
        currentUIController.clearHighlighting();
    }

    /**
     * redraw the UI to match the current state of the inventory.
     */
    public void draw() {
        if (currentUIController != null) {
            currentUIController.draw();
        } else {
            logger.debug("UI cannot be drawn as it doesnt exist");
        }
    }

    /**
     * get the ui controller this controller is using
     *
     * @return the ui controller
     */
    public LocalChestUIController getUIController() {
        return currentUIController;
    }

}
