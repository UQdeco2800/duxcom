package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;

import java.util.ArrayList;

/**
 * Created by LeX on 22/10/2016.
 */
public abstract class ItemViewUIController extends SubMenuUIController {
    /*
     * Below are the FXML element in the 'inventory' UI.
	 */

    // Logger
    protected static Logger logger = LoggerFactory.getLogger(ItemViewUIController.class);


    /* Button for the user to move items in the inventory */
    @FXML
    private Button moveButton;

    /*
     * A custom action button to have the inventory interact with the left
     * menu. The buttons text and action change depending on with menu is
     * on the left.
     */
    @FXML
    protected Button customButton;

    /* Shows the selected item of with will have an action done on it */
    @FXML
    protected ImageView selectedItemImage;

    /*
     * An information box to give a brief description of the item.
     */
    @FXML
    protected Text itemTextInfo;

	/*
	 * The following UI element are the individual ImaveView element that form
	 * the 3 by 4 grid in the inventory UI.
	 */

    @FXML
    private ImageView itemImage1;

    @FXML
    private ImageView itemImage2;

    @FXML
    private ImageView itemImage3;

    @FXML
    private ImageView itemImage4;

    @FXML
    private ImageView itemImage5;

    @FXML
    private ImageView itemImage6;

    @FXML
    private ImageView itemImage7;

    @FXML
    private ImageView itemImage8;

    @FXML
    private ImageView itemImage9;

    @FXML
    private ImageView itemImage10;

    @FXML
    private ImageView itemImage11;

    @FXML
    private ImageView itemImage12;

    /*
     * The UI elements below are the Panes the hold the respective image view.
     */
    @FXML
    private Pane item1Pane;

    @FXML
    private Pane item2Pane;

    @FXML
    private Pane item3Pane;

    @FXML
    private Pane item4Pane;

    @FXML
    private Pane item5Pane;

    @FXML
    private Pane item6Pane;

    @FXML
    private Pane item7Pane;

    @FXML
    private Pane item8Pane;

    @FXML
    private Pane item9Pane;

    @FXML
    private Pane item10Pane;

    @FXML
    private Pane item11Pane;

    @FXML
    private Pane item12Pane;

    protected ItemViewController controller;
    protected HeroPopUpController parentController;

    /*
     * For easy management of the item Panes.
     */
    protected ArrayList<Pane> itemPanes = new ArrayList<Pane>();
    protected ArrayList<ImageView> imageViews = new ArrayList<ImageView>();

    /**
     * Constructor.
     *
     * Note: runs before any of the FXML elements are initialized.
     */
    public ItemViewUIController() {
        // Constructor
    }

    /**
     * Initializer.
     *
     * Note: runs after all of the FXML elements have been initialized.
     */
    @FXML
    public void initialize() {
        logger.debug("Initializing...");



		/* Fill the itemPanes array */
        itemPanes.add(item1Pane);
        itemPanes.add(item2Pane);
        itemPanes.add(item3Pane);
        itemPanes.add(item4Pane);
        itemPanes.add(item5Pane);
        itemPanes.add(item6Pane);
        itemPanes.add(item7Pane);
        itemPanes.add(item8Pane);
        itemPanes.add(item9Pane);
        itemPanes.add(item10Pane);
        itemPanes.add(item11Pane);
        itemPanes.add(item12Pane);

		/* Fill the imageViews array */
        imageViews.add(itemImage1);
        imageViews.add(itemImage2);
        imageViews.add(itemImage3);
        imageViews.add(itemImage4);
        imageViews.add(itemImage5);
        imageViews.add(itemImage6);
        imageViews.add(itemImage7);
        imageViews.add(itemImage8);
        imageViews.add(itemImage9);
        imageViews.add(itemImage10);
        imageViews.add(itemImage11);
        imageViews.add(itemImage12);
            	/* Setup controllers */
        setupControllers();

    }

    abstract void setupControllers();

    public void slot1Clicked() {
        controller.slotClicked(1);
    }

    public void slot2Clicked() {
        controller.slotClicked(2);
    }

    public void slot3Clicked() {
        controller.slotClicked(3);
    }

    public void slot4Clicked() {
        controller.slotClicked(4);
    }

    public void slot5Clicked() {
        controller.slotClicked(5);
    }

    public void slot6Clicked() {
        controller.slotClicked(6);
    }

    public void slot7Clicked() {
        controller.slotClicked(7);
    }

    public void slot8Clicked() {
        controller.slotClicked(8);
    }

    public void slot9Clicked() {
        controller.slotClicked(9);
    }

    public void slot10Clicked() {
        controller.slotClicked(10);
    }

    public void slot11Clicked() {
        controller.slotClicked(11);
    }

    public void slot12Clicked() {
        controller.slotClicked(12);
    }

    public void slot13Clicked() {
        controller.slotClicked(13);
    }

    public void slot14Clicked() {
        controller.slotClicked(14);
    }

    public void slot15Clicked() {
        controller.slotClicked(15);
    }

    public void slot16Clicked() {
        controller.slotClicked(16);
    }


    /**
     * Removes the highlighting on all the elements in the inventory UI.
     */
    public void clearHighlighting() {
        for (Pane itemPane : itemPanes) {
            if (itemPane == null) {
                logger.debug("Pane is null !!!");
            } else {
                itemPane.setStyle("");
            }
        }
    }

    /**
     * Applies a highlighted background to the given slot. Note: Slot number starts at 1 and goes up
     * to 12.
     *
     * @param slotNumber it the slot number to highlight
     */
    public void highlight(int slotNumber) {
        logger.debug("Highlighting slot");
        if (itemPanes.get(slotNumber - 1) == null) {
            logger.debug("Item Pane Is NULL!!!");
        } else {
            itemPanes.get(slotNumber - 1).setStyle(parentController.getHighlightColour());
        }
    }

    /**
     * highlight all slots in the inventory
     */
    public void highlightAll() {
        for (Pane itemPane : itemPanes) {
            if (itemPane == null) {
                logger.debug("Pane is null !!!");
            } else {
                itemPane.setStyle(parentController.getHighlightColour());
            }
        }
    }
}