package uq.deco2800.duxcom.overworld;

import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.overworld.nodes.Level;
import uq.deco2800.duxcom.overworld.nodes.Node;
import uq.deco2800.duxcom.sound.SoundPlayer;

import java.net.URL;
import java.util.ResourceBundle;
import uq.deco2800.duxcom.handlers.keyboard.OverworldScreenKeyHandler;

/**
 * OverworldController is the controller used for the game's overworld. It
 * controls user interaction with the overworld, as well as making the overworld
 * accurately represent the campaign specified in LevelRegister and VirtualMap.
 * User interactions include selecting levels, moving a playerIcon around the
 * overworld and entering levels.
 *
 * This class is initialized through the fxml file used for the overworld. The
 * fxml file is initially called from LoadScreenInterface.
 *
 * @author Sceub
 * @author mtimmo
 * @see LevelRegister
 * @see VirtualMap
 */

public class OverworldController implements Initializable {

	// The fx objects that are default in every overworld
	@FXML
	Pane overworldPane;
	@FXML
	ImageView playerImage;
	@FXML
	ImageView backgroundImage;
	@FXML
	ImageView levelLabelBackground;
	@FXML
	ImageView exitLevelLabel;
	@FXML
	ImageView descriptionBackground;
	@FXML
	Label descriptionLabel;
	@FXML
	Button menuButton;

	private InterfaceManager interfaceManager;

	/*
	 * These values are used to keep track of the players current state, such as
	 * position in the overworld and whether they are moving.
	 */
	private Level currentLevel;

	private Node currentNode;

	private Stage stage;

	private int travelTime = 60;

	private int nodesHigh = 15;

	private int nodesWide = 20;

	private boolean playerMoving;

	/**
	 * Sets up the layout of the overworld, and initialises the KeyHandlers and
	 * MouseEvent handlers on the objects within the overworld.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if the
	 *                  root object was not localized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playerMoving = false;
		SoundPlayer.playOverworldMusic();
		renderOverworld(overworldPane);

		VirtualMap.setImages();
		levelLabelBackground.setImage(TextureRegister.getTexture("boxSarah"));
		levelLabelBackground.setVisible(false);
		levelLabelBackground.setPreserveRatio(false);
		exitLevelLabel.setImage(TextureRegister.getTexture("exitCrossSarah"));
		exitLevelLabel.setVisible(false);
		exitLevelLabel.setPreserveRatio(false);
		descriptionLabel.setWrapText(true);
		descriptionLabel.setTextFill(Color.WHITE);
		descriptionLabel.setMaxWidth(260);
		descriptionLabel.setMaxHeight(170);
		descriptionLabel.setFont(Font.font("Arial", 15));
		descriptionBackground.toFront();
		descriptionLabel.toFront();

		menuButton.setOnMouseClicked(e -> this.returnToMenu());
		menuButton.setLayoutX(1105);
		menuButton.setLayoutY(685);
		menuButton.setPrefWidth(160);
		menuButton.setMaxHeight(40);
		menuButton.setText("Return to Menu");
		hideAllLabels();

		setMouseClickedHandlers();
		setKeyHandlers();
		currentLevel = VirtualMap.getLevels().get(0);
		currentNode = currentLevel;
		renderPlayer();
		movePlayerToNode(currentNode);
		menuButton.toFront();
	}

	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}

	/**
	 * Moves the player via a translation animation to the on-screen position of
	 * the Node above the player, if there is one.
	 *
	 * @see Node
	 */
	private void movePlayerUp() {
		if (!playerMoving) {
			playerMoving = true;
			if (currentNode.getNodeAbove() == null) {
				playerMoving = false;
				return;
			}
			Node destinationNode = currentNode.getNodeAbove();

			while (destinationNode.getNodeAbove() != null && (nodeIsLevel(
					destinationNode) == -1)) {
				destinationNode = destinationNode.getNodeAbove();
			}
			movePlayerToNode(destinationNode);
		}
	}

