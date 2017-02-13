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
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test MouseMovedHandler
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MouseMovedHandlerTest extends ApplicationTest {

    private MouseMovedHandler mouseMovedHandler;
    private Stage stage;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;

    @Test
    public void testEvents() {
        mouseMovedHandler = new MouseMovedHandler(gameManagerMock);
        when(gameManagerMock.getxOffset()).thenReturn(10.0);
        when(gameManagerMock.getyOffset()).thenReturn(10.0);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        MouseEvent mouseEventRelease = new MouseEvent(MOUSE_MOVED, 0, 0, 0, 100, MouseButton.PRIMARY,
                100, false, false, false, false, false, false, false, false, false, false,
                new PickResult(stage, 0, 0));
        mouseMovedHandler.handle(mouseEventRelease);
        verify(gameManagerMock, times(1)).setMouseLocation(anyInt(), anyInt());
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