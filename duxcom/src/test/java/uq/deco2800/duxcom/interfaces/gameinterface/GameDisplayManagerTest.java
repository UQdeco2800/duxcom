package uq.deco2800.duxcom.interfaces.gameinterface;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.interfaces.RenderOrder;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import static org.junit.Assert.assertEquals;
import static uq.deco2800.duxcom.interfaces.gameinterface.GameDisplays.TEST;

/**
 * Tests the game display manager to ensure rendering works and in the order specified
 *
 * Created by liamdm on 21/08/2016.
 */
public class GameDisplayManagerTest {

    GameDisplayManager gameDisplayManager = new GameDisplayManager(GameDisplays.TEST);

    // Used to pass data between internal subclasses
    private static String testStorage = "";

    // The visionGraphicsHandler graphics handler (should be higher render order)
    protected class GraphicsHandlerTestTwo extends GraphicsHandler {

        @Override
        public void render(GraphicsContext graphicsContext) {
            testStorage = "overwrite";
        }

        @Override
        public void updateGraphics(GraphicsContext graphicsContext) {
            // Required
        }

        @Override
        public boolean needsUpdating() {
            return false;
        }
    }

    // The visionGraphicsHandler graphics handler
    protected class GraphicsHandlerTest extends GraphicsHandler {

        @Override
        public void render(GraphicsContext graphicsContext) {
            if(graphicsContext.getFill() != null && graphicsContext.getFill().equals(Color.ORANGE)){
                testStorage = "orange";
            }

            if(graphicsContext.getFill() != null && graphicsContext.getFill().equals(Color.RED)){
                testStorage = "red";
            }

            testStorage = "";

        }

        @Override
        public void updateGraphics(GraphicsContext graphicsContext) {
            // Required
        }

        @Override
        public boolean needsUpdating() {
            return false;
        }
    }

    // The visionGraphicsHandler application
    public static class TestApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(TestApplication.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();

        do {
            // Can't appear before JavaFX
            try {
                new Canvas().getGraphicsContext2D();
            } catch (Exception ex) {
                continue;
            }

            break;
        } while (true);
    }


    @Ignore
    @Test
    public void testAddHandler(){
        gameDisplayManager.setCurrentInterface(TEST);

        // Add the handler
        gameDisplayManager.addHandler(TEST, RenderOrder.BACKGROUND, new GraphicsHandlerTest());

        // Test with blank canvas
        Canvas c = new Canvas();
        gameDisplayManager.render(c.getGraphicsContext2D());

        assertEquals("", testStorage);

        // Test its access to graphics context
        c.getGraphicsContext2D().setFill(Color.ORANGE);
        gameDisplayManager.render(c.getGraphicsContext2D());
        assertEquals("orange", testStorage);

        c.getGraphicsContext2D().setFill(Color.RED);
        gameDisplayManager.render(c.getGraphicsContext2D());
        assertEquals("red", testStorage);

        // Test it's access to game manager
        GameManager gm = new GameManager();
        gm.setMap(new MapAssembly());

        c.getGraphicsContext2D().setFill(Color.BLACK);
        gameDisplayManager.render(c.getGraphicsContext2D());
        assertEquals("map", testStorage);

        // Add a new handler which should be rendered infront of the existing
        gameDisplayManager.addHandler(TEST, RenderOrder.FRONT_OVERLAY, new GraphicsHandlerTestTwo());
        gameDisplayManager.render(c.getGraphicsContext2D());

        assertEquals("overwrite", testStorage);

    }
}