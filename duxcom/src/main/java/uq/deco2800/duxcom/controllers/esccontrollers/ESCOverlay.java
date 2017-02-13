package uq.deco2800.duxcom.controllers.esccontrollers;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;

/**
 * Abstract class for creating Escape Menu Buttons.
 *
 * This abstract class exist for the purpose of additional escape menu that may
 * not look the same as the standard escape menu during gameplay i.e. end
 * game/game over / new level
 *
 * @author The_Magic_Karps
 */
public abstract class ESCOverlay extends OverlayMaker {

    /**
     * Interface Manager of the stage
     */
    protected InterfaceManager interfaceManager;
    /**
     * Parent pane of the overlay
     */
    protected Pane parent;
    /**
     * GameManager of the game
     */
    protected GameManager gameManager;
    /**
     * OverlayMakerHandler created
     */
    protected OverlayMakerPopUp popUp;

    /**
     * Add InterfaceManager and stage into this class
     *
     * @param interfaceManager InterfaceManager to be added
     */
    public void addInterface(InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    /**
     * Abstract method to refresh the overlay
     */
    @Override
    public abstract void refresh();

    /**
     * Starts the Overlay
     *
     * @param gameManager GameManager of the game
     * @param overlayMaker OverlayMakerHandler of this overlay
     */
    @Override
    public void startOverlay(GameManager gameManager, OverlayMakerHandler overlayMaker) {
        this.gameManager = gameManager;
        this.parent = overlayMaker.getParent();
        if (overlayMaker instanceof OverlayMakerPopUp) {
            this.popUp = (OverlayMakerPopUp) overlayMaker;
            super.popUp = this.popUp;
        }
    }

    /**
     * Saves Game
     */
    public void saveGame() {
    	gameManager.saveGame();
    	gameManager.saveStatsLocally();
    	
    	// Display saved icon once all saving is done
    	displayGif();
    }

    /**
     * Load Saved Game
     */
    public void loadGame() {
    	// when loading is done
    	/* approach -> goes back to load screen and load GAME interface segment */
    	if (gameManager != null) {
            gameManager.getController().stopGame();
        }
    	interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, interfaceManager.getStage(), "saved");        
    }

    /**
     * Return to Load Screen
     */
    public void returnToLoad() {
        if (gameManager != null) {
            gameManager.getController().stopGame();
        }
        interfaceManager.returnLoadScreen();
    }

    /**
     * Return to Overworld
     */
    public void returnToOverworld() {
        gameManager.getController().stopGame();
        interfaceManager.returnOverworldScreen();
    }

    /**
     * Log out to log in screen
     */
    public void logOut() {
        if (gameManager != null) {
            gameManager.getController().stopGame();
        }
        interfaceManager.restartGame();
    }

    /**
     * Closes the stage and close the game
     */
    public void closeGame() {
        if (gameManager != null) {
            gameManager.getController().stopGame();
        }
        interfaceManager.killInterfaces();
        interfaceManager.getStage().close();
    }

    /**
     * Close pop up and return to game
     */
    public void returnToGame() {
        this.destroyOverlay();
        deleteIcon();

    }

    @Override
    public void show() {
        if (!super.popUp.isActive()) {
            super.popUp.showOverlay();
        } else {
            refresh();
            hide();
        }
    }

    /**
     * Displays the save icon
     */
    private void displayGif() {
        String path = "/savegif/saveGif.gif";

        /* Create graphic elements*/
        ImageView imageView = new ImageView();
        Image image = new Image(path);

        /* set properties of the image */
        imageView.setFitHeight(42);
        imageView.setFitWidth(42);

        /* Add and position the graphic element in the parent */
        imageView.setImage(image);
        this.parent.getChildren().add(imageView);
        imageView.setX(this.parent.getWidth() - 42);
        imageView.setY(0);
    }

    /**
     * Deletes the save icon once the esc menu has been closed
     */
    protected void deleteIcon() {
        /* remove the save icon once the game has been saved */
        int size = parent.getChildren().size();
        if (size != 0 && (this.parent.getChildren().get(this.parent.getChildren().size() - 1) instanceof ImageView)) {
            this.parent.getChildren().remove(this.parent.getChildren().size() - 1);
        }
    }
}