package uq.deco2800.duxcom.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Platform;
import javafx.stage.Stage;
import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.exceptions.UnknownInterfaceSegmentException;

/**
 * Handles the different interfaces for the duxcom game and controls which ones
 * are rendered on stage.
 *
 * Created by liamdm on 16/08/2016.
 */
public class InterfaceManager {

    private static Logger logger = LoggerFactory.getLogger(InterfaceManager.class);
    
    private Stage primaryStage;

    /**
     * Class constructor to setup all interface segments with their interface class
     */
    public InterfaceManager() {
        setInterfaceSegment(InterfaceSegmentType.GAME, new GameInterface());
        setInterfaceSegment(InterfaceSegmentType.LOAD_SCREEN, new LoadScreenInterface());
        setInterfaceSegment(InterfaceSegmentType.LOGIN_SCREEN, new LoginScreenInterface());
        setInterfaceSegment(InterfaceSegmentType.USER_REGISTRATION, new UserRegistrationInterface());
        setInterfaceSegment(InterfaceSegmentType.MAP_CREATOR, new MapCreatorInterface());
        setInterfaceSegment(InterfaceSegmentType.HERO_SELECT, new HeroSelectInterface());
        setInterfaceSegment(InterfaceSegmentType.OVERWORLD, new OverworldInterface());
        setInterfaceSegment(InterfaceSegmentType.LOBBY, new LobbyInterface());
        setInterfaceSegment(InterfaceSegmentType.PLAYER_REWARDS, new PlayerRewardsInterface());
        setInterfaceSegment(InterfaceSegmentType.LEADER_BOARD, new LeaderboardInterface());
    }

    private String arguments = "";

    /**
     * The interface segment map
     */
    private ConcurrentHashMap<InterfaceSegmentType, InterfaceSegment> interfaceSegments = new ConcurrentHashMap<>();

    /**
     * The current segment to render
     */
    private InterfaceSegmentType currentSegmentType = InterfaceSegmentType.GAME;

    /**
     * Class constructor used to set the default segment of the game
     *
     * @param defaultSegment the default InterfaceSegmentType
     */
    @MethodContract(precondition = {"defaultSegment != null"})
    public InterfaceManager(InterfaceSegmentType defaultSegment) {
        this.currentSegmentType = defaultSegment;
    }

    /**
     * Set the interface  to be used for the given interface type
     *
     * @param type             the InterfaceSegmentType
     * @param interfaceSegment the InterfaceSegment
     */
    @MethodContract(precondition = {"type != null", "interfaceSegment != null"})
    public void setInterfaceSegment(InterfaceSegmentType type, InterfaceSegment interfaceSegment) {
        interfaceSegments.put(type, interfaceSegment);
    }

    /**
     * Loads the current interface to the given primary stage
     *
     * @param primaryStage the Stage of the rendered game window
     */
    @MethodContract(precondition = {"primaryStage != null", "interfaceSegments.containsKey(currentSegmentType)"})
    public void renderInterface(Stage primaryStage) {
        // Load the current interface
        if (!interfaceSegments.containsKey(currentSegmentType)) {
            logger.error("Tried to load interface " + currentSegmentType.toString() + " which did not have an associated interface segment!");
            throw new UnknownInterfaceSegmentException(currentSegmentType.toString());
        }
        this.primaryStage = primaryStage;
        try {
            interfaceSegments.get(currentSegmentType).loadInterface(primaryStage, arguments, this);
            if (currentSegmentType == InterfaceSegmentType.GAME) {
                primaryStage.setMaximized(true);
                primaryStage.setResizable(true);
                ((GameInterface) getCurrentSegment()).getGameManager().centerMapOnCurrentHero();
            } else {
                primaryStage.setMaximized(false);
                primaryStage.setResizable(false);
            }
        } catch (IOException exception) {
            logger.error("IOException in rendering interface " + currentSegmentType.toString(), exception);
            Platform.exit();
        }
    }

    /**
     * Gets the type of the current interface segment being rendered by the InterfaceManager
     *
     * @return the type of the current interface segment
     */
    public InterfaceSegmentType getCurrentSegmentType() {
        return currentSegmentType;
    }

    /**
     * Gets the current interface segment being rendered by the InterfaceManager
     *
     * @return the current interface segment
     */
    public InterfaceSegment getCurrentSegment() {
        return interfaceSegments.get(currentSegmentType);
    }

    /**
     * Changes the current interface on the next call to renderInterface
     *
     * @param newSegmentType the type of the new segment
     * @param arguments      arguments to be provided to the new segment
     */
    @MethodContract(precondition = {"newSegmentType != null", "interfaceSegments.containsKey(newSegmentType)"})
    public void setCurrentSegment(InterfaceSegmentType newSegmentType, String arguments) {
        this.currentSegmentType = newSegmentType;
        this.arguments = arguments;
    }

    /**
     * Changes the current interface immediately
     *
     * @param newSegmentType the type of the new segment
     * @param primaryStage   the Stage of the rendered game window
     * @param arguments      arguments to be provided to the new segment
     */
    @MethodContract(precondition = {"newSegmentType != null", "interfaceSegments.containsKey(newSegmentType)", "primaryStage != null"})
    public void loadSegmentImmediate(InterfaceSegmentType newSegmentType, Stage primaryStage, String arguments) {
        this.currentSegmentType = newSegmentType;
        this.arguments = arguments;
        renderInterface(primaryStage);
    }

    /**
     * Returns the arguments of the most recently loaded segment
     *
     * @return arguments as a string
     */
    public String getArguments() {
        return arguments;
    }

    /**
     * End the game forcefully
     */
    public void killInterfaces() {
        logger.info("Exiting duxcom");
        Platform.exit();
    }

    /**
     * Restart the game from login screen
     *
     */
    public void restartGame() { // Need a logout
        this.setCurrentSegment(InterfaceSegmentType.LOGIN_SCREEN, null);
        this.renderInterface(getStage());
    }
    
    /**
     * Returns to the load screen
     * 
     */
    public void returnLoadScreen() {
        this.setCurrentSegment(InterfaceSegmentType.LOAD_SCREEN, null);
        this.renderInterface(getStage());
    }
    
    /**
     * Returns to the Overworld screen
     */
    public void returnOverworldScreen() {
        this.setCurrentSegment(InterfaceSegmentType.OVERWORLD, null);
        this.renderInterface(getStage());
    }
    
    /**
     * Retrieves the stage
     * 
     * @return stage the stage of the rendered game window
     */
    public Stage getStage() {
        return this.primaryStage;     
    }
}
