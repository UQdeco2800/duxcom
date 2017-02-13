package uq.deco2800.duxcom.handlers.mapcreatorhandlers;

import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

/**
 * Created by Lucas Reher on 9/09/2016.
 */

/*
    Much like Game Event Handler, this class manages
    all of the handlers for map creator
    We are not reusing the existing handlers as we might like to implement
    some handling differently in the future (on click for example)
 */
public abstract class MapCreatorEventHandler<T extends InputEvent> implements EventHandler<T> {

    protected GameManager gameManager;
    protected Stage stage;
    protected InterfaceManager interfaceManager;

    public MapCreatorEventHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public MapCreatorEventHandler(Stage stage, InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
        this.stage = stage;
    }

    public abstract void handleEvent(T event);
}
