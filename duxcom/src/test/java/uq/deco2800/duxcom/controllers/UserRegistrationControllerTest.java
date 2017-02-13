package uq.deco2800.duxcom.controllers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import java.net.URL;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.setupStage;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;

/**
 * Tests the LoginScreenController class by pushing all its buttons.
 * Mockito mocks all interfaceManager calls.
 * @author Alex McLean
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationControllerTest extends ApplicationTest {

    // Declares the JavFX nodes to be tested
    private UserRegistrationController userRegistrationController;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField middleNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private PasswordField passwordCheckField;
    private Button registerButton;
    private Button backButton;
    private Label errorLabel;

    /**
     * Mock the InterfaceManager class and the SingularityRestClient class with Mockito
     */
    @Mock
    InterfaceManager interfaceManager;
    @Mock
    SingularityRestClient restClient;

    /**
     * Initialize the testfx environment
     */
    @Override
    public void start(Stage stage) throws Exception {
        URL location = getClass().getResource("/ui/fxml/userRegistrationScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        userRegistrationController = fxmlLoader.getController();
        userRegistrationController.setInterfaceManager(interfaceManager);
        userRegistrationController.setStage(stage);
        userRegistrationController.setSingularityRestClient(restClient);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Maps the JavaFX nodes to the declared variables before each test
     */
    @Before
    public void mapNodes() {

        usernameField = find("#username-field");
        firstNameField = find("#first-name-field");
        middleNameField = find("#middle-name-field");
        lastNameField = find("#last-name-field");
        passwordField = find("#password-field");
        passwordCheckField = find("#password-check-field");
        registerButton = find("#register-button");
        backButton = find("#back-button");
        errorLabel = find("#error-label");

        reset(restClient);

    }

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be updates
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

    /**
     * Check that all necessary node
     */
    @Test
    public void testAllNodesExist() {

        verifyThat("#username-field", isNotNull());
        verifyThat("#first-name-field", isNotNull());
        verifyThat("#middle-name-field", isNotNull());
        verifyThat("#last-name-field", isNotNull());
        verifyThat("#password-field", isNotNull());
        verifyThat("#password-check-field", isNotNull());
        verifyThat("#register-button", isNotNull());
        verifyThat("#back-button", isNotNull());

    }

    /**
     * Test all possible attemptCreateUser() scenarios that don't throw exceptions
     */
    @Test
    public void testAttemptCreateUser() throws JsonProcessingException, InterruptedException {

        // Check empty field scenarios
        usernameField.setText("");
        firstNameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        passwordCheckField.setText("");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        assertEquals("Invalid registration information", errorLabel.getText());
        assertFalse(registerButton.isDisabled());

        reset(restClient);
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("");
        passwordCheckField.setText("");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        assertEquals("Invalid registration information", errorLabel.getText());
        assertFalse(registerButton.isDisabled());


        reset(restClient);
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        assertEquals("Invalid registration information", errorLabel.getText());
        assertFalse(registerButton.isDisabled());

        // Check mismatched passwords
        reset(restClient);
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty-but-different");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        assertEquals("Password fields don't match", errorLabel.getText());
        assertFalse(registerButton.isDisabled());


        // Check valid creation
        reset(restClient);
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        userRegistrationController.runRegistration(0);
        verify(restClient, times(1)).createUser(any());
        waitForRunLater();
        assertEquals("Registration successful! Logging in...", errorLabel.getText());
        assertTrue(registerButton.isDisabled());

        reset(restClient);
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        userRegistrationController.runRegistration(0);
        verify(restClient, times(1)).createUser(any());
        waitForRunLater();
        assertEquals("Registration successful! Logging in...", errorLabel.getText());
        assertTrue(registerButton.isDisabled());

    }

    /**
     * Test error handling for web application exceptions
     * @throws JsonProcessingException if Json processing fails
     * @throws InterruptedException if interrupted while waiting for JavaFx
     */
    @Test
    public void testWebApplicationException() throws JsonProcessingException, InterruptedException {

        when(restClient.createUser(any())).thenThrow(new WebApplicationException());
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        verify(restClient, times(0)).setupCredentials(any(), any());
        assertEquals("Server error, try again", errorLabel.getText());
        assertFalse(registerButton.isDisabled());

        reset(restClient);
        when(restClient.createUser(any())).thenThrow(new WebApplicationException(409));
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        verify(restClient, times(0)).setupCredentials(any(), any());
        assertEquals("Username already exists", errorLabel.getText());
        assertFalse(registerButton.isDisabled());

    }

    /**
     * Test error handling for processing exceptions
     * @throws JsonProcessingException if Json processing fails
     * @throws InterruptedException if interrupted while waiting for JavaFx
     */
    @Test
    public void testProcessingException() throws JsonProcessingException, InterruptedException {

        reset(restClient);
        when(restClient.createUser(any())).thenThrow(new ProcessingException("ConnectException"));
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        verify(restClient, times(4)).createUser(any());
        assertEquals("Connection error, try again", errorLabel.getText());
        assertFalse(registerButton.isDisabled());

    }

    /**
     * Test error handling for Json processing exceptions
     * @throws JsonProcessingException if Json processing fails
     * @throws InterruptedException if interrupted while waiting for JavaFx
     */
    @Test
    public void JsonProcessingException() throws JsonProcessingException, InterruptedException {

        reset(restClient);
        when(restClient.createUser(any())).thenThrow(new JsonGenerationException(""));
        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        userRegistrationController.runRegistration(0);
        waitForRunLater();
        verify(restClient, never()).setupCredentials(any(), any());
        assertEquals("Error processing Json", errorLabel.getText());
        assertFalse(registerButton.isDisabled());

    }

    /**
     * Test all possible scenarios of loginNewUser()
     */
    @Test
    public void testLoginNewUser() throws InterruptedException {

        // Check standard usage
        userRegistrationController.runLogin("leggy", "duck", 0);
        verify(restClient, timeout(1000).times(1)).setupCredentials(eq("leggy"), eq("duck"));

        // Check correct handling of exceptions
        reset(restClient);
        doThrow(new ProcessingException("ConnectException")).when(restClient).setupCredentials(eq("leggy"), eq("duck"));
        userRegistrationController.runLogin("leggy", "duck", 0);
        waitForRunLater();
        verify(restClient, timeout(1000).times(4)).setupCredentials(eq("leggy"), eq("duck"));

    }

    /**
     * Test the back button
     */
    @Test
    public void testBackButton() throws InterruptedException {

        backButton.fire();
        waitForRunLater();
        verify(interfaceManager, timeout(1000)).loadSegmentImmediate(eq(InterfaceSegmentType.LOGIN_SCREEN), any(Stage.class), eq("login"));

    }

    /**
     * Test the register button
     */
    @Test
    public void testRegisterButton() throws JsonProcessingException, InterruptedException {

        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        registerButton.fire();
        waitForRunLater();
        verify(restClient, timeout(1000).times(1)).createUser(any());

    }

    /**
     * Test logging in using the enter key
     */
    @Test
    public void testEnterKeyPress() throws JsonProcessingException, InterruptedException {

        // Create key press event
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED,
                "ENTER", "", KeyCode.ENTER, false, false, false, false);

        usernameField.setText("not-empty");
        firstNameField.setText("not-empty");
        middleNameField.setText("not-empty");
        lastNameField.setText("not-empty");
        passwordField.setText("not-empty");
        passwordCheckField.setText("not-empty");
        usernameField.fireEvent(keyEvent);
        waitForRunLater();
        verify(restClient, timeout(1000).times(1)).createUser(any());

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
