package uq.deco2800.duxcom.interfaces.overlaymaker;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.handlers.*;

import java.io.IOException;
import java.net.URL;

/**
 * OverlayMaker - Creates an overlay and add to its parent. Using FXML
 *
 * @author The_Magic_Karps
 */
public abstract class OverlayMakerHandler {

    /**
     * Logger
     */
    private static Logger logger = LoggerFactory.getLogger(OverlayMaker.class);

    /**
     * Parent Pane
     */
    protected Pane parent;

    /**
     * Child Pane of overlay (when using OverlayMakerSuperPopUp
     */
    protected Pane underlay;

    /**
     * Disabled mouse press
     */
    protected boolean disabled = false;

    /**
     * Controller of overlay
     */
    protected OverlayMaker controller;

    /**
     * Pane of the overlay
     */
    protected Pane overlay;

    /**
     * GameManager
     */
    protected GameManager gameManager;

    /**
     * Whether the pane is draggable
     */
    protected boolean draggable = false;

    /**
     * Current X position in its parent
     */
    protected double currentXPos;

    /**
     * Current Y Position in its parent
     */
    protected double currentYPos;

    /**
     * X position when clicked mouse event occurred
     */
    protected double clickedX;

    /**
     * Y Position when clicked mouse event occurred
     */
    protected double clickedY;

    /**
     * Event Handler that allows pane to go .toFront when clicked
     */
    private EventHandler<MouseEvent> topEventHandler;

    /**
     * Whether a Pane is centered
     */
    protected boolean center;

    /**
     * Retrieves the fxml and controller of the overlay pane and add into the
     * parent pane
     *
     * @param parent the parent of the overlay pane
     * @throws IOException exception when location does not exist / cannot be
     * opened
     */
    protected OverlayMakerHandler(Pane parent) throws IOException {
        this.parent = parent;
    }

    /**
     * Retrieves the fxml and controller of the overlay pane and add into the
     * parent pane
     *
     * @param parent the parent of the overlay pane
     * @param gameManager manager that stores the data and states of the game
     * @throws IOException exception when location does not exist / cannot be
     * opened
     */
    protected OverlayMakerHandler(Pane parent, GameManager gameManager) throws IOException {
        this.parent = parent;
        this.gameManager = gameManager;
    }

