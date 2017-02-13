package uq.deco2800.duxcom.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.achievements.Achievement;
import uq.deco2800.duxcom.achievements.AchievementManager;
import uq.deco2800.duxcom.achievements.AchievementType;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.heros.HeroType;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static uq.deco2800.duxcom.achievements.AchievementType.*;

/**
 * PlayerRewardsnController is the controller used for the game's player rewards / skin screen. It
 * controls the user interaction with the skin the charcters skin have.
 *
 * This class is initialized through the fxml file used for the player rewaards screen. The fxml
 * file is initially called from PlayerRewardsInterface.
 *
 * @author Daniel Gormly
 * @see PlayerRewardsInterface
 */
public class PlayerRewardsController implements Initializable {

    // Initiating the class logger
    private static Logger logger = LoggerFactory.getLogger(PlayerRewardsController.class);

    // Declaring the class instances to interact with
    private InterfaceManager interfaceManager;

    private Stage stage;

    private SingularityRestClient restClient;

    // Maps achievementID to textureName.
    private Map<String, String> skinMap = new HashMap<>();

    // Maps achievementID to HeroType.
    private Map<String, HeroType> heroMap = new HashMap<>();

    // Maps achievmentID to button.
    private Map<String, Button> buttonMap = new HashMap<>();

    // Maps button to CSS
    private Map<Button, String> cssMap = new HashMap<>();

    // Declaring the fx objects that will be modified
    @FXML
    Button backButton;
    @FXML
    Button numLoginButton1;
    @FXML
    Button numLoginButton2;
    @FXML
    Button numLoginButton3;
    @FXML
    Button numLoginButton4;
    @FXML
    Button numKillsButton1;
    @FXML
    Button numKillsButton2;
    @FXML
    Button numKillsButton3;
    @FXML
    Button numKillsButton4;
    @FXML
    Button numDeathsButton1;
    @FXML
    Button numDeathsButton2;
    @FXML
    Button numDeathsButton3;
    @FXML
    Button numDeathsButton4;
    @FXML
    Button timeStageButton1;
    @FXML
    Button timeStageButton2;
    @FXML
    Button timeStageButton3;
    @FXML
    Button timeStageButton4;
    @FXML
    Button tutorialButton;
    @FXML
    Button secretButton;
    @FXML
    Text achievementTitleLabel;
    @FXML
    Text achievementDescriptionLabel;
    @FXML
    Text usernameLabel;
    @FXML
    ImageView skinPreviewImageView;

    private final String CSS_STYLING = "-fx-background-size: 55.0 55.0;"
            + "-fx-background-repeat: stretch;"
            + "-fx-background-position: center;";

    /**
     * A function which sets up the button and input field handlers.
     *
     * Creates a handler which checks for the input of the ENTER key on each user key stroke.
     * Attempts to login if enter is pressed by the user whilst in an input field.
     *
     * Ties all of the buttons to the desired method on action.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if
     *                  the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object
     *                  was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<KeyEvent> keyHandler = e -> {
            if (e.getCode() == KeyCode.BACK_SPACE) {
                this.startBackToLoadScreen();
            }
        };

        /* Unlocks all achievements for leggy. */
        if ("leggy".equals(AchievementManager.getUsername())) {
            AchievementManager.unlockAll();
        }
        /* Set title. */
        usernameLabel.setText(AchievementManager.getUsername());

        /* Map Buttons to AchievementIDs */
        buttonMap.put("kill1", numKillsButton1);
        buttonMap.put("kill2", numKillsButton2);
        buttonMap.put("kill3", numKillsButton3);
        buttonMap.put("kill4", numKillsButton4);

        buttonMap.put("death1", numDeathsButton1);
        buttonMap.put("death2", numDeathsButton2);
        buttonMap.put("death3", numDeathsButton3);
        buttonMap.put("death4", numDeathsButton4);

        buttonMap.put("login1", numLoginButton1);
        buttonMap.put("login2", numLoginButton2);
        buttonMap.put("login3", numLoginButton3);
        buttonMap.put("login4", numLoginButton4);

