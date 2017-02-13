package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Created by rhysmckenzie on 22/10/2016.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GlossaryGraphicsHandlerTest extends ApplicationTest {
    private boolean visible = false;

    @Override
    public void start(Stage stage) throws Exception {

    }

    public boolean needsUpdating() {
        return false;
    }

    public boolean getVisible() {
        return this.visible;
    }

    @Test
    public void checkInitialisedTrue() {
        GlossaryGraphicsHandler.setInitialised(true);
        assertTrue(GlossaryGraphicsHandler.getInitialised());
    }

    @Test
    public void checkInitialisedFalse(){
        GlossaryGraphicsHandler.setInitialised(false);
        assertFalse(GlossaryGraphicsHandler.getInitialised());
    }

    @Test
    public void checkUpdating(){
        assertFalse(needsUpdating());
    }

    @Test
    public void checkVisibleFalse() {
        assertFalse(getVisible());
    }

    @Test
    public void checkVisibleTrue() {
        visible = true;
        assertTrue(getVisible());
    }

}