    /**
     * Creates the FXML overlay and its respective controller
     *
     * @param fxml the location of the overlay section
     * @throws IOException exception when location does not exist / cannot be
     * opened
     */
    public void startOverlay(String fxml) throws IOException {
        try {
            URL location = getClass().getResource(fxml);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            overlay = (Pane) fxmlLoader.load();
            fxmlLoader.setLocation(location);
            controller = fxmlLoader.getController();
            if (gameManager != null) {
                controller.startOverlay(gameManager, this);
            } else {
                controller.startOverlay(this);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Creates the FXML overlay and its respective controller.
     *
     * Used to create SuperPopUp overlays that covers the entire screen
     *
     * @param fxml location of the overlay section
     * @param superlay the parent where the superlay resides in
     * @throws IOException when files cannot be opened for various reasons
     */
    protected void startSuperlay(String fxml, Pane superlay) throws IOException {
        try {
            overlay = superlay;
            URL location = getClass().getResource(fxml);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            underlay = (Pane) fxmlLoader.load();
            overlay.getChildren().add(underlay);
            fxmlLoader.setLocation(location);
            controller = fxmlLoader.getController();
            controller.startOverlay(gameManager, this);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Displays the overlay by appending overlay pane into parent pane
     */
    public void showOverlay() {
        if (!parent.getChildren().contains(overlay)) {
            returnDrag();
            parent.getChildren().add(overlay);
        }
    }

    /**
     * Hides the overlay by removing it from its parent pane
     */
    public void hideOverlay() {
        if (parent.getChildren().contains(overlay)) {
            parent.getChildren().remove(overlay);
        }
    }

    /**
     * Centers the Overlay, and make center (0,0) geometric point of reference
     *
     * @param value true to center, false to unbind center
     */
    public void setCenter(boolean value) {
        double xCenter = 0;
        double yCenter = 0;
        center = value;
        if (value) {
            xCenter = (parent.widthProperty().doubleValue()
                    - overlay.getPrefWidth()) / 2;
            yCenter = (parent.heightProperty().doubleValue()
                    - overlay.getPrefHeight()) / 2;
        }
        overlay.setLayoutX(xCenter);
        overlay.setLayoutY(yCenter);
        this.currentXPos = xCenter;
        this.currentYPos = yCenter;
    }

    /**
     * Set event handler to make overlay on top on click
     *
     * @param value true to allow click to top, otherwise false
     */
    public void setClickToTop(boolean value) {
        if (topEventHandler == null) {
            makeTopEvent();
        }
        if (value && underlay == null) {
            overlay.addEventHandler(MouseEvent.MOUSE_PRESSED, topEventHandler);
        } else {
            overlay.removeEventHandler(MouseEvent.MOUSE_PRESSED, topEventHandler);
        }
    }

    /**
     * Parent's background will not accept MouseEvent when this is set to true
     *
     * @param value enables parent's MouseEvents
     */
    public void setDisableBackground(boolean value) {
        this.disabled = value;
        EventHandler noEvent = new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent e) {
                e.consume();
            }
        };
        if (disabled) {
            this.parent.setOnMousePressed(noEvent);
            this.parent.setOnScroll(noEvent);
            this.parent.setOnMouseReleased(noEvent);
            this.parent.setOnMouseDragged(noEvent);
            this.parent.setOnMouseMoved(noEvent);
        } else if (!this.disabled && this.gameManager != null) {
            this.parent.setOnMousePressed(new MousePressedHandler(gameManager));
            this.parent.setOnScroll(new MouseScrolledHandler(gameManager));
            this.parent.setOnMouseReleased(new MouseReleasedHandler(gameManager));
            this.parent.setOnMouseDragged(new MouseDraggedHandler(gameManager));
            this.parent.setOnMouseMoved(new MouseMovedHandler(gameManager));
        }
    }

    /**
     * Change the position of the overlay
     *
     * Note: If overlay is centered, use setCenter(false) to un-center the
     * overlay
     *
     * @param x X position to be set on the overlay
     * @param y Y position to be set on the overlay
     */
    public void setPosition(double x, double y) {
        currentXPos = x;
        currentYPos = y;
        overlay.setLayoutX(currentXPos);
        overlay.setLayoutY(currentYPos);
    }

    /**
     * Return to (0, 0) if overlay is out of screen
     */
    private void returnDrag() {
        double parentWidth = parent.widthProperty().doubleValue();
        double parentHeight = parent.heightProperty().doubleValue();
        int boundary = 83;
        if (draggable && currentXPos > (parentWidth - boundary)
                && currentYPos > (parentHeight - boundary)) {
            currentXPos = 0;
            currentYPos = 0;
            overlay.setLayoutX(currentXPos);
            overlay.setLayoutY(currentYPos);
        }
    }

    /**
     * Set the overlay to be draggable, by dragging with the mouse on the
     * 'dragger' Node
     *
     * @param dragger the Pane to be dragged on
     * @param value true to enable, false to disable
     */
    public void setDraggable(Node dragger, boolean value) {

        this.draggable = value;
        if (draggable) {
            dragger.setOnMousePressed((MouseEvent event) -> {
                clickedX = event.getX();
                clickedY = event.getY();
                event.consume();
            });
            dragger.setOnMouseDragged((MouseEvent event) -> {
                currentXPos += event.getX() - clickedX;
                currentYPos += event.getY() - clickedY;
                if (checkXOnScreen()) {
                    overlay.setLayoutX(currentXPos);
                }
                if (checkYOnScreen()) {
                    overlay.setLayoutY(currentYPos);
                }
                event.consume();
            });
        } else {
            dragger.setOnMousePressed(null);
            dragger.setOnMouseDragged(null);
        }
    }

    /**
     * Checks if the overlay is within visible X range on screen
     *
     * @return true if within visible range, false otherwise
     */
    private boolean checkXOnScreen() {
        double boundary = 80;
        double overlayWidth = overlay.prefWidthProperty().doubleValue();
        double eastMax = parent.widthProperty().doubleValue() - boundary;
        double westMax = 0 - overlayWidth + boundary;
        if (currentXPos > eastMax) {
            currentXPos = eastMax - 3;
            overlay.setLayoutX(currentXPos);
            return false;
        } else if (currentXPos < westMax) {
            currentXPos = westMax + 3;
            overlay.setLayoutX(currentXPos);
            return false;
        }
        return true;
    }

    /**
     * Checks if the overlay is within visible Y range on screen
     *
     * @return true if within range, false otherwise
     */
    private boolean checkYOnScreen() {
        double boundary = 80;
        double overlayHeight = overlay.prefHeightProperty().doubleValue();
        double northMax = 0 - overlayHeight + boundary;
        double southMax = parent.heightProperty().doubleValue() - boundary;
        if (currentYPos < northMax) {
            currentYPos = northMax + 3;
            overlay.setLayoutY(currentYPos);
            return false;
        } else if (currentYPos > southMax) {
            currentYPos = southMax - 3;
            overlay.setLayoutY(currentYPos);
            return false;
        }
        return true;
    }

    /**
     * Creates the EventHandler for making pane .toFront() on mouse press
     */
    private void makeTopEvent() {
        topEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                overlay.toFront();
                e.consume();
            }
        };
    }

    /**
     * Retrieves the draggable status of the overlay
     *
     * @return draggable status of the overlay
     */
    public boolean isDraggable() {
        return this.draggable;
    }

    /**
     * Retrieves the controller of the overlay class
     *
     * @return controller of the overlay class
     */
    public OverlayMaker getController() {
        return this.controller;
    }

    /**
     * Retrieves the pane of the overlay
     *
     * @return pane of the overlay
     */
    public Pane getOverlay() {
        return this.overlay;
    }

    /**
     * Retrieves the pane of the parent where the overlay resides
     *
     * @return pane of the parent where the overlay resides
     */
    public Pane getParent() {
        return this.parent;
    }

    /**
     * Shows whether the overlay is centered with relative position of (0,0)
     *
     * Use setCenter(boolean) to change setting
     *
     * @return true if overlay is centered, false if not
     */
    public boolean isCenter() {
        return center;
    }

    /**
     * Check if overlay is currently active. (An existing children in its parent
     * pane)
     *
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return parent.getChildren().contains(overlay);
    }

    /**
     * Initial settings when creating a new overlay
     */
    public abstract void initDefault();
}