        buttonMap.put("time1", timeStageButton1);
        buttonMap.put("time2", timeStageButton2);
        buttonMap.put("time3", timeStageButton3);
        buttonMap.put("time4", timeStageButton4);

        buttonMap.put("tutorial", tutorialButton);
        buttonMap.put("secret", secretButton);

        /* Map achievementID to texture Name */
        skinMap.put("kill1", "silver_archer");
        skinMap.put("kill2", "gold_archer");
        skinMap.put("kill3", "hat_archer");
        skinMap.put("kill4", "silver_cavalier");

        skinMap.put("death1", "gold_cavalier");
        skinMap.put("death2", "hat_cavalier");
        skinMap.put("death3", "silver_knight");
        skinMap.put("death4", "gold_knight");

        skinMap.put("login1", "hat_knight");
        skinMap.put("login2", "silver_warlock");
        skinMap.put("login3", "gold_warlock");
        skinMap.put("login4", "hat_warlock");

        skinMap.put("time1", "silver_priest");
        skinMap.put("time2", "gold_priest");
        skinMap.put("time3", "hat_priest");
        skinMap.put("time4", "silver_rogue");

        skinMap.put("tutorial", "gold_rogue");
        skinMap.put("secret", "hat_rogue");


        /* Map achievementID to HeroType */
        heroMap.put("kill1", HeroType.ARCHER);
        heroMap.put("kill2", HeroType.ARCHER);
        heroMap.put("kill3", HeroType.ARCHER);
        heroMap.put("kill4", HeroType.CAVALIER);

        heroMap.put("death1", HeroType.CAVALIER);
        heroMap.put("death2", HeroType.CAVALIER);
        heroMap.put("death3", HeroType.KNIGHT);
        heroMap.put("death4", HeroType.KNIGHT);

        heroMap.put("login1", HeroType.KNIGHT);
        heroMap.put("login2", HeroType.WARLOCK);
        heroMap.put("login3", HeroType.WARLOCK);
        heroMap.put("login4", HeroType.WARLOCK);

        heroMap.put("time1", HeroType.PRIEST);
        heroMap.put("time2", HeroType.PRIEST);
        heroMap.put("time3", HeroType.PRIEST);
        heroMap.put("time4", HeroType.ROGUE);

        heroMap.put("tutorial", HeroType.ROGUE);
        heroMap.put("secret", HeroType.ROGUE);

        /* Map button to CSS */
        cssMap.put(numKillsButton1, "-fx-background-image: url(\"/playerRewards/achievementIcons/genocide.png\");"
                        + CSS_STYLING);
        cssMap.put(numKillsButton2, "-fx-background-image: url(\"/playerRewards/achievementIcons/black-eye.png\");"
                + CSS_STYLING);
        cssMap.put(numKillsButton3, "-fx-background-image: url(\"/playerRewards/achievementIcons/cage-fight.png\");"
                + CSS_STYLING);
        cssMap.put(numKillsButton4, "-fx-background-image: url(\"/playerRewards/achievementIcons/blood-sky-god.png\");"
                + CSS_STYLING);

        cssMap.put(numDeathsButton1, "-fx-background-image: url(\"/playerRewards/achievementIcons/accident-prone.png\");"
                + CSS_STYLING);
        cssMap.put(numDeathsButton2, "-fx-background-image: url(\"/playerRewards/achievementIcons/pinata-party.png\");"
                + CSS_STYLING);
        cssMap.put(numDeathsButton3, "-fx-background-image: url(\"/playerRewards/achievementIcons/pyramid-of-skulls.png\");"
                + CSS_STYLING);
        cssMap.put(numDeathsButton4, "-fx-background-image: url(\"/playerRewards/achievementIcons/rome-has-fallen.png\");"
                + CSS_STYLING);

