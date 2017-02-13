package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import uq.deco2800.duxcom.coop.MultiplayerGameManager;
import uq.deco2800.duxcom.coop.exceptions.UnexpectedIncorrectGameState;

import java.io.InputStream;

/**
 * The flashbang draw handler
 *
 * Created by liamdm on 20/10/2016.
 */
public class FlashbangDrawHandler extends ContinuousDrawHandler {

    /**
     * The target font
     */
    private Font targetFont;

    /**
     * The drawhandler for flashbands
     * @param refreshInterval the interval of refrehsing
     */
    public FlashbangDrawHandler(long refreshInterval) {
        super(refreshInterval);
        InputStream is = getClass().getResourceAsStream("/fonts/DOWNCOME.ttf");
        if(is == null){
            throw new UnexpectedIncorrectGameState("Could not load font!");
        }
        targetFont = Font.loadFont(is, 50);
    }

    @Override
    public boolean enabled() {
        return MultiplayerGameManager.getFlashbang() != null;
    }

    @Override
    public void handle(GraphicsContext context) {
        Paint fillRestore = context.getFill();
        Paint strokeRestore = context.getStroke();
        Font fontRestore = context.getFont();

        context.setFont(targetFont);

        context.setFill(Color.RED);
        context.fillText(MultiplayerGameManager.getFlashbang(), 400, 400);

        context.setFill(fillRestore);
        context.setStroke(strokeRestore);
        context.setFont(fontRestore);
    }
}
