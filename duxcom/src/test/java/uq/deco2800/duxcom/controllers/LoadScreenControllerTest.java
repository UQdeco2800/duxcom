package uq.deco2800.duxcom.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;

import java.net.URL;
import java.util.concurrent.TimeoutException;

import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests the LoadScreenController class by pushing all its buttons.
 * Mockito mocks all interfaceManager calls.
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class LoadScreenControllerTest extends ApplicationTest {

    /**
     * Mock the InterfaceManager class with Mockito
     */
    @Mock
    InterfaceManager interfaceManager;

    /**
     * Initialize the testfx environment
     */
    @Override
    public void start(Stage stage) throws Exception {
        URL location = getClass().getResource("/ui/fxml/loadScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        LoadScreenController loadScreenController = fxmlLoader.getController();
        loadScreenController.setInterfaceManager(interfaceManager);
        loadScreenController.setStage(stage);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Iteratively test each button on the load screen
     */
    @Test
    public void testAllButtons() {

        // Find all the buttons to be tested
        Button[] buttons = {
                find("#falador-button"),
                find("#map-creator-button"),
                find("#hero-select-button"),
                find("#overworld-button")
        };

        InterfaceSegmentType[] interfaceSegmentTypes = {
                InterfaceSegmentType.GAME,
                InterfaceSegmentType.MAP_CREATOR,
                InterfaceSegmentType.HERO_SELECT,
                InterfaceSegmentType.OVERWORLD
        };

        String[] args = {
                "falador",
                "creator",
                "heroes",
                "overworld"
        };

        // Press all the buttons and verify the right method is called
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].fire();
            verify(interfaceManager).loadSegmentImmediate(eq(interfaceSegmentTypes[i]), any(Stage.class), eq(args[i]));
        }
    }

    /**
     * Closes JavaFx application
     *
     * @throws TimeoutException If closing stage times out
     */
    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
    }
}
