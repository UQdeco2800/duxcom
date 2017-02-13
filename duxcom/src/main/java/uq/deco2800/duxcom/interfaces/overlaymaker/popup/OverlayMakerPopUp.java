package uq.deco2800.duxcom.interfaces.overlaymaker.popup;

import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * OverlayMakerPopUp - OverlayMaker thats used to make a standard popup
 * 
 * @author The_Magic_Karps
 */
public class OverlayMakerPopUp extends OverlayMakerHandler {

    // Cached Overlays
    protected static HashMap<String, OverlayMakerPopUp> cache = new HashMap<>();
    
    /**
     * Static factory method to create an overlay if not cached, or retrieves a
     * cached overlay.
     *
     * Used when GameManager exist
     *
     * @param parent parent of the overlay
     * @param fxml location of the fxml file
     * @param gameManager current game manager
     * @return OverlayMakerPopUp instance class of overlay
     * @throws IOException location of fxml not found
     */
    public static OverlayMakerPopUp makeWithGameManager(Pane parent, String fxml, GameManager gameManager) throws IOException {
        if (gameManager == null) {
            return OverlayMakerPopUp.makeWithoutGameManager(parent, fxml);
        }
        if (OverlayMakerPopUp.cache.containsKey(fxml)
                && !OverlayMakerPopUp.cache.get(fxml).getParent().equals(parent)) {
            OverlayMakerPopUp.removeCache(fxml);

        } else if (OverlayMakerPopUp.cache.containsKey(fxml)) {
            return OverlayMakerPopUp.cache.get(fxml);
        }
        OverlayMakerPopUp popUp = new OverlayMakerPopUp(parent, fxml, gameManager);
        OverlayMakerPopUp.addCache(fxml, popUp);
        return popUp;
    }

    /**
     * Static factory method to create an overlay if not cached, or retrieves a
     * cached overlay.
     *
     * Used when GameManager does not exist
     *
     * @param parent parent of the overlay
     * @param fxml location of the fxml file
     * @return OverlayMakerPopUp instance class of overlay
     * @throws IOException location of fxml not found
     */
    public static OverlayMakerPopUp makeWithoutGameManager(Pane parent, String fxml) throws IOException {
        if (OverlayMakerPopUp.cache.containsKey(fxml)
                && !OverlayMakerPopUp.cache.get(fxml).getParent().equals(parent)) {
            OverlayMakerPopUp.removeCache(fxml);
        }
        if (OverlayMakerPopUp.cache.containsKey(fxml)
                && OverlayMakerPopUp.cache.get(fxml).getParent().equals(parent)) {
            return OverlayMakerPopUp.cache.get(fxml);
        }
        OverlayMakerPopUp popUp = new OverlayMakerPopUp(parent, fxml);
        OverlayMakerPopUp.addCache(fxml, popUp);
        return popUp;
    }

    /**
     * Add an overlay to its cache
     *
     * @param fxml the file location to be used as key
     * @param popUp the overlay created to be cached
     */
    public static void addCache(String fxml, OverlayMakerPopUp popUp) {
        cache.put(fxml, popUp);
    }

    /**
     * Removes an overlay from its cache
     *
     * @param fxml the file location (key) to find the object
     */
    public static void removeCache(String fxml) {
        cache.remove(fxml);
    }

    /**
     * Private Constructor for OverlayMakerPopUp that accepts GameManager
     *
     * @param parent parent of the overlay
     * @param fxml location of the fxml file
     * @param gameManager current game manager
     * @throws IOException location of fxml not found
     */
    protected OverlayMakerPopUp(Pane parent, String fxml, GameManager gameManager) throws IOException {
        super(parent, gameManager);
        startOverlay(fxml);
        initDefault();
    }

    /**
     * Private Constructor for OverlayMakerPopUp that does not accept
     * GameManager
     *
     * @param parent parent of the overlay
     * @param fxml location of the fxml file
     * @throws IOException location of fxml not found
     */
    protected OverlayMakerPopUp(Pane parent, String fxml) throws IOException {
        super(parent);
        startOverlay(fxml);
        initDefault();
    }

    /**
     * Default setting to be executed for this overlay
     */
    @Override
    public void initDefault() {
        setDraggable(this.overlay, true);
        setCenter(true);
        setClickToTop(true);
    }
}
