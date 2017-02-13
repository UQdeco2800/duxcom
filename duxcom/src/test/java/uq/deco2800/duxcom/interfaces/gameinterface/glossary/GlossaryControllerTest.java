package uq.deco2800.duxcom.interfaces.gameinterface.glossary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.GlossaryGraphicsHandler;
import uq.deco2800.duxcom.interfaces.overlaymaker.OverlayMakerHandler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by rhysmckenzie on 22/10/2016
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class GlossaryControllerTest extends ApplicationTest {

    @FXML
    private TabPane tabPane;

    @FXML
    private TabPane testTabs;

    @Mock
    private GameManager gameManager;

    @Mock
    private OverlayMakerHandler overlayMaker;

    @Mock
    private GlossaryController controller;

    @Test
    public void classCheck() {
        assertNotNull (GlossaryController.class.getClass());
        assertNotNull (GlossaryController.class.getMethods());
    }

    @Override
    public void start(Stage stage) throws Exception {
        testTabs = new TabPane();
        tabPane = new TabPane();
        controller.addSectionButtons();
        testTabs.getTabs().addAll(tabPane.getTabs());
        controller.startOverlay(gameManager, overlayMaker);
    }

    @Test
    public void checkTabs(){
        assertNotNull(tabPane.getTabs());
    }


    @Test
    public void checkWorldTabs() {
        controller.addWorldSubTabs();
        assertEquals(testTabs.getTabs(), tabPane.getTabs());
    }

    @Test
    public void checkHeroTabs() {
        controller.addHeroSubTabs();
        assertEquals(testTabs.getTabs(), tabPane.getTabs());
    }

    @Test
    public void checkEnemyTabs() {
        controller.addEnemySubTabs();
        assertEquals(testTabs.getTabs(), tabPane.getTabs());
    }

    @Test
    public void checkBack() {
        controller.addEnemySubTabs();
        controller.back();
        assertEquals(tabPane.getTabs(), testTabs.getTabs());
    }

    @Ignore
    @Test
    public void checkDestroy() {
        controller.destroyOverlay();
        assertEquals(GlossaryGraphicsHandler.getInitialised(), false);
    }

}

