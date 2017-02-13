package uq.deco2800.duxcom;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uq.deco2800.duxcom.interfaces.gameinterface.GameDisplayManager;
import uq.deco2800.duxcom.interfaces.gameinterface.GameDisplays;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook.CurrentHeroNameDrawHandler;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook.EffectsContinuousDrawHandler;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook.FlashbangDrawHandler;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.rawhook.RawHookManager;
import uq.deco2800.duxcom.maps.DemoMap;

/**
 * GameRenderer renders the {@link DemoMap}.
 *
 * @author Leggy
 */
public class GameRenderer extends AnimationTimer {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameRenderer.class);

    /**
     * If the game is loading
     */
	private boolean loading = false;

    /**
     * If the UI is loaded
     */
    private boolean uiLoaded = false;

    /**
     * Used for the main rendering pipeline
     */
    private GraphicsContext graphicsContext;

    /**
     * Used to render particles onto
     */
    private GraphicsContext particleContext;

    /**
     * Manages gameplay
     */
    private GameManager gameManager;

    /**
     * Manages the games interfaces
     */
    private GameDisplayManager gameDisplayManager;

    /**
     * The time of the last fram render
     */
    private long oldFrameTime;

    /**
     * The frame count
     */
    private long frameCount = 1;

	/**
	 * Sets the load screen image
	 * @param image the image to set to
	 */
	public void setLoadScreenImage(Image image){
		loadingImage =image;
	}

	/**
	 * The load screen image
	 */
    private Image loadingImage = new Image("/falador/loadscreen/falador_static.png");

    /**
     * Sets the game over message
     * @param image the image to use
     */
    public void setGameOverImage(Image image){
        gameOverImage = image;
    }

    /**
     * The game over image
     */
    private Image gameOverImage = new Image("/falador/loadscreen/falador_gameover.png");

	/**
	 * Stores if game is over
	 */
    private static boolean gameOver = false;

	/**
	 * Sets whether the game is over or not.
	 * @param set - true if gameOver, false if not
     */
	public static void setGameOver(boolean set) {
		gameOver = set;
	}

	/**
	 * Returns true if the game is over, false if not.
	 * @return iff game over
     */
	public static boolean isGameOver() {
		return gameOver;
	}

	/**
	 * The games raw hook manager
	 */
	private RawHookManager rawHookManager;

	/**
	 * The string containing the current frame rate
	 */
    private String frameRateString = "";

    /**
     * GameRenderer constructor which initiates an instance of GraphicsDisplayManager
     *  @param graphicsContext the context of the renderer
     * @param particleContext
	 * @param gameManager     the game manager
	 */
    public GameRenderer(GraphicsContext graphicsContext, GraphicsContext particleContext, GameManager gameManager) {
        super();
        this.graphicsContext = graphicsContext;
		addCanvasSizeListeners(graphicsContext);
        this.gameManager = gameManager;
        gameDisplayManager = new GameDisplayManager(GameDisplays.MAIN_MAP);
		this.particleContext = particleContext;
		rawHookManager = new RawHookManager(
				new EffectsContinuousDrawHandler(20, this.gameManager),
				new CurrentHeroNameDrawHandler(400),
				new FlashbangDrawHandler(100));
    }

	private void addCanvasSizeListeners(GraphicsContext graphicsContext) {
		graphicsContext.getCanvas().widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
			gameManager.setViewPortWidth((double) newSceneWidth);
			gameManager.fullRenderRefresh();
			rawHookManager = new RawHookManager(
					new EffectsContinuousDrawHandler(20, this.gameManager),
					new CurrentHeroNameDrawHandler(400),
					new FlashbangDrawHandler(100));
				});
		graphicsContext.getCanvas().heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) ->{
			gameManager.setViewPortHeight((double) newSceneHeight);
			gameManager.fullRenderRefresh();
			rawHookManager = new RawHookManager(
					new EffectsContinuousDrawHandler(20, this.gameManager),
					new CurrentHeroNameDrawHandler(400),
					new FlashbangDrawHandler(100));
		});
	}

	/**
	 * Renders the appropriate screen for the appropriate time, then kicks off the render
	 * for the world.
	 *
	 * @param now - the current time (frame)
     */
	@Override
	public void handle(long now) {

		if (loading) {
            graphicsContext.drawImage(loadingImage, 0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
			return;
		}

		if (gameOver) {
            graphicsContext.drawImage(gameOverImage, 0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
            return;
        }

		if (frameCount % 30 == 0) {
			long elapsedNanosPerFrame = (now - oldFrameTime) / 30;
			long frameRate = 1_000_000_000 / elapsedNanosPerFrame ;
			frameRateString = String.format("%d", frameRate);
			oldFrameTime = now;
		}

        renderWorld();
    }

    /**
     * Call the graphics manager to handle the rendering
     */
    private void renderWorld() {

		if (frameCount == 1) {
            logger.info("Loading...");
            loading = true;
			loadTextures();
			return;
		}

		if (gameManager.getPanStatus()) {
			gameManager.panView();
		}

        /**
         * DO NOT EDIT THIS CLASS DIRECTLY...
         * THIS WILL JUST CAUSE A MASSIVE MESS...
         * DEFINE YOUR OWN GraphicsHandler AND SET THE
         * GAME DISPLAY MANAGER TO USE IT!
         */

        /** THIS IS AN EXAMPLE OF HOW TO ADD HANDLERS FOR THE MAP
         // gameDisplayManager.addHandler(GameDisplays.MAIN_MAP, RenderOrder.BACKGROUND, new MapGraphicsHandler());
         // gameDisplayManager.addHandler(GameDisplays.MAIN_MAP, RenderOrder.FRONT_OVERLAY, new OverlayGraphicsHandler());
         // DO NOT USE THEM IN THIS CLASS
         // PUT THEM IN GameDisplayManager
         */

        // Call the game interface renderer which should automatically
        // load the current game interface
        if (frameCount++ % 2 == 0) {
            gameDisplayManager.render(graphicsContext);
        } else {
			gameDisplayManager.updateGraphicsHandlers(graphicsContext);
			gameManager.setGameChanged(false);
        }
        
        if (!uiLoaded) {
            gameManager.getController().getUIController().show();
            uiLoaded = true;
        }


        if (gameManager.isDisplayDebug()) {
			drawDebug();
		}

		// hook the render and delegate to custom renders
		rawHookManager.rawRender(particleContext, 0.03);
	}

	private void drawDebug() {
		//Draw the fps
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillRect(0, 0, 80, 15);
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillText("FPS: " + frameRateString, 2, 12);

		//Draw the mouse hover coordinates
		graphicsContext.setFill(Color.GRAY);
		graphicsContext.fillRect(0, 15, 80, 15);
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillText("ON: " + gameManager.getHoverX() + ", " + gameManager.getHoverY(), 2, 27);
	}

	private void loadTextures() {

		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				gameManager.loadAllTextures();
				gameDisplayManager.updateGraphicsHandlers(graphicsContext);
				frameCount++;
				loading = false;
				gameManager.notifyLoadingComplete();
				return 1;
			}
		};

		Thread loadGameDisplayThread = new Thread(task);
		try {
			loadGameDisplayThread.join();
			loadGameDisplayThread.setDaemon(true);
			loadGameDisplayThread.start();
		} catch (InterruptedException e) {
			logger.error("Delay interrupted");
			Platform.exit();
			Thread.currentThread().interrupt();
		}
	}

    /**
     * Returns the graphics context of the renderer
     *
     * @return the stored context
     */
	public GraphicsContext getGraphicsContext() {
	    return graphicsContext;
    }
}
