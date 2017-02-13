package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.Menus;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.armour.Armour;
import uq.deco2800.duxcom.items.shield.Shield;
import uq.deco2800.duxcom.items.weapon.Weapon;

/**
 * Logic controller for the inventory UI.
 *
 * @author dusksters
 */
public class InventoryController extends ItemViewController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private boolean isMoveSelected;
    private boolean isEquipSelected;


    HeroPopUpController parentController;
    private InventoryUIController currentUIController;

    public InventoryController() {


        isMoveSelected = false;
        stagedItemEmpty = false;
        isEquipSelected = false;
        stagedItem = null;
        stagedItemSlotNum = 0;
        selectedInventory = null;
        parentController = HeroPopUpController.getHeroPopUpController();
    }

    /**
     * Changes the inventory to be used for this controller
     *
     * @param inventory the inventory to be used
     */
    public void changeCurrentInventory(HeroInventory inventory) {
        selectedInventory = inventory;
        draw();
    }

    /**
     * Tells the controller that the shop is on the LHS
     */
    public void selectShopMenu() {
        deselctAll();
    }

    /**
     * Tells the controller that equip is on the LHS
     */
    public void selectEquippedMenu() {
        deselctAll();
    }

    /**
     * Tells the controller that nothing is on the LHS
     */
    private void deselctAll() {
        // going to fix this
    }


    /**
     * Updates every slot's image / background / text on the UI
     */
    @Override
    public void draw() {
        if (currentUIController != null) {
            currentUIController.draw();
        } else {
            logger.debug("UI cannot be drawn as it doesnt exist");
        }
    }

    /**
     * Called whenever a slot in the inventory is clicked
     *
     * @param slotNum the slotNum that was clicked
     */
    @Override
    public void slotClicked(int slotNum) {
        currentUIController.clearHighlighting();
        currentUIController.highlight(slotNum);

		/*
        If the move button was clicked, the user is trying to move the currently selected (staged)
		item to that slot
		 */
        if (isMoveSelected) {
            move(slotNum);
            stageItem(slotNum);
            isMoveSelected = false;
            return;
        }
        /*
		Otherwise, the user is trying to select the item in the slot. Stage this Item so the
		controller knows which item is selected
		 */
        else {
            stageItem(slotNum);
        }
    }

    /**
     * Called when the move button is clicked in the GUI
     */
    public void moveAction() {
        //toggle isMoveSelected
        isMoveSelected = !isMoveSelected;
        //If there is nothing to move, cancel the action
        if (stagedItemEmpty) {
            isMoveSelected = false;
        }
        if (isMoveSelected) {
            //Highlight where the user can move the item to (all slots)
            currentUIController.highlightAll();
        } else {
            //if the user cancelled move , clear the highlights.
            currentUIController.clearHighlighting();
        }
    }

    @Override
    public void updateControllers() {
        parentController.selectItem(stagedItem, Menus.INVENTORY);
    }

    /**
     * moves staged item to the slotnum specified. If there is an item in that slot, it will go to
     * where the original staged item came from.
     *
     * @param slotNum the slotNum for the staged item to move to
     */
    private void move(int slotNum) {
        selectedInventory.moveItem(stagedItemSlotNum, slotNum);
        draw();
    }

    /**
     * Called when there is a chest inventory on the LHS of HPU. Deposits the currently selected
     * item into the chest inventory. If there is no item to deposit, do nothing.
     */
    public void depositStagedItem() {
        if (stagedItemEmpty) {
            parentController.deSelectItem();
        } else {
            selectedInventory.transferItem(
                    parentController.getLocalChestController().getSelectedInventory(), stagedItem);
            parentController.getLocalChestController().draw();
            parentController.deSelectItem();
        }
    }

    /**
     * when equip is pressed, the currently staged items type will be determined. If it can be
     * equipped, then the slots in the equip UI which it can go into wil be highlighted. If it cant
     * be equipped, nothing happens
     */
    public void equipStagedItem() {
        EquippedController equippedController =
                (EquippedController) parentController.getController(Menus.EQUIPPED);
        // If equip was already pressed, the user is trying to stop equipping.
        if (isEquipSelected) {
            logger.debug("Equipped was already pressed!");
            isEquipSelected = false;
            equippedController.clearHighlights();
            EquippedUIController uiController =
                    (EquippedUIController) HeroPopUpController.getHeroPopUpController().getController(Menus.EQUIPPED).getUIController();
            uiController.clearHighlighting();
            return;
        }
        //If the item is a weapon, it can be equipped to the primary/secondary slots
        if (stagedItem instanceof Weapon) {
            equippedController.clearHighlights();
            Weapon stagedWeapon = (Weapon) stagedItem;
            if (stagedWeapon.canBePrimary()) {
                equippedController.highlightPrimary(true);
            }
            if (stagedWeapon.canBeSecondary()) {
                equippedController.highlightSecondary(true);
            }
        }
        // If it is armour, it can only be equipped to the armour slot
        else if (stagedItem instanceof Armour) {
            equippedController.highlightArmour(true);
        }
        // If it is a shield, it can only be equipped to the shield slot
        else if (stagedItem instanceof Shield) {
            equippedController.highlightShield(true);
        }
        // lets the controller know that the user is trying to equip something.
        isEquipSelected = true;
    }

    /**
     * sells the currently staged item and adds the money to the players wallet
     */
    public void sellStagedItem() {
        if (stagedItemEmpty) {
            return;
        }
        unstageItem();
    }

    /**
     * stages null. Used whenever the currently staged item was removed/equipped /sold
     */
    public void unstageItem() {
        if (!stagedItemEmpty) {
            stagedItem = null;
            stagedItemSlotNum = 0;
            stagedItemEmpty = true;
            isMoveSelected = false;
            draw();
        }
    }

    /**
     * @return true if the equip menu is on the LHS, false if otherwise
     */
    public boolean isEquippedMenuSelected() {
        return HeroPopUpController.getHeroPopUpController().getLeftMenu() == Menus.EQUIPPED;
    }

    /**
     * get the currently selected item
     *
     * @return staged item
     */
    public Item getStagedItem() {
        return stagedItem;
    }

    /**
     * get the slot in the inventory where the currently selected item is being held
     */
    public int getStagedItemSlotNum() {
        return stagedItemSlotNum;
    }

    /**
     * @return true if the user press equip, false if not
     */
    public boolean isEquipSelected() {
        return isEquipSelected;
    }

    /**
     * Once an item has been equipped, this method lets the controller know that the user is no
     * longer trying to equip
     *
     * @param toggle Should always be false
     */
    public void setIsEquipSelected(boolean toggle) {
        isEquipSelected = toggle;
    }

    /**
     * get the inventory that this controller is currently using
     *
     * @return the currently selected hero's inventory
     */
    public HeroInventory getSelectedInventory() {
        return (HeroInventory) selectedInventory;
    }

    /**
     * @return true if nothing is selected, false if an item is selected
     */
    public boolean isStagedItemEmpty() {
        return stagedItemEmpty;
    }

    /**
     * This controller needs to calls functions from the inventory UI controller
     *
     * Note: this method is called by Inventory UI controller once it is finished making itself
     *
     * @param inventoryUIController the Inventory UI controller.
     */
    public void setUIController(InventoryUIController inventoryUIController) {
        currentUIController = inventoryUIController;
    }

    /**
     * clears all highlights on all slots
     */
    public void clearHighlights() {
        currentUIController.clearHighlighting();
    }

    /**
     * get the inventory ui controller this controller is using
     *
     * @return the inventory ui controller
     */
    public InventoryUIController getUIController() {
        return currentUIController;
    }
}
