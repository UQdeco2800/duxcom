package uq.deco2800.duxcom.interfaces;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uq.deco2800.duxcom.annotation.ConsiderRevising;
import uq.deco2800.duxcom.annotation.MethodContract;

import static uq.deco2800.duxcom.interfaces.InterfaceSegment.Dimensions.MIN_HEIGHT;
import static uq.deco2800.duxcom.interfaces.InterfaceSegment.Dimensions.MIN_WIDTH;

/**
 * Inheriting this allows you to create a new game interface segment which can
 * then be displayed by the interface manager.
 *
 * Created by liamdm on 16/08/2016.
 */
public interface InterfaceSegment {

    final class Dimensions {
        public static final int MIN_WIDTH = 1280;
        public static final int MIN_HEIGHT = 720;
    }

    /**
     * Load an interface
     * @param primaryStage the stage to load on
     * @param arguments the arguments to pass
     * @param interfaceManager the interface manager to use
     * @return true on succes
     * @throws IOException on failure to load
     */
    boolean loadInterface(Stage primaryStage, String arguments, InterfaceManager interfaceManager) throws IOException;

    /**
     * Destroy the interface and cleanup
     */
    void destroyInterface();

    /**
     * Show the primary stage
     * @param root the root parent to show on
     * @param primaryStage the primary stage to render on
     * @param interfaceManager the interface manager to use
     */
    @MethodContract(precondition = {"root != null", "primaryStage != null", "interfaceManager != null"})
    @ConsiderRevising(
            description = "I don't think this belongs in the interface.",
            suggestion = "perhaps move this elsewhere",
            date = "09/10/16",
            suggestor = "liamdm"
    )
    static void showStage(Parent root, Stage primaryStage, InterfaceManager interfaceManager) {
        Scene scene = new Scene(root, MIN_WIDTH, MIN_HEIGHT);
        primaryStage.setTitle("\u0028\u1557 \u0360\u00b0  \u0a0a \u0360\u00b0 \u0029\u1557  " +
                        "DUXCOM - GOTY Edition" + "  \u1559\u0028 \u0360\u00b0 \u0a0a  \u0360\u00b0 \u1559\u0029");
        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setOnCloseRequest(e -> interfaceManager.killInterfaces());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
