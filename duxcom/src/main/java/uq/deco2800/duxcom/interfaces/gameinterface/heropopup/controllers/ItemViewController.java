package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.inventory.Inventory;
import uq.deco2800.duxcom.items.Item;

/**
 * Created by LeX on 22/10/2016.
 */
public abstract class ItemViewController extends SubMenuController {

    protected Item stagedItem;
    protected int stagedItemSlotNum;
    protected Inventory selectedInventory;
    protected boolean stagedItemEmpty;
    // Logger
    protected static Logger logger = LoggerFactory.getLogger(ItemViewController.class);

    public abstract void slotClicked(int slotNum);

    public abstract void updateControllers();

    /**
     * When an item is selected, it is "staged". This Item will now be used for methods like
     * "sellStagedItem" and "move"
     *
     * if the item in the slot is empty, null will be staged
     *
     * @param slotNum the slot number to be staged
     */
    public void stageItem(int slotNum) {
        stagedItemSlotNum = slotNum;
        logger.debug("Item is staged");
        if (!(selectedInventory.checkIfItemInSlot(slotNum))) {
            logger.debug("Item in the slot is empty");
            stagedItem = null;
        } else {
            logger.debug("Item in the slot was not empty");
            stagedItem = selectedInventory.getItemFromSlot(slotNum);
            stagedItemEmpty = false;
        }
        updateControllers();
        draw();
    }

}