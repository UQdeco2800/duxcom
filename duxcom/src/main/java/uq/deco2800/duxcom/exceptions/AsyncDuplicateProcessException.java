package uq.deco2800.duxcom.exceptions;

/**
 * Exception to be thrown when an async process that is already running is initialized again.
 * @author Alex McLean
 */
public class AsyncDuplicateProcessException extends RuntimeException {

    public AsyncDuplicateProcessException() {
        // Creates the exception
    }

}
