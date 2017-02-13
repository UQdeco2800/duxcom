package uq.deco2800.duxcom.interfaces.overlaymaker.ui;

import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * OverlayMakerUIManager - Manager to make a collection of overlay UI
 * 
 * @author The_Magic_Karps
 */
public class OverlayMakerUIManager {

    // components overlays/UI in this manager
    private final HashMap<String, OverlayMakerUI> components;

    // GameManager of the game
    private final GameManager gameManager;

    // Parent of the current gamePane
    private final Pane parent;

    /**
     * Constructor of OverlayMakerUIManager if intended to use on game window
     *
     * @param gameManager the game manager of the current game
     */
    public OverlayMakerUIManager(GameManager gameManager) {
        components = new HashMap<>();
        this.gameManager = gameManager;
        parent = gameManager.getController().getGamePane();
    }

    /**
     * Constructor of OverlayMakerUIManager if intended to use not on game
     * window
     *
     * @param gameManager the game manager of the current game
     * @param parent the parent the overlay will be on
     */
    public OverlayMakerUIManager(GameManager gameManager, Pane parent) {
        components = new HashMap<>();
        this.gameManager = gameManager;
        this.parent = parent;
    }

    /**
     * Add FXML file to create overlay and its controller
     *
     * @param fxml file name location of the element
     * @param x x position of the UI to be made
     * @param y y position of the UI to be made
     * @param order z order of the UI to be made
     * @throws IOException location of fxml not found
     */
    public void addFXML(String fxml, double x, double y, UIOrder order) throws IOException {
        components.put(fxml, new OverlayMakerUI(fxml, parent, gameManager, x, y, order.getOrder()));
    }

    /**
     * Display a specific component onto the game
     *
     * @param name the name of the component to be displayed onto game
     */
    public void showComponents(String name) {
        components.get(name).showOverlay();
    }

    /**
     * Display all components in this manager
     */
    public void showComponents() {
        for (OverlayMakerUI component : components.values()) {
            component.showOverlay();
        }
    }

    /**
     * Hide all components in this manager
     */
    public void hideComponents() {
        for (OverlayMakerUI component : components.values()) {
            component.hideOverlay();
        }
    }

    /**
     * Hide a specific component from the game
     *
     * @param name the name of the component to be hidden from the game
     */
    public void hideComponents(String name) {
        components.get(name).hideOverlay();
    }

    /**
     * Retrieves the UI Component
     *
     * @param name of the ui section
     * @return the UI overlaymaker
     */
    public OverlayMakerUI getComponent(String name) {
        return components.get(name);
    }

   
}
