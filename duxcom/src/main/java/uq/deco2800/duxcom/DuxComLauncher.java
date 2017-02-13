package uq.deco2800.duxcom;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uq.deco2800.duxcom.async.weathergetter.WeatherGetter;
import uq.deco2800.duxcom.coop.SingularityTarget;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockPointMapper;
import uq.deco2800.duxcom.messaging.MessagingManager;
import uq.deco2800.duxcom.sound.SoundPlayer;


/**
 * Launcher class for duXCOM
 * <p>
 * Created by woody on 26-Jul-16.
 */

public class DuxComLauncher extends Application {

    // Creates a new InterfaceManager
    InterfaceManager interfaceManager = new InterfaceManager();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialises the JavaFX application
     *
     * @param primaryStage the stage created by JavaFX
     * @throws Exception if application can not be initialised
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // set the target 4 singularity
        SingularityTarget.setTargetDebug(false);

        initialiseStaticMethods();

        // Set the application icon
        primaryStage.getIcons().add(new Image("/ui/duxcom-icon.png"));

        // Get the interface manager and set it to the load screen
        // If you want to define your own interfaces do it through
        // the initialiser in InterfaceManager
        interfaceManager.setCurrentSegment(InterfaceSegmentType.LOGIN_SCREEN, null);
        interfaceManager.renderInterface(primaryStage);
    }

    /**
     * Static initialisers
     */
    private void initialiseStaticMethods() {
        // Start the messaging manager
        try {
            MessagingManager.init();
        } catch(Exception ex){
            // unable to start messaging
            MessagingManager.failMessaging();
        }
        // Get the current weather
        WeatherGetter.init();

        // Get the point to block mapper setup
        BlockPointMapper.init();

        SoundPlayer.initPlayer();
        SoundPlayer.playMenuMusic();
    }

}
