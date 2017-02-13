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

import static javafx.scene.input.MouseEvent.MOUSE_DRAGGED;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test MouseDraggedHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MouseDraggedHandlerTest extends ApplicationTest {

    private MouseDraggedHandler mouseDraggedHandler;
    private Stage stage;

    @Mock
    private GameManager gameManagerMock;

    @Test
    public void testEvents() {
        mouseDraggedHandler = new MouseDraggedHandler(gameManagerMock);
        MouseEvent mouseEventRelease = new MouseEvent(MOUSE_DRAGGED, 0, 0, 0, 100, MouseButton.SECONDARY,
                100, false, false, false, false, false, false, false, false, false, false,
                new PickResult(stage, 0, 0));
        mouseDraggedHandler.handle(mouseEventRelease);
        verify(gameManagerMock, times(1)).setDragged(anyInt(), anyInt());
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