        cssMap.put(numLoginButton1, "-fx-background-image: url(\"/playerRewards/achievementIcons/eat-just-one.png\");"
                + CSS_STYLING);
        cssMap.put(numLoginButton2, "-fx-background-image: url(\"/playerRewards/achievementIcons/daily-routine.png\");"
                + CSS_STYLING);
        cssMap.put(numLoginButton3, "-fx-background-image: url(\"/playerRewards/achievementIcons/tis-the-season.png\");"
                + CSS_STYLING);
        cssMap.put(numLoginButton4, "-fx-background-image: url(\"/playerRewards/achievementIcons/call-of-duty.png\");"
                + CSS_STYLING);

        cssMap.put(timeStageButton1, "-fx-background-image: url(\"/playerRewards/achievementIcons/traveller.png\");"
                + CSS_STYLING);
        cssMap.put(timeStageButton2, "-fx-background-image: url(\"/playerRewards/achievementIcons/challenger.png\");"
                + CSS_STYLING);
        cssMap.put(timeStageButton3, "-fx-background-image: url(\"/playerRewards/achievementIcons/knight-shining.png\");"
                + CSS_STYLING);
        cssMap.put(timeStageButton4, "-fx-background-image: url(\"/playerRewards/achievementIcons/lord-of-falador.png\");"
                + CSS_STYLING);

        cssMap.put(secretButton,  "-fx-background-image: url(\"/playerRewards/achievementIcons/question-mark.png\");"
                + CSS_STYLING);
        cssMap.put(tutorialButton,  "-fx-background-image: url(\"/playerRewards/achievementIcons/tutorial-complete.png\");"
                + CSS_STYLING);

        /* Setup key events. */
        numLoginButton1.setOnKeyPressed(keyHandler);
        numLoginButton2.setOnKeyPressed(keyHandler);
        numLoginButton3.setOnKeyPressed(keyHandler);
        numLoginButton4.setOnKeyPressed(keyHandler);
        numKillsButton1.setOnKeyPressed(keyHandler);
        numKillsButton2.setOnKeyPressed(keyHandler);
        numKillsButton3.setOnKeyPressed(keyHandler);
        numKillsButton4.setOnKeyPressed(keyHandler);
        numDeathsButton1.setOnKeyPressed(keyHandler);
        numDeathsButton2.setOnKeyPressed(keyHandler);
        numDeathsButton3.setOnKeyPressed(keyHandler);
        numDeathsButton4.setOnKeyPressed(keyHandler);
        timeStageButton1.setOnKeyPressed(keyHandler);
        timeStageButton2.setOnKeyPressed(keyHandler);
        timeStageButton3.setOnKeyPressed(keyHandler);
        timeStageButton4.setOnKeyPressed(keyHandler);
        tutorialButton.setOnKeyPressed(keyHandler);
        secretButton.setOnKeyPressed(keyHandler);
        backButton.setOnKeyPressed(keyHandler);

        /* Setup hover events */
        numKillsButton1.setOnMouseEntered(e -> this.displayAchievement("kill1"));
        numKillsButton2.setOnMouseEntered(e -> this.displayAchievement("kill2"));
        numKillsButton3.setOnMouseEntered(e -> this.displayAchievement("kill3"));
        numKillsButton4.setOnMouseEntered(e -> this.displayAchievement("kill4"));

        numDeathsButton1.setOnMouseEntered(e -> this.displayAchievement("death1"));
        numDeathsButton2.setOnMouseEntered(e -> this.displayAchievement("death2"));
        numDeathsButton3.setOnMouseEntered(e -> this.displayAchievement("death3"));
        numDeathsButton4.setOnMouseEntered(e -> this.displayAchievement("death4"));

        numLoginButton1.setOnMouseEntered(e -> this.displayAchievement("login1"));
        numLoginButton2.setOnMouseEntered(e -> this.displayAchievement("login2"));
        numLoginButton3.setOnMouseEntered(e -> this.displayAchievement("login3"));
        numLoginButton4.setOnMouseEntered(e -> this.displayAchievement("login4"));

        timeStageButton1.setOnMouseEntered(e -> this.displayAchievement("time1"));
        timeStageButton2.setOnMouseEntered(e -> this.displayAchievement("time2"));
        timeStageButton3.setOnMouseEntered(e -> this.displayAchievement("time3"));
        timeStageButton4.setOnMouseEntered(e -> this.displayAchievement("time4"));

