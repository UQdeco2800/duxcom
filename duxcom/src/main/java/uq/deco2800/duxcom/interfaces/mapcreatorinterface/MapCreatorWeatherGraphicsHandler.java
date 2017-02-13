package uq.deco2800.duxcom.interfaces.mapcreatorinterface;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

import java.util.ArrayList;

/**
 * Renders the background for the Map Creator
 * Can be altered based on the weather chosen by developer
 *
 * @author Lucas Reher
 * @author Sam Thomas
 */
public class MapCreatorWeatherGraphicsHandler extends GraphicsHandler {

    /**
     * Overwrites update graphics function and draws the background for the Map Creator
     * <p>
     * Uses JavaFx tools to create a blue sky-like background
     *
     * @param graphicsContext The graphics context that has the MapCreator canvas bound to it
     */
    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        graphicsRects.clear();
        // This is where the sky will be rendered
        ArrayList<Stop> gradientStops = new ArrayList<>();
        gradientStops.add(new Stop(0, javafx.scene.paint.Color.LIGHTBLUE));
        gradientStops.add(new Stop(graphicsContext.getCanvas().getWidth() / 2, javafx.scene.paint.Color.WHITE));
        Paint rectColor = new LinearGradient(0, 0, graphicsContext.getCanvas().getWidth(), 2000, false, null, gradientStops);
        graphicsRects.add(new RectToAdd(rectColor, 0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));
    }

    @Override
    public boolean needsUpdating() {
        return true;
    }
}
