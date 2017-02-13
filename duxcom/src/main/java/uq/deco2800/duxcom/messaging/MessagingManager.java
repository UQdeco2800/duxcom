package uq.deco2800.duxcom.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.paint.Color;
import uq.deco2800.duxcom.annotation.UtilityConstructor;
import uq.deco2800.duxcom.coop.SingularityTarget;
import uq.deco2800.singularity.clients.realtime.messaging.BroadcastMessagingClient;
import uq.deco2800.singularity.clients.realtime.messaging.BroadcastMessagingEventListener;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;
import uq.deco2800.singularity.common.ServerConstants;
import uq.deco2800.singularity.common.SessionType;
import uq.deco2800.singularity.common.representations.realtime.BroadcastMessage;
import uq.deco2800.singularity.common.representations.realtime.RealTimeSessionConfiguration;

/**
 * Handles messaging for a given client
 * <p>
 * Created by liamdm on 21/09/2016.
 */
public class MessagingManager {

    private static Logger logger = LoggerFactory.getLogger(MessagingManager.class);

    private static final String GLOBAL_CHANNEL = "global";

    private static BroadcastMessagingClient bmc;
    private static AtomicBoolean hasInitialised = new AtomicBoolean(false);

    private static boolean gotSomething = false;
    private static boolean offline = false;
    private static String cacheUsername = null;

    private static boolean messagingFailed = false;

    // yes these are string not char[], these really aren't critical
    private static String cacheAdminPassword = null;
    private static String cacheIbisPassword = null;

    private static ArrayList<String> cacheChannels = new ArrayList<>();

