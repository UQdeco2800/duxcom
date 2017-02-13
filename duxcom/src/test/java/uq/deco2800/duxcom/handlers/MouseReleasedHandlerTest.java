package uq.deco2800.duxcom.handlers;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.stage.Stage;
import uq.deco2800.duxcom.GameManager;

import static javafx.scene.input.MouseEvent.MOUSE_RELEASED;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test MouseReleasedHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MouseReleasedHandlerTest extends ApplicationTest{

    private MouseReleasedHandler mouseReleasedHandler;
    private Stage stage;

    @Mock
    private GameManager gameManagerMock;

    @Test
    public void testEvents() {
        mouseReleasedHandler = new MouseReleasedHandler(gameManagerMock);
        MouseEvent mouseEventRelease = new MouseEvent(MOUSE_RELEASED, 0, 0, 0, 100, MouseButton.PRIMARY,
                100, false, false, false, false, false, false, false, false, false, false,
                new PickResult(stage, 0, 0));
        mouseReleasedHandler.handle(mouseEventRelease);
        verify(gameManagerMock, times(1)).setReleased();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
    }
}