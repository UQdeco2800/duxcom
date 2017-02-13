package uq.deco2800.duxcom.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.achievements.AchievementManager;
import uq.deco2800.duxcom.achievements.AchievementStatistics;
import uq.deco2800.duxcom.auth.LoginManager;
import uq.deco2800.duxcom.interfaces.InterfaceManager;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.interfaces.LoginScreenInterface;
import uq.deco2800.singularity.clients.restful.SingularityRestClient;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * LoginScreenController is the controller used for the game's login screen. It controls the user
 * interaction with the login screen and processes all requests made by the user. The two main
 * functions of the login screen are attempting to login and requesting the user registration
 * interface.
 *
 * Currently the class also includes a lazy login method which will allow the user to login using
 * set credentials.
 *
 * This class is initialized through the fxml file used for the login screen. The fxml file is
 * initially called from LoginScreenInterface.
 *
 * @author Alex McLean
 * @see LoginScreenInterface
 */
public class LoginScreenController implements Initializable {

    // Initiating the class logger
    private static Logger logger = LoggerFactory.getLogger(LoginScreenController.class);

    // Declaring the class instances to interact with
    private InterfaceManager interfaceManager;
    private Stage stage;
    private SingularityRestClient restClient;

    // Initialising variables used for remembering a username
    private String currentUsernameString = "";
    private static final String PATH = "" + System.getProperty("user.dir") + File.separator + "src" + File.separator
            + File.separator + "main" + File.separator + "resources" + File.separator + "settings";
    static final File USERNAME_FILE = new File(PATH, "username.txt");