	/**
	 * Moves the player via a translation animation to the on-screen position of
	 * the Node below the player, if there is one.
	 *
	 * @see Node
	 */
	private void movePlayerDown() {
		if (!playerMoving) {
			playerMoving = true;
			if (currentNode.getNodeBelow() == null) {
				playerMoving = false;
				return;
			}

			Node destinationNode = currentNode.getNodeBelow();

			while (destinationNode.getNodeBelow() != null && (nodeIsLevel(
					destinationNode) == -1)) {
				destinationNode = destinationNode.getNodeBelow();
			}
			movePlayerToNode(destinationNode);
		}
	}

	/**
	 * Moves the player via a translation animation to the on-screen position of
	 * the Node to the right of the player, if there is one.
	 *
	 * @see Node
	 */
	private void movePlayerRight() {
		if (!playerMoving) {
			playerMoving = true;
			if (currentNode.getNodeRight() == null) {
				playerMoving = false;
				return;
			}

			Node destinationNode = currentNode.getNodeRight();

			while (destinationNode.getNodeRight() != null && (nodeIsLevel(
					destinationNode) == -1)) {
				destinationNode = destinationNode.getNodeRight();
			}
			movePlayerToNode(destinationNode);
		}
	}

	/**
	 * Moves the player via a translation animation to the on-screen position of
	 * the Node to the left of the player, if there is one.
	 *
	 * @see Node
	 */
	private void movePlayerLeft() {
		if (!playerMoving) {
			playerMoving = true;
			if (currentNode.getNodeLeft() == null) {
				playerMoving = false;
				return;
			}

			Node destinationNode = currentNode.getNodeLeft();

			while (destinationNode.getNodeLeft() != null && (nodeIsLevel(
					destinationNode) == -1)) {
				destinationNode = destinationNode.getNodeLeft();
			}
			movePlayerToNode(destinationNode);
		}
	}

	/**
	 * Moves the playerIcon to the on-screen position of a Node via a series of
	 * translation animations. The playerIcon will stay on the 'paths' of the
	 * overworld in order to reach it's destination and will take the shortest
	 * possible path.
	 *
	 * @param destinationNode The player will move to the on-screen position of this node.
	 */
	private void movePlayerToNode(Node destinationNode) {
		SequentialTransition movementTransition = new SequentialTransition();
		hideAllLabels();
		if (nodeIsLevel(destinationNode) != -1) {
			EventHandler<ActionEvent> animationHandler = e -> {
				playerMoving = false;
				if (nodeIsLevel(destinationNode) != -1) {
					currentLevel = VirtualMap.getLevels().get(nodeIsLevel(
							destinationNode));
				}
				hideAllLabels();
				revealLevelLabel(currentLevel);
			};

			movementTransition.setOnFinished(animationHandler);

		} else {
			EventHandler<ActionEvent> animationHandler = e -> {
				playerMoving = false;
				descriptionLabel.setText(
						"Our heroes march on, forging ahead into the unknown land ahead...");
			};
			movementTransition.setOnFinished(animationHandler);

			currentLevel = null;
		}

		for (Node node : VirtualMap.getPathBetween(currentNode,
				destinationNode)) {
			movePlayerToDestination(VirtualMap.getNodeActualPosition(node)
							.getX(), VirtualMap.getNodeActualPosition(node).getY(),
					movementTransition);
		}
		movementTransition.play();
		currentNode = destinationNode;
	}