        tutorialButton.setOnMouseEntered(e -> this.displayAchievement("tutorial"));
        secretButton.setOnMouseEntered(e -> this.displayAchievement("secret"));

        /* Clear button text. */
        resetButton(KILL);
        resetButton(DEATH);
        resetButton(SCORE);
        resetButton(TIME);
        resetButton(MIS);

        /* Set Button actions. */
        numKillsButton1.setOnAction(e -> this.changeSkin("kill1"));
        numKillsButton2.setOnAction(e -> this.changeSkin("kill2"));
        numKillsButton3.setOnAction(e -> this.changeSkin("kill3"));
        numKillsButton4.setOnAction(e -> this.changeSkin("kill4"));

        numDeathsButton1.setOnAction(e -> this.changeSkin("death1"));
        numDeathsButton2.setOnMouseClicked(e -> this.changeSkin("death2"));
        numDeathsButton3.setOnMouseClicked(e -> this.changeSkin("death3"));
        numDeathsButton4.setOnMouseClicked(e -> this.changeSkin("death4"));

        numLoginButton1.setOnMouseClicked(e -> this.changeSkin("login1"));
        numLoginButton2.setOnMouseClicked(e -> this.changeSkin("login2"));
        numLoginButton3.setOnMouseClicked(e -> this.changeSkin("login3"));
        numLoginButton4.setOnMouseClicked(e -> this.changeSkin("login4"));

        timeStageButton1.setOnMouseClicked(e -> this.changeSkin("time1"));
        timeStageButton2.setOnMouseClicked(e -> this.changeSkin("time2"));
        timeStageButton3.setOnMouseClicked(e -> this.changeSkin("time3"));
        timeStageButton4.setOnMouseClicked(e -> this.changeSkin("time4"));

        tutorialButton.setOnAction(e -> this.changeSkin("tutorial"));
        secretButton.setOnAction(e -> this.changeSkin("secret"));
        
