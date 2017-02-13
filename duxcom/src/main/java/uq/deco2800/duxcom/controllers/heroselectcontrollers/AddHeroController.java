package uq.deco2800.duxcom.controllers.heroselectcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uq.deco2800.duxcom.entities.heros.*;
import uq.deco2800.duxcom.interfaces.HeroSelectInterface;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

/**
 * Created by Dwyer on 29/09/2016.
 */
public class AddHeroController {

    @FXML
    private Button addHeroButton;

    @FXML
    private VBox archer;

    @FXML
    private VBox cavalier;

    @FXML
    private VBox knight;

    @FXML
    private VBox priest;

    @FXML
    private VBox rogue;

    @FXML
    private VBox warlock;

    @FXML
    private Button btnCloseAddHeroScreen;

    private HeroSelectController heroSelectController;

    private HeroSelectInterface heroSelectInterface;

    @FXML
    public void addSelectedHero(MouseEvent e) {
        AbstractHero hero;
        //get which hero was selected
        VBox heroBox = (VBox) e.getSource();
        String heroString = heroBox.getId();

        switch(heroString) {
            case "rogue": hero = new Rogue(38,20);
                break;
            case "archer": hero = new Archer(38,22);
                break;
            case "cavalier": hero = new Cavalier(38,24);
                break;
            case "knight": hero = new Knight(38,26);
                break;
            case "warlock": hero = new Warlock(38,28);
                break;
            case "priest": hero = new Priest(38,30);
                break;
            default: hero = new Priest(0,6);
        }

        heroSelectController.addHero(hero);
        heroSelectInterface.removeScreen("addHero");
    }

    @FXML
    public void closeAddHero() {
        heroSelectInterface.removeScreen("addHero");
    }
    
    public void setHeroSelectController(HeroSelectController controller) {
        this.heroSelectController = controller;
    }

    public void setHeroSelectInterface(InterfaceManager interfaceManager) {
        heroSelectInterface = (HeroSelectInterface) interfaceManager.getCurrentSegment();

    }

}
