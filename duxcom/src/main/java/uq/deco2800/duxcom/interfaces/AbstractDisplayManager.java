package uq.deco2800.duxcom.interfaces;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.annotation.MethodContract;

/**
 * Handles the rendering of the on game canvas through render order methods.
 * <p>
 * Created by liamdm on 31/08/2016.
 */
public abstract class AbstractDisplayManager<T extends Enum> {

    /**
     * The current interface
     */
    private T currentDisplay;

    /**
     * Sets the current game display render sequence to use for rendering
     *
     * @param newDisplayType the new display type to use
     */
    @MethodContract(precondition = {"newDisplayType != null"})
    public void setCurrentInterface(T newDisplayType) {
        currentDisplay = newDisplayType;
    }

    /**
     * A store of the graphics handlers
     */
    private ConcurrentHashMap<T, HashMap<RenderOrder, LinkedList<GraphicsHandler>>> handlers = new ConcurrentHashMap<>();

    /**
     * Initialise the handler map to contain all the render order values
     * so graphics handlers can always be added
     *
     * @param interfaceToHandle the interface that should be initialised if it doesn't exist
     * @param renderOrder       the render order that should be initialised if it doesn't exist
     */
    @MethodContract(precondition = {"interfaceToHandle != null", "renderOrder != null"})
    private void initializeHandlerMap(T interfaceToHandle, RenderOrder renderOrder) {
        if (!handlers.containsKey(interfaceToHandle)) {
            handlers.put(interfaceToHandle, new HashMap<>());
        }
        if (!handlers.get(interfaceToHandle).containsKey(renderOrder)) {
            handlers.get(interfaceToHandle).put(renderOrder, new LinkedList<>());
        }
    }

    /**
     * Registers a graphics handler to take control of the given game interface in the given render order
     *
     * @param interfaceToHandle the interface the graphics handler should render for
     * @param renderOrder       the render order requested by the handler
     * @param handler           the graphics handler to use for rendering
     */
    @MethodContract(precondition = {"interfaceToHandle != null", "renderOrder != null", "handler != null",
            "!handlers.get(renderOrder).containsKey(handler)"})
    public void addHandler(T interfaceToHandle, RenderOrder renderOrder, GraphicsHandler handler) {
        initializeHandlerMap(interfaceToHandle, renderOrder);

        if (!handlers.get(interfaceToHandle).get(renderOrder).contains(handler)) {
            handlers.get(interfaceToHandle).get(renderOrder).add(handler);
        }
    }

    /**
     * Clear all the handlers at the specified level
     *
     * @param gameInterface the game display to clear the handlers for
     * @param renderOrder   the render order to clear at
     */
    @MethodContract(precondition = {"gameInterface != null", "renderOrder != null", "handlers.containsKey(renderOrder)"})
    public void clearHandlers(T gameInterface, RenderOrder renderOrder) {
        initializeHandlerMap(gameInterface, renderOrder);
        handlers.get(gameInterface).get(renderOrder).clear();
    }

    /**
     * Calling this will trigger all graphics handlers to render in the order they
     * requested.
     *
     * @param graphicsContext The graphics context used for drawing
     */
    @MethodContract(precondition = {"graphicsContext != null", "gameManager != null"})
    public void render(GraphicsContext graphicsContext) {
        // Render each of the graphics handlers registered in order
        boolean forceRender = false;
        for (RenderOrder order : RenderOrder.values()) {
            initializeHandlerMap(currentDisplay, order);

            for (GraphicsHandler graphicsHandler : handlers.get(currentDisplay).get(order)) {
                if (graphicsHandler.needsRendering()) {
                    graphicsHandler.render(graphicsContext);
                    graphicsHandler.updated = false;
                    forceRender = true;
                } else if (forceRender) {
                    graphicsHandler.render(graphicsContext);
                }
            }
        }
    }

    /**
     * Continuously updates graphics handlers if required
     */
    public void updateGraphicsHandlers(GraphicsContext graphicsContext) {
        for (RenderOrder order : RenderOrder.values()) {
            initializeHandlerMap(currentDisplay, order);

            handlers.get(currentDisplay).get(order).stream().filter(GraphicsHandler::needsUpdating).forEach(graphicsHandler -> {
                graphicsHandler.updateGraphics(graphicsContext);
                graphicsHandler.updated = true;
            });
        }
    }

    /**
     * Constructs the current display
     *
     * @param currentDisplay the current display
     */
    @MethodContract(precondition = {"currentDisplay != null"})
    public AbstractDisplayManager(T currentDisplay) {
        super();
        this.currentDisplay = currentDisplay;
    }

}
