/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.coop.MultiplayerGameManager;
import uq.deco2800.duxcom.coop.listeners.BlockStateListener;
import uq.deco2800.duxcom.coop.listeners.MessageRecievedListener;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.listeners.CurrentHeroChangeListener;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpUIController;
import uq.deco2800.duxcom.interfaces.gameinterface.statusbars.ActionPointBar;
import uq.deco2800.duxcom.interfaces.gameinterface.statusbars.HealthBar;
import uq.deco2800.duxcom.interfaces.gameinterface.statuslabels.ActionPointLabel;
import uq.deco2800.duxcom.interfaces.gameinterface.statuslabels.HealthLabel;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMaker;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.interfaces.overlaymaker.ui.OverlayMakerUI;
import uq.deco2800.duxcom.messaging.GameMessageQueue;
import uq.deco2800.duxcom.messaging.MessagingManager;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectives.ProtectionObjective;
import uq.deco2800.duxcom.objectivesystem.GameState;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author TROLL
 */
public class UserInterfaceController extends OverlayMaker implements Initializable, CurrentHeroChangeListener {

    // Initialising the logger variables
    private static Logger logger = LoggerFactory.getLogger(UserInterfaceController.class);
    private AnchorPane gamePane;

    /**
     * Titled pane that holds objectives
     */
    @FXML
    private TitledPane objectivePane;

    /**
     * Titled pane that holds the messages
     */
    @FXML
    private TitledPane chatPane;

    /**
     * A TextField used by other classes for sending messages. It is held within
     * the chatPane.
     */
    @FXML
    public TextField messageSendBox;

    /**
     * Public logbox to be written to by other classes
     */
    @FXML
    public TextArea logbox;

    /**
     * A bar to represent player's current health
     */
    @FXML
    private HealthBar healthBar;

    /**
     * The grid container to hold Active Effect (Buff) icons
     */
    @FXML
    private GridPane activeEffectsIconGrid;

    /**
     * Label for displaying the description of the current active effect
     */
    @FXML
    private Label activeEffectDescription;

    /**
     * A bar to represent player's full health. Is used to contrast player's
     * current health.
     */
    @FXML
    private HealthLabel healthLabel;

    /**
     * A bar to represent a player's AP.
     */
    @FXML
    private ActionPointLabel actionPointLabel;

    /**
     * A bar to represent player's full AP. Is used to contrast a player's
     * current AP.
     */
    @FXML
    private ActionPointBar actionPointBar;

    /**
     * A button to open a player's inventory or shop.
     */
    @FXML
    private Button shopButton;
    
    /**
     * Opens HeroPopUp Button
     */
    @FXML
    private Button heroPopUpButton;
    /**
     * A box to hold the player's ability buttons
     */
    @FXML
    private HBox abilityButtonBox;

    /**
     * A box to hold player's stats.
     */
    @FXML
    private AnchorPane playerStats;

    @FXML
    private AnchorPane heroFrame;

    @FXML
    private ImageView heroImage;

    @FXML
    private Label playerName;

    /**
     * Loot button
     */
    @FXML
    private Button lootButton;

    /**
     * Bottom section wrapper
     */
    @FXML
    private HBox bottomBoxes;

    /**
     * Message Box
     */
    @FXML
    private TextArea messageDisplayBox;

    /**
     * The multiplayer game manager
     */
    private MultiplayerGameManager multiplayerGameManager;

    /**
     * Small, medium, and large popup message Labels for ability information.
     */
    @FXML
    private Label abilityDialogue;
    @FXML
    private Label abilityDialogueOne;
    @FXML
    private Label abilityDialogueTwo;
    int abilityDialogueSizeStop = 0;

    /**
     * Primary Combat buttons (abilities etc.)
     */
    @FXML
    private Button abilityOneButton;
    @FXML
    private Button abilityTwoButton;
    @FXML
    private Button weaponAbilityButton;
    @FXML
    private Button weaponOneButton;
    @FXML
    private Button weaponTwoButton;
    @FXML
    private Button utilityAbilityButton;
    @FXML
    private Button endTurnButton;

    HeroPopUpController heroPopUpController;


    @Override
    public void refresh() {
        // NOT YET SUPPORTED
    }

    @FXML
    public void move() {
        gameManager.setAbilitySelected(AbilitySelected.MOVE);
    }

    /**
     * Process the action on the end turn button
     */
    @FXML
    public void endTurn() {
        gameManager.nextTurn();
    }

