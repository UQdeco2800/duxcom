package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.coop.exceptions.UnexpectedIncorrectGameState;

import java.io.InputStream;

/**
 * The current hero name draw handler
 *
 * Created by liamdm on 20/10/2016.
 */
public class CurrentHeroNameDrawHandler extends ContinuousDrawHandler {

    /**
     * The target font
     */
    private Font targetFont;

    /**
     * The current hero name draw handler
     * @param refreshInterval the refresh interval of the handle
     */
    public CurrentHeroNameDrawHandler(long refreshInterval) {
        super(refreshInterval);
        InputStream is = getClass().getResourceAsStream("/fonts/DOWNCOME.TTF");
        if(is == null){
            throw new UnexpectedIncorrectGameState("Could not load font!");
        }
        targetFont = Font.loadFont(is, 50);
    }

    @Override
    public boolean enabled() {
        return GameLoop.getCurrentGameManager() != null
                && GameLoop.getCurrentGameManager().isMultiplayer()
                && GameLoop.getCurrentGameManager().getMultiplayerGameManager().isMultiplayer()
                && GameLoop.getCurrentGameManager().getMultiplayerGameManager().areClientsSynced();
    }

    @Override
    public void handle(GraphicsContext context) {
        Paint fillRestore = context.getFill();
        Paint strokeRestore = context.getStroke();
        Font fontRestore = context.getFont();

        context.setFont(targetFont);

        String currentName = GameLoop.getCurrentGameManager().getMultiplayerGameManager().getHeroManager().getCurrentPlayer();

        if(GameLoop.getCurrentGameManager().getMultiplayerGameManager().isCurrentTurn()) {
            context.setFill(Color.WHITE);
            context.fillText("Your turn " + currentName, 100, 100);
        } else {
            context.setFill(Color.RED);
            context.fillText("Waiting for " + currentName, 100, 100);
        }

        context.setFill(fillRestore);
        context.setStroke(strokeRestore);
        context.setFont(fontRestore);
    }
}
