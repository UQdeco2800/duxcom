package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook;

import javafx.scene.canvas.GraphicsContext;

/**
 * Handles continuous drawing to canvas.
 *
 * Created by liamdm on 18/10/2016.
 */
public abstract class ContinuousDrawHandler {

    /**
     * If this class should allow tick skipping
     */
    public boolean allowTickSkip(){
        return false;
    }

    /**
     * The estimated delay of your class
     */
    public double estimatedDelay(){
        return 0.001;
    }

    /**
     * If the continuous draw handler is enabled
     */
    public abstract boolean enabled();

    /**
     * The last millis drawn
     */
    private long lastMillis = 0;

    /**
     * Minimum millis to wait
     */
    private long minWait = 0;

    /**
     * Sets the interval in millis at-least between re-draws
     * @param millis the millis to wait
     */
    public void setInterval(long millis){
        minWait = millis;
    }

    /**
     * The interval between renders
     * @return
     */
    public long getInterval(){
        return minWait;
    }

    /**
     * Gets delta since last render
     */
    public long getDelta(){
        return System.currentTimeMillis() - lastMillis;
    }

    /**
     * The raw handle call
     */
    public void handleRaw(GraphicsContext context){
        if(getDelta() > minWait){
            handle(context);
        }
    }

    /**
     * Handle the rendering
     * @param context the context to draw on
     */
    public abstract void handle(GraphicsContext context);

    public ContinuousDrawHandler(long refreshInterval) {
        super();
        this.minWait = refreshInterval;
    }
}
