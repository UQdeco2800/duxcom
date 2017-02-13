package uq.deco2800.duxcom.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.interfaces.UserRegistrationInterface;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;
import uq.deco2800.singularity.common.representations.User;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * UserRegistrationController is the controller used for the game's user registration screen. It
 * controls the user interaction with the registration screen and processes all requests made by the
 * user. The two main functions of the registration screen are attempting to register a user profile
 * on singularity, and requesting to return to the login screen.
 *
 * This class is initialized through the fxml file used for the user registration screen. The fxml
 * file is initially called from UserRegistrationInterface.
 *
 * @author Alex McLean
 * @see UserRegistrationInterface
 */
public class UserRegistrationController implements Initializable {

    // Initiating the class logger
    private static Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    // Declaring the class instances to interact with
    private InterfaceManager interfaceManager;
    private Stage stage;
    private SingularityRestClient restClient;

    // Declaring the fx objects that will be modified
    @FXML
    TextField usernameField;
    @FXML
    TextField firstNameField;
    @FXML
    TextField middleNameField;
    @FXML
    TextField lastNameField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField passwordCheckField;
    @FXML
    Button registerButton;
    @FXML
    Button backButton;
    @FXML
    Label errorLabel;

    /**
     * A function which sets up the button and input field handlers.
     *
     * Creates a handler which checks for the input of the ENTER key on each user key stroke.
     * Attempts to login if enter is pressed by the user whilst in an input field.
     *
     * Ties all of the buttons to the desired method on action.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if
     *                  the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object
     *                  was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<KeyEvent> keyHandler = e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.startRegistration(0);
            }
        };

        usernameField.setOnKeyPressed(keyHandler);
        firstNameField.setOnKeyPressed(keyHandler);
        middleNameField.setOnKeyPressed(keyHandler);
        lastNameField.setOnKeyPressed(keyHandler);
        passwordField.setOnKeyPressed(keyHandler);
        passwordCheckField.setOnKeyPressed(keyHandler);

        registerButton.setOnAction(e -> this.startRegistration(0));
        backButton.setOnAction(e -> this.startBackToLoginScreen());
    }

    /**
     * Creates a new thread to perform the registration process
     *
     * @param attempts The number of attempts already made at registering
     */
    private void startRegistration(int attempts) {
        Runnable registration = () -> runRegistration(attempts);

        Thread registrationThread = new Thread(registration);
        registrationThread.setDaemon(true);
        registrationThread.start();

    }

    /**
     * Creates a new thread to perform the login process
     *
     * @param username The username to login with
     * @param password The password to login with
     * @param attempts The number of attempts already made at logging in
     */
    private void startLogin(String username, String password, int attempts) {
        Runnable login = () -> runLogin(username, password, attempts);

        Thread loginThread = new Thread(login);
        loginThread.setDaemon(true);
        loginThread.start();
    }

    /**
     * Creates a new thread to perform the back to login screen process
     */
    private void startBackToLoginScreen() {
        Runnable back = this::runBackToLoginScreen;

        Thread backThread = new Thread(back);
        backThread.setDaemon(true);
        backThread.start();
    }

    /**
     * The registration process.
     * Should be run only through startRegistration in order to be run in a background thread.
     *
     * @param attempts The number of attempts already made at registration
     */
    void runRegistration(int attempts) {
        Platform.runLater(() -> registerButton.setDisable(true));
        Platform.runLater(() -> errorLabel.setText("Attempting registration"));

        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String passwordCheck = passwordCheckField.getText();

        logger.info("Attempting to register new user with username: [{}], first name: [{}], middle name: [{}], last name: [{}], password [<redacted>]",
                username, firstName, middleName, lastName);

        if (!validFields(username, firstName, lastName, password, passwordCheck)) {
            return;
        }

        User newUser = new User(username, firstName, middleName, lastName, password);
        try {
            restClient.createUser(newUser);
            logger.info("New user registration successful, attempting to login as new user");
            Platform.runLater(() -> errorLabel.setText("Registration successful! Logging in..."));
            startLogin(username, password, 0);
        } catch (JsonProcessingException exception) {
            processJsonProcessingException(exception);
        } catch (WebApplicationException exception) {
            processWebApplicationException(exception);
        } catch (ProcessingException exception) {
            processProcessingException(exception, attempts);
        }
    }

