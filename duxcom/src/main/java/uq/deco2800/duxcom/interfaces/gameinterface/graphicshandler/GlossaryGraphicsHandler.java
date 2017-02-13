package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ticket #61 - Story/Lore and In Game glossary
 *
 * An FXML Controller class for the overlay
 *
 * @author rhysmckenzie (Inspired by The_Magic_Karps' & Ducksters' work)
 */
public abstract class GlossaryGraphicsHandler extends AbstractPopUpGraphicsHandler {

    // Logger
    private static Logger logger = LoggerFactory.getLogger(GlossaryGraphicsHandler.class);

    public GlossaryGraphicsHandler() {
        fxmlFileName = "/ui/fxml/Glossary.fxml";
    }

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsImages.clear();
        try {
            if (!processGraphicsUpdate()) {
                GlossaryGraphicsHandler.setInitialised(false);
            }
        } catch (Exception e) {
            GlossaryGraphicsHandler.setInitialised(false);
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean needsUpdating() {
        return false;
    }

    /**
     * Set the static initialized value of the class, to determine whether the
     * class should create a new overlay or not
     *
     * @param value boolean value of true or false, whether overlay exist or not
     */
    public static void setInitialised(boolean value) {
        GlossaryGraphicsHandler.initialised = value;
    }

    public static boolean getInitialised() {
        return GlossaryGraphicsHandler.initialised;
    }

    public boolean getVisible() {
        return this.visible;
    }
}
