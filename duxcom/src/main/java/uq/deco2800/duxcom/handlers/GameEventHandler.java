package uq.deco2800.duxcom.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.annotation.ConsiderRevising;
import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.dataregisters.TileDataRegister;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

/**
 * Handles a generic game event and passes reference to the game manager.
 * <p>
 * Created by liamdm on 17/08/2016.
 */
public abstract class GameEventHandler<T extends InputEvent> implements EventHandler<T> {

    /**
     * The game manager to pass to inehritants
     */
    protected GameManager gameManager;

    /**
     * The interface manager controlling stage
     */
    protected InterfaceManager interfaceManager;

    /**
     * The current controller with ownership of stage
     */
    protected DuxComController controller;

    /**
     * Parent Pane
     */
    protected Pane parentPane;


    /**
     * Constructs the event handler
     *
     * @param gameManager the game manager to pass
     */
    @MethodContract(precondition = {"gameManager != null"})
    public GameEventHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Creates a game event handler with multiple objects
     *
     * @param interfaceManager the current interface manager
      * @param gameManager      the game manager
     * @param controller       the controller
     */
    @ConsiderRevising(description = "Perhaps add a builder here", date = "09/10/16", suggestor = "liamdm")
    public GameEventHandler(InterfaceManager interfaceManager, GameManager gameManager, Object controller) {
        if (controller instanceof DuxComController) {
            this.gameManager = gameManager;
            this.controller = (DuxComController) controller;
            this.interfaceManager = interfaceManager;
        }
    }

    /**
     * Creates a game event handler with panes
     *
     * @param interfaceManager the interface manager
     * @param parent           the parent pane
     */
    public GameEventHandler(InterfaceManager interfaceManager, Pane parent) {
        this.interfaceManager = interfaceManager;
        this.parentPane = parent;
    }

    /**
     * Handles an event
     *
     * @param event the event to handle
     */
    public abstract void handleEvent(T event);

    /**
     * Prevents interactivity during countdown
     */
    public void handle(T event){
        if(gameManager != null
                && gameManager.getMultiplayerGameManager() != null
                && gameManager.getMultiplayerGameManager().isMultiplayer()
                && gameManager.getMultiplayerGameManager().isBlocking()){
            return;
        }
        handleEvent(event);
    }

    /**
     * Sets the mouse location in the game manager to the event location
     *
     * @param x x location
     * @param y y location
     */
    void setMouseLocationToEvent(double x, double y) {
        // get the in game coordinates and real coordinates on screen
        double offsetX = gameManager.getxOffset();
        double offsetY = gameManager.getyOffset();
        double scale = gameManager.getScale();
        double scaledWidth = TileDataRegister.TILE_WIDTH * scale;
        double scaledHeight = TileDataRegister.TILE_HEIGHT * scale;
        double basex =  (gameManager.getMap().getWidth() * scaledWidth) / 2.0
                + scaledWidth/4.0;
        double basey = scaledHeight/4.0;

        //reverse the math logic from the GameRenderer
        int i = (int) (((y - offsetY + basey) / scaledHeight)
                - ((x - offsetX) - basex) /  scaledWidth);

        int j = (int) ((2 * ((x - offsetX) - basex) / scaledWidth)  + i);

        gameManager.setMouseLocation(i, j);
    }
}