    /**
     * Initialises the messaging service
     */
    public static void init() throws IOException {
        if (hasInitialised.getAndSet(true)) {
            logger.warn("Tried to re-init messaging manager...");
            return;
        }

        SingularityRestClient restClient = new SingularityRestClient(SingularityTarget.getCurrentTarget(), ServerConstants.REST_PORT);
        RealTimeSessionConfiguration configuration = new RealTimeSessionConfiguration();

        configuration.setPort(ServerConstants.MESSAGING_PORT);
        configuration.setSession(SessionType.BROADCAST);
        bmc = new BroadcastMessagingClient(configuration, restClient);

        logger.info("Broadcast messaging manager initialised [{}]", bmc);

        bmc.addListener(new BroadcastMessagingEventListener() {
            @Override
            public void recievedBroadcastMessage(BroadcastMessage message) {
                gotSomething = true;

                if (message.getMessageType().equals(BroadcastMessage.MessageType.SEND_MESSAGE)) {
                    String formattedMessage = "[" + message.getTargetChannel() + "] " + message.getSource() + ": " + message.getMessage();
                    if (message.isLocal()) {
                        GameMessageQueue.add(formattedMessage, Color.AQUA);
                    } else if (("ibis").equals(message.getSource()) || ("server").equals(message.getSource())) {
                        GameMessageQueue.add(formattedMessage, Color.ORANGE);
                    } else {
                        GameMessageQueue.add(formattedMessage, Color.WHITE);
                    }
                } else {
                    switch (message.getMessageType()) {
                        case NICKNAME_REJECT:
                            GameMessageQueue.add("server: your nickname was rejected, reason: " + message.getMessage(), Color.ORANGE);
                            break;
                        case NICKNAME_RESPONSE:
                            cacheUsername = message.getMessage();
                            GameMessageQueue.add("server: your nickname was accepted: " + message.getMessage(), Color.ORANGE);
                            break;
                        case ADMIN_GRANT:
                            GameMessageQueue.add("server: you were authenticated as an admin!", Color.ORANGE);
                            break;
                        case IBIS_GRANT:
                            GameMessageQueue.add("server: you were authenticated as an ibis!", Color.ORANGE);
                            break;
                        case AUTH_FAIL:
                            GameMessageQueue.add("server: your login was rejected, this will be logged!", Color.ORANGE);
                            break;
                        case REGISTRATION_REPLY:
                            GameMessageQueue.add("server: you were succesfully registered!", Color.ORANGE);
                            break;
                        case NOT_REGISTERED:
                            GameMessageQueue.add("server: we don't know you, try /register!", Color.ORANGE);
                            break;
                        case GENERAL_FAILURE:
                            GameMessageQueue.add("server: general failure, reason: " + message.getMessage(), Color.ORANGE);
                            break;
                        default:
                            GameMessageQueue.add("server: unanticipated failure, reason: " + message.getMessage(), Color.ORANGE);
                    }
                }

            }

            @Override
            public void disconnected() {
                if (offline) {
                    return;
                }

                gotSomething = false;
                for (int i = 1; i <= 10 && !gotSomething; ++i) {
                    GameMessageQueue.add("You were disconnected! Trying to re-connect in 5 seconds... Retry " + i + " of 10...", Color.RED);

                    bmc.removeListener(this);
                    try {
                        bmc = new BroadcastMessagingClient(configuration, restClient);
                    } catch (IOException e) {
                        logger.error("Failed to create broadcast messaging client!", e);
                    }
                    bmc.addListener(this);

                    bmc.sendMessage(BroadcastMessage.register());
                    bmc.sendMessage(BroadcastMessage.joinChannel(GLOBAL_CHANNEL));

                    if (cacheAdminPassword != null) {
                        bmc.sendMessage(BroadcastMessage.login(cacheAdminPassword, false));
                    }
                    if (cacheIbisPassword != null) {
                        bmc.sendMessage(BroadcastMessage.login(cacheIbisPassword, true));
                    }
                    if (cacheUsername != null) {
                        bmc.sendMessage(BroadcastMessage.getNickname(cacheUsername));
                    }
                    for (String channel : cacheChannels) {
                        bmc.sendMessage(BroadcastMessage.joinChannel(channel));
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                if (gotSomething) {
                    GameMessageQueue.add("Reconnected to server!", Color.GREEN);
                } else {
                    GameMessageQueue.add("Failed to connect to server!", Color.RED);
                }
            }
        });

        bmc.sendMessage(BroadcastMessage.register());
        bmc.sendMessage(BroadcastMessage.joinChannel(GLOBAL_CHANNEL));

        GameMessageQueue.add("If you would like to get help on messages, type /help below!", Color.RED);

    }

    /**
     * Handle a message in the message console
     *
     * @param message
     */
    public static void parseMessage(String message) {
        if (message.length() > 500) {
            GameMessageQueue.add("Your message was too long! Must be < 500 characters!", Color.RED);
            return;
        }

        String strippedMessage = message.trim();
        if (strippedMessage.startsWith("/")) {
            handleCommand(message);
        } else {
            BroadcastMessage bm = BroadcastMessage.createBroadcastMessage(message, GLOBAL_CHANNEL);
            bmc.sendMessage(bm);
        }

    }

    private static boolean handleCommand(String raw) {

        String strippedMessage = raw.trim();
        String command;
        boolean noParam = !strippedMessage.contains(" ");

        if (noParam) {
            // single command
            command = strippedMessage.toLowerCase().substring(1);
        } else {
            command = strippedMessage.toLowerCase().substring(1, strippedMessage.indexOf(' '));
        }

        GameMessageQueue.add(raw, Color.DARKGRAY);

        String parameters = "";
        if (!noParam) {
            parameters = strippedMessage.substring(strippedMessage.indexOf(' ') + 1, strippedMessage.length());
            if (parameters.length() > 150) {
                GameMessageQueue.add("Your command was too long!", Color.RED);
            }
            parameters = parameters.trim();
            if (parameters.isEmpty()) {
                GameMessageQueue.add("Your command had no parameters!", Color.RED);
            }
        }

        // help
        if ((command.contains("help") && command.length() < 6) || ("h").equals(command)) {
            printHelp();
        } else if (("leaveall").equals(command)) {
            cacheChannels.clear();
            bmc.sendMessage(BroadcastMessage.leaveAllChannels());
            GameMessageQueue.add("You just left all channels, to rejoin master go /join global!", Color.RED);
        } else if (("leave").equals(command)) {
            if (noParam) {
                GameMessageQueue.add("Please specify the channel to leave!", Color.RED);
                return false;
            }
            cacheChannels.remove(parameters);
            bmc.sendMessage(BroadcastMessage.leaveChannel(parameters));
            GameMessageQueue.add("You just left the channel \"" + parameters + "\"!", Color.RED);
        } else if (("offline").equals(command)) {
            bmc.sendMessage(BroadcastMessage.leaveAllChannels());
            bmc.sendMessage(BroadcastMessage.goOffline());
            offline = true;
            GameMessageQueue.add("You are now offline, and cannot come back online!", Color.RED);
        } else if (("nick").equals(command) || ("nickname").equals(command)) {
            if (parameters.length() < 4 || parameters.length() > 15) {
                GameMessageQueue.add("Nickname must be > 4 and < 15 characters long!", Color.RED);
                return false;
            }
            bmc.sendMessage(BroadcastMessage.getNickname(parameters));
            GameMessageQueue.add("Trying to get nickname \"" + parameters + "\" from server...", Color.GREY);
        } else if (("join").equals(command)) {
            if (parameters.length() > 20) {
                GameMessageQueue.add("Channel names must be < 20 characters long!", Color.RED);
                return false;
            }
            if (!cacheChannels.contains(parameters)) {
                cacheChannels.add(parameters);
            }
            bmc.sendMessage(BroadcastMessage.joinChannel(parameters));
            GameMessageQueue.add("You have been subscribed to the channel \"" + parameters + "\"!");
        } else if (("login").equals(command)) {
            cacheAdminPassword = parameters;
            bmc.sendMessage(BroadcastMessage.login(parameters, false));
        } else if (("loginibis").equals(command)) {
            cacheIbisPassword = parameters;
            bmc.sendMessage(BroadcastMessage.login(parameters, true));
        } else if (("register").equals(command)) {
            offline = false;
            bmc.sendMessage(BroadcastMessage.register());
        } else if (command.endsWith(":")) {
            // message to a channel
            if (command.length() > 21) {
                GameMessageQueue.add("Channel names must be < 20 characters long!", Color.RED);
                return false;
            }
            String channel = command.substring(0, command.length() - 1);
            bmc.sendMessage(BroadcastMessage.createBroadcastMessage(parameters, channel));
        }
        return true;
    }

    private static void printHelp() {
        Color helpColor = Color.RED;
        GameMessageQueue.add("=== Help for message system === ", helpColor);
        GameMessageQueue.add("To send a message to LITERALLY EVERYONE type a message below and press enter", helpColor);
        GameMessageQueue.add("To change your nickname, go /nick <nickname>. Your new nickname cannot start with server -.-", helpColor);
        GameMessageQueue.add("To join a channel type /join <channel>", helpColor);
        GameMessageQueue.add("To message a specific channel go /<channel>: your message", helpColor);
        GameMessageQueue.add("To leave a channel go /leave <channel>, or to leave everything go /leaveall", helpColor);
        GameMessageQueue.add("To login as an admin, go /login <password>", helpColor);
        GameMessageQueue.add("To go offline type /offline, you cannot come back online!", helpColor);
        GameMessageQueue.add("If you try and break this you will succeed.", helpColor);
    }

    @UtilityConstructor
    private MessagingManager(){}

    /**
     * Called when mesaging fails
     */
    public static void failMessaging() {
        messagingFailed = true;
    }

    public static boolean isMessagingFailed() {
        return messagingFailed;
    }
}
