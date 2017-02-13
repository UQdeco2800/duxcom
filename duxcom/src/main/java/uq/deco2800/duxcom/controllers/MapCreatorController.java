package uq.deco2800.duxcom.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.MapCreatorRenderer;
import uq.deco2800.duxcom.handlers.MouseScrolledHandler;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.savegame.SaveCreatedMap;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This class works as a set up for all the UI functionality for the Map Creator. It
 * ensures that the user may interact with the MapCreator GUI and is able to Save Maps
 * Load Maps, and edit them by selecting tiles and editing their contents and
 * characteristics.
 *
 * Class is instantiated through the MapCreatorInterface fxml file in MapCreatorInterface
 *
 * @author Lucas Reher
 * @author Sam Thomas
 * @author Winston Fang
 */
public class MapCreatorController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(MapCreatorController.class);

    private InterfaceManager interfaceManager;
    private Stage stage;
    private GameManager gameManager;
    private MapAssembly map;
    private MapCreatorRenderer mapCreatorRenderer;
    private ExecutorService executor;
    private AtomicBoolean quit;

    @FXML
    private AnchorPane mapPane;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnExit;

    @FXML
    private ListView<String> itemList;

    @FXML
    private Label infoWindowHeader;

    @FXML
    private Label infoWindowDescription;

    /**
     * Creates a new canvas to display the map.
     *
     * Binds the mapPane to canvas and adds listeners for mouse and keyboard events.
     *
     * Creates a graphic context for canvas and binds it to the Map Creator Renderer class,
     * which manages how objects will be displayed externally.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if
     *                  the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object
     *                  was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialise itemList
        ObservableList<String> items = FXCollections.observableArrayList("");
        updateItemList(items);
    }

    /**
     * Starts the rendering of the game world when everything is ready
     *
     * @param gameManager the manager controlling the map
     */
    public void startRenderingMap(GameManager gameManager) {
        this.gameManager = gameManager;

        DemoMap emptyMap = new DemoMap("test");
        this.map = new MapAssembly(emptyMap);
        gameManager.setMap(map);

        //Create canvas
        Canvas canvas = new Canvas();
        mapPane.getChildren().add(canvas);
        canvas.widthProperty().bind(mapPane.widthProperty());
        canvas.heightProperty().bind(mapPane.heightProperty());

        // Add scrolling and set base zoom level
        mapPane.setOnScroll(new MouseScrolledHandler(gameManager));

        while (gameManager.getScale() >= 0.15) {
            gameManager.zoomOut();
        }

        //Create and begin rendering
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        executor = Executors.newCachedThreadPool();
        quit = new AtomicBoolean(false);
        executor.execute(new GameLoop(50, quit, gameManager));
        mapCreatorRenderer = new MapCreatorRenderer(graphicsContext, gameManager);
        mapCreatorRenderer.start();
    }

    /**
     * Stops the current creator by destroying the current game manager ans
     * shutting down all executables
     */
    public void stopRendering() {
        this.gameManager = null;
        if (mapCreatorRenderer != null) {
            mapCreatorRenderer.stop();
        }

        if (executor != null && quit != null) {
            quit.set(true);
            executor.shutdown();
        }
    }

    /**
     * Updates all the items that can be utilized by the developer
     * Eg. Tile types, Objects and Enemies
     */
    private void updateItemList(ObservableList<String> items) {
        itemList.setItems(items);

        // Updates InfoPane on click and arrow selection
        itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String header = itemList.getSelectionModel().getSelectedItem();
            infoWindowHeader.setText(header);
        });
    }

    /**
     * Creates a list of available Heroes and updates the item list
     */
    @FXML
    public void btnHeroesClick(){
        ObservableList<String> items = FXCollections.observableArrayList("Archer", "Cavalier", "Knight",
                                                                            "Priest", "Rogue", "Warlock");
        updateItemList(items);

        //Update InfoPane with Hero information
        infoWindowHeader.setText("Heroes");
        infoWindowDescription.setText("Choose the types of heroes you want on the map. Different heroes have different advantages.");
    }

    /**
     * Creates a list of available Terrain and updates the item list
     */
    @FXML
    public void btnTerrainClick(){
        ObservableList<String> items = FXCollections.observableArrayList("Grass", "Water", "Sand");
        updateItemList(items);

        //Update InfoPane with terrain information
        infoWindowHeader.setText("Terrain");
        infoWindowDescription.setText("Different types of terrain can affect heroes and enemies. " +
                                        "Terrain available are Grass, Water and Sand.");
    }

    /**
     * Creates a list of available Enemies and updates the item list
     */
    @FXML
    public void btnEnemiesClick(){
        ObservableList<String> items = FXCollections.observableArrayList("Archer", "Brute", "Dark Knight", "Dark Mage",
                                                                        "Grunt", "Paladin", "Plague Doctor", "Rogue");
        updateItemList(items);

        //Update InfoPane with Enemy information
        infoWindowHeader.setText("Enemies");
        infoWindowDescription.setText("Add different types of enemies to the map for different kind of gameplay.");
    }

    /**
     * Creates a list of available Items and updates the item list
     */
    @FXML
    public void btnItemsClick(){
        ObservableList<String> items = FXCollections.observableArrayList("Ammo", "Armour", "Arrow", "Bow", "Dagger",
                                                                        "Food", "Hammer", "Magic Staff", "Potion",
                                                                        "Potion Grenade", "Shield", "Sword");
        updateItemList(items);

        //Update InfoPane with Item information
        infoWindowHeader.setText("Items");
        infoWindowDescription.setText("Select an item to add to the map. Items can be hidden within chests.");
    }

    /**
     * Creates a list of available Objectives and updates the item list
     */
    @FXML
    public void btnObjectivesClick(){
        ObservableList<String> items = FXCollections.observableArrayList("Objective1", "Objective2", "Objective3");
        updateItemList(items);

        //Update InfoPane with Objectives information
        infoWindowHeader.setText("Objectives");
        infoWindowDescription.setText("Choose how the player will complete the map. For example, an objective might be to kill 30 enemies.");
    }

    /**
     * Creates a list of available Weather and updates the item list
     */
    @FXML
    public void btnWeatherClick(){
        ObservableList<String> items = FXCollections.observableArrayList("Sun", "Rain", "Hail", "Snow", "Fog");
        updateItemList(items);

        //Update InfoPane with Weather information
        infoWindowHeader.setText("Weather");
        infoWindowDescription.setText("Choose a type of weather for the map. Weather options are sun, rain, hail, snow and fog.");
    }


    /**
     * Enables Load Map screen
     */
    @FXML
    public void btnLoadClick() {
        //This list will dynamically update based on saved maps once fully implemented
        List<String> savedMaps = new ArrayList<>();
        savedMaps.add("Placeholder1");
        savedMaps.add("Placeholder2");
        savedMaps.add("Placeholder3");

        ChoiceDialog<String> loadDialog = new ChoiceDialog<>("Placeholder1", savedMaps);
        loadDialog.setTitle("Load Map");
        loadDialog.setHeaderText("Choose Saved Map");


        // Change this when map loading has been fully implemented
        Optional<String> result = loadDialog.showAndWait();
        if (result.isPresent()){
            logger.info(result.get() + " chosen.");
        }

    }

    /**
     * Enables Save Map screen
     */
    @FXML
    public void btnSaveClick() {
        logger.info("Save button clicked.");
        String path = "src/main/resources/levels/";
        String end = ".txt";
        TextInputDialog saveDialog = new TextInputDialog("");
        saveDialog.setTitle("Save Map");
        saveDialog.setHeaderText("Save Map As:");

        //Doesn't currently handle saving with existing name.
        //Will be changed when saving map has been fully implmented
        Optional<String> result = saveDialog.showAndWait();
        File file = new File(path+result.get()+end);
        try {
			SaveCreatedMap.saveMap(map, file, true);
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
        
        if (result.isPresent()){
            logger.info("Map saved as " + result.get());
        }

    }

    /**
     * Safely exits the game
     */
    @FXML
    public void btnExitClick() {
        ((Stage) btnExit.getScene().getWindow()).close();
    }

    /**
     * Sets the interface manager of the controller
     *
     * @param interfaceManager the instance of InterfaceManager
     */
    public void setInterfaceManager(InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    /**
     * Sets the stage of the controller
     *
     * @param stage the current javafx stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the interface manager of the controller
     *
     * @return the instance of InterfaceManager
     */
    public InterfaceManager getInterfaceManager() {
        return interfaceManager;
    }

    /**
     * Gets the stage of the controller
     *
     * @return the current javafx stage
     */
    public Stage getStage() {
        return stage;
    }
}
