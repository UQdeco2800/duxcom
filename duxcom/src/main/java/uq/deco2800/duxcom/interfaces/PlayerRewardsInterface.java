package uq.deco2800.duxcom.interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uq.deco2800.duxcom.controllers.PlayerRewardsController;

import java.io.IOException;
import java.net.URL;

/**
 * PlayerRewardsInterface implements an InterfaceSegment and is used to display the game's plauyer reward
 * screen. The class is listed in the Enumeration InterfaceSegmentType as
 * PLAYER_REWARD.
 *
 * @author Daniel Gormly
 * @see InterfaceSegment
 * @see InterfaceSegmentType
 */
public class PlayerRewardsInterface implements InterfaceSegment {

    /**
     * Loads the interface as a scene such that it can be displayed to the user as required.
     *
     * IMPORTANT: Should only be called once for any instance of the class.
     *
     * @param primaryStage     The Stage in which the InterfaceSegment should be displayed.
     * @param arguments        Optional arguments that can be provided as launch options to the
     *                         interface.
     * @param interfaceManager The InterfaceManager controlling the game's interfaces.
     * @return Returns true if interface is successfully loaded.
     * @throws IOException Thrown if the fxml file cannot be found.
     */
    @Override
    public boolean loadInterface(Stage primaryStage, String arguments, InterfaceManager interfaceManager) throws IOException {
        URL location = getClass().getResource("/ui/fxml/playerRewardsScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        PlayerRewardsController playerRewardsController = fxmlLoader.getController();
        playerRewardsController.setInterfaceManager(interfaceManager);
        playerRewardsController.setStage(primaryStage);

        InterfaceSegment.showStage(root, primaryStage, interfaceManager);

        return true;
    }

    /**
     * Destroys the current instance of the interface.
     */
    @Override
    public void destroyInterface() {
        // would perform cleanup here if there was any
    }
}