    /**
     * These are placeholder methods - they assume that every hero has exactly
     * two abilities. I expect the way this is handled to be changed quite a bit
     * as more abilities are added and the way they are selected are also
     * changed.
     */
    @FXML
    public void abilityOne() {
        if (gameManager.getAbilitySelected() == AbilitySelected.ABILITY1) {
            gameManager.setAbilitySelected(AbilitySelected.MOVE);
        } else {
            gameManager.setAbilitySelected(AbilitySelected.ABILITY1);
        }
    }

    /**
     * Ability 2 button
     */
    @FXML
    public void abilityTwo() {
        if (gameManager.getAbilitySelected() == AbilitySelected.ABILITY2) {
            gameManager.setAbilitySelected(AbilitySelected.MOVE);
        } else {
            gameManager.setAbilitySelected(AbilitySelected.ABILITY2);
        }
    }

    /**
     * Weapon ability button
     */
    @FXML
    public void weaponAbility() {
        if (gameManager.getAbilitySelected() == AbilitySelected.WEAPON) {
            gameManager.setAbilitySelected(AbilitySelected.MOVE);
        } else {
            gameManager.setAbilitySelected(AbilitySelected.WEAPON);
        }
    }

    /**
     * Item 1 button
     */
    @FXML
    public void weaponOne() {
        // Item 1 methods
    }

    /**
     * Item 2 button
     */
    @FXML
    public void weaponTwo() {
        // Item 2 methods
    }

    /**
     * Utility ability button
     */
    @FXML
    public void utilityAbility() {
        if (gameManager.getAbilitySelected() == AbilitySelected.UTILITY) {
            gameManager.setAbilitySelected(AbilitySelected.MOVE);
        } else {
            gameManager.setAbilitySelected(AbilitySelected.UTILITY);
        }
    }

