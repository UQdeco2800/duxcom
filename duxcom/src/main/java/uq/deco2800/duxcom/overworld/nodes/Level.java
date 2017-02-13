package uq.deco2800.duxcom.overworld.nodes;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uq.deco2800.duxcom.graphics.TextureRegister;

import java.awt.geom.Point2D;
import java.util.Arrays;

/**
 * A level object extends a node and additionally carries information about game maps and their completion status.
 *
 * @author Sceub
 * @author mtimmo
 */
public class Level extends Node {

	// The unique identifier for the level.
	private String levelId;

	// An array of levels that must be conquered before this level can be played.
	private Level[] unlockedAfter;

	// The unlocked state of the level - if the level is unlocked (true) then the level can be played.
	private boolean unlocked = false;

	// The conquered (completed) state of the level.
	private boolean conquered = false;

	// The image settings to display on the overworld map.
	private ImageView imageView = new ImageView();

	// The label that appears underneath the selected level.
	private Label levelLabel = new Label();

	// The description of the level that will appear in the corner of the screen.
	private String levelDescription;

	/**
	 * Constructor. Also sets the default settings for the Label.
	 *
	 * @param levelId          the unique identified for the level that corresponds to the game map name
	 * @param coordinates      the virtual position of this Level on the Overworld map
	 * @param parentNode       the Node that this Node branched from
	 * @param unlockedAfter    an array of levels that need to be conquered (completed) before this level unlocks
	 */
	public Level(String levelId, Point2D coordinates, Node parentNode, Level[] unlockedAfter) {
		super(coordinates, parentNode);
		setLevelId(levelId);
		setUnlockedAfter(unlockedAfter);

		// If the unlockedAfter array is empty, marked level as unlocked
		if (unlockedAfter == null) {
			unlocked = true;
		}

		// Set up the ImageView and Label
		this.imageView.setPreserveRatio(false);
		this.levelLabel.setFont(Font.font("Arial, 15"));
		this.levelLabel.setVisible(false);
		this.levelLabel.setContentDisplay(ContentDisplay.TOP);
		this.levelLabel.setWrapText(true);
		this.levelLabel.setMaxHeight(180);
		this.levelLabel.setMaxWidth(105);
		this.levelLabel.setTextFill(Color.WHITE);
	}

	/**
	 * Get the level description for this level
	 * @return the level description for this level
	 */
	public String getLevelDescription() {
		return this.levelDescription;
	}

	public void setLevelDescription(String description) {
		this.levelDescription = description;
	}

	public void setLevelLabelText(String labelText) {
		this.levelLabel.setText(labelText);
	}

	public void setLevelImage(String imageName) {
		this.imageView.setImage(TextureRegister.getTexture(imageName));
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public Label getLevelLabel() {
		return this.levelLabel;
	}

	public Level[] getUnlockedAfter() {
		return unlockedAfter;
	}

	public void setUnlockedAfter(Level[] unlockedAfter) {
		this.unlockedAfter = unlockedAfter;
	}

	/**
	 * Check if the level is unlocked or not
	 * @return true if the level is unlocked, false if not
	 */
	public boolean isUnlocked() {
		return unlocked;
	}

	/**
	 * Set the level as unlocked or locked
	 * @param unlocked true if unlocked, false if not
	 */
	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;

		if (unlocked) {
			setLevelImage("swordIconOpen");
		} else {
			setLevelImage("lock");
		}
	}

	/**
	 * Check if the level is conquered or not
	 * @return true if the level is conquered, false if not
	 */
	public boolean isConquered() {
		return conquered;
	}

	/**
	 * Set the level as conquered or unconquered
	 * @param conquered true if conquered, false if not
	 */
	public void setConquered(boolean conquered) {
		this.conquered = conquered;

		if (conquered) {
			setLevelImage("complete");
		} else {
			if (this.unlocked) {
				setLevelImage("swordIconOpen");
			} else {
				setLevelImage("lock");
			}
		}
	}

	/**
	 * Get the ImageView for this level
	 * @return the ImageView for this level
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * Sets up the ImageView for this level
	 *
	 * @param xPos           The virtual x position for the ImageView
	 * @param yPos           The virtual y position for the ImageView
	 * @param imageHeight    The ImageView height in pixels
	 * @param imageWidth     The ImageView width in pixels
	 */
	public void setUpImageView(double xPos, double yPos, double imageHeight, double imageWidth) {
		imageView.setX(xPos);
		imageView.setY(yPos);
		imageView.setFitWidth(imageWidth);
		imageView.setFitHeight(imageHeight);
		levelLabel.setLayoutX(xPos + 65);
		levelLabel.setLayoutY(yPos + 65);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Level)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		Level level = (Level) o;

		if (isUnlocked() != level.isUnlocked()) {
			return false;
		}
		if (isConquered() != level.isConquered()) {
			return false;
		}
		if (getLevelId() != null ? !getLevelId().equals(level.getLevelId()) : level.getLevelId() != null) {
			return false;
		}
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		if (!Arrays.equals(getUnlockedAfter(), level.getUnlockedAfter())) {
			return false;
		}
		return getImageView() != null ? getImageView().equals(level.getImageView()) : level.getImageView() == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (getLevelId() != null ? getLevelId().hashCode() : 0);
		result = 31 * result + Arrays.hashCode(getUnlockedAfter());
		result = 31 * result + (isUnlocked() ? 1 : 0);
		result = 31 * result + (isConquered() ? 1 : 0);
		result = 31 * result + (getImageView() != null ? getImageView().hashCode() : 0);
		return result;
	}
}
