package uq.deco2800.duxcom.interfaces.gameinterface.statusbars;

import javafx.scene.paint.Color;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

import java.util.List;

/**
 * Abstraction and minor redesign of original Mini-Health-Bar to be used
 * in game on Enemies and Heroes
 *
 * Created by jay-grant on 5/10/2016.
 */
public class MiniHealthBar {

    private double healthBarHeight;
    private double healthBarWidth;

    /**
     * The health bar of a character should not obscure the top of their sprite, so a fixed offset for the health bar
     * to 'hover' above the character is set.
     */
    private static double healthBarHover;

    public MiniHealthBar(double height, double width, double hover) {
        healthBarHeight = height;
        healthBarWidth = width;
        healthBarHover = hover;
    }

    private double backDropShadow = 2;
    private double backHealthBarHeight = healthBarHeight + backDropShadow;
    private double backHealthBarWidth = healthBarWidth;

    private static final double LOW_HEALTH_THRESHOLD = 0.5;
    private static final double VERY_LOW_HEALTH_THRESHOLD = 0.25;

    /**
     * Draws the mini health bar which appears above each hero's head on the map.
     * @param target Entity whose mini health bar is being drawn
     * @param graphicsRects list of rectangles to draw
     * @param offsetX X coordinate from which this hero sprite is drawn
     * @param offsetY Y coordinate from which this hero sprite is drawn
     * @param scale Represents the current zoom level
     */
    public void draw(AbstractCharacter target, List<GraphicsHandler.RectToAdd> graphicsRects, double offsetX,
                     double offsetY, double scale, Color backGround, double height, double width) {
        this.healthBarHeight = height;
        this.healthBarWidth = width;
        backDropShadow = 2;
        backHealthBarHeight = healthBarHeight + backDropShadow;
        backHealthBarWidth = healthBarWidth;
        double miniHealthBarX = offsetX;
        double miniHealthBarY = offsetY - (scale * healthBarHover);

        // Draw the black background to the mini health bar
        graphicsRects.add(new GraphicsHandler.RectToAdd(backGround,
                miniHealthBarX, miniHealthBarY + backDropShadow /2, backHealthBarWidth * scale,
                backHealthBarHeight * scale));

        // Draw the coloured filling on top of and inside the black underlay to show how much hero health remains.
        // Colour of the filling will vary depending on how much health the hero has left.
        double healthLeft = target.getRatioHealthRemaining();
        Color color;
        if (healthLeft < VERY_LOW_HEALTH_THRESHOLD) {
            color = Color.RED;
        } else if (healthLeft < LOW_HEALTH_THRESHOLD) {
            color = Color.GOLD;
        } else {
            color = Color.LIME;
        }
        graphicsRects.add(new GraphicsHandler.RectToAdd(color, miniHealthBarX,
                miniHealthBarY, healthBarWidth * scale * healthLeft,
                healthBarHeight * scale));
    }
}
