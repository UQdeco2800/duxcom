package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.messaging.GameMessageQueue;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Created by LeX on 12/10/2016.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MessageGraphicsHandlerTest extends ApplicationTest {

    MessageGraphicsHandler messageGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;

    @Test
    public void testUpdateGraphics() {
        GameMessageQueue.setNewMessage(true);
        GameMessageQueue.add("helloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                "I'm too loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong");
        messageGraphicsHandler.updateGraphics(graphicsContext);
        assertFalse(GameMessageQueue.getNewMessage());
    }

    @Test
    public void testNeedsUpdating() {
        GameMessageQueue.setNewMessage(false);
        assertFalse(messageGraphicsHandler.needsUpdating());

        GameMessageQueue.setNewMessage(true);
        assertTrue(messageGraphicsHandler.needsUpdating());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(canvas);
        Scene scene = new Scene(anchorPane, 1280, 720);
        stage.setScene(scene);
        stage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        new GameLoop(10, new AtomicBoolean(false), gameManagerMock);
        messageGraphicsHandler = new MessageGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}