        backButton.setOnAction(e -> this.startBackToLoadScreen());
    }

    /**
     * Creates a new thread to perform the back to load screen process
     */
    private void startBackToLoadScreen() {
        Runnable back = this::runBackToLoadScreen;

        Thread backThread = new Thread(back);
        backThread.setDaemon(true);
        backThread.start();
    }

    /**
     * Clears the text and style of the corresponding button catergory.
     *
     * @param type
     */
    private void resetButton(AchievementType type) {
        switch (type) {
            case KILL:
                numKillsButton1.setText("");
                numKillsButton2.setText("");
                numKillsButton3.setText("");
                numKillsButton4.setText("");

                numKillsButton1.setStyle(cssMap.get(numKillsButton1));
                numKillsButton2.setStyle(cssMap.get(numKillsButton2));
                numKillsButton3.setStyle(cssMap.get(numKillsButton3));
                numKillsButton4.setStyle(cssMap.get(numKillsButton4));
                break;
            case DEATH:
                numDeathsButton1.setText("");
                numDeathsButton2.setText("");
                numDeathsButton3.setText("");
                numDeathsButton4.setText("");

                numDeathsButton1.setStyle(cssMap.get(numDeathsButton1));
                numDeathsButton2.setStyle(cssMap.get(numDeathsButton2));
                numDeathsButton3.setStyle(cssMap.get(numDeathsButton3));
                numDeathsButton4.setStyle(cssMap.get(numDeathsButton4));
                break;
            case SCORE:
                numLoginButton1.setText("");
                numLoginButton2.setText("");
                numLoginButton3.setText("");
                numLoginButton4.setText("");

                numLoginButton1.setStyle(cssMap.get(numLoginButton1));
                numLoginButton2.setStyle(cssMap.get(numLoginButton2));
                numLoginButton3.setStyle(cssMap.get(numLoginButton3));
                numLoginButton4.setStyle(cssMap.get(numLoginButton4));
                break;
            case TIME:
                timeStageButton1.setText("");
                timeStageButton2.setText("");
                timeStageButton3.setText("");
                timeStageButton4.setText("");

                timeStageButton1.setStyle(cssMap.get(timeStageButton1));
                timeStageButton2.setStyle(cssMap.get(timeStageButton2));
                timeStageButton3.setStyle(cssMap.get(timeStageButton3));
                timeStageButton4.setStyle(cssMap.get(timeStageButton4));
                break;
            case MIS:
                tutorialButton.setText("");
                secretButton.setText("");

                tutorialButton.setStyle(cssMap.get(tutorialButton));
                secretButton.setStyle(cssMap.get(secretButton));
            default:
                break;
        }
    }

    /**
     * Displays the achievement information.
     *
     * @param achievementID
     */
    private void displayAchievement(String achievementID) {
        Achievement achievement = AchievementManager.getAchievement(achievementID);
        String achievementTitle = achievement.getName();
        String achievementDescription = achievement.getDescription();
        this.achievementTitleLabel.setText(achievementTitle);
        this.achievementDescriptionLabel.setText(achievementDescription);
        displaySkin(achievementID);
    }

    /**
     * Displays the corresponding skin. If the skin isn't unlocked a black and white image will be displayed.
     *
     * @param achievementID
     */
    private void displaySkin(String achievementID) {
        Image skin = TextureRegister.getTexture("test_skin");
        Achievement achievement = AchievementManager.getAchievement(achievementID);
        /* Set achievement skins. */
        if (skinMap.containsKey(achievementID)) {
            String textureName = skinMap.get(achievementID);
            skin = TextureRegister.getTexture(textureName);
        }
        /* Process silhouette. */
        if (!AchievementManager.getAccountAchievements().contains(achievement)) {
            skin = createDarkImage(skin);
        }
        skinPreviewImageView.setImage(skin);
    }

    /**
     * Takes given image and returns a silhouette image.
     *
     * @param image, image to be converted to a darker image.
     */
    private static Image createDarkImage(Image image) {
        WritableImage core = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter corePixelWriter = core.getPixelWriter();
        PixelReader imagePixelReader = image.getPixelReader();

        for (int k = 0; k < image.getWidth(); k++) {
            for (int l = 0; l < image.getHeight(); l++) {
                Color oldColor = imagePixelReader.getColor(k, l);
                if (oldColor.getOpacity() > 0.9) {
                    corePixelWriter.setColor(k, l, Color.BLACK);
                }
            }
        }
        return core;
    }

    /**
     * Changes the skin of a hero if a skin has been registered for the achievement.
     *
     * @param achievementID, ID of the achievement.
     */
    private void changeSkin(String achievementID) {
        if (AchievementManager.isUnlocked(AchievementManager.getAchievement(achievementID))
                && skinMap.containsKey(achievementID) && heroMap.containsKey(achievementID)) {
            //DataRegisterManager.getHeroDataRegister().getData(heroMap.get(achievementID))
            //        .setHeroGraphic(skinMap.get(achievementID));
            Achievement achievement = AchievementManager.getAchievement(achievementID);
            AchievementType type = achievement.getTypeEnum();
            resetButton(type);
            Button button = buttonMap.get(achievementID);
            if (button.getStyle().equals(cssMap.get(button))) {
                button.setStyle(button.getStyle() +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow( gaussian , rgba(255, 204, 0, 0.7) , 5, 0.0 , 0 , 1 );"
                );
            } else {
                logger.info("Resetting Icon");
                resetButton(type);
            }
        }
    }

    /**
     * Changes to the login interface.
     * Should be run only through startBackToLoginScreen in order to be run in a background thread.
     */
    private void runBackToLoadScreen() {
        logger.info("Returning to load screen");
        Platform.runLater(() -> interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, stage, "load"));
    }

    /**
     * Sets the interfaceManager class variable such that the class is able to call InterfaceManager
     * methods.
     *
     * @param interfaceManager The interfaceManager which is controlling the Interface that called
     *                         the registration fxml file.
     */
    public void setInterfaceManager(InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    /**
     * Sets the Stage variable such that the class is able to render to the Stage.
     *
     * @param stage The Stage of the interface
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
