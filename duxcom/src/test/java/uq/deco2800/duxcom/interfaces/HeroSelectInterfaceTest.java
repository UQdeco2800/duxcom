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
 * Tests HeroSelectInterface
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class HeroSelectInterfaceTest extends ApplicationTest {

    private HeroSelectInterface heroSelectInterface;
    private boolean created = false;

    @Mock
    InterfaceManager interfaceManager;

    @Override
    public void start(Stage stage) throws Exception {
        heroSelectInterface = new HeroSelectInterface();
        created = heroSelectInterface.loadInterface(stage, "args", interfaceManager);
    }

    @Test
    public void testCreated() throws IOException {
        assertTrue(created);
    }

    @Test
    public void testDestroy() {
        heroSelectInterface.destroyInterface();
        assertNotNull(heroSelectInterface);
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