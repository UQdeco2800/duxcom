package uq.deco2800.duxcom.interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.dataregisters.TileDataRegister;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.LiveTile;

import java.util.ArrayList;

/**
 * The graphics handler allows classes that inherit it to store lists of graphics which can be
 * rendered directly to the JavaFX canvas upon request.
 * <p>
 * The graphics which can be stored in the lists must be in the form of the contained static classes.
 * <p>
 * Currently there is support for rendering Images, Rectangles, Ovals, Text, and Polygons.
 * <p>
 * Each graphics handler also has a boolean variable which indicates whether or not its lists of
 * graphics have been updated.
 * <p>
 * Each graphics handler will have its own method to update the list of graphics it wishes to display on the canvas.
 * <p>
 * Each graphics handler will also have a method which can determine whether or not it needs to update its graphics,
 * this method will depend on flags in the supplied GameManager.
 *
 * @author Alex McLean
 */
public abstract class GraphicsHandler {

    /**
     * The game manager of the handler
     */
    public final GameManager currentGameManager = GameLoop.getCurrentGameManager();

    /**
     * Variables used by the graphics handlers to render at a given position
     */
    protected static final int TILE_HEIGHT = TileDataRegister.TILE_HEIGHT;
    protected static final int TILE_WIDTH = TileDataRegister.TILE_WIDTH;
    protected double scale;
    protected double scaledWidth;
    protected double scaledHeight;
    protected double x;
    protected double y;
    protected String graphicName;
    protected double anchorX;
    protected double anchorY;
    protected double offsetX;
    protected double offsetY;
    protected double baseX;
    protected double baseY = 0;

    /**
     * Class used to store an image which need sto be rendered
     */
    public static class ImageToAdd {

        /**
         * The Image to be rendered.
         */
        public final Image image;

        /**
         * the x position of the Image to be rendered.
         */
        public final double x;

        /**
         * the y position of the Image to be rendered.
         */
        public final double y;

        /**
         * the scaledWidth of the Image to be rendered.
         */
        public final double scaledWidth;

        /**
         * the scaledHeight of the Image to be rendered.
         */
        public final double scaledHeight;

