package uq.deco2800.duxcom.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uq.deco2800.duxcom.controllers.heroselectcontrollers.AddHeroController;
import uq.deco2800.duxcom.controllers.heroselectcontrollers.HeroSelectController;

/**
 * Created by Sean Dwyer on 30/08/2016.
 */
public class HeroSelectInterface implements InterfaceSegment {
    //the root of the scene
    private Pane root;

    //the Add Hero pane
    Pane addHeroPane;

    //the logger
    private static Logger logger = LoggerFactory.getLogger(InterfaceManager.class);

    //controller variables
    private HeroSelectController heroSelectController;
    private AddHeroController addHeroController;

    //interface Manager
    private InterfaceManager interfaceManager;

    @Override
    public boolean loadInterface(Stage primaryStage, String arguments, InterfaceManager interfaceManager) throws IOException {

        this.interfaceManager = interfaceManager;

        URL heroSelect = getClass().getResource("/ui/fxml/heroSelect/heroSelect.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(heroSelect);

        root = (Pane) fxmlLoader.load(heroSelect.openStream());

        heroSelectController = fxmlLoader.getController();
        heroSelectController.setInterfaceManager(interfaceManager);
        heroSelectController.setStage(primaryStage);
        heroSelectController.initializeHeroSelect();

        InterfaceSegment.showStage(root, primaryStage, interfaceManager);

        return true;
    }


    @Override
    public void destroyInterface() {
        // cleanup
    }

    /**
     * Loads the Add Hero screen or the Edit hero screen
     */
    public void loadScreen(String screenType) {
        String url;
        //decide which FXML object to open
        switch (screenType) {
            case "addHero":
                url = "/ui/fxml/heroSelect/addHero.fxml";
                break;
            case "editHero":
                url = "/ui/fxml/heroSelect/editHero.fxml";
                break;
            default:
                url = "error - filename not found";
        }
        //the loader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));

        try {
            addHeroPane = loader.load();
            addHeroController = loader.getController();
            //set it to the middle of the parent element
            addHeroPane.translateXProperty()
                    .bind(root.widthProperty().subtract(addHeroPane.widthProperty())
                            .divide(2));
            addHeroPane.translateYProperty()
                    .bind(root.heightProperty().subtract(addHeroPane.heightProperty())
                            .divide(5));

            root.getChildren().add(addHeroPane);

            //set the heroSelectcontroller variable
            addHeroController.setHeroSelectController(heroSelectController);
            //set the interface manager
            addHeroController.setHeroSelectInterface(interfaceManager);
        } catch (IOException e) {
            logger.error("Could not initialize add hero pane", e);
        }
    }

    public void removeScreen(String screenType) {
        if ("addHero".equals(screenType)) {
            root.getChildren().remove(addHeroPane);
        }
        else if("editHero".equals(screenType)) {
            //TODO: remove edit hero screen
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}