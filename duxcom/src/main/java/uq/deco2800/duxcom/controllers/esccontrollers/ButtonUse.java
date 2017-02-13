package uq.deco2800.duxcom.controllers.esccontrollers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;

/**
 * Enumeration to create eventhandlers for EscMenu buttons
 * @author The_Magic_Karps
 */
public enum ButtonUse {

    CLOSE_OVERLAY {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.returnToGame();
                e.consume();
            };
        }
    },
    SAVE_GAME {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.saveGame();
                e.consume();
            };
        }
    },
    LOAD_GAME {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.loadGame();
                e.consume();
            };
        }
    },
    RETURN_LOAD {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.returnToLoad();
                e.consume();
            };
        }
    },
    RETURN_LOG {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.logOut();
                e.consume();
            };
        }
    },
    RETURN_OVERWORLD {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.returnToOverworld();
                e.consume();
            };
        }
    },
    CLOSE_GAME {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                controller.closeGame();
                e.consume();
            };
        }
    },
    SHORTCUT_HELP {
        @Override
        public EventHandler<ActionEvent> getEventHandler(EscMenuController controller) {
            return (ActionEvent e) -> {
                try {
                    controller.hide();
                    controller.deleteIcon();
                    OverlayMakerPopUp popUp = OverlayMakerPopUp
                            .makeWithoutGameManager(controller.gameManager
                                    .getController().getGamePane()
                                    , "/ui/fxml/guidePopUp.fxml");
                    if (!popUp.isActive()) {
                        popUp.showOverlay();
                    }
                    
                } catch (IOException event) {
                    logger.error(event.getMessage(), event);
                }
                e.consume();
            };
        }
    };

    // Logger
    private static Logger logger = LoggerFactory.getLogger(ButtonUse.class);

    public abstract EventHandler<ActionEvent> getEventHandler(EscMenuController controller);
}
