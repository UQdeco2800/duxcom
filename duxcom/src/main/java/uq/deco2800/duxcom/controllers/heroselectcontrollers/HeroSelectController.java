package uq.deco2800.duxcom.controllers.heroselectcontrollers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.interfaces.HeroSelectInterface;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.overworld.LevelRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dwyer on 1/09/2016.
 */
public class HeroSelectController {

    private static InterfaceManager interfaceManager;

    private static HeroSelectInterface heroSelectInterface;

    private Stage primaryStage;

    //an array of the slected heroes
    private static AbstractHero[] heroes;

    //the position where the user wants to add a new hero
    private int selectedIndex;

    private static Logger logger = LoggerFactory.getLogger(HeroSelectController.class);


    @FXML
    private VBox hero1;

    @FXML
    private VBox hero2;

    @FXML
    private VBox hero3;

    @FXML
    private VBox hero4;

    @FXML
    private Button loadGameButton;

    public static void initializeHeroSelect() {
        //set the hero select interface
        heroSelectInterface = (HeroSelectInterface) interfaceManager.getCurrentSegment();
        //initialize array
        heroes = new AbstractHero[4];
    }

    /**
     * retrieves the seleted heroes and loads a level.
     */
    public void loadGame() {

        List<AbstractHero> selectedHeroes = this.getSelectedHeroes();
        if (selectedHeroes.isEmpty()) {
            return;
        }

        //load the map
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, primaryStage, LevelRegister.getCurrentLevel().getLevelId());
    }

    /**
     * loads the Add Hero FXML document over the top of the current screen
     */
    @FXML
    public void openAddHeroScreen(ActionEvent e) {
        heroSelectInterface.loadScreen("addHero");

        //set the SelectedIndex variable
        Button button = (Button) e.getSource();
        VBox vbox = (VBox) button.getParent();
        HBox hbox = (HBox) vbox.getParent();

        ObservableList<Node> heroList = hbox.getChildren();
        selectedIndex = heroList.indexOf(vbox);
    }

    /**
     * Adds the specified hero to the VBOX in Hero Select
     *
     * @param hero - the hero to be added
     */
    public void addHero(AbstractHero hero) {
        //add the hero
        HeroSelectController.heroes[selectedIndex] = hero;

        VBox heroNode;

        switch (selectedIndex) {
            case 0:
                heroNode = hero1;
                break;
            case 1:
                heroNode = hero2;
                break;
            case 2:
                heroNode = hero3;
                break;
            case 3:
                heroNode = hero4;
                break;

            default:
                heroNode = hero1;
        }


        editHeroData(heroNode.getChildren(), hero);

    }

    /**
     * Edits a Hero's portfolio in the Hero Select interface
     *
     * @param children - the javaFX elements of the portfolio
     * @param hero     - the hero to be selected
     */
    private void editHeroData(ObservableList<Node> children, AbstractHero hero) {
        //set the heroes name
        Label label = (Label) children.get(0);
        label.setText(hero.getClass().getSimpleName());

        //set the heroes image
        ImageView image = (ImageView) children.get(1);
        image.setImage(new Image("/heroes/" + hero.getImageName() + ".png"));
        image.setFitWidth(135);
        image.setFitHeight(120);
        image.preserveRatioProperty().setValue(true);

        //remove the addHero button
        children.get(2).setVisible(false);
        //show the clearHero button
        children.get(3).setVisible(true);

    }

    /**
     * Clears a currently selected hero from the menu
     *
     * @param e the action event related to the button being pressed.
     */
    public void clearHero(ActionEvent e) {
        Button eventButton = (Button) e.getSource();
        VBox parentBox = (VBox) eventButton.getParent();

        HBox grandparentBox = (HBox) parentBox.getParent();

        int deleteIndex = grandparentBox.getChildren().indexOf(parentBox);
        HeroSelectController.heroes[deleteIndex] = null;


        //set all the siblings of the button to reset
        ObservableList<Node> siblings = parentBox.getChildren();

        //reset siblings
        Label label = new Label();
        ImageView view = new ImageView();
        view.setFitHeight(120);
        view.setFitWidth(135);

        siblings.set(0, label);
        siblings.set(1, view);
        //show add hero button and hide other buttons
        siblings.get(2).setVisible(true);
        siblings.get(3).setVisible(false);
    }

    /**
     * Returns the currently selected heroes in list form
     *
     * @return an arrayList of heroes
     */
    @FXML
    public List<AbstractHero> getSelectedHeroes() {
        List<AbstractHero> heroList = new ArrayList<>();
        for (AbstractHero hero : heroes) {
            if (hero != null)
                heroList.add(hero);
        }
        return heroList;
    }

    /**
     * Sets the interface manager that is currently in use.
     *
     * @param interfaceManager the loaded interface manager
     */
    public void setInterfaceManager(InterfaceManager interfaceManager) {
        HeroSelectController.interfaceManager = interfaceManager;
    }

    /**
     * sets the current stage in order to use the interface manager's methods
     *
     * @param primaryStage the current stage
     */
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void back() {
        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, primaryStage, "load");
    }

    public static List<AbstractHero> staticGetSelectedHeroes() {
        List<AbstractHero> heroList = new ArrayList<>();
        for (AbstractHero hero : heroes) {
            if (hero != null)
                heroList.add(hero);
        }
        return heroList;
    }
}

