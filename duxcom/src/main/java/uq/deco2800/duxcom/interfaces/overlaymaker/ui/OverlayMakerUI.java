package uq.deco2800.duxcom.interfaces.overlaymaker.ui;

import javafx.scene.layout.Pane;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;

import java.io.IOException;

/**
 * OverlayMakerUI - OverlayMaker for User Interface
 *
 * Better used with OverlayMakerUIManager (Check Wiki for more detail)
 *
 * @author The_Magic_Karps
 */
public class OverlayMakerUI extends OverlayMakerHandler {

    // X Position of the UI
    private double xPos;

    // Y Position of the UI
    private double yPos;

    // Z Order of the UI
    private int zPos;

    /**
     * Constructor of the overlaymaker
     *
     * @param parent parent of the overlay
     * @param gameManager current game manager
     * @param x x position of UI to be created on relatively to parent
     * @param y y position of UI to be created on relatively to parent
     * @param z z order of UI relative to other components
     * @throws IOException location of fxml not found
     */
    public OverlayMakerUI(String fxml, Pane parent, GameManager gameManager, double x, double y, int z) throws IOException {
        super(parent, gameManager);
        startOverlay(fxml);
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
        initDefault();
    }

    /**
     * Default settings for the overlay to be created
     */
    @Override
    public void initDefault() {
        setPosition(xPos, yPos);
        setZOrder();
    }

    /**
     * Set the Z order of the UI to be created
     */
    private void setZOrder() {
        overlay.setTranslateZ(zPos);
    }

    /**
     * Set z order of overlay using integers
     *
     * @param z z order to be set on overlay
     */
    public void setZLayout(int z) {
        overlay.setTranslateZ(z);
    }

    /**
     * Set z order of overlay using integers
     *
     * @param order z order to be set on overlay using UIOrder enum
     */
    public void setZLayout(UIOrder order) {
        overlay.setTranslateZ(order.getOrder());
    }

    /**
     * Retrieve the Z order of the UI
     *
     * @return z order of the UI
     */
    public int getZOrder() {
        return zPos;
    }

}
