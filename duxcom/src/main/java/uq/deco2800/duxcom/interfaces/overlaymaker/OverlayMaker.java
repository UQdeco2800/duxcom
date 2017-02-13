package uq.deco2800.duxcom.interfaces.overlaymaker;

import uq.deco2800.duxcom.GameManager;

/**
 * Abstract class for all overlays made by OverlayMakerHandler
 *
 * Override them and replace with your own functionality
 *
 * @author The_Magic_Karps
 */
public abstract class OverlayMaker {

    /**
     * GameManager of the current game
     */
    protected GameManager gameManager;
    /**
     * OverlayMakerHandler
     */
    protected OverlayMakerHandler popUp;

    /**
     * Parse in important references when creating overlay
     *
     * If this method is overridden, ensure you provide reference of popUp to
     * this abstract class or also override show() and hide() i.e. this.popUp =
     * super.popUp; in child class
     *
     * @param gameManager GameManager of the current game
     * @param overlayMaker handler used to create the overlay
     */
    public void startOverlay(GameManager gameManager, OverlayMakerHandler overlayMaker) {
        this.gameManager = gameManager;
        this.popUp = overlayMaker;
    }

    /**
     * Parse in important references when creating overlay
     *
     * If this method is overridden, ensure you provide reference of popUp to
     * this abstract class or also override show() and hide() i.e. this.popUp =
     * super.popUp; in child class
     *
     * @param overlayMaker handler used to create the overlay
     */
    public void startOverlay(OverlayMakerHandler overlayMaker) {
        this.popUp = overlayMaker;
    }

    /**
     * Method to destroy the interface Remember to re-enable background mouse
     * event before destroying if setDisableBackground is set to true
     */
    public void destroyOverlay() {
        this.hide();
    }

    /**
     * Refreshes the contents in the overlay
     */
    public abstract void refresh();

    /**
     * Make overlay visible
     */
    public void show() {
        if (!popUp.isActive()) {
            popUp.showOverlay();
        } else {
            hide();
        }
    }

    /**
     * Hide overlay
     */
    public void hide() {
        popUp.hideOverlay();
    }
}
