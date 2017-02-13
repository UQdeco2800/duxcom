package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import uq.deco2800.duxcom.graphics.TextureRegister;

/**
 * Ticket #66 - HeroPopUp Menu
 *
 * FXML Controller class for overlay
 *
 * @author The_Magic_Karps & Ducksters
 */
public class HeroPopUpUIController extends OverlayMaker {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(HeroPopUpUIController.class);

    // Top Navigation Bar pane
    @FXML
    protected Pane navBar;

    // Footer Pane
    @FXML
    protected Pane footBar;

    // Content Pane that holds the different primary sections
    @FXML
    protected Pane left;

    // Content Pane that holds the corresponding section depending on the left section. 
    @FXML
    protected Pane right;

    // Bar for buttons
    @FXML
    private FlowPane navigationButtons;

    // HBox that displays the money
    @FXML
    private HBox moneyBox;

    // Bar to hold all the heros
    @FXML
    private FlowPane heroSelector;

    /* Hero Panes */
    static List<Pane> heroPanes = new ArrayList<Pane>();

    /* Hero Images */
    List<ImageView> heroImages = new ArrayList<ImageView>();


    /**
     * Initializes the controller class.
     */
    public HeroPopUpUIController() {
        // Any code here will run before the FXML library uses the class.
    }

    /**
     * Obtaining information on starting the overlay
     *
     * @param gameManager  GameManager of data about the game
     * @param overlayMaker maker handler of the overlay
     */
    @Override
    public void startOverlay(GameManager gameManager, OverlayMakerHandler overlayMaker) {
        super.startOverlay(gameManager, overlayMaker);
        addSubMenuButtons();
        loadHeroUIElements();
        showCurrency();
    }