	/**
	 * Sets key handlers to allow movement through the overworld though the use
	 * of the arrow keys, as well as the ability to enter levels with the Enter
	 * key. These key handlers are bound to the base pane of the fxml scene.
	 */
	private void setKeyHandlers() {
		EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.UP) {
					movePlayerUp();
				} else if (event.getCode() == KeyCode.DOWN) {
					movePlayerDown();
				} else if (event.getCode() == KeyCode.LEFT) {
					movePlayerLeft();
				} else if (event.getCode() == KeyCode.RIGHT) {
					movePlayerRight();
				} else if (event.getCode() == KeyCode.ENTER) {
					loadLevel(currentLevel);
				}
			}
		};
		overworldPane.setOnKeyPressed(keyHandler);

	}

	/**
	 * Places the player on the level it is currently on. Used to place the
	 * player on the screen when overworld is first loaded.
	 */
	private void renderPlayer() {
		double imageHeight = currentLevel.getImageView().getFitHeight();
		double imageWidth = currentLevel.getImageView().getFitWidth();

		playerImage.setFitWidth(imageWidth);
		playerImage.setFitHeight(imageHeight);
		playerImage.setImage(TextureRegister.getTexture("playerIcon"));
		playerImage.toFront();
	}

	/**
	 * Draws a rectangle in the overworld from the on-screen position of one
	 * node to another. The width and height of this rectangle are dictated by
	 * attributes of VirtualMap.
	 *
	 * @param pane  The pane on which the rectangle is drawn.
	 * @param start The starting node that the rectangle is drawn from.
	 * @param end   The ending node that the rectangle is drawn to.
	 * @see VirtualMap
	 * @see Node
	 */
	private void renderRectangle(Pane pane, Node start, Node end) {
		Rectangle r = new Rectangle();
		double scale = 0.4;
		double imageWidth = VirtualMap.getImageWidth();
		double imageHeight = VirtualMap.getImageHeight();
		double pathWidth = Math.min(imageHeight, imageWidth) * scale;
		double startX = VirtualMap.getNodeActualPosition(start).getX()
				+ (imageWidth - pathWidth) / 2;
		double startY = VirtualMap.getNodeActualPosition(start).getY()
				+ (imageHeight - pathWidth) / 2;
		double endX = VirtualMap.getNodeActualPosition(end).getX() + (imageWidth
				- pathWidth) / 2;
		double endY = VirtualMap.getNodeActualPosition(end).getY()
				+ (imageHeight - pathWidth) / 2;

		r.setFill(Color.GREY);

		if (start.equals(end)) {
			r.setX(startX);
			r.setY(startY);
			r.setHeight(pathWidth);
			r.setWidth(pathWidth);
			pane.getChildren().add(r);
			return;
		}

		if (Math.abs(startX - endX) <= 0) {
			r.setX(Math.min(startX, endX));
			r.setY(Math.min(startY, endY));
			r.setHeight(Math.abs(startY - endY));
			r.setWidth(pathWidth);
		} else {
			r.setX(Math.min(startX, endX));
			r.setY(Math.min(startY, endY));
			r.setHeight(pathWidth);
			r.setWidth(Math.abs(startX - endX));
		}

		pane.getChildren().add(r);
	}

	/**
	 * Draws a path of rectangles from a Level to a node. Makes use of Nodes
	 * parentNode attribute to recursively call renderPath and create a full
	 * path. The path is made of only horizontal and vertical rectangles.
	 *
	 * @param pane The JavaFX pane on which the path is drawn.
	 * @param node The Node to which a path is being drawn.
	 * @see Node
	 */
	private void renderPath(Pane pane, Node node) {
		Node parentNode = node.getParentNode();
		if (parentNode != null) {
			renderRectangle(pane, node, parentNode);
			if (!(parentNode instanceof Level)) {
				renderPath(pane, parentNode);
			}
		}
	}

	/**
	 * Determines if a Node represents a Level in the overworld. If it does,
	 * it's index in the list of levels in VirtualMap is returned. Otherwise -1
	 * is returned.
	 *
	 * @param node This Node is checked to determine if it represents a Level in
	 *             the overworld.
	 * @return The index of Level the node represents in the list of levels
	 * stored in VirtualMap. If the node does not represent a level, -1
	 * is returned.
	 */
	public int nodeIsLevel(Node node) {
		int index = 0;
		for (Level level : VirtualMap.getLevels()) {
			if (level.getCoordinates().equals(node.getCoordinates())) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * Render the overworld Virtual Map onto the overworld JavaFX pane.
	 *
	 * @param pane The JavaFx pane to render the overworld Virtual Map onto.
	 */
	private void renderOverworld(Pane pane) {
		// Set the backdrop
		backgroundImage.setImage(TextureRegister.getTexture("map2.0"));
		descriptionBackground.setImage(TextureRegister.getTexture("boxSarah"));
		descriptionLabel.setText(
				"Welcome to the duXCOM campaign! Select a level and begin your adventure");// 214
		// CHARACTER
		// MAXIMUM

		// Tell the virtual map the number of nodes to allow for
		VirtualMap.scaleVirtualMap(pane.getPrefHeight(), pane.getPrefWidth(),
				nodesHigh, nodesWide);

		// Load levels in the level register
		if (VirtualMap.getLevels().size() == 0) {
			VirtualMap.loadLevels();
		}
		// Place the roads onto the pane
		for (Level level : VirtualMap.getLevels()) {
			renderRectangle(pane, level, level);
			renderPath(pane, level);
		}

		// Place the levels onto the pane
		for (Level level : VirtualMap.getLevels()) {
			pane.getChildren().add(level.getImageView());
			pane.getChildren().add(level.getLevelLabel());
		}
	}

	/**
	 * Hides all the JavaFX Labels tied to the Levels in overworld.
	 *
	 * @see Level
	 */
	private void hideAllLabels() {
		for (Level level : VirtualMap.getLevels()) {
			hideLevelLabel(level);
		}
	}

	/**
	 * Sets the MouseClicked handlers of every level icon on the overworld to a
	 * method that moves the player icon to that level icon's position.
	 */
	private void setMouseClickedHandlers() {
		for (Level level : VirtualMap.getLevels()) {
			level.getImageView().setOnMouseClicked(e -> movePlayerToLevel(
					level));
		}
		playerImage.setOnMouseClicked(e -> loadLevel(currentLevel));
		exitLevelLabel.setOnMouseClicked(e -> hideLevelLabel(currentLevel));
	}

	/**
	 * Moves the player icon to the on-screen position of a Level through a
	 * series of translation animations. This movement always sticks to the
	 * paths of the overworld, and will take the shortest path.
	 *
	 * @param destinationLevel The Level that the player icon will travel to.
	 */
	public void movePlayerToLevel(Level destinationLevel) {
		if (!playerMoving) {
			playerMoving = true;

			if (currentNode.getCoordinates().equals(destinationLevel
					.getCoordinates())) {
				loadLevel(currentLevel);
			}

			hideAllLabels();

			SequentialTransition movementTransition = new SequentialTransition();

			for (Node node : VirtualMap.getPathBetween(currentNode,
					destinationLevel)) {
				movePlayerToDestination(VirtualMap.getNodeActualPosition(node)
								.getX(), VirtualMap.getNodeActualPosition(node).getY(),
						movementTransition);
			}

			currentLevel = destinationLevel;
			currentNode = destinationLevel;
			movementTransition.play();

			EventHandler<ActionEvent> animationHandler = e -> {
				revealLevelLabel(destinationLevel);
				playerMoving = false;
			};

			movementTransition.setOnFinished(animationHandler);
		}
	}

	/**
	 * Hides the JavaFX Label tied to a Level
	 *
	 * @param level The Level that has it's JavaFX Label hidden.
	 * @see Level
	 */
	private void hideLevelLabel(Level level) {
		if (level == null) {
			return;
		}
		this.exitLevelLabel.setVisible(false);
		this.levelLabelBackground.setVisible(false);
		level.getLevelLabel().setVisible(false);

	}

	/**
	 * Reveals the JavaFX label tied to a Level. This Label is placed on the
	 * overworld just below the on-screen icon corresponding to the level. Also
	 * reveals a button to hide this label at the top right of the label.
	 *
	 * @param level The Level that has it's JavaFX Label revealed.
	 */
	private void revealLevelLabel(Level level) {
		level.getLevelLabel().setVisible(true);

		descriptionLabel.setText(currentLevel.getLevelDescription());

		this.levelLabelBackground.setFitWidth(level.getLevelLabel().getWidth()
				+ 20);
		this.levelLabelBackground.setFitHeight(level.getLevelLabel().getHeight()
				+ 20);

		this.levelLabelBackground.setLayoutX(level.getLevelLabel().getLayoutX()
				- 10);
		this.levelLabelBackground.setLayoutY(level.getLevelLabel().getLayoutY()
				- 10);

		this.exitLevelLabel.setLayoutX(levelLabelBackground.getLayoutX()
				+ this.levelLabelBackground.getFitWidth() - 15);
		this.exitLevelLabel.setLayoutY(levelLabelBackground.getLayoutY() - 2);
		this.exitLevelLabel.setVisible(true);

		this.levelLabelBackground.setVisible(true);
		this.levelLabelBackground.toFront();
		level.getLevelLabel().toFront();
		this.exitLevelLabel.toFront();

	}

	/**
	 * Adds a translation animation to an x,y coordinate on the overworld to a
	 * SequentialTransition. This translation has linear speed.
	 *
	 * @param xDestination         The x coordinate that the added animation travels to.
	 * @param yDestination         The y coordinate that the added animation travels to.
	 * @param sequentialTransition The translation animation to the coordinate is added to this
	 *                             SequentiialTransition.
	 * @see SequentialTransition
	 */
	private void movePlayerToDestination(double xDestination,
										 double yDestination, SequentialTransition sequentialTransition) {
		TranslateTransition movementAnimation = new TranslateTransition(Duration
				.millis(travelTime), playerImage);
		movementAnimation.setInterpolator(Interpolator.LINEAR);
		movementAnimation.setToX(xDestination);
		movementAnimation.setToY(yDestination);
		movementAnimation.setCycleCount(1);
		sequentialTransition.getChildren().add(movementAnimation);
	}

	/**
	 * Enters the gameplay of a given Level.
	 *
	 * @param level The gameplay of this level is entered.
	 * @see LevelRegister
	 */
	private void loadLevel(Level level) {
		if (nodeIsLevel(currentNode) != -1) {
			LevelRegister.loadLevel(interfaceManager, stage, level);
		}
	}

	/**
	 * Returns to the Main Menu.
	 */
	private void returnToMenu() {
		SoundPlayer.playMenuMusic();
		interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN,
				stage, "");

	}

	/**
	 * Sets the interfaceManager class variable such that the class is able to
	 * call InterfaceManager methods.
	 *
	 * @param interfaceManager The interfaceManager which is controlling the Interface that
	 *                         called the login screen fxml file.
	 */
	public void setInterfaceManager(InterfaceManager interfaceManager) {
		this.interfaceManager = interfaceManager;
	}

	/**
	 * Sets the Stage variable such that the class is able to render to the
	 * Stage.
	 *
	 * @param stage The Stage which the interfaces are rendered on.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Has the JavaFX pane of the overworld requestFocus. Should be called after
	 * the overworld is opened so that keyboard events are received properly.
	 */
	public void getFocus() {
		overworldPane.requestFocus();
	}
        
    /**
     * Sets the Keyboard shortcut handler
     * 
     * @param interfaceManager InterfaceManager of the game
     */
    public void setKeyEvent(InterfaceManager interfaceManager) {
        overworldPane.setOnKeyPressed(new OverworldScreenKeyHandler(
                interfaceManager, overworldPane));
    }
}
