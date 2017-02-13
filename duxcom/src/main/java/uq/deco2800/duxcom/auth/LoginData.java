package uq.deco2800.duxcom.auth;

/**
 * An imutable class containing login data.
 *
 * Created by liamdm on 9/10/2016.
 */
public class LoginData {
    private final String username;
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginData(String username, String password){
        this.username = username;
        this.password = password;
    }
}
