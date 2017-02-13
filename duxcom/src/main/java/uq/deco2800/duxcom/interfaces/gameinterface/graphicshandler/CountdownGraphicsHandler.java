package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import uq.deco2800.duxcom.coop.exceptions.UnexpectedIncorrectGameState;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;

import java.io.InputStream;

/**
 * Counts down till multiplayer begins
 *
 * Created by liamdm on 16/10/2016.
 */
public class CountdownGraphicsHandler extends GraphicsHandler {

    private Font targetFont;

    public CountdownGraphicsHandler() {
        InputStream is = getClass().getResourceAsStream("/fonts/DOWNCOME.TTF");
        if(is == null){
            throw new UnexpectedIncorrectGameState("Could not load font!");
        }
        targetFont = Font.loadFont(is, 50);
    }

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {
        if(currentGameManager.getMultiplayerGameManager().getGameCountdown().getCountdownNumber() == 0){
            return;
        }

        Paint fillRestore = graphicsContext.getFill();
        Paint strokeRestore = graphicsContext.getStroke();
        Font fontRestore = graphicsContext.getFont();

        double cWidth = graphicsContext.getCanvas().getWidth();
        double cHeight = graphicsContext.getCanvas().getHeight();
        double midPointX = cWidth / 2d;
        double midPointY = cHeight / 2d;

        double bannerHeight = 0.2 * cHeight;
        double bannerStartY = midPointY - (bannerHeight / 2);

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, bannerStartY, cWidth, bannerHeight);

        double textSize = 50;

        graphicsContext.setStroke(Color.RED);
        graphicsContext.setFill(Color.RED);
        graphicsContext.setFont(targetFont);

        String toWrite;

        if(!currentGameManager.getMultiplayerGameManager().areClientsSynced()){
            toWrite = "Waiting for clients to synchronise";
        } else {
            toWrite ="Game starts in " + currentGameManager.getMultiplayerGameManager().getGameCountdown().getCountdownNumber();
        }

        double textWidth = toWrite.length() * textSize;
        double textStartX = midPointX - (textWidth / 2d) + (textWidth * 0.1);
        double textStartY = midPointY - (textSize / 2d);

        graphicsContext.fillText(toWrite, textStartX, textStartY);

        graphicsContext.setFill(fillRestore);
        graphicsContext.setStroke(strokeRestore);
        graphicsContext.setFont(fontRestore);
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isMultiplayer() &&
                (!currentGameManager.getMultiplayerGameManager().areClientsSynced()
                || currentGameManager.getMultiplayerGameManager().getGameCountdown().isCountingDown());
    }
}
