package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.AbilitiesController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.EquippedController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.InventoryController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.LocalChestController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.SubMenuController;
import uq.deco2800.duxcom.inventory.ChestManager;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.Item;

/**
 * Ticket #66 - HeroPopUp Menu
 *
 * FXML Controller class for overlay
 *
 * @author The_Magic_Karps & Ducksters
 */
public class HeroPopUpController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(HeroPopUpController.class);

    // Store the static instance of this object for access by the UI controllers
    private static HeroPopUpController currentInstance = null;

    // The child controllers
    private Map<Menus, SubMenuController> controllers = null;

    /* Instance of the game manager */
    private GameManager gameManager;

    /* Reference to the left menu */
    Menus currentLeftMenu = null;

    /* List to store order of the menus for when the buttons are added. */
    private ArrayList<Menus> menuList = new ArrayList<Menus>();

    private Item currentlySelectedItem;
    private boolean selectedItemEmpty;
    private Menus selectedItemMenu;
    /*
     * Map to store the relations between menus. This maps keys must be in the
     * array list
     */
    private Map<Menus, Menus> menuPair = new HashMap<Menus, Menus>();

    /* The hero list */
    private List<AbstractHero> heros = null;

    /* The index of the selected hero from the list of heros */
    private int selectedHeroIndex = 0;
    private String highlightColour;

    /**
     * Constructor.
     */
    public HeroPopUpController(GameManager gameManager) {

		/* report on this controller*/

		/* set the static accessor variable */
        HeroPopUpController.currentInstance = this;
		
		/* Store the game manager */
        this.gameManager = gameManager;

		/* highlight colour for selecting slots */
        highlightColour = "-fx-background-color: rgba(0, 0, 0, 0.5);";

		/* load the heros into the local variables */
        this.heros = this.gameManager.getMap().getHeroManager().getHeroList();

        /* Select the current hero */
        this.selectedHeroIndex =
                this.heros.indexOf(
                        this.gameManager.getMap().getHeroManager().getCurrentHero());

        // Create menus and relations
        addMenuPair(Menus.EQUIPPED, Menus.INVENTORY);
        addMenuPair(Menus.CHEST, Menus.INVENTORY);
        addMenuPair(Menus.ABILITIES, Menus.INVENTORY);

        // Add the logic controllers
        controllers = new HashMap<Menus, SubMenuController>();
        controllers.put(Menus.INVENTORY, new InventoryController());
        controllers.put(Menus.ABILITIES, new AbilitiesController());
        controllers.put(Menus.CHEST, new LocalChestController());
        controllers.put(Menus.EQUIPPED, new EquippedController());


		/* report on this controller*/
    }

    /**
     * Get the current instance of the HeroPopUpController.
     *
     * @return the current instance of the HeroPopUpController
     */
    public static HeroPopUpController getHeroPopUpController() {
        return currentInstance;
    }

    /**
     * Gets access to the logic controller within the HeroPupUpController that corresponds to the
     * given ENUM.
     *
     * @return the instance of the SubMenuController
     */
    public SubMenuController getController(Menus menu) {
        return this.controllers.get(menu);
    }


    /**
     * Get the left menu currently in the Hero Pop Up.
     *
     * @return the current left HeroPopUpSubMenu
     */
    public Menus getLeftMenu() {
        return this.currentLeftMenu;
    }

    /**
     * Set the left menu currently in the Hero Pop Up.
     *
     * @param left is the HeroPopUpSubMenu
     */
    public void setLeftMenu(Menus left) {
        this.currentLeftMenu = left;
    }

    /**
     * Get the right menu in the Hero Pop Up menu. This will be dependent on the left menu.
     *
     * @return the right menu in the Hero Pop Up menu
     */
    public Menus getRightMenu() {
        return this.menuPair.get(this.currentLeftMenu);
    }

    private void addMenuPair(Menus left, Menus right) {
        // Add to the list
        this.menuList.add(left);

        // Construct the relation for the right menu.
        this.menuPair.put(left, right);
    }

    /**
     * Gives a list of the primary menus to be used.
     *
     * @return an ordered array of HeroPopUpSubMenu which are to be accessible to the user.
     */
    public List<Menus> getLeftMenuList() {
        return this.menuList;
    }

    /**
     * Get the currently selected hero.
     */
    public AbstractHero getSelectedHero() {
        return this.selectedHeroIndex == -1 ? null : this.heros.get(this.selectedHeroIndex);
    }

    /**
     * Get the currently selected hero.
     */
    public List<AbstractHero> getHeros() {
        return this.heros;
    }

    /**
     * Go to the next hero in the list.
     */
    public void goToNextHero() {
    	/* Go to the next hero in the list */
        this.selectedHeroIndex++;
    	
    	/* When at the end of the list, go back to the beginning. */
        if (this.selectedHeroIndex == this.heros.size()) {
            this.selectedHeroIndex = 0;
        }

        heroChangeEvent();
    }

    /**
     * Go to the previous hero in the list.
     */
    public void goToPreviousHero() {
    	/* if at the beginning of the list, to go the end */
        if (this.selectedHeroIndex == 0) {
            this.selectedHeroIndex = this.heros.size() - 1;
        } else {
            this.selectedHeroIndex--;
        }

        heroChangeEvent();
    }

    /**
     * Get the index of the currently selected hero in the menu list.
     */
    public int getSelectedHeroIndex() {
        return this.selectedHeroIndex;
    }

    /**
     * Go directly to the hero at an index.
     *
     * @param index is the position in the heros array
     */
    public void gotoHeroAt(int index) {
        this.selectedHeroIndex = index;

        heroChangeEvent();
    }

    /**
     * This method is called when a hero is changed and when the menu is loaded.
     */
    public void heroChangeEvent() {
        logger.debug("Hero Change Event Occured.");
        updateControllersInventory();
    }

    /**
     * Updates all controllers with the currently selected hero's inventory
     */
    public void updateControllersInventory() {
        HeroInventory heroInventory = getSelectedHero().getInventory();
        logger.debug("Updating Current Inventory...");
        getInventoryController().changeCurrentInventory(heroInventory);
        getEquippedController().changeCurrentInventory(heroInventory);
        //Sets the local chest to whichever chest is closest to the current player
        updateLocalChest();
    }

    /**
     * updates the selected hero's local chest
     */
    public void updateLocalChest() {
        getLocalChestController().setSelectedInventory(ChestManager.getChestManager().
                getChestInvInHeroArea(getSelectedHero()));
    }

    /**
     * Clear all the UI references in the child controllers.
     */
    public void clearUIControllerReferences() {
        for (SubMenuController controller : controllers.values()) {
            controller.setUIController(null);
            logger.debug("removing reference: " + controller.toString());
        }
    }

    /**
     * This method is called everytime the menu changes
     */
    public void menuChangeEvent() {
        this.controllers.get(getLeftMenu()).draw();
        this.controllers.get(Menus.INVENTORY).draw();
    }

    /**
     * reDraws the inventory if it is open
     */
    public void reDraw() {
        SubMenuController left = controllers.get(getLeftMenu());
        SubMenuController right = controllers.get(getLeftMenu());
        if (left == null || right == null) {
            return;
        }
        if (getRightMenu() != Menus.INVENTORY) {
            return;
        }
        if (getInventoryController().getUIController() != null) {
            getInventoryController().draw();
        }
    }

    /**
     * Select the currently selected item. This method lets all the controllers know that a new item
     * has been selected. This stops multiple controllers from having items selected at the same
     * time
     *
     * @param item          The item that was just selected
     * @param menuSelecting the menu the item was selected in
     */
    public void selectItem(Item item, Menus menuSelecting) {
        selectedItemMenu = menuSelecting;
        currentlySelectedItem = item;
        selectedItemEmpty = currentlySelectedItem == null;

		/*
		If the selection occured in the inventory menu, deselect all the other menu's selections
		 */
        if (selectedItemMenu == Menus.INVENTORY) {
            if (getLeftMenu() == Menus.EQUIPPED) {
                getEquippedController().unStageitem();
                getEquippedController().clearHighlights();
            } else if (getLeftMenu() == Menus.CHEST) {
                getLocalChestController().clearHighlights();
                getLocalChestController().unstageItem();
            }
        }
		/*
		Otherwise, if the other menus selected an item, tell inventory that another menu is
		selecting an item
		 */
        else if (selectedItemMenu == Menus.EQUIPPED || selectedItemMenu == Menus.CHEST) {
            getInventoryController().unstageItem();
            getInventoryController().clearHighlights();
        }
    }

    /**
     * deselect any currently selected item
     */
    public void deSelectItem() {
        if (!selectedItemEmpty) {
            currentlySelectedItem = null;
            selectedItemEmpty = true;
            getInventoryController().unstageItem();
            getInventoryController().clearHighlights();
            if (getLeftMenu() == Menus.EQUIPPED) {
                getEquippedController().unStageitem();
                getEquippedController().clearHighlights();
            } else if (getLeftMenu() == Menus.CHEST) {
                getLocalChestController().unstageItem();
                getLocalChestController().clearHighlights();
            }
        }
    }

    /**
     * get the currently selected item
     *
     * @return item
     */
    public Item getSelectedItem() {
        return currentlySelectedItem;
    }

    /**
     * @return True if there is no item selected, false if there is an item selected
     */
    public boolean isSelectedItemEmpty() {
        return selectedItemEmpty;
    }

    /**
     * Used by all controllers to get the highlight colour. The highlight colour is set in the
     * constructor of this class
     */
    public String getHighlightColour() {
        return highlightColour;
    }

    /**
     * Get the inventory controller
     *
     * @return the inventory controller
     */
    public InventoryController getInventoryController() {
        return (InventoryController) getController(Menus.INVENTORY);
    }

    /**
     * get the equipped controller
     *
     * @return the equipped controller
     */
    public EquippedController getEquippedController() {
        return (EquippedController) getController(Menus.EQUIPPED);
    }

    /**
     * get the local chest controller
     *
     * @return the local chest controller
     */
    public LocalChestController getLocalChestController() {
        return (LocalChestController) getController(Menus.CHEST);
    }



    public void setSelectedHeroIndex(int index) {
        if (index > heros.size()) {
            this.selectedHeroIndex = heros.size() - 1;
        } else if (index < 0) {
            this.selectedHeroIndex = 0;
        } else {
            this.selectedHeroIndex = index;
        }
        heroChangeEvent();
    }
}
