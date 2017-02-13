package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.Menus;
import uq.deco2800.duxcom.inventory.HeroInventory;

import java.util.ArrayList;

/**
 * FXML controller for the Equipped UI menu.
 *
 * @author Ducksters
 */
public class EquippedUIController extends SubMenuUIController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(EquippedUIController.class);

    /*
     * Images for the Equipped UI.
     */
    @FXML
    private ImageView primaryImage;

    @FXML
    private ImageView secondaryImage;

    @FXML
    private ImageView armourImage;

    @FXML
    private ImageView shieldImage;

    @FXML
    private ImageView characterImage;

    /*
     * Panes that hold the images for the Equipped UI.
     */
    @FXML
    private Pane primaryPane;

    @FXML
    private Pane secondaryPane;

    @FXML
    private Pane armourPane;

    @FXML
    private Pane shieldPane;

    @FXML
    private Pane characterPane;

    /*
     * Buttons that may need enabling/disabling.
     */
    @FXML
    private Button equipButton;

    EquippedController controller;
    HeroPopUpController parentController;

    ArrayList<Pane> itemPanes = new ArrayList<Pane>();
    ArrayList<ImageView> imageViews = new ArrayList<ImageView>();

    /**
     * Constructor.
     *
     * Note: runs before any of the FXML elements are initialized.
     */
    public EquippedUIController() {
        logger.debug("Constructing...");
    }

    /**
     * Initializer.
     *
     * Note: runs after all of the FXML elements have been initialized.
     */
    @FXML
    public void initialize() {
        logger.debug("Initializing...");

    	/* Setup controllers */
        controller = (EquippedController)
                HeroPopUpController.getHeroPopUpController().getController(Menus.EQUIPPED);
        controller.setUIController(this);
        parentController = HeroPopUpController.getHeroPopUpController();

		/* Fill the itemPanes array */
        itemPanes.add(primaryPane);
        itemPanes.add(secondaryPane);
        itemPanes.add(armourPane);
        itemPanes.add(shieldPane);
        itemPanes.add(characterPane);

		/* Fill the imageViews array */
        imageViews.add(primaryImage);
        imageViews.add(secondaryImage);
        imageViews.add(armourImage);
        imageViews.add(shieldImage);
        imageViews.add(characterImage);
        clearHighlighting();
    }

    /**
     * method called when a slot is clicked
     */
    public void primaryClicked() {
        controller.primaryClicked();
    }

    /**
     * method called when a slot is clicked
     */
    public void secondaryClicked() {
        controller.secondaryClicked();
    }

    /**
     * method called when a slot is clicked
     */
    public void armourClicked() {
        controller.armourClicked();
    }

    /**
     * method called when a slot is clicked
     */
    public void shieldClicked() {
        controller.shieldClicked();
    }

    /**
     * method called when a slot is clicked
     */
    public void unequipClicked() {
        controller.unequipClicked();
    }

    /**
     * Removes the highlighting on all the elements in the inventory UI.
     */
    public void clearHighlighting() {
        primaryPane.setStyle("");
        secondaryPane.setStyle("");
        armourPane.setStyle("");
        shieldPane.setStyle("");
    }

    /**
     * updates the UI to reflect the current state of the Inventory/StagedItems
     */
    @Override
    public void draw() {
        HeroInventory currentInventory = controller.getSelectedInventory();
        if (currentInventory == null) {
            return;
        }
        /*
        Check if a slot has something in it, if it does, display that items graphic in the slot.
         */
        if (currentInventory.checkIfPrimaryWeaponEquipped()) {
            primaryImage.setImage(TextureRegister.getTexture(
                    currentInventory.getPrimaryWeapon().getInvetorySpriteName()));
        } else {
            primaryImage.setImage(TextureRegister.getTexture(
                    "empty"));
        }

        if (currentInventory.checkIfSecondaryWeaponEquipped()) {
            secondaryImage.setImage(TextureRegister.getTexture(
                    currentInventory.getSecondaryWeapon().getInvetorySpriteName()));
        } else {
            secondaryImage.setImage(TextureRegister.getTexture(EMPTY));
        }

        if (currentInventory.checkIfShieldEquipped()) {
            shieldImage.setImage(TextureRegister.getTexture(
                    currentInventory.getShield().getInvetorySpriteName()));
        } else {
            shieldImage.setImage(TextureRegister.getTexture(EMPTY));
        }

        if (currentInventory.checkIfArmourEquipped()) {
            armourImage.setImage(TextureRegister.getTexture(
                    currentInventory.getArmour().getInvetorySpriteName()));
        } else {
            armourImage.setImage(TextureRegister.getTexture(EMPTY));
        }

		/* Draw the hero */
        AbstractHero hero = HeroPopUpController.getHeroPopUpController().getSelectedHero();
        Image sprite;
        try {
            sprite = TextureRegister.getTexture(hero.getImageName());
        } catch (IllegalArgumentException e) {
            sprite = new Image("/heroes/duck.png");
            logger.error("This image does not exist so suck a duck", e);
        }

        characterImage.setImage(sprite);
    }

    /**
     * Applies a highlighted background to the given slot.
     *
     * @param slot is the slot type to highlight
     */
    public void highlight(EquippableSlot slot) {
        switch (slot) {
            case PRIMARY:
                primaryPane.setStyle(parentController.getHighlightColour());
                break;
            case SECONDARY:
                secondaryPane.setStyle(parentController.getHighlightColour());
                break;
            case ARMOUR:
                armourPane.setStyle(parentController.getHighlightColour());
                break;
            case SHIELD:
                shieldPane.setStyle(parentController.getHighlightColour());
                break;
            default:
                logger.debug("Invalid slot selection");
        }
    }
}
