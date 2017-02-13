package uq.deco2800.duxcom.interfaces.gameinterface.glossary;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.GlossaryGraphicsHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Ticket #61 - Story/Lore and In Game glossary
 *
 * An FXML Controller class for the glossary
 *
 * @author rhysmckenzie (Inspired by The_Magic_Karps' & Ducksters' work)
 */

public class GlossaryController extends OverlayMaker implements Initializable {

    // Content Pane that holds the different sections
    @FXML
    protected Pane content;

    // Tab for the buttons
    @FXML
    private TabPane tabPane;

    //Label in which the content will be displayed
    @FXML
    protected Label contentText;

    //ImageView in which the images for each section will be displayed
    @FXML
    protected ImageView glossaryImage;

    // Pane of the parent of glossary
    protected Pane gamePane;

    // Overlay instance of glossary
    protected Pane overlay;

    //Instance of OverlayMakerHandler
    private OverlayMakerHandler overlayMaker;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contentText.setText("Select a topic that you would like to read " +
                "about");
    }

    /**
     * Add buttons to toggle between different sections
     */
    protected void addSectionButtons() {
        for (GlossarySections sectionType : GlossarySections.values()) {
            GlossarySection glossarySection = sectionType.glossarySection;
            Tab tab = new Tab (glossarySection.getName());
            Button button = new Button ();
            button.setOnAction(event -> {
                for (Tab buttons : tabPane.getTabs()) {
                    buttons.setDisable(false);
                }
                if ("Heroes".equals(glossarySection.getName())){
                    updateGlossary(glossarySection.getText(), glossarySection.getImage());
                    addHeroSubTabs();
                } else if ("Enemies".equals(glossarySection.getName())){
                    updateGlossary(glossarySection.getText(), glossarySection.getImage());
                    addEnemySubTabs();
                } else if ("World".equals(glossarySection.getName())) {
                    updateGlossary(glossarySection.getText(), glossarySection.getImage());
                    addWorldSubTabs();
                }
            });
            button.getStyleClass().add("tab-button");
            tabPane.getTabs().add(tab);
            tab.setGraphic(button);
            tab.getGraphic();
        }
    }

    /**
     * Create and display the sections
     *
     * @param text location of the fxml file
     */
    @FXML
    protected void updateGlossary(String text, String imageLocation) {
        Image image = new Image(imageLocation);
        contentText.setText(text);
        glossaryImage.setImage(image);
        glossaryImage.setFitWidth(100);
        glossaryImage.setPreserveRatio(true);
        glossaryImage.setSmooth(true);
    }


    /**
     * changes the tabs in tabPane to the Hero sub-section tabs
     */
    protected void addHeroSubTabs() {
        tabPane.getTabs().clear();
        for (HeroTabSections sectionType : HeroTabSections.values()) {
            GlossarySection glossarySection = sectionType.glossarySection;
            generateTab(glossarySection.getName(), glossarySection.getText(), glossarySection.getImage());
        }
    }

    /**
     * changes the tabs in tabPane to the Enemy sub-section tabs
     */
    protected void addEnemySubTabs() {
        tabPane.getTabs().clear();
        for (EnemyTabSections sectionType : EnemyTabSections.values()) {
            GlossarySection glossarySection = sectionType.glossarySection;
            generateTab(glossarySection.getName(), glossarySection.getText(), glossarySection.getImage());
        }
    }

    /**
     * changes the tabs in tabPane to the World sub-section tabs
     */
    protected void addWorldSubTabs() {
        tabPane.getTabs().clear();
        for (WorldSections sectionType : WorldSections.values()) {
            GlossarySection glossarySection = sectionType.glossarySection;
            generateTab(glossarySection.getName(), glossarySection.getText(), glossarySection.getImage());
        }
    }

    /**
     * Generates new tabs for the tab pane and sets a button as the
     * tabs graphic
     *
     * On click the button will update the glossary to display the text and
     * image specified
     *
     * @param name The name of the section for the tab
     * @param text The text of the section to be displayed
     * @param image The image of the section to be displayed
     */
    protected void generateTab(String name, String text, String image) {
        Tab tab = new Tab (name);
        Button button = new Button ();
        button.setOnAction(event -> {
            for (Tab buttons : tabPane.getTabs()) {
                buttons.setDisable(false);
            }
            updateGlossary(text, image);
        });
        button.getStyleClass().add("tab-button");
        tabPane.getTabs().add(tab);
        tab.setGraphic(button);
        tab.getGraphic();
    }

    /**
     * Refreshes the data of overlay
     *
     * Destroys when hero changes
     */
    @Override
    public void refresh() {
        /* necessary override */
    }

    /**
     * Obtaining information on starting the overlay
     *
     * @param gameManager GameManager of data about the game
     * @param overlayMaker maker handler of the overlay
     */
    @Override
    public void startOverlay(GameManager gameManager, OverlayMakerHandler overlayMaker) {
        this.gamePane = overlayMaker.getParent();
        this.gameManager = gameManager;
        this.overlay = overlayMaker.getOverlay();
        this.overlayMaker = overlayMaker;
        addSectionButtons();
        overlayMaker.setDisableBackground(true);
    }

    /**
     * Destroys this overlay
     */
    @Override
    public void destroyOverlay() {
        overlayMaker.setDisableBackground(false);
        hide();
        GlossaryGraphicsHandler.setInitialised(false);
    }

    /**
     * Resets the glossary tabs and content pane to their orginal state
     */
    public void back() {
        tabPane.getTabs().clear();
        addSectionButtons();
        contentText.setText("Select a topic that you would like to read " +
                "about");

    }


    /**
     * Shows the overlay
     */
    @Override
    public void show() {
        overlayMaker.showOverlay();
    }

    /**
     * Hides the overlay
     */
    @Override
    public void hide() {
        overlayMaker.hideOverlay();
    }
}
