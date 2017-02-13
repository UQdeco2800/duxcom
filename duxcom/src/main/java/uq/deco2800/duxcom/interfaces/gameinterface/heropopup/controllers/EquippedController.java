package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.Menus;
import uq.deco2800.duxcom.inventory.HeroInventory;

/**
 * Logic controller for the equipped UI.
 *
 * @author dusksters
 */
public class EquippedController extends SubMenuController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(EquippedController.class);

    /*
    Slots are in an arrayList in the UIController, this is the index order.
     */
    private static final int DESELECTED = -1;
    private static final int PRIMARYINDEX = 0;
    private static final int SECONDARYINDEX = 1;
    private static final int ARMOURINDEX = 2;
    private static final int SHIELDINDEX = 3;
    private static final int CHARACTERINDEX = 4;

    /*
     To tell the highlighting methods that this slot is being highlighted because it was selected
     not because it is about to have an item equipped to it.
      */
    private static final boolean NOTEQUIPPING = false;


    /*
    Toggles for whether a slot is ready to accept an item for equipping
	 */
    boolean primaryReadyForEquipping;
    boolean secondaryReadyForEquipping;
    boolean shieldReadyForEquipping;
    boolean armourReadyForEquipping;

    int selectedSlot = -1;
    HeroInventory selectedInventory;
    InventoryController inventoryController;
    HeroPopUpController parentController;
    EquippedUIController currentUIController;

    /**
     * Constructor
     */
    public EquippedController() {

        logger.debug("Constructing...");

        selectedInventory = null;
        parentController = HeroPopUpController.getHeroPopUpController();
        inventoryController = (InventoryController) parentController.getController(Menus.INVENTORY);
        primaryReadyForEquipping = false;
        secondaryReadyForEquipping = false;
        shieldReadyForEquipping = false;
        armourReadyForEquipping = false;
    }

    /**
     * changes the inventory to the supplied one
     *
     * @param inventory the inv to be changed to
     */
    public void changeCurrentInventory(HeroInventory inventory) {
        selectedInventory = inventory;
        draw();
    }

    /**
     * If the user is trying to equip an item to this slot, the item will be equipped. If the user
     * is trying to unequip an item in this slot, the slot will be selected. The user can then press
     * unequip to unequip the item.
     */
    public void primaryClicked() {
        currentUIController.clearHighlighting();
        highlightPrimary(NOTEQUIPPING);
        selectedSlot = PRIMARYINDEX;

        if (!selectedInventory.canWield(inventoryController.getStagedItem())) {
            if (GameLoop.getCurrentGameManager() != null
                    && GameLoop.getCurrentGameManager().getController() != null
                    && GameLoop.getCurrentGameManager().getController().getUIController() != null) {

                GameLoop.getCurrentGameManager().getController().getUIController().messageAbilityDialogue("INCOMPATIBLE WEAPON TYPE.");
            }
            return;
        }

        if (primaryReadyForEquipping) {
            int slotNum = inventoryController.getStagedItemSlotNum();
            if (selectedInventory.checkIfPrimaryWeaponEquipped()) {
                selectedInventory.removeItem(slotNum);
                selectedInventory.unEquipPrimaryWeapon(slotNum);
                selectedInventory.equipPrimaryWeapon(inventoryController.getStagedItem());
            } else {
                selectedInventory.equipPrimaryWeapon(inventoryController.getStagedItem(), slotNum);
            }
            parentController.deSelectItem();
        } else {
            parentController.selectItem(selectedInventory.getPrimaryWeapon(), Menus.EQUIPPED);
        }
        inventoryController.draw();
        draw();
        clearEquippingToggles();
    }

    /**
     * If the user is trying to equip an item to this slot, the item will be equipped. If the user
     * is trying to unequip an item in this slot, the slot will be selected. The user can then press
     * unequip to unequip the item.
     */
    public void secondaryClicked() {
        currentUIController.clearHighlighting();
        highlightSecondary(NOTEQUIPPING);
        selectedSlot = SECONDARYINDEX;

        if (secondaryReadyForEquipping) {
            int slotNum = inventoryController.getStagedItemSlotNum();
            if (selectedInventory.checkIfSecondaryWeaponEquipped()) {
                selectedInventory.removeItem(slotNum);
                selectedInventory.unEquipSecondaryWeapon(slotNum);
                selectedInventory.equipSecondaryWeapon(inventoryController.getStagedItem());
            } else {
                selectedInventory.equipSecondaryWeapon(inventoryController.getStagedItem(), slotNum);
            }
            parentController.deSelectItem();
        } else {
            parentController.selectItem(selectedInventory.getSecondaryWeapon(), Menus.EQUIPPED);
        }
        inventoryController.draw();
        draw();
        clearEquippingToggles();
    }

    /**
     * If the user is trying to equip an item to this slot, the item will be equipped. If the user
     * is trying to unequip an item in this slot, the slot will be selected. The user can then press
     * unequip to unequip the item.
     */
    public void shieldClicked() {
        currentUIController.clearHighlighting();
        highlightShield(NOTEQUIPPING);
        selectedSlot = SHIELDINDEX;

        if (shieldReadyForEquipping) {
            int slotNum = inventoryController.getStagedItemSlotNum();
            if (selectedInventory.checkIfShieldEquipped()) {
                selectedInventory.removeItem(slotNum);
                selectedInventory.unEquipShield(slotNum);
                selectedInventory.equipShield(inventoryController.getStagedItem());
            } else {
                selectedInventory.equipShield(inventoryController.getStagedItem(), slotNum);
            }
            parentController.deSelectItem();
        } else {
            parentController.selectItem(selectedInventory.getShield(), Menus.EQUIPPED);
        }
        inventoryController.draw();
        draw();
        clearEquippingToggles();
    }

    /**
     * If the user is trying to equip an item to this slot, the item will be equipped. If the user
     * is trying to unequip an item in this slot, the slot will be selected. The user can then press
     * unequip to unequip the item.
     */
    public void armourClicked() {
        currentUIController.clearHighlighting();
        highlightArmour(NOTEQUIPPING);
        selectedSlot = ARMOURINDEX;

        if (armourReadyForEquipping) {
            int slotNum = inventoryController.getStagedItemSlotNum();
            if (selectedInventory.checkIfArmourEquipped()) {
                selectedInventory.removeItem(slotNum);
                selectedInventory.unEquipArmor(slotNum);
                selectedInventory.equipArmor(inventoryController.getStagedItem());
            } else {
                selectedInventory.equipArmour(inventoryController.getStagedItem(), slotNum);
            }
            parentController.deSelectItem();
        } else {
            parentController.selectItem(selectedInventory.getArmour(), Menus.EQUIPPED);
        }
        inventoryController.draw();
        draw();
        clearEquippingToggles();
    }

    /**
     * If a slot is selected, and an item is in that slot, the item will be unequipped and returned
     * to the inventory. if there is no space in the inventory, nothing happens.
     */
    public void unequipClicked() {
        if (selectedSlot != DESELECTED) {
            switch (selectedSlot) {
                case PRIMARYINDEX:
                    selectedInventory.unEquipPrimaryWeapon();
                    break;
                case SECONDARYINDEX:
                    selectedInventory.unEquipSecondaryWeapon();
                    break;
                case SHIELDINDEX:
                    selectedInventory.unEquipShield();
                    break;
                case ARMOURINDEX:
                    selectedInventory.unEquipArmor();
                    break;
                default:
            }
            deSelectItem();
            parentController.deSelectItem();

            /* Redraw both menus and remove highlighting */
            draw();
            HeroPopUpController.getHeroPopUpController().getController(Menus.INVENTORY).draw();
            InventoryUIController inventoryControllerReference =
                    (InventoryUIController) (HeroPopUpController.getHeroPopUpController()
                            .getController(Menus.INVENTORY).getUIController());
            inventoryControllerReference.clearHighlighting();
            getUIController().clearHighlighting();
        }
    }

    /**
     * Updates the UI to reflect the state of the inventory
     */
    public void draw() {
        if (currentUIController != null) {
            currentUIController.draw();
        } else {
            logger.debug("UI cannot be drawn as it doesnt exist");
        }
    }

    /**
     * deselects whatever item
     */
    public void deSelectItem() {
        selectedSlot = DESELECTED;
    }

    /**
     * clears all highlights on slots
     */
    public void clearHighlights() {
        currentUIController.clearHighlighting();
    }

    /**
     * highlights the primary slot
     *
     * @param inventoryEquipping If this slot was highlighted because the user is about to equip a
     *                           weapon to it. This parameter will be TRUE, otherwise, its FALSE
     */
    public void highlightPrimary(boolean inventoryEquipping) {
        currentUIController.highlight(EquippableSlot.PRIMARY);
        if (inventoryEquipping) {
            primaryReadyForEquipping = true;
        }
    }

    /**
     * highlights the secondary slot
     *
     * @param inventoryEquipping If this slot was highlighted because the user is about to equip a
     *                           weapon to it. This parameter will be TRUE, otherwise, its FALSE
     */
    public void highlightSecondary(boolean inventoryEquipping) {
        currentUIController.highlight(EquippableSlot.SECONDARY);
        if (inventoryEquipping) {
            secondaryReadyForEquipping = true;
        }

    }

    /**
     * highlights the armour slot
     *
     * @param inventoryEquipping If this slot was highlighted because the user is about to equip a
     *                           weapon to it. This parameter will be TRUE, otherwise, its FALSE
     */
    public void highlightArmour(boolean inventoryEquipping) {
        currentUIController.highlight(EquippableSlot.ARMOUR);
        if (inventoryEquipping) {
            armourReadyForEquipping = true;
        }
    }

    /**
     * highlights the shield slot
     *
     * @param inventoryEquipping If this slot was highlighted because the user is about to equip a
     *                           weapon to it. This parameter will be TRUE, otherwise, its FALSE
     */
    public void highlightShield(boolean inventoryEquipping) {
        currentUIController.highlight(EquippableSlot.SHIELD);
        if (inventoryEquipping) {
            shieldReadyForEquipping = true;
        }
    }

    /**
     * this method tells the controller that nothing is trying to be equipped right now
     */
    private void clearEquippingToggles() {

        // If the user was trying to equip something but then cancelled, clear the highlights.
        if (primaryReadyForEquipping || secondaryReadyForEquipping || shieldReadyForEquipping
                || armourReadyForEquipping) {
            clearHighlights();
        }
        primaryReadyForEquipping = false;
        secondaryReadyForEquipping = false;
        shieldReadyForEquipping = false;
        armourReadyForEquipping = false;
        inventoryController.setIsEquipSelected(false);
    }

    /**
     * get the inventory this controller is accessing
     *
     * @return the inventory
     */
    public HeroInventory getSelectedInventory() {
        return selectedInventory;
    }

    /**
     * This controller needs to calls functions from the equipped UI controller
     *
     * Note: this method is called by equipped UI controller once it is finished making itself
     *
     * @param equippedUIController the equipped UI controller.
     */
    public void setUIController(EquippedUIController equippedUIController) {
        currentUIController = equippedUIController;
    }

    /**
     * Deselect whatever item is currently selected. also, clear the equipping toggles
     */
    public void unStageitem() {
        deSelectItem();
        clearEquippingToggles();
    }

    /**
     * get the ui controller this controller is using
     *
     * @return the ui controller
     */
    public EquippedUIController getUIController() {
        return currentUIController;
    }

}
