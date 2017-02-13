package uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers;


/**
 * Abstract controller for all the logic controllers.
 *
 * @author dusksters
 */
public abstract class SubMenuController {

    /* reference to the UI controller */
    SubMenuUIController uiController = null;

    /**
     * Returns the corresponding SubMenuUIController for this logic controller if the UI controller
     * is on screen. If the UI controller is not on screen then null is returned.
     *
     * @return the UI controller corresponding to this logic controller or null
     */
    public SubMenuUIController getUIController() {
        return uiController;
    }

    /**
     * Sets the UI controller reference for this logic controller. Setting the reference to null
     * indicates that the UI is not being displayed on screen.
     *
     * @param uiController is the reference to the UI controller
     */
    public void setUIController(SubMenuUIController uiController) {
        this.uiController = uiController;
    }

    public void draw() {
    }
}
