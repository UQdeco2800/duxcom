package uq.deco2800.duxcom.coop;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Shows a nice error
 *
 * Created by liamdm on 19/10/2016.
 */
public class NiceError {
    /**
     * Shows a popup box with the given nice error
     * @param title the title of the window
     * @param message the message of the window
     * @param submessage the submessge
     * @param shouldBlock if block till acknowledged
     */
    public static void niceError(String title, String message, String submessage, boolean shouldBlock) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(message);
            alert.setContentText(submessage);
            if (shouldBlock) {
                alert.showAndWait();
            } else {
                alert.show();
            }
        });
    }
}
