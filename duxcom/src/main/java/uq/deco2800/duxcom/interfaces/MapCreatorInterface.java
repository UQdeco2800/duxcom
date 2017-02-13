package uq.deco2800.duxcom.interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.controllers.MapCreatorController;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.io.IOException;
import java.net.URL;

/**
 * Instantiated by Interface Manager
 * Loads the FXML and connects it to MapCreatorController
 *
 * @author Lucas Reher
 * @author Sam Thomas
 * @author Winston Fang
 */
public class MapCreatorInterface implements InterfaceSegment {

    /**
     * Loads the FXML into MapCreatorController.
     * Sets Interface to primary stage
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
        URL location = getClass().getResource("/ui/fxml/mapCreatorScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        AbstractGameMap map = new DemoMap("");
        GameManager gameManager = new GameManager();
        gameManager.setMap(new MapAssembly(map));
        gameManager.fullRenderRefresh();

        Parent root = fxmlLoader.load(location.openStream());
        MapCreatorController mapCreatorController = fxmlLoader.getController();
        mapCreatorController.setInterfaceManager(interfaceManager);
        mapCreatorController.setStage(primaryStage);

        mapCreatorController.startRenderingMap(gameManager);

        InterfaceSegment.showStage(root, primaryStage, interfaceManager);
        primaryStage.setTitle("Map Creator");
        primaryStage.setOnCloseRequest(e -> mapCreatorController.stopRendering());

        return true;
    }

    /**
     * Remove current interface segment
     */
    @Override
    public void destroyInterface() {
        //Cleanup will go here
    }
}
