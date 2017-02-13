package uq.deco2800.duxcom.interfaces;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import uq.deco2800.duxcom.controllers.LobbyController;
import uq.deco2800.duxcom.coop.GameplayClient;
import uq.deco2800.duxcom.coop.MultiplayerClient;
import uq.deco2800.duxcom.coop.MultiplayerGameManager;
import uq.deco2800.duxcom.coop.exceptions.IncorrectGameState;
import uq.deco2800.duxcom.coop.exceptions.MultiplayerException;
import uq.deco2800.singularity.clients.duxcom.GameplayEventListener;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.*;

import java.io.IOException;
import java.net.URL;

import static uq.deco2800.duxcom.coop.NiceError.niceError;

/**
 * Handles the lobby interactions.
 * <p>
 * Created by liamdm on 27/09/2016.
 */
public class LobbyInterface implements InterfaceSegment {
    private Logger logger = Logger.getLogger(LobbyInterface.class);

    /**
     * Used to de-register the listener refrerence once not needed
     */
    private static GameplayEventListener deregistrationReference;

    /**
     * Call only to de-register this listener from the succesor class
     */
    public static GameplayEventListener getDeregistrationReference(){
        return deregistrationReference;
    }

    private static final String COULD_NOT_JOIN_TITLE = "Could Not Join";
    private static final String COULD_NOT_JOIN_HEADER = "Could not join the server you requested!";
    private static final String COULD_NOT_JOIN_CONTENT = "Either it was already created, or something went wrong: ";

