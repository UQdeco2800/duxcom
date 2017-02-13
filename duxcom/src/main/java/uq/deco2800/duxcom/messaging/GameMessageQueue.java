package uq.deco2800.duxcom.messaging;

import javafx.scene.paint.Color;
import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.duxcom.controllers.UserInterfaceController;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Stores messages to be displayed on the game screen.
 *
 * Created by liamdm on 21/09/2016.
 */
public class GameMessageQueue {
    public static final String DELIM = ",";

    // messages to display on screen
    public static final int MAX_MESSAGES = 50;

    private static final ConcurrentLinkedDeque<String> messageStore = new ConcurrentLinkedDeque<>();
    private static final ConcurrentLinkedDeque<String> messages = new ConcurrentLinkedDeque<>();

    private static int lastMessageHash = 0;
    private static String[] visibleMessageCache = new String[]{};

    private static boolean newMessage = false;

    public static void setNewMessage(boolean set) {
        newMessage = set;
    }

    public static boolean getNewMessage() {
        return newMessage;
    }

    public static Color getColor(String raw){
        String cString = raw.substring(0, raw.indexOf(DELIM));
        Color c = Color.valueOf(cString);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), 1);
    }

    public static String getMessage(String raw){
        return raw.substring(raw.indexOf(DELIM) + 1);
    }

    /**
     * Adds a newly recieved message
     * @param message
     */
    public static void add(String message, Color color){
        String cString = color == null ? Color.WHITE.toString() : color.toString();
        messages.addFirst(cString + DELIM + message);

        UserInterfaceController.recievedMessage();

        //if(messages.size() > MAX_MESSAGES){
        //    messageStore.add(messages.poll());
        //}

        lastMessageHash = 0;

        newMessage = true;
    }

    public static void add(String message){
        add(message, null);
    }

    /**
     * Gets an array of messages visible on screen
     * @return
     */
    public static String[] getVisibleMessages(){
        if(messages.hashCode() != lastMessageHash) {
            // todo prevent race condition here
            lastMessageHash = messages.hashCode();
            visibleMessageCache = messages.toArray(new String[0]);
        }

        return visibleMessageCache;
    }

    @UtilityConstructor
    private GameMessageQueue(){}
}
