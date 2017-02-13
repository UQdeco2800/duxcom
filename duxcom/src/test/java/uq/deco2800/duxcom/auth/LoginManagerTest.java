package uq.deco2800.duxcom.auth;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the login manager.
 *
 * Created by liamdm on 9/10/2016.
 */
public class LoginManagerTest {
    @Test
    public void store() throws Exception {
        LoginManager.store("test1","test2");

        // if no error, store succeeded
    }

    @Test
    public void getUsername() throws Exception {
        LoginManager.store("asdfg", "test");

        assertEquals("asdfg", LoginManager.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        LoginManager.store("test", "asdfg");

        assertEquals("asdfg", LoginManager.getPassword());
    }

    @Test
    public void initialised() throws Exception {
        LoginManager.store(null, null);

        assertFalse(LoginManager.initialised());

        LoginManager.store("a", "b");

        assertTrue(LoginManager.initialised());
    }

}