    private void processProcessingException(ProcessingException exception, int attempts) {
        if (attempts < 3) {
            logger.error("Unable to connect to server, trying again (attempts: {})", attempts + 1, exception);
            runRegistration(attempts + 1);
        } else {
            logger.error("Unable to connect to server on 4 attempts", exception);
            Platform.runLater(() -> {
                errorLabel.setText("Connection error, try again");
                registerButton.setDisable(false);
            });
        }
    }

    private void processJsonProcessingException(JsonProcessingException exception) {
        logger.error("Error processing Json", exception);
        Platform.runLater(() -> {
            errorLabel.setText("Error processing Json");
            registerButton.setDisable(false);
        });
    }

    private void processWebApplicationException(WebApplicationException exception) {
        if (exception.getResponse().getStatus() == 409) {
            logger.error("Username already exists", exception);
            Platform.runLater(() -> errorLabel.setText("Username already exists"));
        } else {
            logger.error("Server error", exception);
            Platform.runLater(() -> errorLabel.setText("Server error, try again"));
        }
        Platform.runLater(() -> registerButton.setDisable(false));
    }

    private boolean validFields(String username, String firstName, String lastName, String password, String passwordCheck) {
        // Check if any field other than middle name is empty
        if (("").equals(password) || ("").equals(passwordCheck)) {
            logger.error("One or more required registration fields is empty");
            Platform.runLater(() -> {
                errorLabel.setText("Invalid registration information");
                registerButton.setDisable(false);
            });
            return false;
        }

        // Check if any field other than middle name is empty
        if (("").equals(username) || ("").equals(firstName) || ("").equals(lastName)) {
            logger.error("One or more required registration fields is empty");
            Platform.runLater(() -> {
                errorLabel.setText("Invalid registration information");
                registerButton.setDisable(false);
            });
            return false;
        }


        // Check if input password fields match
        if (!password.equals(passwordCheck)) {
            logger.error("Password fields don't match");
            Platform.runLater(() -> {
                errorLabel.setText("Password fields don't match");
                registerButton.setDisable(false);
            });
            return false;
        }

        return true;
    }

    /**
     * The login process.
     * Should be run only through startLogin in order to be run in a background thread.
     *
     * @param attempts The number of attempts already made at logging in
     */
    void runLogin(String username, String password, int attempts) {

        Timeline unsuccessfulLogin = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> Platform.runLater(() ->
                        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOGIN_SCREEN, stage, "registered")
                )));

        Timeline successfulLogin = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> Platform.runLater(() ->
                        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, stage, "load")
                )));

        try {
            restClient.setupCredentials(username, password);
            logger.info("New user successfully logged in");
            successfulLogin.play();
        } catch (ProcessingException exception) {
            if (attempts < 3) {
                logger.error("Unable to connect to server, trying again (attempts: {})", attempts + 1, exception);
                startLogin(username, password, attempts + 1);
            } else {
                logger.error("Account created, failed to login in 4 attempts", exception);
                Platform.runLater(() -> errorLabel.setText("Account created, failed to login."));
                unsuccessfulLogin.play();
            }
        }
    }

    /**
     * Changes to the login interface.
     * Should be run only through startBackToLoginScreen in order to be run in a background thread.
     */
    private void runBackToLoginScreen() {
        logger.info("Returning to login screen");
        Platform.runLater(() -> interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOGIN_SCREEN, stage, "login"));
    }

    /**
     * Sets the interfaceManager class variable such that the class is able to call InterfaceManager
     * methods.
     *
     * @param interfaceManager The interfaceManager which is controlling the Interface that called
     *                         the registration fxml file.
     */
    public void setInterfaceManager(InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    /**
     * Sets the Stage variable such that the class is able to render to the Stage.
     *
     * @param stage The Stage of the interface
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the SingularityRestClient variable such that the class is able to interact with
     * singularity.
     *
     * @param restClient The SingularityRestClient of the game instance.
     */
    public void setSingularityRestClient(SingularityRestClient restClient) {
        this.restClient = restClient;
    }
}
