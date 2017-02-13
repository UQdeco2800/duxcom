package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.messaging.GameMessageQueue;

/**
 * Handles the display of messages
 * Created by liamdm on 21/09/2016.
 */
public class MessageGraphicsHandler extends GraphicsHandler {

    public static final int LINE_HEIGHT = 15;
    public static final int MAX_LENGTH = 150;

    @Override
    public void render(GraphicsContext graphicsContext) {
        if (currentGameManager.isChatVisible()) {
            super.render(graphicsContext);
        }
    }

    @Override
    public void updateGraphics(GraphicsContext graphicsContext) {

        graphicsTexts.clear();

        final double messageStartY = graphicsContext.getCanvas().getHeight() - 10;
        final double messageStartX = 5;

        String[] messageList = GameMessageQueue.getVisibleMessages();
        for(int i = 0; i < messageList.length; ++i){
            double yPos = messageStartY - i * LINE_HEIGHT;

            String message = messageList[i];

            Paint color = GameMessageQueue.getColor(message);

            message = GameMessageQueue.getMessage(message);

            if(message.length() > MAX_LENGTH){
                message = message.substring(0, MAX_LENGTH) + "...";
            }

            graphicsTexts.add(new TextToAdd(color, message, messageStartX, yPos));

        }

        GameMessageQueue.setNewMessage(false);

    }

    @Override
    public boolean needsUpdating(){
        return GameMessageQueue.getNewMessage();
    }

}
