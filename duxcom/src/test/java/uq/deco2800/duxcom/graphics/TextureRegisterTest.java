package uq.deco2800.duxcom.graphics;

import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import uq.deco2800.duxcom.exceptions.InvalidRegisterAccessException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the TextureRegister
 * @author Alex McLean
 */
@Ignore
public class TextureRegisterTest extends ApplicationTest{

    /**
     * Tests that the texture register can be accessed without exception
     */
    @Test
    public void testCache() throws Exception {
        TextureRegister.getTexture("beta_ibis");
        TextureRegister.getTexture("beta_ibis");
    }

    /**
     * Tests the handling of an invalid register access
     */
    @Test
    public void testInvalidRegisterAccess() {
        boolean correctExceptionThrown = false;

        try {
            TextureRegister.getTexture("qut_ibis");
        } catch (InvalidRegisterAccessException exception) {
            correctExceptionThrown = true;
        }

        assertTrue(correctExceptionThrown);
    }

    /**
     * Tests the creation of the texture register
     */
    @Test
    public void testArbitraryCreation() {
        TextureRegister textureRegister = new TextureRegister();
        assertNotNull(textureRegister);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // initializes JavaFX
    }
}