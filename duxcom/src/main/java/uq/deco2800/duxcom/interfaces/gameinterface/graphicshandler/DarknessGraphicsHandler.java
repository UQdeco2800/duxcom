package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

import java.util.ArrayList;

/**
 * Created by elliot on 19/08/2016.
 * (Thanks to @Liamdm for the help!)
 */
public class DarknessGraphicsHandler extends GraphicsHandler {
    private double tVal = 0;

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        double currentTime = currentGameManager.getDayNightClock().getTime();

        double ratio = getRatio(currentTime);

        //Smoother transitions of sunset/rise between turns
        if(Math.abs(tVal - ratio) > 0.001d){
            tVal += tVal > ratio ? -0.001d : 0.001d;
        }

        ArrayList<Stop> gradientStops = new ArrayList<>();
        gradientStops.add(new Stop(0, javafx.scene.paint.Color.BLACK));
        gradientStops.add(new Stop(tVal, Color.BLACK));
        Paint paint = new LinearGradient(0, 0, graphicsContext.getCanvas().getWidth(), 2000, false, null, gradientStops);
        graphicsRects.add(new RectToAdd(paint, 0, 0,
                graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight()));

        currentGameManager.setBackgroundChanged(false);
    }

    @Override
    public boolean needsUpdating() {
        if (currentGameManager.isGameChanged()) {
            updated = true;
        }
        return currentGameManager.isBackgroundChanged();
    }

    /**
     * Gets the ratio for the linear gradient -
     * @param clockValue - value of the current day/night clock
     * @return end point of gradient (or ratio)
     */
    public double getRatio(double clockValue) {
        double ratio;
        if (clockValue >= 12){
            ratio = (23.99 - clockValue) / 20;
            if (ratio <= 0) {
                ratio = 0;
            }
        } else {
            ratio = clockValue / 20;
        }
        return ratio;
    }

}
