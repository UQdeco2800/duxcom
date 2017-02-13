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
import uq.deco2800.duxcom.time.DayNightClock;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests DarknessGraphicsHandlerTest
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class DarknessGraphicsHandlerTest extends ApplicationTest {

    private DarknessGraphicsHandler darknessGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private DayNightClock dayNightClockMock;

    @Test
    public void testUpdateGraphics() {
        when(gameManagerMock.getDayNightClock()).thenReturn(dayNightClockMock);
        when(dayNightClockMock.getTime()).thenReturn(20.0);

        when(gameManagerMock.isBackgroundChanged()).thenReturn(true);
        darknessGraphicsHandler.updateGraphics(graphicsContext);
        verify(gameManagerMock, times(1)).setBackgroundChanged(false);
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isBackgroundChanged()).thenReturn(false);
        when(gameManagerMock.isGameChanged()).thenReturn(false);
        assertFalse(darknessGraphicsHandler.needsUpdating());
        assertFalse(darknessGraphicsHandler.needsRendering());

        when(gameManagerMock.isBackgroundChanged()).thenReturn(true);
        when(gameManagerMock.isGameChanged()).thenReturn(true);
        assertTrue(darknessGraphicsHandler.needsUpdating());
        assertTrue(darknessGraphicsHandler.needsRendering());
    }

    @Test
    public void testGetRatio() {
        assertTrue(Math.abs(darknessGraphicsHandler.getRatio(0) - 0) < 0.001d);
        assertTrue(Math.abs(darknessGraphicsHandler.getRatio(4) - 0.2) < 0.001d);
        assertTrue(Math.abs(darknessGraphicsHandler.getRatio(24) - 0) < 0.001d);
        assertTrue(Math.abs(darknessGraphicsHandler.getRatio(20) - (23.99 - 20) / 20) < 0.001d);
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
        darknessGraphicsHandler = new DarknessGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}