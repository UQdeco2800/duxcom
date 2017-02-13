package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.Menus;

public class AbilitiesUIController extends SubMenuUIController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(AbilitiesUIController.class);

    // The UI controller
    AbilitiesController controller = null;

    //speed buttons
    @FXML
    private Button ap_1;
    @FXML
    private Button ap_2;
    @FXML
    private Button ap_3;
    @FXML
    private Button ap_4;
    @FXML
    private Button ap_5;

    //armour private Buttons
    @FXML
    private Button armour_1;
    @FXML
    private Button armour_2;
    @FXML
    private Button armour_3;
    @FXML
    private Button armour_4;
    @FXML
    private Button armour_5;

    //health private Buttons
    @FXML
    private Button health_1;
    @FXML
    private Button health_2;
    @FXML
    private Button health_3;
    @FXML
    private Button health_4;
    @FXML
    private Button health_5;

    //upgrade points avaliable
    @FXML
    private Label upgradePoints;


    /**
     * Constructor
     */
    public AbilitiesUIController() {

        logger.debug("Constructing...");

    	/* Setup reference */
        controller = (AbilitiesController) HeroPopUpController.getHeroPopUpController().getController(Menus.ABILITIES);
        controller.setUIController(this);

    }

    /**
     * Initializer. <p> Note: runs after all of the FXML elements have been initialized.
     */
    @FXML
    public void initialize() {
        addButtonsToList();
        upgradePoints.setText(HeroPopUpController.getHeroPopUpController().getSelectedHero().getUpgradePoints() + "");
    }

    /**
     * Called to draw the UI
     */
    @Override
    public void draw() {

    }

    public void addSelected(ActionEvent actionEvent) {
        Button selectedButton = (Button) actionEvent.getSource();
        controller.selectButton(selectedButton);
    }

    public void updateUpgradePoints(int newUpgradePoints) {
        upgradePoints.setText(newUpgradePoints + "");
    }

    /**
     * Adds each stat button to its respective linked list in AbilitiesController
     */
    private void addButtonsToList() {
        //add armour buttons
        controller.addArmourButton(armour_1);
        controller.addArmourButton(armour_2);
        controller.addArmourButton(armour_3);
        controller.addArmourButton(armour_4);
        controller.addArmourButton(armour_5);

        //add speed buttons
        controller.addAPButton(ap_1);
        controller.addAPButton(ap_2);
        controller.addAPButton(ap_3);
        controller.addAPButton(ap_4);
        controller.addAPButton(ap_5);

        //add health buttons
        controller.addHealthButton(health_1);
        controller.addHealthButton(health_2);
        controller.addHealthButton(health_3);
        controller.addHealthButton(health_4);
        controller.addHealthButton(health_5);
    }
}