    // Declaring the fx objects that will be modified
    @FXML
    TextField userField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Button loginSkipButton;
    @FXML
    CheckBox rememberUserCheckBox;
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
                this.startLogin();
            }
        };

        userField.setOnKeyPressed(keyHandler);
        passwordField.setOnKeyPressed(keyHandler);

        loginButton.setOnAction(e -> this.startLogin());
        registerButton.setOnAction(e -> this.startToUserRegistration());
        loginSkipButton.setOnAction(e -> this.startLazyLogin());

        checkUsernameFile();

    }

    /**
     * Checks for the existence of a remembered username file
     */
    void checkUsernameFile() {
        if (USERNAME_FILE.exists()) {
            String fileError = "Failed to read username file.";
            Platform.runLater(() -> {
                try {
                    userField.setText(readUsernameFile());
                } catch (IOException exception) {
                    logger.error(fileError, exception);
                    errorLabel.setText(fileError);
                }
            });
            Platform.runLater(() -> {
                passwordField.requestFocus();
                rememberUserCheckBox.setSelected(true);
            });
        }
    }

    /**
     * Creates a new thread to perform the login process
     */
    private void startLogin() {
        LoginManager.store(userField.getText(), passwordField.getText());
        Runnable login = () -> runLogin(userField.getText(), passwordField.getText(), 0);

        Thread loginThread = new Thread(login);
        loginThread.setDaemon(true);
        loginThread.start();

    }

    /**
     * Creates a new thread to perform the lazy login process
     */
    private void startLazyLogin() {
        Runnable login = () -> runLogin("leggy", "duck", 0);

        Thread loginThread = new Thread(login);
        loginThread.setDaemon(true);
        loginThread.start();

    }

    /**
     * Creates a new thread to perform the back to login screen process
     */
    private void startToUserRegistration() {
        Runnable toRegistration = this::runToUserRegistration;

        Thread toRegistrationThread = new Thread(toRegistration);
        toRegistrationThread.setDaemon(true);
        toRegistrationThread.start();
    }

    /**
     * The login process.
     * Should be run only through startLogin in order to be run in a background thread.
     *
     * @param attempts The number of attempts already made at logging in
     */
    void runLogin(String username, String password, int attempts) {
        if(attempts == 3){
            // force login if leggy
            if("leggy".equals(username)){
                LoginManager.store(username, password);
                logger.info("Forcing offline login.");
                Platform.runLater(() ->
                        interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, stage, "load"));
            }
        }

        logger.info("Attempting to login with username: [{}], password: [<redacted>]", username);

        LoginManager.store(username, password);

        Platform.runLater(() -> loginButton.setDisable(true));
        Platform.runLater(() -> loginSkipButton.setDisable(true));
        Platform.runLater(() -> errorLabel.setText("Attempting login"));

        // Check if an input field is empty
        if (("").equals(password) || ("").equals(username)) {
            logger.error("One or more required login fields is empty");
            Platform.runLater(() -> {
                errorLabel.setText("Invalid login information");
                loginButton.setDisable(false);
                loginSkipButton.setDisable(false);
            });
            return;
        }
        try {
            restClient.setupCredentials(username, password);
            logger.info("User successfully logged in");

            // store
            LoginManager.store(username, password);

            if (rememberUserCheckBox.isSelected()) {
                logger.info("Remembering username for next time");
                rememberUserName(username);
            }
            logger.info("Going to load screen");
            AchievementManager.setUsername(username);
            AchievementManager.loadAccountAchievements();
            AchievementStatistics.load();
            AchievementStatistics.addLogins(1);
            AchievementStatistics.save();

            Platform.runLater(() ->
                    interfaceManager.loadSegmentImmediate(InterfaceSegmentType.LOAD_SCREEN, stage, "load"));
        } catch (WebApplicationException exception) {
            if (exception.getResponse().getStatus() == 403) {
                logger.error("Username or password is incorrect", exception);
                Platform.runLater(() -> errorLabel.setText("Username or password is incorrect"));
            } else {
                logger.error("Server error", exception);
                Platform.runLater(() -> errorLabel.setText("Server error, try again"));
            }
            Platform.runLater(() -> {
                loginButton.setDisable(false);
                loginSkipButton.setDisable(false);
            });
        } catch (ProcessingException exception) {
            if (attempts < 3) {
                logger.error("Unable to connect to server, trying again (attempts: {})", attempts + 1, exception);
                runLogin(username, password, attempts + 1);
            } else {
                logger.error("Unable to connect to server on 4 attempts", exception);
                Platform.runLater(() -> {
                    errorLabel.setText("Connection error, try again");
                    loginButton.setDisable(false);
                    loginSkipButton.setDisable(false);
                });
            }
        }
    }

    /**
     * Changes to the user registration interface
     * Should be run only through startToUserRegistration in order to be run in a background thread.
     */
    private void runToUserRegistration() {
        logger.info("Going to user registration screen");
        Platform.runLater(() -> interfaceManager.loadSegmentImmediate(InterfaceSegmentType.USER_REGISTRATION, stage, "create user"));
    }

    /**
     * Sets up the ability to remember the username locally in a .txt file
     *
     * @param username The username to be remembered
     */
    void rememberUserName(String username) {
        try {
            if (USERNAME_FILE.exists()) {
                currentUsernameString = readUsernameFile();
            }
            if (!currentUsernameString.equals(username)) {
                FileWriter fw = new FileWriter(USERNAME_FILE);
                fw.write(username);
                fw.flush();
                fw.close();
            }
        } catch (FileNotFoundException exception) {
            logger.error("Could not find username file", exception);
        } catch (IOException exception) {
            logger.error("Error saving username", exception);
        }
    }

    /**
     * Reads a remembered username from a .txt file and returns the username as a string
     *
     * @return The remembered username
     * @throws IOException Thrown when file cannot be read from
     */
    private String readUsernameFile() throws IOException {
        FileReader usernameReader = new FileReader(USERNAME_FILE);
        BufferedReader bufferedUsername = new BufferedReader(usernameReader);
        currentUsernameString = bufferedUsername.readLine();
        bufferedUsername.close();
        usernameReader.close();
        return currentUsernameString;
    }

    /**
     * Sets the interfaceManager class variable such that the class is able to call InterfaceManager
     * methods.
     *
     * @param interfaceManager The interfaceManager which is controlling the Interface that called
     *                         the login screen fxml file.
     */
    public void setInterfaceManager(InterfaceManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    /**
     * Sets the Stage variable such that the class is able to render to the Stage.
     *
     * @param stage The Stage which the interfaces are rendered on.
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
