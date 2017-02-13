package uq.deco2800.duxcom.auth;

import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.annotation.UtilityConstructor;

/**
 * Stores login details (somewhat securely)
 *
 * Created by liamdm on 8/10/2016.
 */
public class LoginManager {
    /**
     * The stored password
     */
    private static String password = null;

    /**
     * The stored username
     */
    private static String username = null;

    /**
     * Stores the login credentials
     * @param username the username
     * @param password the password
     */
    @MethodContract(precondition = {"username != null", "password != null"})
    public static void store(String username, String password){
        LoginManager.username = username;
        LoginManager.password = password;
    }

    /**
     * Gets the stored username
     * @return the stored username
     */
    @MethodContract(precondition = {"initialised()"})
    public static String getUsername(){
        return LoginManager.username;
    }

    /**
     * Returns the stored password
     * @return the stored password
     */
    @MethodContract(precondition = {"initialised()"})
    public static String getPassword(){
        return LoginManager.password;
    }

    /**
     * Returns true if stored
     * @return true if stored
     */
    public static boolean initialised(){
        return username != null;
    }

    @UtilityConstructor
    private LoginManager(){}

    /**
     * Returns the login data pair
     * @return the login data pair
     */
    public static LoginData getData(){
        return new LoginData(username,password);
    }

}
