package uq.deco2800.duxcom.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.interfaces.InterfaceManager;

import java.net.URL;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test the lobby controller
 *
 * Created by liamdm on 21/10/2016.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class LobbyControllerTest extends ApplicationTest {

    /**
     * Mock the InterfaceManager class with Mockito
     */
    @Mock
    InterfaceManager interfaceManager;

    @Test
    public void lobbyControllerTest() {
        Button[] buttons =
                {
                    find("#acOpenServer"),
                    find("#acDitchGame"),
                    find("#acStartGame")
                };
        assertFalse(buttons[0].isDisable());
        assertTrue(buttons[1].isDisable());
        assertTrue(buttons[2].isDisable());

        buttons[0].fire();

        assertFalse(buttons[0].isDisable());
        assertTrue(buttons[1].isDisable());
        assertTrue(buttons[2].isDisable());

        for(Button b : buttons){
            b.fire();
        }

        AnchorPane anchorPane = find("#outer-anchor-pane");
        for(Node n : anchorPane.getChildren()){
            assertTrue(n != null);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        URL location = getClass().getResource("/ui/fxml/lobbyScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        LobbyController lobbyController = fxmlLoader.getController();
        lobbyController.setInterfaceManager(interfaceManager);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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