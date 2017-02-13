package uq.deco2800.duxcom.interfaces;

import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Tests LoadScreenInterface
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class LoadScreenInterfaceTest extends ApplicationTest{

    private LoadScreenInterface loadScreenInterface;
    private boolean created = false;

    /**
     * Mocks the InterfaceManager to be used
     */
    @Mock
    InterfaceManager interfaceManager;

    /**
     * Sets up the TestFX environment to show a new UserRegistrationInterface
     */
    @Override
    public void start(Stage stage) throws Exception {
        loadScreenInterface = new LoadScreenInterface();
        created = loadScreenInterface.loadInterface(stage, "args", interfaceManager);
    }

    /**
     * Tests that creation of the interface was successful
     */
    @Test
    public void testCreated() throws IOException {
        assertTrue(created);
    }

    /**
     * Tests the destruction of the interface
     */
    @Test
    public void testDestroy() {
        loadScreenInterface.destroyInterface();
        assertNotNull(loadScreenInterface);
    }

    /**
     * Closes JavaFx application
     * @throws TimeoutException If closing stage times out
     */
    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
    }
}