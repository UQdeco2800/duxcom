package uq.deco2800.duxcom.exceptions;

/**
 * Exception to be thrown if startGame() is called in DuxComController whilst a game is already running.
 * @author Alex McLean
 */
public class DuplicateStartGameException extends RuntimeException {

    public DuplicateStartGameException() {
        // Creates the exception
    }

}
