package uq.deco2800.duxcom.maps.mapgen.generator.biomegenerator;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.interfaces.gameinterface.GameDisplayManagerTest;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.util.Array2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test methods for biome generator
 *
 * Created by liamdm on 25/08/2016.
 */
@Ignore
public class BiomeGeneratorTest {

    // The test application
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
                Application.launch(GameDisplayManagerTest.TestApplication.class, new String[0]);
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

    @Test
    public void biomeGeneratorTests(){
        BiomeGenerator biomeGenerator = new BiomeGenerator();

        long a = biomeGenerator.getCurrentSeed();
        assertEquals(a, biomeGenerator.getCurrentSeed());

        long b = biomeGenerator.regenerateSeed();
        assertEquals(b, biomeGenerator.getCurrentSeed());
        assertNotEquals(a, biomeGenerator.getCurrentSeed());

        Array2D<Tile> gen = biomeGenerator.generateBlock(10, 20, BiomeType.TEST_REALISTIC);
        assertEquals(10, gen.getWidth());
        assertEquals(20, gen.getHeight());
    }
}