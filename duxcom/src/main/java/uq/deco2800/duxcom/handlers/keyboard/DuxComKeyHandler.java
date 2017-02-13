package uq.deco2800.duxcom.handlers.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.controllers.esccontrollers.ESCOverlay;
import uq.deco2800.duxcom.controllers.esccontrollers.EscMenuController;
import uq.deco2800.duxcom.controllers.esccontrollers.EscSegmentType;
import uq.deco2800.duxcom.graphics.AnimationManager;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

import java.io.IOException;

/**
 * Ticket 52 - Keyboard Shortcuts Keyboard Handler for in game (duxcom)
 *
 * @author The_Magic_Karps
 */
public class DuxComKeyHandler extends KeyEventHandler {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DuxComKeyHandler.class);

    /**
     * For KeyEvents dealing with gameManager (i.e. heroes, inventory, message)
     *
     * @param interfaceManager interfaceManager of the current interface
     * @param gameManager GameManager with data about the game
     * @param controller Controller for in game
     */
    public DuxComKeyHandler(InterfaceManager interfaceManager, GameManager gameManager, DuxComController controller) {
        super(interfaceManager, gameManager, controller);
    }

    /**
     * Handles the key event
     *
     * @param event KeyEvent to be handled
     */
    @Override
    public void handleEvent(KeyEvent event) {
        if (superPopUp == null || !superPopUp.isActive()) {
            handleEvents(event);
            multiKeysEvent(event);
        } else if (event.getCode() == KeyCode.ESCAPE
                && makeOverlay("/ui/fxml/EscMenu.fxml", true)) {
            ((EscMenuController) superPopUp.getController()).setSegment(EscSegmentType.MAIN_GAME);
            ((ESCOverlay) overlayController).addInterface(interfaceManager);
        }
    }

    /**
     * Events to be handled
     *
     * @param event KeyEvent to be handled
     */
    private void handleEvents(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE:
                if (makeOverlay("/ui/fxml/EscMenu.fxml", true)) {
                    AnimationManager.unpause();
                    ((EscMenuController) superPopUp.getController()).setSegment(EscSegmentType.MAIN_GAME);
                    ((ESCOverlay) overlayController).addInterface(interfaceManager);
                } else {
                    AnimationManager.pause();
                }
                break;
            case M:
                gameManager.toggleMiniMap();
                break;
            case C:
                gameManager.toggleChat();
                break;
            case H:
                controller.getUIController().openHeroPopUp();
                break;
            case S:
                controller.getUIController().openShop();
                break;
            case O:
                controller.getUIController().toggleObjectives();
                break;
            case D:
                gameManager.setDisplayDebug(!gameManager.isDisplayDebug());
                break;
            case EQUALS:
            case PLUS:
                gameManager.zoomIn();
                break;
            case MINUS:
                gameManager.zoomOut();
                break;
            case LEFT:
                gameManager.moveViewRelative(-30, 0);
                controller.getUIController().resetFocus();
                break;
            case RIGHT:
                gameManager.moveViewRelative(30, 0);
                controller.getUIController().resetFocus();
                break;
            case UP:
                gameManager.moveViewRelative(0, -20);
                controller.getUIController().resetFocus();
                break;
            case DOWN:
                gameManager.moveViewRelative(0, 20);
                controller.getUIController().resetFocus();
                break;
            case DIGIT1:
                gameManager.setAbilitySelected(AbilitySelected.MOVE);
                break;
            case DIGIT2:
                toggleAbility(AbilitySelected.ABILITY1);
                break;
            case DIGIT3:
                toggleAbility(AbilitySelected.ABILITY2);
                break;
            case DIGIT4:
                toggleAbility(AbilitySelected.WEAPON);
                break;
            case DIGIT5:
                // Item 1 requested, but currently don't exist
                break;
            case DIGIT6:
                // Item 2 requested, but currently don't exist
                break;
            case DIGIT7:
                toggleAbility(AbilitySelected.UTILITY);
                break;
            case TAB:
                gameManager.toggleMiniHealthBars();
                controller.getUIController().resetFocus();
                break;
            case SPACE:
                gameManager.nextTurn();
                break;
            case F12:
                makeOverlay("/ui/fxml/guidePopUp.fxml", false);
                break;
            case ENTER:
                gameManager.useSelectedAbility();
                break;
            case T:
                controller.getUIController().toggleChatBox();
                break;
            case F:
                openLootWindow();
                break;
            default:
                break;
        }
    }

    /**
     * Opens up the loot window
     */
    private void openLootWindow() {
        try {
            gameManager.getMap().openLootWindow();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Toggles abilities
     *
     * @param ability ability to be toggled
     */
    private void toggleAbility(AbilitySelected ability) {
        if (gameManager.getAbilitySelected() == ability) {
            gameManager.setAbilitySelected(AbilitySelected.MOVE);
        } else {
            gameManager.setAbilitySelected(ability);
        }
    }

    /**
     * Keys that requires multiple key presses
     *
     * @param event KeyEvent to be handled
     */
    private void multiKeysEvent(KeyEvent event) {
        if (event.isAltDown() && event.getCode() == KeyCode.F1) {
            gameManager.getController().getUIController().show();
        } else if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
            toggleFullScreen();
        }
    }
}