    /**
     * Add buttons to toggle between different menus
     */
    private void addSubMenuButtons() {
        boolean first = true;
        for (Menus subMenu :
                HeroPopUpController.getHeroPopUpController().getLeftMenuList()) {
            Button button = new Button(subMenu.getName());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (Node buttons : navigationButtons.getChildren()) {
                        buttons.setDisable(false);
                        buttons.setId("grayed-out");
                    }
                    left.getChildren().clear();
                    right.getChildren().clear();
                    loadMenu(subMenu, button);
                }
            });
            
            /*
             * The load above in the handler only loads the sub menus when the
             * buttons are clicked. So if this is the first element, then load
             * the sub menus into the Hero Pop Up Menu. 
             */
            if (first) {
                loadMenu(subMenu, button);
                first = false;
            }
            navigationButtons.getChildren().add(button);
        }
    }

    /**
     * Creates and displays the sub menus.
     *
     * @param leftMenu is the primary menu that is to be displayed
     * @param button   is the Button the user clicks to get to the view requested
     */
    public void loadMenu(Menus leftMenu, Button button) {
        try {
            HeroPopUpController.getHeroPopUpController().heroChangeEvent(); //initialize the current inventory to be used
            /*
             * Notify the controller which menu is currently in the left.
             * Note: this must happen first in this method.
    		 */
            HeroPopUpController.getHeroPopUpController().setLeftMenu(leftMenu);
            
            /* Second, clear all the UI references. */
            HeroPopUpController.getHeroPopUpController().clearUIControllerReferences();

            // load the left menu
            URL leftLocation = getClass().getResource(leftMenu.getLocation());
            FXMLLoader fxmlLoader = new FXMLLoader(leftLocation);
            fxmlLoader.setLocation(leftLocation);
            Pane pane = fxmlLoader.load();
            left.getChildren().add(pane);

            // load the right menu
            Menus rightMenu = HeroPopUpController.getHeroPopUpController().getRightMenu();
            URL rightLocation = getClass().getResource(rightMenu.getLocation());
            fxmlLoader = new FXMLLoader(rightLocation);
            fxmlLoader.setLocation(rightLocation);
            pane = fxmlLoader.load();
            right.getChildren().add(pane);

            // disable the button that takes the user to the current menu, they are already there
            button.setDisable(true);
            
            /* Notify the main controller that the menu has changed */
            HeroPopUpController.getHeroPopUpController().menuChangeEvent();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Display Currency in (HBox)moneyBox
     */
    private void showCurrency() {
        moneyBox.getChildren().clear();
        Image img = TextureRegister.getTexture("large_coin");
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Label balance = new Label(gameManager.getGameWallet().getBalance() + "");
        balance.setTextFill(Color.WHITE);
        moneyBox.setSpacing(10);
        moneyBox.setAlignment(Pos.CENTER);
        moneyBox.getChildren().addAll(imageView, balance);

    }

    /**
     * Loads the Hero UI images and makes them clickable.
     */
    private void loadHeroUIElements() {
        /* Graphic settings - removeal of magic numbers */
        int heroElementSize = 42;
        int heroImageSize = 32;

    	/* Add the left arrow */
        Pane leftArrowPane = new Pane();
        ImageView leftArrowImage = new ImageView();
        leftArrowImage.setFitWidth(heroElementSize);
        leftArrowImage.setFitHeight(heroElementSize);
        Image leftArrowSprite = new Image("/ui/leftArrow.png");
        leftArrowImage.setImage(leftArrowSprite);
        leftArrowPane.getChildren().add(leftArrowImage);
        this.heroSelector.getChildren().add(leftArrowPane);

        /**
         *  Add the handler for the left arrow.
         */
        leftArrowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HeroPopUpController.getHeroPopUpController().goToPreviousHero();
                highlightHeroSelected();
            }
        });

        /**
         * Add all the hero elements.
         */
        for (AbstractHero hero :
                HeroPopUpController.getHeroPopUpController().getHeros()) {

            int index = HeroPopUpController.getHeroPopUpController().getHeros().indexOf(hero);
    		
    		/* Create the Pane */
            Pane heroPane = new Pane();
            heroPane.setId("unselectedhero");
            heroPane.setPrefSize(heroElementSize, heroElementSize);
    		
    		/* Create the ImageView */
            ImageView heroImage = new ImageView();
            heroImage.setFitWidth(heroImageSize);
            heroImage.setFitHeight(heroImageSize);
            heroImage.setX((heroElementSize - heroImageSize) / 2);
            heroImage.setY((heroElementSize - heroImageSize) / 2);
            logger.debug("trying to load: " + "'/heroes/{class}/" + hero.getImageName() + ".png'");
            Image sprite;
            try {
                sprite = TextureRegister.getTexture(hero.getImageName());
            } catch (IllegalArgumentException e) {
                sprite = new Image("/heroes/duck.png");
                logger.error("This image does not exist so suck a duck", e);
            }
            heroImage.setImage(sprite);
    		
    		/* Add the handler to the hero button */
            heroPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    HeroPopUpController.getHeroPopUpController().gotoHeroAt(index);
                    highlightHeroSelected();
                }
            });
    		
    		/* add elements */
            heroPane.getChildren().add(heroImage);
    		
    		/* Add the elements to the UI */
            this.heroPanes.add(heroPane);
            this.heroImages.add(heroImage);
            this.heroSelector.getChildren().add(heroPane);
        }
    	
    	/* Add the right arrow */
        Pane rightArrowPane = new Pane();
        ImageView rightArrowImage = new ImageView();
        rightArrowImage.setFitWidth(heroElementSize);
        rightArrowImage.setFitHeight(heroElementSize);
        Image rightArrowSprite = new Image("/ui/rightArrow.png");
        rightArrowImage.setImage(rightArrowSprite);
        rightArrowPane.getChildren().add(rightArrowImage);
        this.heroSelector.getChildren().add(rightArrowPane);
    	
    	/* Add the handler for the right arrow */
        rightArrowPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HeroPopUpController.getHeroPopUpController().goToNextHero();
                highlightHeroSelected();
            }
        });
    	
    	/* Now everythings been added to the UI, highlight the selected hero */
        highlightHeroSelected();
    }

    /**
     * Highlights the hero at the given index.
     *
     * @param index of the hero to highlight
     */
    private static void highlightHeroSelectedAt(int index) {
        if (heroPanes.size() == 0) {
            return;
        }
        heroPanes.get(index).setId("selectedhero");
    }

    /**
     * Highlights the hero selected.
     */
    public static void highlightHeroSelected() {
    	/* clear the current highlighting */
        clearHeroSelectionHighlighting();
    	
    	/* highlight the selected hero only */
        int index =
                HeroPopUpController.getHeroPopUpController().getHeros().indexOf(
                        HeroPopUpController.getHeroPopUpController().getSelectedHero());
        highlightHeroSelectedAt(index);
    }

    /**
     * Clears the highlighting from the selection.
     */
    public static void clearHeroSelectionHighlighting() {
        for (Pane pane : heroPanes) {
            pane.setId("unselectedhero");
        }
    }

    @Override
    public void refresh() {
        // abstract implementation of OverlayMaker. using reDraw to refresh
    }
}
