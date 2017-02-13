package uq.deco2800.duxcom.interfaces;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.coop.GameplayClient;
import uq.deco2800.duxcom.coop.MultiplayerClient;
import uq.deco2800.duxcom.exceptions.DuplicateStartGameException;
import uq.deco2800.duxcom.maps.*;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.savegame.SaveGame;
import uq.deco2800.duxcom.savegame.SaveOriginator;
import uq.deco2800.duxcom.sound.SoundPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * The interface for displaying and playing the game
 *
 * Created by liamdm on 16/08/2016.
 */
public class GameInterface implements InterfaceSegment {

    private static Logger logger = LoggerFactory.getLogger(GameInterface.class);

    /**
     * Arguments currently takes new or old, a reference to the new or old texture theme
     */

    private GameManager gameManager;
    private MultiplayerClient multiplayerClient;
    private GameplayClient gameplayClient;

    @Override
    public boolean loadInterface(Stage primaryStage, String arguments, InterfaceManager interfaceManager) throws IOException {

        /**
         * get the clients
         */
        this.multiplayerClient = MultiplayerClient.getMultiplayerClient();
        this.gameplayClient = MultiplayerClient.getGameplayClient();

        String mpArgs = null;
        String mpData;

        URL location = getClass().getResource("/ui/fxml/duXCOM.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        gameManager = GameLoop.getCurrentGameManager();

        // THE ONLY INSTANCE WHERE GAME MANAGER SHOULD BE CREATED IS HERE
        initialiseGameManager();

        if(arguments.startsWith("MP,")) {
            mpArgs = arguments.split(",")[1];
            mpData = arguments.split(",")[2];
            logger.info("Multiplayer argumets loaded [{}], data set: [{}]", mpArgs, mpData);
            arguments = mpData;
        }

        if (!loadSpecifiedMap(arguments, gameManager)) {
            AbstractGameMap map = new OptimisedDemo();
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
        }

        // Refresh rendering engine to resolve rending bug
        gameManager.fullRenderRefresh();

        Parent root = fxmlLoader.load(location.openStream());
        DuxComController duxComController = fxmlLoader.getController();
        gameManager.setController(duxComController);


        try {
            duxComController.startGame(gameManager);
            duxComController.setUpUI();
        } catch (DuplicateStartGameException exception) {
            logger.error("Game is already running! Cannot start new game", exception);
            Platform.exit();
        }

        if("mpcountdown".equals(mpArgs)){
            SoundPlayer.playGameCountdown();
        }

        gameManager.getMap().addHealthListenerAllHeroes(duxComController.getUIController().getHealthBar());
        gameManager.getMap().addHealthListenerAllHeroes(duxComController.getUIController().getHealthLabel());
        gameManager.getMap().addActionPointListenerAllHeroes(duxComController.getUIController().getActionPointBar());
        gameManager.getMap().addActionPointListenerAllHeroes(duxComController.getUIController().getActionPointLabel());
        gameManager.getMap().addActionPointListenerAllHeroes(gameManager.getMap());
        gameManager.getMap().addDeathListenerAllEnemies(gameManager.getMap());
        gameManager.getMap().addDeathListenerAllHeroes(gameManager.getMap());
        gameManager.getMap().addDeathListenerAllHeroesToManager();
        gameManager.getMap().addDeathListenerAllEnemiesToManager();

        gameManager.getMap().initialiseStatusListeners();
        duxComController.setKeyEvent(interfaceManager);

        InterfaceSegment.showStage(root, primaryStage, interfaceManager);
        primaryStage.setOnCloseRequest(e -> duxComController.stopGame());

        gameManager.setLevelInstructions(gameManager.getMap().getLevelInstructions());
        gameManager.nextTurn();
        return true;
    }

    private boolean loadSpecifiedMap(String arguments, GameManager gameManager) {
        if(arguments != null && "old".equals(arguments)){
            AbstractGameMap map = new DemoMap("");
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        if(arguments != null && "auto".equals(arguments)){
            AbstractGameMap map = new AutogenerateDemo();
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        if (arguments != null && "enemy".equals(arguments)) {
            AbstractGameMap map = new EnemyTestMap("");
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        if (arguments != null && "dynamic".equals(arguments)) {
            AbstractGameMap map = new DynamicEntityTestMap("");
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        if (arguments != null && "falador".equals(arguments)) {
            AbstractGameMap map = new Falador("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        if (arguments != null && "beta".equals(arguments)) {
            AbstractGameMap map = new BetaStage("");
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        if (arguments != null && "tutorial".equals(arguments)) {
            AbstractGameMap map = new TutorialMap("");
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }

        if (arguments != null && "karamja".equals(arguments)) {
            AbstractGameMap map = new Karamja("");
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        
        if (arguments != null && "saved".equals(arguments)) {
        	SaveGame saveGame = new SaveGame();
        	SaveOriginator saveOriginator = new SaveOriginator();
        	gameManager.setSaveState(saveGame, saveOriginator);
        	
        	try {
				saveGame.loadGame(saveOriginator);
	        	AbstractGameMap map = saveOriginator.getAbstract();
	        	gameManager.setMap(new MapAssembly(map));
	            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
	            return true;
			} catch (IOException e) {
				logger.error("Unable to Load game",e);
			}
        	AbstractGameMap map = saveOriginator.getAbstract();
            GameState gameState = saveOriginator.getSavedGameState();
            List<Objective> objectiveList = saveOriginator.getSavedObjectives();
            ObjectiveTracker tracker = new ObjectiveTracker(gameState, objectiveList);
        	gameManager.setMap(new MapAssembly(map));
            gameManager.loadExistingGameInternalFramework(tracker);
            return true;
        }

        if(arguments != null && "camp".equals(arguments)) {
        	AbstractGameMap map = new Map001("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        
        if(arguments != null && "lyon_ambush".equals(arguments)) {
        	AbstractGameMap map = new Map002("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        
        if(arguments != null && "coast".equals(arguments)) {
        	AbstractGameMap map = new Map003("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        
        if(arguments != null && "long_dry".equals(arguments)) {
        	AbstractGameMap map = new Map004("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
      
        if(arguments != null && "enemy_hands".equals(arguments)) {
        	AbstractGameMap map = new Map005("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }

        if(arguments != null && "village".equals(arguments)) {
        	AbstractGameMap map = new Map006("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        
        if(arguments != null && "south".equals(arguments)) {
        	AbstractGameMap map = new Map007("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }
        
        if(arguments != null && "further_south".equals(arguments)) {
        	AbstractGameMap map = new Map008("", false);
            gameManager.setMap(new MapAssembly(map));
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));
            return true;
        }

        if(arguments != null && arguments.startsWith("MP")){
            AbstractGameMap map = new MultiplayerArena();
            gameManager.enableMultiplayer();

            logger.info("Multiplayer manager is now recieveing events...");

            MapAssembly assembly = new MapAssembly("test", map);
            gameManager.getMultiplayerGameManager().loadMap(assembly);
            gameManager.setMap(assembly);
            gameManager.setInitialGameInternalFramework(new ObjectiveBuilder(map));

            return true;
        }
        return false;
    }

    /**
     * Initialises the game manager or logs an error and does nothing if initialized elsewhere
     */
    void initialiseGameManager() {
        if (gameManager == null) {
            gameManager = new GameManager();
        } else {
            logger.error("GameManager was already initialized elsewhere, reconsider implementation");
        }
    }

    @Override
    public void destroyInterface() {
        // Cleanup goes here
    }

    /**
     * Returns an instance of GameManager
     * @return the current GameManager
     */
    public GameManager getGameManager() {
        return this.gameManager;
    }
}
