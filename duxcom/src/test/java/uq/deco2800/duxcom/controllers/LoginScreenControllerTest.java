package uq.deco2800.duxcom.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.*;
import static org.loadui.testfx.GuiTest.find;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.setupStage;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import static uq.deco2800.duxcom.controllers.LoginScreenController.USERNAME_FILE;

/**
 * Tests the LoginScreenController class by pushing all its buttons.
 * Mockito mocks all interfaceManager calls.
 *
 * @author Alex McLean
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class LoginScreenControllerTest extends ApplicationTest {

    // Declares the JavFX nodes to be tested
    private LoginScreenController loginScreenController;
    private static TextField usernameField;
    private static PasswordField passwordField;
    private static CheckBox rememberUserCheckbox;
    private static Button loginButton;
    private static Button registerButton;
    private static Button loginSkipButton;
    private static Label errorLabel;

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

        URL location = getClass().getResource("/ui/fxml/loginScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);

        Parent root = fxmlLoader.load(location.openStream());
        loginScreenController = fxmlLoader.getController();
        loginScreenController.setInterfaceManager(interfaceManager);
        loginScreenController.setStage(stage);
        loginScreenController.setSingularityRestClient(restClient);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Ensures the remembered username is empty
     */
    @BeforeClass
    public static void emptyUsernameFile() throws IOException {
        deleteIfExists(USERNAME_FILE.toPath());
    }

    /**
     * Maps the JavaFX nodes to the declared variables before each test
     */
    @Before
    public void setUp() {

        usernameField = find("#username-field");
        passwordField = find("#password-field");
        rememberUserCheckbox = find("#remember-user-checkbox");
        loginButton = find("#login-button");
        registerButton = find("#register-button");
        loginSkipButton = find("#login-skip-button");
        errorLabel = find("#error-label");

        reset(restClient);

    }

    /**
     * Creates a micro that allows for tests to wait for all JavaFX nodes to be updates
     *
     * @throws InterruptedException if interrupted whilst waiting
     */
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

    /**
     * Check that all necessary nodes exist
     */
    @Test
    public void testAllEntryNodesExist() {

        verifyThat("#username-field", isNotNull());
        verifyThat("#password-field", isNotNull());
        verifyThat("#remember-user-checkbox", isNotNull());
        verifyThat("#login-button", isNotNull());
        verifyThat("#register-button", isNotNull());
        verifyThat("#login-skip-button", isNotNull());

    }

    /**
     * Test all possible runLogin() scenarios
     */
    @Test
    public void testAttemptLogin() throws InterruptedException {

        // Check empty field scenarios
        reset(restClient);
        loginScreenController.runLogin("", "", 0);
        waitForRunLater();
        assertEquals("Invalid login information", errorLabel.getText());
        assertFalse(loginButton.isDisabled());

        reset(restClient);
        loginScreenController.runLogin("", "duck", 0);
        waitForRunLater();
        assertEquals("Invalid login information", errorLabel.getText());
        assertFalse(loginButton.isDisabled());

        reset(restClient);
        loginScreenController.runLogin("leggy", "", 0);
        waitForRunLater();
        assertEquals("Invalid login information", errorLabel.getText());
        assertFalse(loginButton.isDisabled());

        // Check valid login
        reset(restClient);
        loginScreenController.runLogin("leggy", "duck", 0);
        waitForRunLater();
        verify(restClient, times(1)).setupCredentials(eq("leggy"), eq("duck"));
        verify(interfaceManager).loadSegmentImmediate(eq(InterfaceSegmentType.LOAD_SCREEN), any(Stage.class), eq("load"));

    }
    /**
     * Test error handling for web application exceptions
     */
    @Test
    public void testWebApplicationException() throws InterruptedException {

        // Check correct handling of exceptions
        reset(restClient);
        doThrow(new WebApplicationException()).when(restClient).setupCredentials(any(), any());
        loginScreenController.runLogin("throw", "WebApplicationException", 0);
        waitForRunLater();
        verify(restClient, times(1)).setupCredentials(eq("throw"), eq("WebApplicationException"));
        assertEquals("Server error, try again", errorLabel.getText());
        assertFalse(loginButton.isDisabled());

        reset(restClient);
        doThrow(new WebApplicationException(403)).when(restClient).setupCredentials(any(), any());
        loginScreenController.runLogin("throw", "WebApplicationException", 0);
        waitForRunLater();
        verify(restClient, times(1)).setupCredentials(eq("throw"), eq("WebApplicationException"));
        assertEquals("Username or password is incorrect", errorLabel.getText());
        assertFalse(loginButton.isDisabled());

    }
    /**
     * Test error handling for processing exceptions
     */
    @Test
    public void testProcessingException() throws InterruptedException {

        reset(restClient);
        doThrow(new ProcessingException("ConnectException")).when(restClient).setupCredentials(any(), any());
        loginScreenController.runLogin("throw", "ConnectException", 0);
        waitForRunLater();
        verify(restClient, times(4)).setupCredentials(eq("throw"), eq("ConnectException"));
        assertEquals("Connection error, try again", errorLabel.getText());
        assertFalse(loginButton.isDisabled());

    }

    /**
     * Test the user registration button
     */
    @Test
    public void testUserRegistrationButton() throws InterruptedException {

        registerButton.fire();
        waitForRunLater();
        verify(interfaceManager, timeout(1000)).loadSegmentImmediate(eq(InterfaceSegmentType.USER_REGISTRATION), any(Stage.class), eq("create user"));

    }

    /**
     * Test the login button
     */
    @Test
    public void testLoginButton() throws InterruptedException {

        reset(restClient);
        usernameField.setText("leggy");
        passwordField.setText("duck");
        loginButton.fire();
        waitForRunLater();
        verify(restClient, timeout(1000).times(1)).setupCredentials(eq("leggy"), eq("duck"));

    }

    /**
     * Test the lazy login button
     */
    @Test
    public void testLazyLoginButton() throws InterruptedException {

        reset(restClient);
        loginSkipButton.fire();
        waitForRunLater();
        verify(restClient, timeout(1000).times(1)).setupCredentials(eq("leggy"), eq("duck"));

    }

    /**
     * Test logging in using the enter key
     */
    @Test
    public void testEnterKeyPress() throws InterruptedException {

        // Create key press event
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED,
                "ENTER", "", KeyCode.ENTER, false, false, false, false);

        reset(restClient);
        usernameField.setText("leggy");
        passwordField.setText("duck");
        usernameField.fireEvent(keyEvent);
        waitForRunLater();
        verify(restClient, timeout(1000).times(1)).setupCredentials(eq("leggy"), eq("duck"));

    }

    /**
     * Test the remember username functionality of the interface
     */
    @Test
    public void testRememberUsername() throws InterruptedException, IOException {

        FileReader usernameReader;
        BufferedReader bufferedUsername;
        String rememberedUsername;

        reset(restClient);
        usernameField.setText("hello");
        passwordField.setText("duck");
        rememberUserCheckbox.setSelected(true);
        loginButton.fire();
        waitForRunLater();
        verify(restClient, timeout(1000).times(1)).setupCredentials(eq("hello"), eq("duck"));

        waitForRunLater();
        usernameReader = new FileReader(USERNAME_FILE);
        bufferedUsername = new BufferedReader(usernameReader);
        rememberedUsername = bufferedUsername.readLine();
        bufferedUsername.close();
        usernameReader.close();
        assertTrue(("hello").equals(rememberedUsername));

        loginScreenController.rememberUserName("chicken");
        waitForRunLater();
        usernameReader = new FileReader(USERNAME_FILE);
        bufferedUsername = new BufferedReader(usernameReader);
        rememberedUsername = bufferedUsername.readLine();
        bufferedUsername.close();
        usernameReader.close();
        assertTrue(("chicken").equals(rememberedUsername));

        loginScreenController.checkUsernameFile();
        waitForRunLater();

        deleteIfExists(USERNAME_FILE.toPath());

    }

    /**
     * Closes JavaFx application
     *
     * @throws TimeoutException If closing stage times out
     */
    @AfterClass
    public static void cleanUp() throws TimeoutException, IOException {
        setupStage(Stage::close);
    }

}
