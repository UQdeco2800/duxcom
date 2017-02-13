package uq.deco2800.duxcom.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.GameRenderer;
import uq.deco2800.duxcom.achievements.AchievementStatistics;
import uq.deco2800.duxcom.exceptions.DuplicateStartGameException;
import uq.deco2800.duxcom.graphics.AnimationManager;
import uq.deco2800.duxcom.handlers.*;
import uq.deco2800.duxcom.handlers.keyboard.DuxComKeyHandler;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.overlaymaker.ui.OverlayMakerUI;
import uq.deco2800.duxcom.interfaces.overlaymaker.ui.OverlayMakerUIManager;
import uq.deco2800.duxcom.interfaces.overlaymaker.ui.UIOrder;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class used to setup the controller for the entire game
 */
public class DuxComController implements Initializable {

    // Initialising the logger variables
    private static Logger logger = LoggerFactory.getLogger(DuxComController.class);
    private static final String SEND_ERROR = "Unable to send message";

    // Declaring the fx objects that will be modified
    @FXML
    protected AnchorPane particlePane;

    @FXML
    protected AnchorPane gamePane;

    @FXML
    private AnchorPane dividerPane;

    protected ExecutorService executor;
    
    private boolean running = false;
    
    protected GameManager gameManager;
    
    protected AtomicBoolean quit;
    
    protected GameRenderer gameRenderer;
    
    protected AnimationManager animationManager;

    private Date startDate;

    private Date endDate;

    /**
     * UI Manager
     */
    protected OverlayMakerUIManager uiManager;
    
    protected UserInterfaceController uiController;
	private InterfaceManager interfaceManager;

    /**
     * Class constructor
     *
     * @throws IOException if IO error occurs
     */
    public DuxComController() throws IOException {
        // Constructs DuxComController
    }

    /**
     * This method exists such that DuxComController can be an implementation of
     * Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Implements initializable
        
    }

    /**
     * Starts the game by initializing all core components and handlers
     *
     * @param gameManager The instance of GameManger which is managing Duxcom
     * @throws DuplicateStartGameException if the game has already been started
     */
    public void startGame(GameManager gameManager) throws DuplicateStartGameException, IOException {
        this.gameManager = gameManager;
        
        if (!running) {

            /* Get time stamp for achievements. */
            startDate = new Date();

            /**
             * Creating Canvas
             */
            Canvas canvas = new Canvas();
            gamePane.getChildren().add(canvas);
            canvas.widthProperty().bind(gamePane.widthProperty());
            canvas.heightProperty().bind(gamePane.heightProperty());

            Canvas particleCanvas = new Canvas();
            Platform.runLater(() -> particlePane.getChildren().add(particleCanvas));
            particleCanvas.widthProperty().bind(particlePane.widthProperty());
            particleCanvas.heightProperty().bind(particlePane.heightProperty());

            GraphicsContext particleContext = particleCanvas.getGraphicsContext2D();

            setUpUI();
            
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            
            executor = Executors.newCachedThreadPool();
            
            quit = new AtomicBoolean(false);
            executor.execute(new GameLoop(50, quit, gameManager));
            gameRenderer = new GameRenderer(graphicsContext, particleContext, gameManager);
            gameRenderer.start();
            animationManager = new AnimationManager(gameManager);
            animationManager.start();
            
            gamePane.setOnMousePressed(new MousePressedHandler(gameManager));
            gamePane.setOnScroll(new MouseScrolledHandler(gameManager));
            gamePane.setOnMouseReleased(new MouseReleasedHandler(gameManager));
            gamePane.setOnMouseDragged(new MouseDraggedHandler(gameManager));
            gamePane.setOnMouseMoved(new MouseMovedHandler(gameManager));

            running = true;


        } else {
            throw new DuplicateStartGameException();
        }
    }
    
    public void setUpUI() throws IOException {
        uiManager = new OverlayMakerUIManager(gameManager, gamePane);
        uiManager.addFXML("/ui/fxml/userInterface.fxml", 0, 0, UIOrder.FRONT);
        OverlayMakerUI uiBase = uiManager.getComponent("/ui/fxml/userInterface.fxml");
        uiController = (UserInterfaceController) uiBase.getController();
        uiBase.setCenter(true);
    }

    /**
     * Stops the current game by destroying the current game manager and shutting down all
     * executables
     */
    public void stopGame() {
        /* Get timestamp. */
        endDate = new Date();
        long diff;
        if (startDate != null) {
            diff = endDate.getTime() - startDate.getTime();
        } else {
            diff = 0;
        }
        long mins = diff;
        mins /= 1000;
        mins /= 60;

        AchievementStatistics.addMins((int) mins);
        AchievementStatistics.save();

        if (gameManager != null) {
            gameManager = null;
        }

        if (gameRenderer != null) {
            gameRenderer.stop();
        }
        GameLoop.destroyCurrentGameManager();
        if (animationManager != null) {
            animationManager.stop();
        }
        
        if (executor != null && quit != null) {
            quit.set(true);
            executor.shutdown();
        }
    }

    /**
     * Creates a datagram socket.
     *
     * @return the created socket
     */
    private DatagramSocket createSocket() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(80);
        } catch (IOException exception) {
            uiController.writeToLogBox(SEND_ERROR);
            logger.error(SEND_ERROR, exception);
        }
        return socket;
    }

    /**
     * finds the InetAddress of the host server
     *
     * @return and InetAddress object containing the address.
     */
    private InetAddress getAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName("singularity.rubberducky.io");
        } catch (UnknownHostException exception) {
            uiController.writeToLogBox(SEND_ERROR);
            logger.error(SEND_ERROR, exception);
        }
        
        return address;
    }

    /**
     * sends a packet to the InetAddress
     *
     * @param packet the packet to be sent
     */
    private void sendPacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException exception) {
            uiController.writeToLogBox(SEND_ERROR);
            logger.error(SEND_ERROR, exception);
        }
    }

    
    public void setKeyEvent(InterfaceManager interfaceManager) {
    	this.interfaceManager = interfaceManager;
        dividerPane.setOnKeyPressed(new DuxComKeyHandler(interfaceManager, gameManager, this));
    }
    
    public InterfaceManager getInterfaceManager(){
    	return this.interfaceManager;
    }
    
    
    
    public AnchorPane getGamePane() {
        return this.gamePane;
    }

	/**
	 * Enable and disable user's ability to click buttons or interact with main pane.
	 *
	 * Used for instructions/cut-scenes when the player should not be able to play the game.
	 *
	 * @param disable If true, disable. If false, enable.
	 */
	public void setDisableUserInteraction(boolean disable) {
		uiController.setDisableInteraction(disable);
	}


    public UserInterfaceController getUIController() {
        return uiController;
    }
    
}
