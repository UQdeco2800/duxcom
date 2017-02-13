/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.deco2800.duxcom.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.interfaces.gameinterface.statusbars.ActionPointBar;
import uq.deco2800.duxcom.interfaces.gameinterface.statusbars.HealthBar;
import uq.deco2800.duxcom.interfaces.gameinterface.statuslabels.ActionPointLabel;
import uq.deco2800.duxcom.interfaces.gameinterface.statuslabels.HealthLabel;
import uq.deco2800.duxcom.interfaces.overlaymaker.ui.OverlayMakerUIManager;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.scoring.ScoreSystem;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.assertEquals;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

/**
 *
 * @author TROLL
 */
@RunWith(MockitoJUnitRunner.class)
public class UserInterfaceControllerTest extends ApplicationTest {

    private UserInterfaceController userInterfaceController;
    private AnchorPane parentPane;
    private OverlayMakerUIManager uiManager;
    private UserInterfaceController controller;
    private static TextArea logbox;
    private static HealthBar healthBar;
    private static HealthLabel healthLabel;
    private static ActionPointBar actionPointBar;
    private static ActionPointLabel actionPointLabel;
    private static Button shopButton;
    private static TextField messageSendBox;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private ScoreSystem scoreSystemMock;
    @Mock
    private DuxComController duxComControllerMock;
    @Mock
    private GameState gameStateMock;

    @Override
    public void start(Stage stage) throws Exception {

        URL location = getClass().getResource("/ui/fxml/userInterface.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        controller = fxmlLoader.getController();
        gameManagerMock.setController(duxComControllerMock);
        when(duxComControllerMock.getUIController()).thenReturn(controller);

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Before
    public void setUp() {
            logbox = find("#logbox");
            healthBar = find("#healthBar");
            healthLabel = find("#healthLabel");
            actionPointBar = find("#actionPointBar");
            actionPointLabel = find("#actionPointLabel");
            shopButton = find("#shopButton");
            messageSendBox = find("#messageSendBox");

    }

    @Test
    public void testAllnodesExist() {

        verifyThat("#logbox", isNotNull());
        verifyThat("#healthBar", isNotNull());
        verifyThat("#healthLabel", isNotNull());
        verifyThat("#actionPointBar", isNotNull());
        verifyThat("#actionPointLabel", isNotNull());
        verifyThat("#shopButton", isNotNull());
        verifyThat("#messageSendBox", isNotNull());
    }

    /**
     * Tests the getter methods of fxml nodes
     */
    @Test
    public void testFXMLGetters() {
        assertEquals(healthBar, controller.getHealthBar());
        assertEquals(actionPointBar, controller.getActionPointBar());
        assertEquals(healthLabel, controller.getHealthLabel());
        assertEquals(actionPointLabel, controller.getActionPointLabel());
    }

    /**
     * Tests the shop interface
     */
    @Ignore
    @Test
    public void testShop() {
        controller.openShop();
        verifyThat("#closeButton", isNotNull());
        verifyThat("#rootPane", isNotNull());
        verifyThat("#categoryBox", isNotNull());
        verifyThat("#itemPane", isNotNull());
        Button closeButton = find("#closeButton");
        closeButton.fire();
    }

    /**
     * Tests the ability to write to the logbox
     */
    @Test
    public void testWriteToLogBox() throws InterruptedException {
        controller.writeToLogBox("Test");
        waitForRunLater();
        assertEquals("Test", logbox.getText().trim());
    }

    /**
     * Tests the displaying of objectives in the logbox
     */
    @Test
    public void testDisplayObjectives() throws InterruptedException {
        controller.setGameManager(gameManagerMock);
        List<Objective> objectiveList = new ArrayList<>();
        Map<Object, Object> statistics = new HashMap<>();

        when(gameManagerMock.getObjectives()).thenReturn(objectiveList);
        when(gameManagerMock.getGameState()).thenReturn(gameStateMock);
        when(gameStateMock.getStatistics()).thenReturn(statistics);
        when(gameManagerMock.getScoreCounter()).thenReturn(scoreSystemMock);
        when(scoreSystemMock.getScore()).thenReturn(0);
        controller.displayObjectives();
        waitForRunLater();
        assertEquals("Score: 0" + System.lineSeparator() + "No objectives present!"
                + System.lineSeparator(), logbox.getText().replace("\n", System.lineSeparator()));
    }

    /**
     * Tests that the game buttons call the correct methods
     */
    @Test
    public void testButtonMethods() {
        controller.setGameManager(gameManagerMock);
        controller.move();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.MOVE);

        controller.endTurn();
        verify(gameManagerMock, times(1)).nextTurn();

        controller.abilityOne();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.ABILITY1);
        controller.abilityOne();
        controller.abilityOne();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.MOVE);

        controller.abilityTwo();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.ABILITY2);
        controller.abilityTwo();
        controller.abilityTwo();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.MOVE);

        controller.weaponAbility();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.WEAPON);
        controller.weaponOne();
        controller.weaponOne();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.MOVE);

        controller.utilityAbility();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.UTILITY);
        controller.utilityAbility();
        controller.utilityAbility();
        verify(gameManagerMock, times(1)).setAbilitySelected(AbilitySelected.MOVE);

    }

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be
     * updates
     *
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

}
