package uq.deco2800.duxcom.interfaces.overlaymaker.popup;

import java.io.IOException;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;

/**
 * OverlayMakerSuperPopUp - OverlayMaker used to make expansive pop up
 *
 * @author The_Magic_Karps
 */
public class OverlayMakerSuperPopUp extends OverlayMakerPopUp {

    // Cached Overlays
    protected static HashMap<String, OverlayMakerSuperPopUp> superCache = new HashMap<>();

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
    public static OverlayMakerSuperPopUp makeSuperPopUpGM(Pane parent, String fxml, GameManager gameManager) throws IOException {
        if (gameManager == null) {
            return OverlayMakerSuperPopUp.makeSuperPopUpNoGM(parent, fxml);
        }
        if (OverlayMakerSuperPopUp.superCache.containsKey(fxml)
                && !OverlayMakerSuperPopUp.superCache.get(fxml).getParent().equals(parent)) {
            OverlayMakerSuperPopUp.removeCache(fxml);

        } else if (OverlayMakerSuperPopUp.superCache.containsKey(fxml)) {
            return OverlayMakerSuperPopUp.superCache.get(fxml);
        }
        Pane superlay = new Pane();
        superlay.prefWidthProperty().bind(parent.widthProperty());
        superlay.prefHeightProperty().bind(parent.heightProperty());
        superlay.setStyle("-fx-background-color: rgba(0,0,0,0.85);");
        superlay.setPickOnBounds(false);
        superlay.setMouseTransparent(false);
        OverlayMakerSuperPopUp popUp = new OverlayMakerSuperPopUp(parent, fxml, gameManager, superlay);
        OverlayMakerSuperPopUp.addSuperCache(fxml, popUp);
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
    public static OverlayMakerSuperPopUp makeSuperPopUpNoGM(Pane parent, String fxml) throws IOException {
        if (OverlayMakerSuperPopUp.superCache.containsKey(fxml)
                && !OverlayMakerSuperPopUp.superCache.get(fxml).getParent().equals(parent)) {
            OverlayMakerSuperPopUp.removeSuperCache(fxml);
        }
        if (OverlayMakerSuperPopUp.superCache.containsKey(fxml)
                && OverlayMakerSuperPopUp.superCache.get(fxml).getParent().equals(parent)) {
            return OverlayMakerSuperPopUp.superCache.get(fxml);
        }
        Pane superlay = new Pane();
        superlay.prefWidthProperty().bind(parent.widthProperty());
        superlay.prefHeightProperty().bind(parent.heightProperty());
        superlay.setStyle("-fx-background-color: rgba(0,0,0,0.85);");
        superlay.setPickOnBounds(false);
        superlay.setMouseTransparent(false);
        OverlayMakerSuperPopUp popUp = new OverlayMakerSuperPopUp(parent, fxml, superlay);
        OverlayMakerSuperPopUp.addSuperCache(fxml, popUp);
        return popUp;
    }

    /**
     * Add an overlay to its cache
     *
     * @param fxml the file location to be used as key
     * @param popUp the overlay created to be cached
     */
    public static void addSuperCache(String fxml, OverlayMakerSuperPopUp popUp) {
        superCache.put(fxml, popUp);
    }

    /**
     * Removes an overlay from its cache
     *
     * @param fxml the file location (key) to find the object
     */
    public static void removeSuperCache(String fxml) {
        superCache.remove(fxml);
    }

    /**
     * Private Constructor that makes the overlay
     *
     * @param parent parent of the overlay
     * @param fxml location of the fxml file
     * @param gameManager current game manager
     * @param superlay the pane the fxml file's base layer will reside in
     * @throws IOException location of fxml not found
     */
    private OverlayMakerSuperPopUp(Pane parent, String fxml, GameManager gameManager, Pane superlay) throws IOException {
        super(parent, fxml, gameManager);
        startSuperlay(fxml, superlay);
        initDefault();
    }

    /**
     * Private Constructor that makes the overlay (Without GameManager)
     *
     * @param parent parent of the overlay
     * @param fxml location of the fxml file
     * @param superlay the pane the fxml file's base layer will reside in
     * @throws IOException location of fxml not found
     */
    private OverlayMakerSuperPopUp(Pane parent, String fxml, Pane superlay) throws IOException {
        super(parent, fxml);
        startSuperlay(fxml, superlay);
        initDefault();
    }

    /**
     * Initialise default overlay settings
     */
    @Override
    public void initDefault() {
        disableBackground();
        setCenter(true);
        setSuperlayCenter(true);
        setClickToTop(true);
    }

    /**
     * Creates an event handler that does nothing, for PickOnBounds().
     */
    private void disableBackground() {
        overlay.addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent e) {
                e.consume();
            }
        });
    }

    /**
     * Centers all aspects of the overlay
     *
     * @param value true to center, false otherwise
     */
    public void setSuperlayCenter(boolean value) {
        if (value && underlay != null) {

            underlay.translateXProperty()
                    .bind(overlay.widthProperty().subtract(underlay.widthProperty())
                            .divide(2));
            underlay.translateYProperty()
                    .bind(overlay.heightProperty().subtract(underlay.heightProperty())
                            .divide(2));
            this.currentXPos = this.underlay.widthProperty().divide(2).doubleValue();
            this.currentYPos = this.underlay.heightProperty().divide(2).doubleValue();
        } else if (!value && underlay != null) {
            overlay.translateXProperty().unbind();
            overlay.translateYProperty().unbind();
        }
    }
}
