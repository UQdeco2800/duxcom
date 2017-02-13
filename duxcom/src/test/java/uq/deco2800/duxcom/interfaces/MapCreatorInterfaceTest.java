package uq.deco2800.duxcom.interfaces;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javafx.stage.Stage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests MapCreatorInterface
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MapCreatorInterfaceTest extends ApplicationTest {

    private MapCreatorInterface mapCreatorInterface;
    private boolean created = false;

    @Mock
    InterfaceManager interfaceManager;

    @Override
    public void start(Stage stage) throws Exception {
        mapCreatorInterface = new MapCreatorInterface();
        created = mapCreatorInterface.loadInterface(stage, "args", interfaceManager);
    }

    @Test
    public void testCreated() throws IOException {
        assertTrue(created);
    }

    @Test
    public void testDestroy() {
        mapCreatorInterface.destroyInterface();
        assertNotNull(mapCreatorInterface);
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