        /**
         * Stores an Image that will later be added to the JavaFX canvas
         *
         * @param image        image to be added later
         * @param x            x coordinate to add at
         * @param y            y coordinate to add at
         * @param scaledWidth  desired width of the render
         * @param scaledHeight desired height of the render
         */
        public ImageToAdd(Image image, double x, double y, double scaledWidth, double scaledHeight) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.scaledWidth = scaledWidth;
            this.scaledHeight = scaledHeight;
        }
    }

    /**
     * Class used to store a rectangle which needs to be rendered
     */
    public static class RectToAdd {

        /**
         * The Paint to fill the Rectangle to be rendered
         */
        public final Paint paint;

        /**
         * the x position of the Rectangle to be rendered.
         */
        public final double x;

        /**
         * the y position of the Rectangle to be rendered.
         */
        public final double y;

        /**
         * the scaledWidth of the Rectangle to be rendered.
         */
        public final double scaledWidth;

        /**
         * the scaledHeight of the Rectangle to be rendered.
         */
        public final double scaledHeight;

        /**
         * Stores rectangle parameters that will later be added to the JavaFX canvas
         *
         * @param paint        paint color of the rectangle
         * @param x            top left x coordinate to add at
         * @param y            top left y coordinate to add at
         * @param scaledWidth  desired width of the rectangle
         * @param scaledHeight desired height of the rectangle
         */
        public RectToAdd(Paint paint, double x, double y, double scaledWidth, double scaledHeight) {
            this.paint = paint;
            this.x = x;
            this.y = y;
            this.scaledWidth = scaledWidth;
            this.scaledHeight = scaledHeight;
        }
    }

    /**
     * Class used to store an oval which needs to be rendered
     */
    public static class OvalToAdd {

        /**
         * the Paint to fill the Oval to be rendered.
         */
        public final Paint paint;

        /**
         * the x position of the Oval to be rendered.
         */
        public final double x;

        /**
         * the y position of the Oval to be rendered.
         */
        public final double y;

        /**
         * the scaledWidth of the Oval to be rendered.
         */
        public final double scaledWidth;

        /**
         * the scaledHeight of the Oval to be rendered.
         */
        public final double scaledHeight;

        /**
         * Stores oval parameters that will later be added to the JavaFX canvas
         *
         * @param paint        paint color of the oval
         * @param x            top left x coordinate to add at
         * @param y            top left y coordinate to add at
         * @param scaledWidth  desired width of the oval
         * @param scaledHeight desired height of the oval
         */
        public OvalToAdd(Paint paint, double x, double y, double scaledWidth, double scaledHeight) {
            this.paint = paint;
            this.x = x;
            this.y = y;
            this.scaledWidth = scaledWidth;
            this.scaledHeight = scaledHeight;
        }
    }

    /**
     * Class used to store a polygon which needs to be rendered
     */
    public static class PolygonToAdd {

        /**
         * the Paint to fill the Polygon
         */
        public final Paint paint;

        /**
         * the x points to form the Polygon
         */
        public final double[] xPoints;

        /**
         * the y points to form the Polygon
         */
        public final double[] yPoints;

        /**
         * the total number of points in the Polygon
         */
        public final int numPoints;

        /**
         * Stores polygon parameters that will later be added to the JavaFX canvas
         *
         * @param paint     paint color of the polygon
         * @param xPoints   array of x coordinates of polygon
         * @param yPoints   array of y coordinates of polygon
         * @param numPoints number of points in polygon
         */
        public PolygonToAdd(Paint paint, double[] xPoints, double[] yPoints, int numPoints) {
            this.paint = paint;
            this.xPoints = xPoints;
            this.yPoints = yPoints;
            this.numPoints = numPoints;
        }
    }

    /**
     * Class used to store text which needs to be rendered
     */
    public static class TextToAdd {

        /**
         * the Paint to fill the Text
         */
        public final Paint paint;

        /**
         * the String message
         */
        public final String message;

        /**
         * the x position of the Text to be rendered
         */
        public final double x;

        /**
         * the y position of the Text to be rendered
         */
        public final double y;

        /**
         * Stores text that will later be added to the JavaFX canvas
         *
         * @param paint   paint color of the text
         * @param message text message
         * @param x       bottom left x coordinate to add at
         * @param y       bottom left y coordinate to add at
         */
        public TextToAdd(Paint paint, String message, double x, double y) {
            this.paint = paint;
            this.message = message;
            this.x = x;
            this.y = y;
        }
    }

    // Lists storing graphics to be rendered to JavaFX canvas
    protected ArrayList<ImageToAdd> graphicsImages = new ArrayList<>();
    protected ArrayList<RectToAdd> graphicsRects = new ArrayList<>();
    protected ArrayList<OvalToAdd> graphicsOvals = new ArrayList<>();
    protected ArrayList<PolygonToAdd> graphicsPolygons = new ArrayList<>();
    protected ArrayList<TextToAdd> graphicsTexts = new ArrayList<>();
    protected boolean updated = false;

    /**
     * Renders all images from the stored lists onto the givens graphics context.
     *
     * @param graphicsContext the context to render into
     */
    public void render(GraphicsContext graphicsContext) {
        for (ImageToAdd toAdd : graphicsImages) {
            graphicsContext.drawImage(toAdd.image, toAdd.x, toAdd.y, toAdd.scaledWidth, toAdd.scaledHeight);
        }
        for (RectToAdd toAdd : graphicsRects) {
            graphicsContext.setFill(toAdd.paint);
            graphicsContext.fillRect(toAdd.x, toAdd.y, toAdd.scaledWidth, toAdd.scaledHeight);
        }
        for (OvalToAdd toAdd : graphicsOvals) {
            graphicsContext.setFill(toAdd.paint);
            graphicsContext.fillOval(toAdd.x, toAdd.y, toAdd.scaledWidth, toAdd.scaledHeight);
        }
        for (PolygonToAdd toAdd : graphicsPolygons) {
            graphicsContext.setFill(toAdd.paint);
            graphicsContext.fillPolygon(toAdd.xPoints, toAdd.yPoints, toAdd.numPoints);
        }
        for (TextToAdd toAdd : graphicsTexts) {
            graphicsContext.setFill(toAdd.paint);
            graphicsContext.fillText(toAdd.message, toAdd.x, toAdd.y);
        }
    }

    /**
     * Must be overrode by extending classes.
     * Updates the lists of graphics that need to be rendered onto the screen when render() is called.
     *
     * @param graphicsContext the context of the graphics
     */
    public abstract void updateGraphics(GraphicsContext graphicsContext);

    /**
     * Must be overrode by extending classes.
     * Determines whether or not the graphics need updating, based on the status of flags in GameManager.
     *
     * @return true iff graphics need updating
     */
    public abstract boolean needsUpdating();

    /**
     * Determines whether or not the graphics handler has updated and thus needs to be rendered
     *
     * @return true iff updated
     */
    public boolean needsRendering() {
        return updated;
    }

    /**
     * Allows for an image to be added to the list of images for future rendering
     *
     * @param graphicName the name of the graphic to add
     */
    public void addImage(String graphicName) {
        javafx.scene.image.Image image = TextureRegister.getTexture(graphicName);
        anchorX = (image.getWidth() - TILE_WIDTH) / 2.0 * scale;
        anchorY = (image.getHeight() - TILE_HEIGHT) / 2.0 * scale;
        offsetX = x - anchorX + currentGameManager.getxOffset();
        offsetY = y - anchorY + currentGameManager.getyOffset();


        graphicsImages.add(new ImageToAdd(image, offsetX, offsetY, scaledWidth * image.getWidth() / TILE_WIDTH,
                scaledHeight * image.getHeight() / TILE_HEIGHT));
    }

    /**
     * Currently does nothing?
     */
    public void loadLiveTileSettings() {

    }

    public void drawLiveTile(LiveTile liveTile, boolean floor, MapAssembly map) {

        String resource;
        if (floor) {
            resource = liveTile.getCurrentBaseTexture();
        } else {
            resource = liveTile.getCurrentFrameTexture();
        }

        Image image;
        if (map.getTile(liveTile.getX(), liveTile.getY()).isHidden()) {
            image = TextureRegister.getFadedTexture(resource);
        } else {
            image = TextureRegister.getTexture(resource);
        }

        //the position of the entity in the 2D array
        double i = liveTile.getX() - liveTile.getElevation();
        double j = liveTile.getY() - liveTile.getElevation();

        //math transformation logic
        x = baseX + (j - i) * scaledWidth / 2;
        y = baseY + (j + i) * scaledHeight / 2;

        //considers the size of the image to ensure it is rendered from the correct point
        anchorY = (image.getHeight() - TILE_HEIGHT) / 2.0 * scale;
        anchorX = (image.getWidth() - TILE_WIDTH) / 2.0 * scale;

        offsetX = x - anchorX + currentGameManager.getxOffset();
        offsetY = y - anchorY + currentGameManager.getyOffset();

        graphicsImages.add(new ImageToAdd(image, offsetX, offsetY, scaledWidth * image.getWidth() / TILE_WIDTH,
                scaledHeight * image.getHeight() / TILE_HEIGHT));
    }
}
