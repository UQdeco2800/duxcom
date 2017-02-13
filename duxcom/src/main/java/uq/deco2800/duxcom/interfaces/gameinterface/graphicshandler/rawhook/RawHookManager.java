package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Manages the raw graphics hooks.
 *
 * Created by liamdm on 18/10/2016.
 */
public class RawHookManager {

    /**
     * The handler list
     */
    private LinkedList<ContinuousDrawHandler> handlerList = new LinkedList<>();

    /**
     * Create the hook manager with the relevant handlers
     * @param handlers the handlers to create it with
     */
    public RawHookManager(ContinuousDrawHandler ... handlers) {
        if(handlers.length > 0) {
            Collections.addAll(handlerList, handlers);
        }
    }

    public void addHandler(ContinuousDrawHandler handler){
        handlerList.add(handler);
    }

    /**
     * Raw render the continuous drawables
     * @param maxTickDelay the maximum delay incurrable
     */
    public int rawRender(GraphicsContext graphicsContext, double maxTickDelay){
        int handlersSkipped = 0;
        double usedDelay = 0;

        for(ContinuousDrawHandler handler : handlerList){
            if(usedDelay + handler.estimatedDelay() > maxTickDelay && handler.allowTickSkip()
                    && handler.getDelta() - 500 < handler.getInterval()){
                // skip the handler
                ++handlersSkipped;
                continue;
            }

            if(!handler.enabled()){
                continue;
            }

            // run the handler
            usedDelay += handler.estimatedDelay();
            handler.handle(graphicsContext);
        }

        return handlersSkipped;
    }
}