    @FXML
    public void openHeroPopUp() {
        try {
            OverlayMakerPopUp
                    .makeWithGameManager(gameManager.getController().getGamePane(),
                    "/ui/fxml/HeroPopUp.fxml", 
                    gameManager).getController().show();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    @FXML
    public void openShop() {
        try {
            OverlayMakerPopUp
                    .makeWithGameManager(gameManager.getController().getGamePane(),
                    "/ui/fxml/ShopGUIFXML.fxml", 
                    gameManager).getController().show();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Opens/closes then updates the objective pane
     */
    @FXML
    public void toggleObjectives() {
        if (Math.round(objectivePane.getLayoutX()) == -190) {
            objectivePane.setLayoutX(12);
        } else {
            objectivePane.setLayoutX(-190.0);
        }

        displayObjectives();
    }

    /**
     * Opens/closes the chat box
     */
    @FXML
    public void toggleChatBox() {
        if (Math.round(chatPane.getLayoutX()) == -266) {
            chatPane.setLayoutX(70);
            setFocusMessaging();
        } else {
            chatPane.setLayoutX(-266.0);
        }
    }

    /**
     * Processes the send message button
     */
    @FXML
    public void sendMessage() {
        String message = messageSendBox.getText();
        messageSendBox.setText("");

        logger.info("Got in game message: " + String.valueOf(message));

        if (message != null && !"".equals(message)) {
            MessagingManager.parseMessage(message);
            UserInterfaceController.recievedMessage();
        }

    }

    /**
     * The message recieved listener for this class
     */
    private MessageRecievedListener messageRecievedListener = new MessageRecievedListener() {
        @Override
        public void recievedMessage() {
            displayMessage();
        }
    };

    /**
     * Static passthrough for message reiceved listener
     */
    private static MessageRecievedListener passThroughListener;
    {
        passThroughListener = messageRecievedListener;
    }

    /**
     * Called when a message is recieved
     */
    public static void recievedMessage(){
        if(passThroughListener != null) {
            passThroughListener.recievedMessage();
        }
    }

    /**
     * Displays chat in messageDisplayBox
     */
    private void displayMessage() {
        messageDisplayBox.clear();
        if(MessagingManager.isMessagingFailed()){
            ensureMessagingFailClear();
        } else {
            String[] messages = GameMessageQueue.getVisibleMessages();
            for (int i = messages.length - 1; i >= 0; --i) {
                String message = GameMessageQueue.getMessage(messages[i]);
                Platform.runLater(() -> messageDisplayBox.appendText(message + "\r\n"));
            }
        }
    }

    /**
     * Ensures the failure to message is clear
     */
    private void ensureMessagingFailClear() {
        if (messageDisplayBox != null) {
            messageDisplayBox.clear();
            messageDisplayBox.setText("Messaging services not connected!");
            messageDisplayBox.setDisable(true);
            messageSendBox.setDisable(true);
        }
    }

    /**
     * Opens up the loot window
     */
    @FXML
    public void openLoot() {
        try {
            LootPopUpController controller = (LootPopUpController) OverlayMakerPopUp
                    .makeWithGameManager(gameManager.getController().getGamePane(),
                            "/ui/fxml/lootPopUp.fxml", gameManager).getController();
            controller.show();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Sets the loot button visibility
     */
    public void setLootButtonVisibility(boolean value) {
        lootButton.setVisible(value);
    }

    public void setFocusMessaging() {
        Platform.runLater(() -> messageSendBox.requestFocus());
    }

    public void resetFocus() {
        Platform.runLater(() -> gamePane.requestFocus());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Implementation of initialized..
    }

    /**
     * Icon Listenters
     */
    @Override
    public void onHeroChange(AbstractHero hero){
    	playerName.setText(hero.getName());
    	heroImage.setImage(TextureRegister.getTexture(hero.getProfileImage()));
    }

    /**
     * Resize Listeners
     */
    private void setUpResizeListeners() {
        setUpAbilityButtonListeners();
        setUpPlayerStatsListeners();
    }

    private void setUpPlayerStatsListeners() {
        // The container height/width as a percentage of the screen height
        final double vboxWidthPercentage = 1 / 2.5;

        // The bar/avatar height/width as a percentage of the screen height
        final double barWidthPercentage = 1 / 5.0;
        final double avatarWidthPercentage = 1 / 8.0;

        gamePane.heightProperty().addListener((observable, oldValue, newValue) -> {
            playerStats.setPrefWidth((double) newValue * vboxWidthPercentage);
            healthBar.setPrefWidth((double) newValue * barWidthPercentage);
            actionPointBar.setPrefWidth((double) newValue * barWidthPercentage);
            heroFrame.setPrefSize((double) newValue * avatarWidthPercentage, (double) newValue * avatarWidthPercentage);
            heroImage.setFitHeight((double) newValue * avatarWidthPercentage - 14);
            heroImage.setFitWidth((double) newValue * avatarWidthPercentage - 14);
            healthBar.setPrefHeight(heroFrame.getPrefHeight() / 4);
            actionPointBar.setPrefHeight(heroFrame.getPrefHeight() / 4);
            playerName.setPrefWidth(heroImage.getFitWidth());
        });
    }

    private void setUpAbilityButtonListeners() {
        // The button height/width as a percentage of screen height
        final double buttonHeightPercentage = 1 / 8.0;

        // Values obtained from the fxml
        final int numButtons = abilityButtonBox.getChildren().size();
        final double spacing = abilityButtonBox.getSpacing();

        // Set up the x offset to change on change of pane width
        gamePane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)
                -> bottomBoxes.setLayoutX(((double) newSceneWidth - abilityButtonBox.getPrefWidth()) / 2.0));

        // Set up the size of the buttons and the offset to change with pane height
        gamePane.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            abilityButtonBox.setPrefHeight((double) newSceneHeight * buttonHeightPercentage);
            abilityButtonBox.setPrefWidth(abilityButtonBox.getPrefHeight() * numButtons
                    + (numButtons - 1) * spacing);
            bottomBoxes.setLayoutX((gamePane.getWidth() - abilityButtonBox.getPrefWidth()) / 2.0);
            for (Node button : abilityButtonBox.getChildren()) {
                ((Button) button).setPrefHeight((double) newSceneHeight / 8);
                ((Button) button).setPrefWidth((double) newSceneHeight / 8);
            }
            if (Math.round((double) newSceneHeight) < 720) {
                abilityDialogueSizeStop = 0;
            } else if (Math.round((double) newSceneHeight) < 1080) {
                abilityDialogueSizeStop = 1;
            } else {
                abilityDialogueSizeStop = 2;
            }
        });
    }

    /**
     * Gets the health bar FXML node
     *
     * @return the HealthBar in the game interface
     */
    public HealthBar getHealthBar() {
        return healthBar;
    }

    /**
     * Gets the action point bar FXML node
     *
     * @return the ActionPointBar in the game interface
     */
    public ActionPointBar getActionPointBar() {
        return actionPointBar;
    }

    /**
     * Gets the health label FXML node
     *
     * @return the HealthLabel in the game interface
     */
    public HealthLabel getHealthLabel() {
        return healthLabel;
    }

    /**
     * Gets the action point label FXML node
     *
     * @return the ActionPointLabel in the game interface
     */
    public ActionPointLabel getActionPointLabel() {
        return actionPointLabel;
    }

    /**
     * Write to the objectives
     */
    public void writeToLogBox(String message) {
        Platform.runLater(() -> logbox.appendText(message + System.lineSeparator()));
    }

    /**
     * Demo for integrating objectives - click "Objectives..." to display
     * objectives in logbox Thomas Bricknell
     */
    @FXML
    public void displayObjectives() {
        logbox.clear();
        List<Objective> objectiveList = this.gameManager.getObjectives();
        GameState gs = this.gameManager.getGameState();
        Map<Object, Object> statistics = gs.getStatistics();

        // Score
        writeToLogBox("Score: " + this.gameManager.getScoreCounter().getScore());

        // Objectives and their completion status - C/NC for objectives
        // using booleans for their values, else x/y style
        for (Objective o : objectiveList) {
            Object key = o.getObjectiveTarget();
            Object objectiveValue = o.getObjectiveValue();
            Object value = statistics.get(key);
            String completionStatus;
            if (value instanceof Boolean) {
                if (o.met()) {
                    completionStatus = "Completed";
                } else {
                    completionStatus = "Not completed";
                }
            } else if (o instanceof ProtectionObjective) {
                if (!o.met()) {
                    completionStatus = "Failed";
                } else {
                    completionStatus = "Maintained";
                }
            } else {
                Integer vi = (Integer) value;
                Integer targetAmount = (Integer) objectiveValue;
                completionStatus = vi + "/" + targetAmount;
            }
            writeToLogBox(o.getDescription() + ": " + completionStatus);
        }

        if (objectiveList.isEmpty()) {
            writeToLogBox("No objectives present!");
        } else {
            writeToLogBox("Completed: " + this.gameManager.getTracker().getCurrentCompletionPercentage() + "%");
            if (this.gameManager.getTracker().allObjectivesMet()) {
                writeToLogBox("You've met all objectives! Well done!");
            }
        }
    }

    public OverlayMakerUI getHandler() {
        return (OverlayMakerUI) popUp;
    }

    /**
     * Set the gameManager for this controller
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Disable or enable the components of the user interface.
     *
     * Disabling the user interface is needed when the player should be able to
     * see a level but not interact with it, such as during an instruction
     * sequence.
     *
     * @param disable If true disable the UI components; otherwise enable them.
     */
    public void setDisableInteraction(boolean disable) {
        abilityButtonBox.setDisable(disable);
        objectivePane.setDisable(disable);
        chatPane.setDisable(disable);
        shopButton.setDisable(disable);
    }

    public void addActiveEffectIcon(AbstractBuff buff, int column) {

        double iconScale = 0.4;

        Image image = TextureRegister.getTexture(buff.getIconTextureName());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(heroFrame.getHeight() * iconScale);
        imageView.setPreserveRatio(true);

        gamePane.heightProperty().addListener(((observable, oldValue, newValue) -> {
            imageView.setFitHeight(heroFrame.getHeight() * iconScale);
        }));

        imageView.setOnMouseEntered(event -> {
            activeEffectDescription.setText(buff.getDescription() + "\n\nDAMAGE: " + buff.getBuffStrength() + "\nTURNS: " + (buff.getDuration() + 1));
            activeEffectDescription.setMaxWidth(160);
            activeEffectDescription.setWrapText(true);
            activeEffectDescription.setVisible(true);

        });
        imageView.setOnMouseExited(event1 -> {
            if (activeEffectDescription.isVisible()) {
                activeEffectDescription.setVisible(false);
            }
        });

        activeEffectsIconGrid.add(imageView, column, 1);
    }

    public void clearActiveEffectsGrid() {
        activeEffectsIconGrid.getChildren().clear();
    }

    public void clearAbilityDialogue() {
        abilityDialogue.setVisible(false);
    }

    boolean abilityDialogueDisplayed = false;

    /**
     * This will replace the currently displayed AbilityDialogue text with the new message and reset the transition
     * animation.
     *
     * @param message string message to be displayed.
     */
    public void messageAbilityDialogue(String message) {

        switch (abilityDialogueSizeStop) {
            case 0:
                abilityDialogue.setText(message);
                abilityDialogue.setVisible(true);
                abilityDialogueOne.setVisible(false);
                abilityDialogueTwo.setVisible(false);
                displayPopup(abilityDialogue);
                break;
            case 1:
                abilityDialogueOne.setText(message);
                abilityDialogue.setVisible(false);
                abilityDialogueOne.setVisible(true);
                abilityDialogueTwo.setVisible(false);
                displayPopup(abilityDialogueOne);
                break;
            case 2:
                abilityDialogueTwo.setText(message);
                abilityDialogue.setVisible(false);
                abilityDialogueOne.setVisible(false);
                abilityDialogueTwo.setVisible(true);
                displayPopup(abilityDialogueTwo);
                break;
            default:
                abilityDialogueOne.setText(message);
                abilityDialogue.setVisible(false);
                abilityDialogueOne.setVisible(true);
                abilityDialogueTwo.setVisible(false);
                displayPopup(abilityDialogueOne);
        }


    }

    private void displayPopup(Label label) {
        label.setOpacity(1.0);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), label);
        if (!abilityDialogueDisplayed) {
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setDelay(Duration.millis(500));
            fadeOut.play();
            abilityDialogueDisplayed = true;
            fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    abilityDialogueDisplayed = false;
                }
            });
        } else {
            fadeOut.playFromStart();
        }
    }

    public void displayHoverBox(AbstractBuff buff, int column) {

    }

    /**
     * Refreshes the Ability Button hotbar
     */
    public void refreshAbilityHotbar() {
        String NONE = "NONE.";
        GameManager gameManager = GameLoop.getCurrentGameManager();
        if (gameManager == null) {
            return;
        }

        AbstractHero hero = gameManager.getHeroManager().getCurrentHero();
        if (hero == null) {
            return;
        }

        switch (hero.getAbilities().size()) {
            case 0:
                // No Abilities
                abilityOneButton.setText(NONE);
                abilityOneButton.setDisable(true);
                abilityTwoButton.setText(NONE);
                abilityTwoButton.setDisable(true);
                break;
            case 1:
                // Ability One
                abilityOneButton.setText(hero.getAbilities().get(0).getName());
                abilityOneButton.setDisable(false);
                abilityTwoButton.setText(NONE);
                abilityTwoButton.setDisable(true);
                break;
            default:
                // Ability One and Two
                abilityOneButton.setText(hero.getAbilities().get(0).getName());
                abilityOneButton.setDisable(false);
                abilityTwoButton.setText(hero.getAbilities().get(1).getName());
                abilityTwoButton.setDisable(false);
                break;
        }

        if (hero.getWeaponAbility() != null) {
            // Weapon Ability
            weaponAbilityButton.setText(hero.getWeaponAbility().getName());
            weaponAbilityButton.setDisable(false);
        } else {
            weaponAbilityButton.setText(NONE);
            weaponAbilityButton.setDisable(true);
        }

        if (hero.getUtilityAbility() != null) {
            // Weapon Ability
            utilityAbilityButton.setText(hero.getUtilityAbility().getName());
            utilityAbilityButton.setDisable(false);
        } else {
            utilityAbilityButton.setText(NONE);
            utilityAbilityButton.setDisable(true);
        }
    }

    public void setPopupHeroIndex(int index) {
        if (heroPopUpController != null) {
            heroPopUpController.setSelectedHeroIndex(index);
        }
        HeroPopUpUIController.highlightHeroSelected();
    }

    /** 
     * starts the overlay
     */
    @Override
    public void startOverlay(GameManager gameManager, OverlayMakerHandler overlayMaker) {
        super.startOverlay(gameManager, overlayMaker);
        heroPopUpController = new HeroPopUpController(gameManager);
        this.gamePane = (AnchorPane) overlayMaker.getOverlay();

        if (gameManager.isMultiplayer()) {
            logger.info("Adding block state listener to user controller...");
            multiplayerGameManager = gameManager.getMultiplayerGameManager();
            multiplayerGameManager.addBlockStateListener(new BlockStateListener() {
                @Override
                public void onRequireUpdate() {
                    logger.info("User controller got block state listener update w/ new state [{}]", multiplayerGameManager.isBlocking());

                    Platform.runLater(() -> {
                        while (abilityButtonBox.isDisable() != multiplayerGameManager.isBlocking()) {
                            logger.info("Trying to update panel disabled state to [{}]", multiplayerGameManager.isBlocking());
                            abilityButtonBox.setDisable(multiplayerGameManager.isBlocking());
                            for (Node node : abilityButtonBox.getChildren()) {
                                node.setDisable(multiplayerGameManager.isBlocking());
                            }
                        }
                    });
                }
            });
        } else {
            logger.info("User controller detected game was not multiplayer and the block state listener was not installed!");
        }
        gameManager.addCurrentHeroChangeListener(this);
        //Init Profile on Load
        if(gameManager.getHeroManager().getCurrentHero() != null) {
            this.onHeroChange(gameManager.getHeroManager().getCurrentHero());
        }
        setUpResizeListeners();
    }

    // ensure messaging failures are clear
    {
        if(MessagingManager.isMessagingFailed()){
            ensureMessagingFailClear();
        }
    }
}
