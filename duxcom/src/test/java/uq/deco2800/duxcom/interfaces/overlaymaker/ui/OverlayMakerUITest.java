package uq.deco2800.duxcom.interfaces.overlaymaker.ui;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;

/**
 * OverlayMakerUIManagerTest
 *
 * @author The_Magic_Karps
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class OverlayMakerUITest extends ApplicationTest {

    private AnchorPane gamePane;

    private OverlayMakerUI ui;

    @Mock
    private GameManager gameManagerMock;

    @Before
    public void setUp() throws IOException {
        ui = new OverlayMakerUI("/ui/fxml/guidePopUp.fxml",
                gamePane, gameManagerMock, 0, 0, 0);
    }

    /**
     * Test of initDefault method, of class OverlayMakerUI.
     */
    @Test
    public void testInitDefault() {
        assertTrue(ui.getOverlay().getLayoutX() == 0);
        assertTrue(ui.getOverlay().getLayoutY() == 0);
        assertTrue(ui.getOverlay().translateZProperty().get() == 0);
    }

    /**
     * Test of setZLayout method, of class OverlayMakerUI.
     */
    @Test
    public void testSetZLayout_int() {
        ui.setZLayout(5);
        assertTrue(ui.getOverlay().getTranslateZ() == 5.0);
        
    }

    /**
     * Test of setZLayout method, of class OverlayMakerUI.
     */
    @Test
    public void testSetZLayout_UIOrder() {
        ui.setZLayout(UIOrder.FRONT);
        assertEquals(UIOrder.FRONT.getOrder(),
                (int) ui.getOverlay().getTranslateZ());
    }

    @Override
    public void start(Stage stage) throws Exception {
        gamePane = new AnchorPane();
        stage.setScene(new Scene(gamePane, 800, 500));
        stage.show();
    }
    
}
