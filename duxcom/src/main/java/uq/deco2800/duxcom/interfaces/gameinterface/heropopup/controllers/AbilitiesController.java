package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;

import javafx.scene.control.Button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;

import java.util.LinkedList;
import java.util.ListIterator;

public class AbilitiesController extends SubMenuController {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(AbilitiesController.class);

    //button lists
    private LinkedList<Button> speedButtons;
    private LinkedList<Button> armourButtons;
    private LinkedList<Button> healthButtons;

    private AbilitiesUIController abilitiesUIController;

    private AbstractHero currentHero;


    /**
     * Constructor
     */
    public AbilitiesController() {
        speedButtons = new LinkedList<Button>();
        armourButtons = new LinkedList<Button>();
        healthButtons = new LinkedList<Button>();
        currentHero = HeroPopUpController.getHeroPopUpController().getSelectedHero();
    }

    /**
     * adds the button to the start of the  speed Linked List Button is added at the start to make
     * it easier for traversing when setting selected.
     *
     * @param button - the button to add to the list of buttons
     */
    public void addAPButton(Button button) {
        speedButtons.addLast(button);
    }

    /**
     * adds the button to the start of the armour Linked List
     *
     * @param button - the button to add to the list of buttons
     */
    public void addArmourButton(Button button) {
        armourButtons.addLast(button);
    }

    /**
     * adds the button to the start of the health Linked List
     *
     * @param button - the button to add to the list of buttons
     */
    public void addHealthButton(Button button) {
        healthButtons.addLast(button);
    }

    /**
     * gets what list the button belongs to and selects all previous buttons on that list
     */
    public void selectButton(Button button) {
        int buttonIndex = -1;

        if (button.getId().startsWith("ap")) {
            buttonIndex = speedButtons.indexOf(button);
            selectButtonsInList(speedButtons, buttonIndex);
        } else if (button.getId().startsWith("health")) {
            buttonIndex = healthButtons.indexOf(button);
            selectButtonsInList(healthButtons, buttonIndex);
        } else if (button.getId().startsWith("armour")) {
            buttonIndex = armourButtons.indexOf(button);
            selectButtonsInList(armourButtons, buttonIndex);
        } else {
            logger.error("Button ID does not match a specified button list");
        }
    }

    private void selectButtonsInList(LinkedList<Button> buttonList, int buttonIndex) {

        Button currentButton;
        ListIterator<Button> listIterator = buttonList.listIterator(buttonIndex);
        if (listIterator.hasNext()) {

            currentButton = listIterator.next();
            //check the that previous tier has been upgraded and this current tier has not bee upgraded
            if (!currentButton.getStyleClass().contains("selected")) {

                //get the stat and tier of the button
                String buttonId = currentButton.getId();
                int buttonTier = Integer.parseInt(currentButton.getId().substring(currentButton.getId().indexOf("_") + 1, currentButton.getId().length()));
                if (currentHero != null && currentHero.statUpgrade(buttonId, buttonTier)) {
                    currentButton.getStyleClass().add("selected");
                    abilitiesUIController.updateUpgradePoints(currentHero.getUpgradePoints());
                }
            }

        }
    }

    public void setUIController(AbilitiesUIController controller) {
        this.abilitiesUIController = controller;
    }

}