    @Override
    public boolean loadInterface(Stage primaryStage, String arguments, InterfaceManager interfaceManager) throws IOException {

        URL location = getClass().getResource("/ui/fxml/lobbyScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        LobbyController lobbyController = fxmlLoader.getController();
        lobbyController.setInterfaceManager(interfaceManager);

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, primaryStage, "");
            }
        });

        InterfaceSegment.showStage(root, primaryStage, interfaceManager);

        String joinMode = arguments.substring(0, 1);
        String joinArgs = arguments.length() > 1 ? arguments.substring(1, arguments.length()) : "";

        MultiplayerClient mc;
        try {
            if ("J".equals(joinMode)) {
                logger.info("Tried to join: " + joinArgs);
                mc = MultiplayerClient.connectToServer(joinArgs);
            } else {
                // try creating server
                logger.info("Tried to create: " + joinArgs);
                mc = MultiplayerClient.createServer(joinArgs);
            }
        } catch (MultiplayerException multiplayerException) {
            niceError(COULD_NOT_JOIN_TITLE,
                    COULD_NOT_JOIN_HEADER,
                    COULD_NOT_JOIN_CONTENT + multiplayerException + " --> " + multiplayerException.getErrorCode(),
                    false);

            interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, primaryStage, "");
            return false;
        } catch (IncorrectGameState incorrectGameState) {
            niceError(COULD_NOT_JOIN_TITLE,
                    COULD_NOT_JOIN_HEADER,
                    COULD_NOT_JOIN_CONTENT + incorrectGameState,
                    false);

            interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, primaryStage, "");
            return false;
        }

        lobbyController.setServerNameText(mc.getServerName());


        GameplayClient gc = new GameplayClient(mc.getConfiguration(), mc);

        // store the clients
        MultiplayerClient.storeClient(mc, gc);

        Platform.runLater(() ->lobbyController.getMpLoadStatusLabel().setText("Server not open"));

        final int[] joinedUsers = {0};

        GameplayEventListener gameplayEventListener = new GameplayEventListener() {
            @Override
            public void recievedGameMetadataQuery(GameMetadata gameMetadata) {
                logger.error("Got metadata message...");
                if (gameMetadata.getInnerMessageType() == GameMetadata.MessageType.USER_DATA) {
                    logger.error("Got user data message...");
                    // user has joined
                    Platform.runLater(() -> {
                        if (gameMetadata.getJoinedUsers() != null) {
                            lobbyController.getMpLobbyList().getItems().clear();
                            logger.error("Cleared lobby list... Adding: " + gameMetadata.getJoinedUsers());
                            for(String user : gameMetadata.getJoinedUsers()) {
                                lobbyController.getMpLobbyList().getItems().add(user);
                            }
                        } else {
                            logger.error("The game metadata for user list was null!" );
                        }
                    });
                }
            }

            @Override
            public void recievedGameRegistrationMessage(GameRegistration gameRegistration) {
                if(gameRegistration.getInnerMessageType() == GameRegistration.MessageType.REPLY_FAIL){
                    // failed to join
                    Platform.runLater(() -> {
                        niceError(COULD_NOT_JOIN_TITLE,
                                COULD_NOT_JOIN_HEADER,
                                "Got a failure from the server: " + gameRegistration.getErrorCode() + " --> " + gameRegistration.getErrorMessage(),
                                false);

                        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, primaryStage, "");
                    });
                }
            }

            @Override
            public void recievedControlMessage(ControlMessage controlMessage) {

                if (controlMessage.isReceipt() && !controlMessage.isSuccess()) {
                    Platform.runLater(() -> {
                        niceError("Control Command Failed",
                                "Control Command Failed",
                                "The control command you tried to execute was rejected by the server," +
                                        "reason: " + controlMessage.getErrorMessage(),
                                false);
                    });
                    logger.error("Control command failed: " + controlMessage.getErrorMessage());
                }
            }

            @Override
            public void recievedStateMessage(StateChange stateChange) {
                logger.info("Got state change to " + stateChange.getState());

                if(stateChange.shouldDitch()){
                    Platform.runLater(() ->{
                        niceError("Server Ditch",
                                "Server Ditch",
                                "The admin either left or something went wrong, the server isn't going to bother trying to recover so your connection has been ditched!", false);

                        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, primaryStage, ""); });
                    return;
                }

                Platform.runLater(() -> lobbyController.setServerStateText(String.valueOf(stateChange.getState())));
                if (stateChange.getState() == GameState.LOBBY) {
                    Platform.runLater(() -> {
                        lobbyController.getAcOpenServer().setDisable(true);
                        lobbyController.getAcStartGame().setDisable(false);
                        lobbyController.getAcDitchGame().setDisable(false);
                        lobbyController.getMpLoadStatusLabel().setText("Waiting for players");
                    });
                }

                if(stateChange.getState() == GameState.IN_GAME){
                    logger.info("The map is starting, beginning just in time server message scheduling till manager comes online...");
                    // in game
                    Platform.runLater(() -> {
                        lobbyController.getAcOpenServer().setDisable(true);
                        lobbyController.getAcStartGame().setDisable(true);
                        lobbyController.getAcDitchGame().setDisable(true);
                        lobbyController.getMpMapNameLabel().setText("Ibis Overwatch");
                        lobbyController.getMpLoadStatusLabel().setText("Entering Game...");
                    });
                }
            }

            @Override
            public void recievedUpdateMessage(GameUpdate gameUpdate) {
                // check game started

                if(gameUpdate.getInnerMessageType() == GameUpdate.MessageType.MAP_INIT){
                    // game has started, start map countdown
                    Platform.runLater(() -> {
                        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.GAME, primaryStage, "MP,mpcountdown,MP_multiplayer_" + joinedUsers[0]);
                    });


                } else if(gameUpdate.getInnerMessageType() == GameUpdate.MessageType.PLAYER_ORDER){
                    logger.info("Recieved the player order early. Cannot get the player order to the multiplayer manager through conventional means, beginning sneaky mode...");
                    MultiplayerGameManager.sneakyTransferPlayerOrder(gameUpdate);
                }
            }

            @Override
            public void recievedPlayerActionMessage(PlayerAction playerAction) {
                /* Necessary override */
            }

            @Override
            public void recievedSquadStateMessage(SquadState squadState) {
                /* Necessary override */
            }


            @Override
            public String toString() {
                return "LobbyInterfaceEventListener"+ Thread.currentThread().getId();
            }
        };
        gc.addListener(gameplayEventListener);

        if ("J".equals(joinMode)) {
            // try and join
            logger.info("Trying to join server...");
            gc.sendMessage(GameRegistration.requestJoin(mc.getUsername()));
            lobbyController.enableUserPanel();
        } else {
            // try and create
            logger.info("Trying to create server...");
            gc.sendMessage(GameRegistration.requestSession(mc.getUsername()));
            lobbyController.enableAdminPanel();
        }


        MultiplayerClient finalMc = mc;
        Platform.runLater(() -> {
            lobbyController.getMpLobbyList().getItems().clear();
            lobbyController.getMpLobbyList().getItems().add(finalMc.getUsername());
        });


        // lobby is up

        // try and open the server
        lobbyController.getAcOpenServer().setOnAction(event -> gc.sendMessage(ControlMessage.switchLobby()));

        // ditch game
        lobbyController.getAcDitchGame().setOnAction(event -> Platform.exit());

        // open the server
        lobbyController.getAcStartGame().setOnAction(event -> gc.sendMessage(ControlMessage.startGame("ibis")));
        return true;
    }


    @Override
    public void destroyInterface() {
        // Cleanup
    }
}
